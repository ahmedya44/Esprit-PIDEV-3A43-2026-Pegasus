<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Admin;
use App\Entity\Artiste;
use App\Entity\NormalUser;
use App\Entity\Sponsor;
use App\Entity\User;
use App\Repository\PasskeyCredentialRepository;
use App\Service\AvatarService;
use App\Service\CloudflareStylizerService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\ConstraintViolationListInterface;
use Symfony\Component\Validator\Validator\ValidatorInterface;

final class FrontController extends AbstractController
{
    #[Route('/', name: 'front_home', methods: ['GET'])]
    public function home(): Response
    {
        return $this->render('front/index.html.twig');
    }

    #[Route('/menu', name: 'front_menu', methods: ['GET'])]
    public function menu(): Response
    {
        return $this->render('front/menu.html.twig');
    }

    #[Route('/about', name: 'front_about', methods: ['GET'])]
    public function about(): Response
    {
        return $this->render('front/about.html.twig');
    }

    #[Route('/book', name: 'front_book', methods: ['GET'])]
    public function book(): Response
    {
        return $this->render('front/book.html.twig');
    }

    #[Route('/profile', name: 'front_profile', methods: ['GET', 'POST'])]
    public function profile(
        Request $request,
        EntityManagerInterface $entityManager,
        AvatarService $avatarService,
        CloudflareStylizerService $cloudflareStylizerService,
        PasskeyCredentialRepository $passkeyCredentialRepository
    ): Response
    {
        $user = $this->getUser();

        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        if ($request->isMethod('POST')) {
            $token = (string) $request->request->get('_csrf_token', '');
            if (!$this->isCsrfTokenValid('profile_avatar_update', $token)) {
                $this->addFlash('danger', 'Invalid request token.');

                return $this->redirectToRoute('front_profile');
            }

            $presetAvatar = trim((string) $request->request->get('preset_avatar', ''));
            $stylizedAvatar = trim((string) $request->request->get('stylized_avatar', ''));
            /** @var UploadedFile|null $avatarFile */
            $avatarFile = $request->files->get('avatar');
            /** @var UploadedFile|null $stylizeSourceFile */
            $stylizeSourceFile = $request->files->get('stylize_source');
            $stylizeStyle = strtolower(trim((string) $request->request->get('stylize_style', 'anime')));

            if ('' !== $stylizedAvatar) {
                if (!$avatarService->isPublicAvatarPathAllowed($stylizedAvatar)) {
                    $this->addFlash('danger', 'Invalid stylized avatar selection.');

                    return $this->redirectToRoute('front_profile');
                }

                $user->setAvatarUrl($stylizedAvatar);
                $entityManager->persist($user);
                $entityManager->flush();
                $request->getSession()->remove('stylized_avatar_options');
                $this->addFlash('success', 'Stylized avatar applied.');

                return $this->redirectToRoute('front_profile');
            }

            if ($stylizeSourceFile instanceof UploadedFile) {
                $allowedMimeTypes = ['image/jpeg', 'image/png', 'image/webp'];
                if (!in_array($stylizeSourceFile->getMimeType(), $allowedMimeTypes, true)) {
                    $this->addFlash('danger', 'Only JPG, PNG, and WEBP images are allowed.');

                    return $this->redirectToRoute('front_profile');
                }

                if ($stylizeSourceFile->getSize() > 5 * 1024 * 1024) {
                    $this->addFlash('danger', 'Image size must be 5MB or less for stylization.');

                    return $this->redirectToRoute('front_profile');
                }

                $allowedStyles = ['anime', 'comic', 'pixar'];
                if (!in_array($stylizeStyle, $allowedStyles, true)) {
                    $this->addFlash('danger', 'Invalid stylization style selected.');

                    return $this->redirectToRoute('front_profile');
                }

                $prepared = $avatarService->prepareStylizedOutput($user, $stylizeStyle);

                try {
                    $generatedBinary = $cloudflareStylizerService->generateStyle($stylizeSourceFile, $stylizeStyle);
                } catch (\Throwable $exception) {
                    $this->addFlash('danger', 'Stylization API failed. '.$exception->getMessage());

                    return $this->redirectToRoute('front_profile');
                }

                if (!is_string($generatedBinary) || '' === $generatedBinary) {
                    $this->addFlash('danger', 'Stylization failed. Try another image or retry in a few minutes.');

                    return $this->redirectToRoute('front_profile');
                }

                $writeOk = @file_put_contents($prepared['absolute_path'], $generatedBinary);
                if (false === $writeOk) {
                    $this->addFlash('danger', 'Stylization failed. Try another image or retry in a few minutes.');

                    return $this->redirectToRoute('front_profile');
                }

                $existing = $request->getSession()->get('stylized_avatar_options', []);
                if (!is_array($existing)) {
                    $existing = [];
                }
                $existing[$stylizeStyle] = $prepared['public_path'];
                $request->getSession()->set('stylized_avatar_options', $existing);
                $this->addFlash('success', ucfirst($stylizeStyle).' style generated. You can generate other styles too.');

                return $this->redirectToRoute('front_profile');
            }

            if ('' !== $presetAvatar) {
                $presetAvatars = $avatarService->listPresetAvatars();
                if (!in_array($presetAvatar, $presetAvatars, true)) {
                    $this->addFlash('danger', 'Invalid avatar selection.');

                    return $this->redirectToRoute('front_profile');
                }

                $user->setAvatarUrl($presetAvatar);
            } elseif ($avatarFile instanceof UploadedFile) {
                $allowedMimeTypes = ['image/jpeg', 'image/png', 'image/webp'];
                if (!in_array($avatarFile->getMimeType(), $allowedMimeTypes, true)) {
                    $this->addFlash('danger', 'Only JPG, PNG, and WEBP images are allowed.');

                    return $this->redirectToRoute('front_profile');
                }

                if ($avatarFile->getSize() > 2 * 1024 * 1024) {
                    $this->addFlash('danger', 'Image size must be 2MB or less.');

                    return $this->redirectToRoute('front_profile');
                }

                try {
                    $user->setAvatarUrl($avatarService->storeUploadedAvatar($user, $avatarFile));
                } catch (\RuntimeException) {
                    $this->addFlash('danger', 'Could not upload image. Please try again.');

                    return $this->redirectToRoute('front_profile');
                }
            } else {
                $this->addFlash('danger', 'Please choose a preset avatar or upload an image.');

                return $this->redirectToRoute('front_profile');
            }
            $entityManager->persist($user);
            $entityManager->flush();
            $this->addFlash('success', 'Profile picture updated.');

            return $this->redirectToRoute('front_profile');
        }

        $accountType = 'User';
        $roleKey = 'normal';
        $extraFields = [];

        if ($user instanceof Admin) {
            $accountType = 'Admin';
            $roleKey = 'admin';
            $extraFields = [
                'Birth Date' => $user->getBirthDate()?->format('Y-m-d'),
                'Super Admin' => $user->isSuperAdmin() ? 'Yes' : 'No',
            ];
        } elseif ($user instanceof Artiste) {
            $accountType = 'Artiste';
            $roleKey = 'artiste';
            $extraFields = [
                'Birth Date' => $user->getBirthDate()?->format('Y-m-d'),
                'Bio' => $user->getBio(),
                'Styles' => $user->getStyles(),
                'Facebook' => $user->getFacebook(),
                'Instagram' => $user->getInstagram(),
                'Portfolio URL' => $user->getPortfolioUrl(),
                'Verified' => $user->isVerified() ? 'Yes' : 'No',
            ];
        } elseif ($user instanceof Sponsor) {
            $accountType = 'Sponsor';
            $roleKey = 'sponsor';
            $extraFields = [
                'Company Name' => $user->getCompanyName(),
                'Website' => $user->getWebsite(),
                'Address' => $user->getAddress(),
                'Description' => $user->getDescription(),
            ];
        } elseif ($user instanceof NormalUser) {
            $accountType = 'Normal User';
            $roleKey = 'normal';
            $extraFields = [
                'Birth Date' => $user->getBirthDate()?->format('Y-m-d'),
            ];
        }

        $extraFields = array_filter($extraFields, static fn ($value): bool => null !== $value && '' !== trim((string) $value));

        $passkeys = $passkeyCredentialRepository->findByUser($user);

        return $this->render('front/profile.html.twig', [
            'profile_user' => $user,
            'account_type' => $accountType,
            'role_key' => $roleKey,
            'extra_fields' => $extraFields,
            'preset_avatars' => $avatarService->listPresetAvatars(),
            'stylized_avatar_options' => $request->getSession()->get('stylized_avatar_options', []),
            'passkey_count' => count($passkeys),
            'passkeys' => $passkeys,
        ]);
    }

    #[Route('/profile/edit', name: 'front_profile_edit', methods: ['GET'])]
    public function editProfile(): Response
    {
        $user = $this->getUser();

        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        $accountType = 'User';
        $roleKey = 'normal';

        if ($user instanceof Admin) {
            $accountType = 'Admin';
            $roleKey = 'admin';
        } elseif ($user instanceof Artiste) {
            $accountType = 'Artiste';
            $roleKey = 'artiste';
        } elseif ($user instanceof Sponsor) {
            $accountType = 'Sponsor';
            $roleKey = 'sponsor';
        } elseif ($user instanceof NormalUser) {
            $accountType = 'Normal User';
            $roleKey = 'normal';
        }

        return $this->render('front/profile_edit.html.twig', [
            'profile_user' => $user,
            'account_type' => $accountType,
            'role_key' => $roleKey,
        ]);
    }

    #[Route('/profile/update', name: 'front_profile_update', methods: ['POST'])]
    public function updateProfile(
        Request $request,
        EntityManagerInterface $entityManager,
        ValidatorInterface $validator
    ): Response
    {
        $user = $this->getUser();

        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        $token = (string) $request->request->get('_csrf_token', '');
        if (!$this->isCsrfTokenValid('profile_update', $token)) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('front_profile');
        }

        $username = trim((string) $request->request->get('username', ''));
        if ('' === $username) {
            $this->addFlash('danger', 'Username cannot be empty.');

            return $this->redirectToRoute('front_profile');
        }

        $user->setUsername($username);
        $phone = trim((string) $request->request->get('phone', ''));
        $user->setPhone('' === $phone ? null : $phone);

        if ($user instanceof Admin) {
            try {
                $user->setBirthDate($this->toDateOrNull((string) $request->request->get('birthDate', '')));
            } catch (\InvalidArgumentException) {
                $this->addFlash('danger', 'Invalid birth date format.');

                return $this->redirectToRoute('front_profile');
            }
        } elseif ($user instanceof NormalUser) {
            try {
                $user->setBirthDate($this->toDateOrNull((string) $request->request->get('birthDate', '')));
            } catch (\InvalidArgumentException) {
                $this->addFlash('danger', 'Invalid birth date format.');

                return $this->redirectToRoute('front_profile');
            }
        } elseif ($user instanceof Artiste) {
            try {
                $user->setBirthDate($this->toDateOrNull((string) $request->request->get('birthDate', '')));
            } catch (\InvalidArgumentException) {
                $this->addFlash('danger', 'Invalid birth date format.');

                return $this->redirectToRoute('front_profile');
            }
            $user->setBio($this->toNullable((string) $request->request->get('bio', '')));
            $user->setStyles($this->toNullable((string) $request->request->get('styles', '')));
            $user->setFacebook($this->toNullable((string) $request->request->get('facebook', '')));
            $user->setInstagram($this->toNullable((string) $request->request->get('instagram', '')));
            $user->setPortfolioUrl($this->toNullable((string) $request->request->get('portfolioUrl', '')));
        } elseif ($user instanceof Sponsor) {
            $companyName = trim((string) $request->request->get('companyName', ''));
            if ('' === $companyName) {
                $this->addFlash('danger', 'Company name cannot be empty.');

                return $this->redirectToRoute('front_profile');
            }

            $user->setCompanyName($companyName);
            $user->setWebsite($this->toNullable((string) $request->request->get('website', '')));
            $user->setAddress($this->toNullable((string) $request->request->get('address', '')));
            $user->setDescription($this->toNullable((string) $request->request->get('description', '')));
        }

        $violations = $validator->validate($user);
        if (count($violations) > 0) {
            $this->addValidationErrorsToFlash($violations);

            return $this->redirectToRoute('front_profile');
        }

        $entityManager->persist($user);
        $entityManager->flush();
        $this->addFlash('success', 'Profile updated successfully.');

        return $this->redirectToRoute('front_profile');
    }

    #[Route('/profile/change-password', name: 'front_profile_change_password', methods: ['POST'])]
    public function changePassword(
        Request $request,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ): Response {
        $user = $this->getUser();

        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        $token = (string) $request->request->get('_csrf_token', '');
        if (!$this->isCsrfTokenValid('change_password', $token)) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('front_profile_edit');
        }

        $currentPassword = (string) $request->request->get('currentPassword', '');
        $newPassword = (string) $request->request->get('newPassword', '');
        $confirmPassword = (string) $request->request->get('confirmPassword', '');

        if ('' === $currentPassword || '' === $newPassword || '' === $confirmPassword) {
            $this->addFlash('danger', 'All password fields are required.');

            return $this->redirectToRoute('front_profile_edit');
        }

        if (!$passwordHasher->isPasswordValid($user, $currentPassword)) {
            $this->addFlash('danger', 'Current password is incorrect.');

            return $this->redirectToRoute('front_profile_edit');
        }

        if (strlen($newPassword) < 6) {
            $this->addFlash('danger', 'New password must be at least 6 characters.');

            return $this->redirectToRoute('front_profile_edit');
        }

        if ($newPassword !== $confirmPassword) {
            $this->addFlash('danger', 'New password and confirmation do not match.');

            return $this->redirectToRoute('front_profile_edit');
        }

        $user->setPassword($passwordHasher->hashPassword($user, $newPassword));
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Password changed successfully.');

        return $this->redirectToRoute('front_profile_edit');
    }

    private function toNullable(string $value): ?string
    {
        $value = trim($value);

        return '' === $value ? null : $value;
    }

    private function toDateOrNull(string $value): ?\DateTimeImmutable
    {
        $value = trim($value);
        if ('' === $value) {
            return null;
        }

        $date = \DateTimeImmutable::createFromFormat('Y-m-d', $value);
        $errors = \DateTimeImmutable::getLastErrors();

        if (
            !$date instanceof \DateTimeImmutable ||
            (false !== $errors && ($errors['warning_count'] > 0 || $errors['error_count'] > 0))
        ) {
            throw new \InvalidArgumentException('Invalid date format');
        }

        return $date;
    }

    private function addValidationErrorsToFlash(ConstraintViolationListInterface $violations): void
    {
        foreach ($violations as $violation) {
            $this->addFlash('danger', $violation->getMessage());
        }
    }
}
