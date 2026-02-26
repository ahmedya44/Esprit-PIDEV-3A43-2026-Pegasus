<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\PasskeyCredential;
use App\Entity\User;
use App\Enum\AccountStatus;
use App\Repository\PasskeyCredentialRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use lbuchs\WebAuthn\Binary\ByteBuffer;
use lbuchs\WebAuthn\WebAuthn;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\SecurityBundle\Security;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/passkey')]
final class WebAuthnController extends AbstractController
{
    #[Route('/login/options', name: 'app_passkey_login_options', methods: ['POST'])]
    public function loginOptions(
        Request $request,
        UserRepository $userRepository,
        PasskeyCredentialRepository $passkeyCredentialRepository
    ): JsonResponse {
        try {
            $payload = $request->toArray();
        } catch (\Throwable) {
            return $this->json(['error' => 'Invalid request payload.'], Response::HTTP_BAD_REQUEST);
        }

        $email = strtolower(trim((string) ($payload['email'] ?? '')));
        if ('' === $email) {
            return $this->json(['error' => 'Email is required for passkey login.'], Response::HTTP_BAD_REQUEST);
        }

        $user = $userRepository->findOneBy(['email' => $email]);
        if (!$user instanceof User || $user->getStatus() !== AccountStatus::ACTIVE) {
            return $this->json(['error' => 'No active account found for this email.'], Response::HTTP_NOT_FOUND);
        }

        $credentials = $passkeyCredentialRepository->findByUser($user);
        if ([] === $credentials) {
            return $this->json(['error' => 'No passkey is registered for this account yet.'], Response::HTTP_NOT_FOUND);
        }

        $webauthn = $this->createWebAuthn($request);
        $credentialIds = array_map(
            static fn (PasskeyCredential $credential): ByteBuffer => ByteBuffer::fromBase64Url((string) $credential->getCredentialId()),
            $credentials
        );
        $options = $webauthn->getGetArgs($credentialIds, 60, false, false, false, false, true, 'required');
        $challenge = $this->byteBufferToBase64Url($webauthn->getChallenge());

        $request->getSession()->set('passkey_login', [
            'challenge' => $challenge,
            'user_id' => $user->getId(),
            'expires_at' => time() + 300,
        ]);

        return $this->json($options);
    }

    #[Route('/login/verify', name: 'app_passkey_login_verify', methods: ['POST'])]
    public function loginVerify(
        Request $request,
        EntityManagerInterface $entityManager,
        PasskeyCredentialRepository $passkeyCredentialRepository,
        Security $security
    ): JsonResponse {
        try {
            $payload = $request->toArray();
        } catch (\Throwable) {
            return $this->json(['error' => 'Invalid request payload.'], Response::HTTP_BAD_REQUEST);
        }

        $sessionPayload = $request->getSession()->get('passkey_login');
        if (!is_array($sessionPayload)) {
            return $this->json(['error' => 'Passkey login session expired.'], Response::HTTP_BAD_REQUEST);
        }

        if ((int) ($sessionPayload['expires_at'] ?? 0) < time()) {
            $request->getSession()->remove('passkey_login');

            return $this->json(['error' => 'Passkey login session expired.'], Response::HTTP_BAD_REQUEST);
        }

        $credentialId = $this->normalizeBase64Url((string) ($payload['id'] ?? ''));
        $rawId = $this->normalizeBase64Url((string) ($payload['rawId'] ?? ''));
        $responsePayload = $payload['response'] ?? null;

        if ('' === $credentialId || !is_array($responsePayload)) {
            return $this->json(['error' => 'Invalid passkey response.'], Response::HTTP_BAD_REQUEST);
        }

        $passkey = $passkeyCredentialRepository->findOneBy([
            'credentialId' => $credentialId,
            'user' => $sessionPayload['user_id'] ?? 0,
        ]);

        if (!$passkey instanceof PasskeyCredential) {
            if ('' !== $rawId) {
                $passkey = $passkeyCredentialRepository->findOneBy([
                    'credentialId' => $rawId,
                    'user' => $sessionPayload['user_id'] ?? 0,
                ]);
            }

            if (!$passkey instanceof PasskeyCredential) {
                return $this->json(['error' => 'Passkey not recognized for this account.'], Response::HTTP_NOT_FOUND);
            }
        }

        $clientData = $this->base64UrlDecode((string) ($responsePayload['clientDataJSON'] ?? ''));
        $authenticatorData = $this->base64UrlDecode((string) ($responsePayload['authenticatorData'] ?? ''));
        $signature = $this->base64UrlDecode((string) ($responsePayload['signature'] ?? ''));
        $challenge = $this->base64UrlDecode((string) ($sessionPayload['challenge'] ?? ''));

        if (null === $clientData || null === $authenticatorData || null === $signature || null === $challenge) {
            return $this->json(['error' => 'Invalid passkey payload encoding.'], Response::HTTP_BAD_REQUEST);
        }

        try {
            $webauthn = $this->createWebAuthn($request);
            $webauthn->processGet(
                $clientData,
                $authenticatorData,
                $signature,
                (string) $passkey->getPublicKeyPem(),
                $challenge,
                $passkey->getSignCount(),
                true,
                true
            );
        } catch (\Throwable $exception) {
            return $this->json(['error' => 'Passkey verification failed: '.$exception->getMessage()], Response::HTTP_UNAUTHORIZED);
        }

        $newSignCount = $webauthn->getSignatureCounter();
        if (is_int($newSignCount)) {
            $passkey->setSignCount($newSignCount);
        }
        $passkey->setLastUsedAt(new \DateTimeImmutable());
        $entityManager->persist($passkey);
        $entityManager->flush();
        $request->getSession()->remove('passkey_login');

        try {
            $security->login($passkey->getUser(), null, 'main');
        } catch (\Throwable) {
            return $this->json(['error' => 'Passkey verified but session login failed.'], Response::HTTP_UNAUTHORIZED);
        }

        return $this->json([
            'ok' => true,
            'redirect' => $this->generateUrl('front_home'),
        ]);
    }

    #[Route('/register/options', name: 'app_passkey_register_options', methods: ['POST'])]
    #[IsGranted('ROLE_USER')]
    public function registerOptions(Request $request, PasskeyCredentialRepository $passkeyCredentialRepository): JsonResponse
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->json(['error' => 'You must be logged in.'], Response::HTTP_UNAUTHORIZED);
        }

        $credentials = $passkeyCredentialRepository->findByUser($user);
        $excludeIds = array_map(
            static fn (PasskeyCredential $credential): ByteBuffer => ByteBuffer::fromBase64Url((string) $credential->getCredentialId()),
            $credentials
        );

        $webauthn = $this->createWebAuthn($request);
        $options = $webauthn->getCreateArgs(
            (string) $user->getId(),
            (string) $user->getEmail(),
            (string) ($user->getUsername() ?: $user->getEmail()),
            60,
            false,
            'required',
            false,
            $excludeIds
        );

        $challenge = $this->byteBufferToBase64Url($webauthn->getChallenge());
        $request->getSession()->set('passkey_register', [
            'challenge' => $challenge,
            'user_id' => $user->getId(),
            'expires_at' => time() + 300,
        ]);

        return $this->json($options);
    }

    #[Route('/register/verify', name: 'app_passkey_register_verify', methods: ['POST'])]
    #[IsGranted('ROLE_USER')]
    public function registerVerify(
        Request $request,
        EntityManagerInterface $entityManager,
        PasskeyCredentialRepository $passkeyCredentialRepository
    ): JsonResponse {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->json(['error' => 'You must be logged in.'], Response::HTTP_UNAUTHORIZED);
        }

        try {
            $payload = $request->toArray();
        } catch (\Throwable) {
            return $this->json(['error' => 'Invalid request payload.'], Response::HTTP_BAD_REQUEST);
        }

        $sessionPayload = $request->getSession()->get('passkey_register');
        if (!is_array($sessionPayload)) {
            return $this->json(['error' => 'Passkey registration session expired.'], Response::HTTP_BAD_REQUEST);
        }

        if (($sessionPayload['user_id'] ?? null) !== $user->getId() || (int) ($sessionPayload['expires_at'] ?? 0) < time()) {
            $request->getSession()->remove('passkey_register');

            return $this->json(['error' => 'Passkey registration session expired.'], Response::HTTP_BAD_REQUEST);
        }

        $responsePayload = $payload['response'] ?? null;
        if (!is_array($responsePayload)) {
            return $this->json(['error' => 'Invalid passkey registration response.'], Response::HTTP_BAD_REQUEST);
        }

        $clientData = $this->base64UrlDecode((string) ($responsePayload['clientDataJSON'] ?? ''));
        $attestationObject = $this->base64UrlDecode((string) ($responsePayload['attestationObject'] ?? ''));
        $challenge = $this->base64UrlDecode((string) ($sessionPayload['challenge'] ?? ''));

        if (null === $clientData || null === $attestationObject || null === $challenge) {
            return $this->json(['error' => 'Invalid passkey payload encoding.'], Response::HTTP_BAD_REQUEST);
        }

        try {
            $webauthn = $this->createWebAuthn($request);
            $created = $webauthn->processCreate($clientData, $attestationObject, $challenge, true, true);
        } catch (\Throwable $exception) {
            return $this->json(['error' => 'Passkey registration failed: '.$exception->getMessage()], Response::HTTP_BAD_REQUEST);
        }

        if (!isset($created->credentialId, $created->credentialPublicKey)) {
            return $this->json(['error' => 'Invalid passkey registration result.'], Response::HTTP_BAD_REQUEST);
        }

        $derivedCredentialId = $created->credentialId instanceof ByteBuffer
            ? $this->byteBufferToBase64Url($created->credentialId)
            : (string) $created->credentialId;
        $rawId = $this->normalizeBase64Url((string) ($payload['rawId'] ?? ''));
        $credentialId = '' !== $rawId ? $rawId : $this->normalizeBase64Url($derivedCredentialId);

        $existing = $passkeyCredentialRepository->findOneBy(['credentialId' => $credentialId]);
        if ($existing instanceof PasskeyCredential) {
            return $this->json(['error' => 'This passkey is already registered.'], Response::HTTP_CONFLICT);
        }

        $label = trim((string) ($payload['label'] ?? ''));
        $transports = $payload['transports'] ?? null;
        $transportsList = is_array($transports)
            ? implode(',', array_filter(array_map(static fn ($v): string => trim((string) $v), $transports)))
            : null;

        $passkey = (new PasskeyCredential())
            ->setUser($user)
            ->setCredentialId($credentialId)
            ->setPublicKeyPem((string) $created->credentialPublicKey)
            ->setSignCount((int) ($created->signatureCounter ?? 0))
            ->setTransports($transportsList ?: null)
            ->setLabel('' !== $label ? $label : null);

        $entityManager->persist($passkey);
        $entityManager->flush();
        $request->getSession()->remove('passkey_register');

        return $this->json(['ok' => true]);
    }

    #[Route('/remove/{id}', name: 'app_passkey_remove', methods: ['POST'])]
    #[IsGranted('ROLE_USER')]
    public function remove(
        int $id,
        Request $request,
        EntityManagerInterface $entityManager,
        PasskeyCredentialRepository $passkeyCredentialRepository
    ): Response {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        $passkey = $passkeyCredentialRepository->find($id);
        if (!$passkey instanceof PasskeyCredential || $passkey->getUser()?->getId() !== $user->getId()) {
            $this->addFlash('danger', 'Passkey not found.');

            return $this->redirectToRoute('front_profile');
        }

        $csrfToken = (string) $request->request->get('_csrf_token', '');
        if (!$this->isCsrfTokenValid('remove_passkey_'.$passkey->getId(), $csrfToken)) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('front_profile');
        }

        $entityManager->remove($passkey);
        $entityManager->flush();
        $this->addFlash('success', 'Passkey removed.');

        return $this->redirectToRoute('front_profile');
    }

    private function createWebAuthn(Request $request): WebAuthn
    {
        $host = $request->getHost();
        if (in_array($host, ['127.0.0.1', '::1'], true)) {
            $host = 'localhost';
        }

        return new WebAuthn('Feane', $host, null, true);
    }

    private function byteBufferToBase64Url(ByteBuffer $buffer): string
    {
        return (string) $buffer->jsonSerialize();
    }

    private function base64UrlDecode(string $value): ?string
    {
        if ('' === trim($value)) {
            return null;
        }

        $decoded = base64_decode(strtr($value, '-_', '+/').str_repeat('=', (4 - strlen($value) % 4) % 4), true);

        return is_string($decoded) ? $decoded : null;
    }

    private function normalizeBase64Url(string $value): string
    {
        $value = trim($value);
        if ('' === $value) {
            return '';
        }

        $value = strtr($value, '+/', '-_');
        $value = rtrim($value, '=');

        return preg_match('/^[A-Za-z0-9\-_]+$/', $value) === 1 ? $value : '';
    }
}
