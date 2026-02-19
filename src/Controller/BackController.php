<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Admin;
use App\Entity\Artiste;
use App\Entity\NormalUser;
use App\Entity\Sponsor;
use App\Enum\AccountStatus;
use App\Repository\AdminRepository;
use App\Repository\ArtisteRepository;
use App\Repository\NormalUserRepository;
use App\Repository\SponsorRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\ConstraintViolationListInterface;
use Symfony\Component\Validator\Validator\ValidatorInterface;
use Twig\Error\LoaderError;
use App\Entity\User;

#[Route('/back', name: 'back_')]
final class BackController extends AbstractController
{
    #[Route('', name: 'dashboard', methods: ['GET'])]
    public function dashboard(): RedirectResponse
    {
        return $this->redirectToRoute('back_page', ['path' => 'index']);
    }

    #[Route('/profile', name: 'profile', methods: ['GET'])]
    public function profile(): Response
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        return $this->render('back/profile.html.twig', [
            'profile_user' => $user,
            'edit_mode' => false,
        ]);
    }

    #[Route('/profile/edit', name: 'profile_edit', methods: ['GET'])]
    public function profileEdit(): Response
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        return $this->render('back/profile.html.twig', [
            'profile_user' => $user,
            'edit_mode' => true,
        ]);
    }

    #[Route('/users', name: 'users', methods: ['GET'])]
    public function users(
        AdminRepository $adminRepository,
        ArtisteRepository $artisteRepository,
        SponsorRepository $sponsorRepository,
        NormalUserRepository $normalUserRepository
    ): Response {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        $isSuperAdmin = $user instanceof Admin && $user->isSuperAdmin();

        return $this->render('back/profile.html.twig', [
            'users_page' => true,
            'admins' => $adminRepository->findAll(),
            'artistes' => $artisteRepository->findAll(),
            'sponsors' => $sponsorRepository->findAll(),
            'normal_users' => $normalUserRepository->findAll(),
            'status_options' => AccountStatus::cases(),
            'is_super_admin' => $isSuperAdmin,
        ]);
    }

    #[Route('/users/{id}/status', name: 'users_change_status', methods: ['POST'])]
    public function usersChangeStatus(
        int $id,
        Request $request,
        UserRepository $userRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_status_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $user = $userRepository->find($id);
        if (!$user instanceof User) {
            $this->addFlash('danger', 'User not found.');

            return $this->redirectToRoute('back_users');
        }

        $status = AccountStatus::tryFrom((string) $request->request->get('status', ''));
        if (!$status instanceof AccountStatus) {
            $this->addFlash('danger', 'Invalid status value.');

            return $this->redirectToRoute('back_users');
        }

        $user->setStatus($status);
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'User status updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/promote-admin', name: 'users_promote_admin', methods: ['POST'])]
    public function usersPromoteAdmin(
        int $id,
        Request $request,
        UserRepository $userRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_promote_admin_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $user = $userRepository->find($id);
        if (!$user instanceof NormalUser) {
            $this->addFlash('danger', 'Only normal users can be promoted with this action.');

            return $this->redirectToRoute('back_users');
        }

        $user->setRoles(['ROLE_ADMIN']);
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'User promoted to admin role.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/make-super-admin', name: 'users_make_super_admin', methods: ['POST'])]
    public function usersMakeSuperAdmin(
        int $id,
        Request $request,
        AdminRepository $adminRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_make_super_admin_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $admin = $adminRepository->find($id);
        if (!$admin instanceof Admin) {
            $this->addFlash('danger', 'Admin not found.');

            return $this->redirectToRoute('back_users');
        }

        if ($admin->isSuperAdmin()) {
            $this->addFlash('success', 'This admin is already super admin.');

            return $this->redirectToRoute('back_users');
        }

        $admin->setSuperAdmin(true);
        $entityManager->persist($admin);
        $entityManager->flush();

        $this->addFlash('success', 'Admin upgraded to super admin.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/artiste-verified', name: 'users_change_artiste_verified', methods: ['POST'])]
    public function usersChangeArtisteVerified(
        int $id,
        Request $request,
        ArtisteRepository $artisteRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_artiste_verified_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $artiste = $artisteRepository->find($id);
        if (!$artiste instanceof Artiste) {
            $this->addFlash('danger', 'Artiste not found.');

            return $this->redirectToRoute('back_users');
        }

        $verifiedRaw = (string) $request->request->get('verified', '');
        if (!in_array($verifiedRaw, ['0', '1'], true)) {
            $this->addFlash('danger', 'Invalid verified value.');

            return $this->redirectToRoute('back_users');
        }

        $artiste->setVerified($verifiedRaw === '1');
        $entityManager->persist($artiste);
        $entityManager->flush();

        $this->addFlash('success', 'Artiste verification updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/sponsor-verified', name: 'users_change_sponsor_verified', methods: ['POST'])]
    public function usersChangeSponsorVerified(
        int $id,
        Request $request,
        SponsorRepository $sponsorRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_sponsor_verified_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $sponsor = $sponsorRepository->find($id);
        if (!$sponsor instanceof Sponsor) {
            $this->addFlash('danger', 'Sponsor not found.');

            return $this->redirectToRoute('back_users');
        }

        $verifiedRaw = (string) $request->request->get('verified', '');
        if (!in_array($verifiedRaw, ['0', '1'], true)) {
            $this->addFlash('danger', 'Invalid verified value.');

            return $this->redirectToRoute('back_users');
        }

        $sponsor->setVerified($verifiedRaw === '1');
        $entityManager->persist($sponsor);
        $entityManager->flush();

        $this->addFlash('success', 'Sponsor verification updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/profile/update', name: 'profile_update', methods: ['POST'])]
    public function profileUpdate(
        Request $request,
        EntityManagerInterface $entityManager,
        ValidatorInterface $validator
    ): RedirectResponse
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        if (!$this->isCsrfTokenValid('back_profile_update', (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $username = trim((string) $request->request->get('username', ''));
        if ($username === '') {
            $this->addFlash('danger', 'Username cannot be empty.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $user->setUsername($username);

        $phone = trim((string) $request->request->get('phone', ''));
        $user->setPhone($phone === '' ? null : $phone);

        if ($user instanceof Admin) {
            $birthDateRaw = trim((string) $request->request->get('birthDate', ''));
            if ($birthDateRaw === '') {
                $user->setBirthDate(null);
            } else {
                $birthDate = \DateTimeImmutable::createFromFormat('Y-m-d', $birthDateRaw);
                $errors = \DateTimeImmutable::getLastErrors();
                if (
                    !$birthDate instanceof \DateTimeImmutable ||
                    (false !== $errors && ($errors['warning_count'] > 0 || $errors['error_count'] > 0))
                ) {
                    $this->addFlash('danger', 'Invalid birth date format.');

                    return $this->redirectToRoute('back_profile_edit');
                }

                $user->setBirthDate($birthDate);
            }
        }

        $violations = $validator->validate($user);
        if (count($violations) > 0) {
            $this->addValidationErrorsToFlash($violations);

            return $this->redirectToRoute('back_profile_edit');
        }

        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Profile updated successfully.');

        return $this->redirectToRoute('back_profile');
    }

    #[Route('/profile/change-password', name: 'profile_change_password', methods: ['POST'])]
    public function profileChangePassword(
        Request $request,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ): RedirectResponse {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        if (!$this->isCsrfTokenValid('back_profile_change_password', (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $currentPassword = (string) $request->request->get('currentPassword', '');
        $newPassword = (string) $request->request->get('newPassword', '');
        $confirmPassword = (string) $request->request->get('confirmPassword', '');

        if ($currentPassword === '' || $newPassword === '' || $confirmPassword === '') {
            $this->addFlash('danger', 'All password fields are required.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if (!$passwordHasher->isPasswordValid($user, $currentPassword)) {
            $this->addFlash('danger', 'Current password is incorrect.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if (strlen($newPassword) < 6) {
            $this->addFlash('danger', 'New password must be at least 6 characters.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if ($newPassword !== $confirmPassword) {
            $this->addFlash('danger', 'New password and confirmation do not match.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $user->setPassword($passwordHasher->hashPassword($user, $newPassword));
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Password changed successfully.');

        return $this->redirectToRoute('back_profile_edit');
    }

    #[Route('/{path}.html', name: 'page', requirements: ['path' => '[A-Za-z0-9_\-/]+'], methods: ['GET'])]
    public function page(string $path): Response
    {
        if (str_contains($path, '..')) {
            throw $this->createNotFoundException();
        }

        $template = sprintf('back/%s.html.twig', $path);

        try {
            return $this->render($template);
        } catch (LoaderError) {
            throw $this->createNotFoundException(sprintf('Back template "%s" was not found.', $template));
        }
    }

    private function requireSuperAdmin(): ?Admin
    {
        $user = $this->getUser();
        if (!$user instanceof Admin || !$user->isSuperAdmin()) {
            $this->addFlash('danger', 'Only super admins can perform this action.');

            return null;
        }

        return $user;
    }

    private function addValidationErrorsToFlash(ConstraintViolationListInterface $violations): void
    {
        foreach ($violations as $violation) {
            $this->addFlash('danger', $violation->getMessage());
        }
    }
}
