<?php

declare(strict_types=1);

namespace App\Security;

use App\Entity\User;
use App\Enum\AccountStatus;
use Symfony\Component\Security\Core\Exception\CustomUserMessageAccountStatusException;
use Symfony\Component\Security\Core\User\UserCheckerInterface;
use Symfony\Component\Security\Core\User\UserInterface;

final class BackofficeUserChecker implements UserCheckerInterface
{
    public function checkPreAuth(UserInterface $user): void
    {
        if (!$user instanceof User) {
            return;
        }

        if (!in_array('ROLE_ADMIN', $user->getRoles(), true)) {
            throw new CustomUserMessageAccountStatusException('Backoffice access is restricted to admins.');
        }

        if ($user->getStatus() !== AccountStatus::ACTIVE) {
            throw new CustomUserMessageAccountStatusException('Your account is not active.');
        }
    }

    public function checkPostAuth(UserInterface $user): void
    {
    }
}
