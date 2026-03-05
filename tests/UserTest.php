<?php

declare(strict_types=1);

namespace App\Tests;

use App\Entity\User;
use App\Enum\AccountStatus;
use PHPUnit\Framework\TestCase;

final class UserTest extends TestCase
{
    // New user should have createdAt and implicit ROLE_USER.
    public function testDefaultCreatedAtAndRole(): void
    {
        $user = new User();

        self::assertInstanceOf(\DateTimeImmutable::class, $user->getCreatedAt());
        self::assertContains('ROLE_USER', $user->getRoles());
    }

    // Explicitly assigned user fields should be returned unchanged.
    public function testSettersAndGetters(): void
    {
        $user = new User();
        $user
            ->setEmail('test@example.com')
            ->setUsername('ahmed')
            ->setPassword('hashed-password')
            ->setPhone('+21612345678')
            ->setAvatarUrl('uploads/avatar.jpg')
            ->setStatus(AccountStatus::ACTIVE);

        self::assertSame('test@example.com', $user->getEmail());
        self::assertSame('ahmed', $user->getUsername());
        self::assertSame('hashed-password', $user->getPassword());
        self::assertSame('+21612345678', $user->getPhone());
        self::assertSame('uploads/avatar.jpg', $user->getAvatarUrl());
        self::assertSame(AccountStatus::ACTIVE, $user->getStatus());
    }
}
