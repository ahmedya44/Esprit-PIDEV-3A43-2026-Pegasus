<?php

declare(strict_types=1);

namespace App\Security;

use App\Entity\Artiste;
use App\Entity\Sponsor;
use App\Entity\User;
use App\Enum\AccountStatus;
use Symfony\Component\Security\Core\Exception\CustomUserMessageAccountStatusException;
use Symfony\Component\Security\Core\User\UserCheckerInterface;
use Symfony\Component\Security\Core\User\UserInterface;

final class UserChecker implements UserCheckerInterface
{
    public function checkPreAuth(UserInterface $user): void
    {
        if (!$user instanceof User) {
            return;
        }

        if ($user->getStatus() === AccountStatus::BANNED) {
            throw new CustomUserMessageAccountStatusException('Your account has been banned. Please contact support.');
        }

        if ($user->getStatus() !== AccountStatus::ACTIVE) {
            throw new CustomUserMessageAccountStatusException('Your account is pending email verification.');
        }

        if (
            ($user instanceof Artiste && !$user->isVerified()) ||
            ($user instanceof Sponsor && !$user->isVerified())
        ) {
            throw new CustomUserMessageAccountStatusException('Wait for the admin to verify you.');
        }
    }

    public function checkPostAuth(UserInterface $user): void
    {
    }
}
