<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Admin;
use App\Entity\Artiste;
use App\Entity\NormalUser;
use App\Entity\Sponsor;
use App\Entity\User;
use App\Enum\AccountStatus;
use App\Form\ForgotPasswordRequestType;
use App\Form\GoogleSignupCompleteType;
use App\Form\ResetPasswordType;
use App\Form\UserType;
use App\Service\AvatarService;
use Doctrine\ORM\EntityManagerInterface;
use InvalidArgumentException;
use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\SecurityBundle\Security;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

final class SecurityController extends AbstractController
{
    #[Route('/connect/google', name: 'app_connect_google_start', methods: ['GET'])]
    public function connectGoogleStart(ClientRegistry $clientRegistry): RedirectResponse
    {
        return $clientRegistry
            ->getClient('google_main')
            ->redirect(['openid', 'profile', 'email'], []);
    }

    #[Route('/connect/google/check', name: 'app_connect_google_check', methods: ['GET'])]
    public function connectGoogleCheck(
        Request $request,
        ClientRegistry $clientRegistry,
        EntityManagerInterface $entityManager,
        Security $security
    ): Response {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        try {
            $client = $clientRegistry->getClient('google_main');
            $accessToken = $client->getAccessToken();
            $googleUser = $client->fetchUserFromToken($accessToken);
        } catch (\Throwable) {
            $this->addFlash('danger', 'Google sign-in failed. Please try again.');

            return $this->redirectToRoute('app_login');
        }

        $googleData = (array) $googleUser->toArray();
        $email = trim((string) ($googleData['email'] ?? ''));
        if ('' === $email) {
            $this->addFlash('danger', 'Google account did not provide an email address.');

            return $this->redirectToRoute('app_login');
        }

        $existingUser = $entityManager->getRepository(User::class)->findOneBy(['email' => $email]);
        if ($existingUser instanceof User) {
            try {
                $security->login($existingUser, null, 'main');
            } catch (\Throwable) {
                $this->addFlash('danger', 'This account cannot sign in yet. Please check account status.');

                return $this->redirectToRoute('app_login');
            }
            $this->addFlash('success', 'Signed in with Google.');

            return $this->redirectToRoute('front_home');
        }

        $request->getSession()->set('google_signup_pending', [
            'email' => $email,
            'name' => trim((string) ($googleData['name'] ?? '')),
            'avatar' => trim((string) ($googleData['picture'] ?? '')),
        ]);

        return $this->redirectToRoute('app_google_signup_complete');
    }

    #[Route('/connect/google/complete-signup', name: 'app_google_signup_complete', methods: ['GET', 'POST'])]
    public function googleSignupComplete(
        Request $request,
        EntityManagerInterface $entityManager,
        AvatarService $avatarService,
        UserPasswordHasherInterface $passwordHasher,
        Security $security
    ): Response {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        $pending = $request->getSession()->get('google_signup_pending');
        if (!is_array($pending) || !isset($pending['email'])) {
            $this->addFlash('danger', 'Google sign-up session expired. Please try again.');

            return $this->redirectToRoute('app_register');
        }

        $email = trim((string) $pending['email']);
        if ('' === $email) {
            $this->addFlash('danger', 'Google sign-up session is invalid. Please try again.');

            return $this->redirectToRoute('app_register');
        }

        $existingUser = $entityManager->getRepository(User::class)->findOneBy(['email' => $email]);
        if ($existingUser instanceof User) {
            $request->getSession()->remove('google_signup_pending');
            try {
                $security->login($existingUser, null, 'main');
            } catch (\Throwable) {
                $this->addFlash('danger', 'This account cannot sign in yet. Please check account status.');

                return $this->redirectToRoute('app_login');
            }

            return $this->redirectToRoute('front_home');
        }

        $newUser = (new NormalUser())
            ->setEmail($email)
            ->setRoles(['ROLE_USER'])
            ->setStatus(AccountStatus::ACTIVE);

        $suggestedUsername = trim((string) ($pending['name'] ?? ''));
        if ('' !== $suggestedUsername) {
            $newUser->setUsername($suggestedUsername);
        }

        $googleAvatar = trim((string) ($pending['avatar'] ?? ''));
        if ('' !== $googleAvatar && strlen($googleAvatar) <= 255 && str_starts_with($googleAvatar, 'http')) {
            $newUser->setAvatarUrl($googleAvatar);
        }

        $form = $this->createForm(GoogleSignupCompleteType::class, $newUser);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if (null === $newUser->getAvatarUrl() || '' === trim((string) $newUser->getAvatarUrl())) {
                $starterAvatar = $avatarService->pickRandomPresetAvatar();
                if (null !== $starterAvatar) {
                    $newUser->setAvatarUrl($starterAvatar);
                }
            }

            $randomPassword = bin2hex(random_bytes(24));
            $newUser->setPassword($passwordHasher->hashPassword($newUser, $randomPassword));
            $newUser->setEmailVerificationToken(null);
            $newUser->setEmailVerificationTokenExpiresAt(null);

            $entityManager->persist($newUser);
            $entityManager->flush();

            $request->getSession()->remove('google_signup_pending');
            $security->login($newUser, null, 'main');
            $this->addFlash('success', 'Account created with Google.');

            return $this->redirectToRoute('front_home');
        }

        return $this->render('security/google_complete_signup.html.twig', [
            'form' => $form->createView(),
            'google_email' => $email,
        ]);
    }

    #[Route('/login', name: 'app_login', methods: ['GET', 'POST'])]
    public function login(AuthenticationUtils $authenticationUtils): Response
    {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/login.html.twig', [
            'last_username' => $lastUsername,
            'error' => $error,
        ]);
    }

    #[Route('/resend-verification', name: 'app_resend_verification', methods: ['POST'])]
    public function resendVerificationEmail(
        Request $request,
        EntityManagerInterface $entityManager,
        MailerInterface $mailer
    ): Response {
        $email = trim((string) $request->request->get('email', ''));
        $csrfToken = (string) $request->request->get('_csrf_token', '');

        if (!$this->isCsrfTokenValid('resend_verification', $csrfToken)) {
            $this->addFlash('danger', 'Invalid request. Please try again.');

            return $this->redirectToRoute('app_login');
        }

        if ($email === '') {
            $this->addFlash('danger', 'Please provide a valid email address.');

            return $this->redirectToRoute('app_login');
        }

        $user = $entityManager->getRepository(User::class)->findOneBy(['email' => $email]);

        if ($user instanceof User && $user->getStatus() === AccountStatus::PENDING) {
            $verificationToken = bin2hex(random_bytes(32));
            $user->setEmailVerificationToken($verificationToken);
            $user->setEmailVerificationTokenExpiresAt(new \DateTimeImmutable('+24 hours'));

            $entityManager->persist($user);
            $entityManager->flush();

            $verificationUrl = $this->generateUrl('app_verify_email', ['token' => $verificationToken], UrlGeneratorInterface::ABSOLUTE_URL);
            $fromAddress = $_ENV['MAILER_FROM_ADDRESS'] ?? 'no-reply@example.com';

            $message = (new Email())
                ->from($fromAddress)
                ->to((string) $user->getEmail())
                ->subject('Verify your account')
                ->text("Your account is pending verification.\n\nClick this link to verify your email:\n".$verificationUrl."\n\nThis link expires in 24 hours.");

            try {
                $mailer->send($message);
            } catch (TransportExceptionInterface) {
                $this->addFlash('danger', 'Could not resend verification email right now. Please try again.');

                return $this->redirectToRoute('app_login');
            }
        }

        $this->addFlash('success', 'If your account is pending, a new verification email has been sent.');

        return $this->redirectToRoute('app_login');
    }

    #[Route('/register', name: 'app_register', methods: ['GET'])]
    public function registerChoice(): Response
    {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        return $this->render('security/register_choice.html.twig');
    }

    #[Route('/register/{role}', name: 'app_register_role', methods: ['GET', 'POST'], requirements: ['role' => 'admin|artiste|sponsor|normal'])]
    public function register(
        string $role,
        Request $request,
        UserPasswordHasherInterface $passwordHasher,
        EntityManagerInterface $entityManager,
        MailerInterface $mailer,
        AvatarService $avatarService
    ): Response
    {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        $user = $this->createUserByRole($role);
        $form = $this->createForm(UserType::class, $user, [
            'registration_role' => $role,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Encode password
            $plainPassword = $form->get('plainPassword')->getData();
            $hashedPassword = $passwordHasher->hashPassword($user, $plainPassword);
            $user->setPassword($hashedPassword);
            $user->setStatus(AccountStatus::PENDING);
            $verificationToken = bin2hex(random_bytes(32));
            $user->setEmailVerificationToken($verificationToken);
            $user->setEmailVerificationTokenExpiresAt(new \DateTimeImmutable('+24 hours'));
            if (null === $user->getAvatarUrl() || '' === trim((string) $user->getAvatarUrl())) {
                $starterAvatar = $avatarService->pickRandomPresetAvatar();
                if (null !== $starterAvatar) {
                    $user->setAvatarUrl($starterAvatar);
                }
            }

            $entityManager->persist($user);
            $entityManager->flush();

            $verificationUrl = $this->generateUrl('app_verify_email', ['token' => $verificationToken], UrlGeneratorInterface::ABSOLUTE_URL);
            $fromAddress = $_ENV['MAILER_FROM_ADDRESS'] ?? 'no-reply@example.com';

            $message = (new Email())
                ->from($fromAddress)
                ->to((string) $user->getEmail())
                ->subject('Verify your account')
                ->text("Your account has been created and is pending verification.\n\nClick this link to verify your email:\n".$verificationUrl."\n\nThis link expires in 24 hours.");

            try {
                $mailer->send($message);
            } catch (TransportExceptionInterface) {
                $this->addFlash('danger', 'Account created but verification email could not be sent right now. Please try again later.');

                return $this->redirectToRoute('app_login');
            }

            $this->addFlash('success', 'Account created successfully. Please check your email to verify your account before signing in.');

            return $this->redirectToRoute('app_login');
        }

        return $this->render('security/register.html.twig', [
            'form' => $form->createView(),
            'selected_role' => $role,
            'selected_role_label' => $this->getRoleLabel($role),
        ]);
    }

    #[Route('/logout', name: 'app_logout')]
    public function logout(): Response
    {
        throw new \LogicException('This method is intercepted by the firewall logout.');
    }

    #[Route('/forgot-password', name: 'app_forgot_password_request', methods: ['GET', 'POST'])]
    public function forgotPasswordRequest(
        Request $request,
        EntityManagerInterface $entityManager,
        MailerInterface $mailer
    ): Response {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        $form = $this->createForm(ForgotPasswordRequestType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $email = (string) $form->get('email')->getData();
            $user = $entityManager->getRepository(User::class)->findOneBy(['email' => $email]);

            if ($user instanceof User) {
                $token = bin2hex(random_bytes(32));
                $user->setResetToken($token);
                $user->setResetTokenExpiresAt(new \DateTimeImmutable('+1 hour'));

                $entityManager->persist($user);
                $entityManager->flush();

                $resetUrl = $this->generateUrl('app_reset_password', ['token' => $token], UrlGeneratorInterface::ABSOLUTE_URL);
                $fromAddress = $_ENV['MAILER_FROM_ADDRESS'] ?? 'no-reply@example.com';

                $message = (new Email())
                    ->from($fromAddress)
                    ->to((string) $user->getEmail())
                    ->subject('Reset your password')
                    ->text("We received a password reset request.\n\nUse this link to reset your password:\n".$resetUrl."\n\nThis link expires in 1 hour.");

                try {
                    $mailer->send($message);
                } catch (TransportExceptionInterface) {
                    $this->addFlash('danger', 'Could not send reset email right now. Please try again in a moment.');

                    return $this->redirectToRoute('app_forgot_password_request');
                }
            }

            $this->addFlash('success', 'If that email exists, a reset link has been sent.');

            return $this->redirectToRoute('app_login');
        }

        return $this->render('security/forgot_password_request.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/reset-password/{token}', name: 'app_reset_password', methods: ['GET', 'POST'])]
    public function resetPassword(
        string $token,
        Request $request,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ): Response {
        if ($this->getUser()) {
            return $this->redirectToRoute('front_home');
        }

        $user = $entityManager->getRepository(User::class)->findOneBy(['resetToken' => $token]);

        if (
            !$user instanceof User ||
            !$user->getResetTokenExpiresAt() instanceof \DateTimeImmutable ||
            $user->getResetTokenExpiresAt() < new \DateTimeImmutable()
        ) {
            $this->addFlash('danger', 'Invalid or expired reset link.');

            return $this->redirectToRoute('app_forgot_password_request');
        }

        $form = $this->createForm(ResetPasswordType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $plainPassword = (string) $form->get('plainPassword')->getData();
            $user->setPassword($passwordHasher->hashPassword($user, $plainPassword));
            $user->setResetToken(null);
            $user->setResetTokenExpiresAt(null);

            $entityManager->persist($user);
            $entityManager->flush();

            $this->addFlash('success', 'Password updated successfully. You can sign in now.');

            return $this->redirectToRoute('app_login');
        }

        return $this->render('security/reset_password.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/verify-email/{token}', name: 'app_verify_email', methods: ['GET'])]
    public function verifyEmail(string $token, EntityManagerInterface $entityManager): Response
    {
        $user = $entityManager->getRepository(User::class)->findOneBy(['emailVerificationToken' => $token]);

        if (
            !$user instanceof User ||
            !$user->getEmailVerificationTokenExpiresAt() instanceof \DateTimeImmutable ||
            $user->getEmailVerificationTokenExpiresAt() < new \DateTimeImmutable()
        ) {
            $this->addFlash('danger', 'Invalid or expired verification link.');

            return $this->redirectToRoute('app_login');
        }

        $user->setStatus(AccountStatus::ACTIVE);
        $user->setEmailVerificationToken(null);
        $user->setEmailVerificationTokenExpiresAt(null);

        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Email verified successfully. You can sign in now.');

        return $this->redirectToRoute('app_login');
    }

    private function createUserByRole(string $role): User
    {
        return match ($role) {
            'admin' => (new Admin())
                ->setRoles(['ROLE_ADMIN'])
                ->setSuperAdmin(false),
            'artiste' => (new Artiste())
                ->setRoles(['ROLE_ARTISTE']),
            'sponsor' => (new Sponsor())
                ->setRoles(['ROLE_SPONSOR'])
                ->setCompanyName(''),
            'normal' => (new NormalUser())
                ->setRoles(['ROLE_USER']),
            default => throw new InvalidArgumentException('Invalid role.'),
        };
    }

    private function getRoleLabel(string $role): string
    {
        return match ($role) {
            'admin' => 'Admin',
            'artiste' => 'Artiste',
            'sponsor' => 'Sponsor',
            'normal' => 'Normal User',
            default => 'User',
        };
    }
}
