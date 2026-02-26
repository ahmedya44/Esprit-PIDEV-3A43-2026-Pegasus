<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\SecurityBundle\Security;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

final class BackSecurityController extends AbstractController
{
    #[Route('/back/connect/google', name: 'app_back_connect_google_start', methods: ['GET'])]
    public function connectGoogleStart(ClientRegistry $clientRegistry): RedirectResponse
    {
        return $clientRegistry
            ->getClient('google_back')
            ->redirect(['openid', 'profile', 'email'], []);
    }

    #[Route('/back/connect/google/check', name: 'app_back_connect_google_check', methods: ['GET'])]
    public function connectGoogleCheck(
        ClientRegistry $clientRegistry,
        EntityManagerInterface $entityManager,
        Security $security
    ): Response {
        if ($this->isGranted('ROLE_ADMIN')) {
            return $this->redirectToRoute('back_dashboard');
        }

        try {
            $client = $clientRegistry->getClient('google_back');
            $accessToken = $client->getAccessToken();
            $googleUser = $client->fetchUserFromToken($accessToken);
        } catch (\Throwable) {
            $this->addFlash('danger', 'Google sign-in failed. Please try again.');

            return $this->redirectToRoute('app_back_login');
        }

        $email = trim((string) $googleUser->getEmail());
        if ('' === $email) {
            $this->addFlash('danger', 'Google account did not provide an email address.');

            return $this->redirectToRoute('app_back_login');
        }

        $user = $entityManager->getRepository(User::class)->findOneBy(['email' => $email]);
        if (!$user instanceof User || !in_array('ROLE_ADMIN', $user->getRoles(), true)) {
            $this->addFlash('danger', 'Backoffice access is restricted to admin accounts.');

            return $this->redirectToRoute('app_back_login');
        }

        try {
            $security->login($user, null, 'back');
        } catch (\Throwable) {
            $this->addFlash('danger', 'This admin account cannot sign in right now.');

            return $this->redirectToRoute('app_back_login');
        }

        $this->addFlash('success', 'Signed in with Google.');

        return $this->redirectToRoute('back_dashboard');
    }

    #[Route('/back/login', name: 'app_back_login', methods: ['GET', 'POST'])]
    public function login(AuthenticationUtils $authenticationUtils): Response
    {
        if ($this->isGranted('ROLE_ADMIN')) {
            return $this->redirectToRoute('back_dashboard');
        }

        return $this->render('back/security/login.html.twig', [
            'last_username' => $authenticationUtils->getLastUsername(),
            'error' => $authenticationUtils->getLastAuthenticationError(),
        ]);
    }

    #[Route('/back/logout', name: 'app_back_logout', methods: ['GET'])]
    public function logout(): void
    {
        // Intercepted by Symfony logout.
    }
}
