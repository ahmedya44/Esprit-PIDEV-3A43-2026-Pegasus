<?php

declare(strict_types=1);

namespace App\Tests;

use App\Entity\Post;
use PHPUnit\Framework\TestCase;

final class ForumPostTest extends TestCase
{
    // Forum post should expose the expected status helpers.
    public function testStatusHelpersForOpenAndClosed(): void
    {
        $post = new Post();

        self::assertTrue($post->isOpen());
        self::assertFalse($post->isClosed());
        self::assertFalse($post->isHidden());

        $post->setStatus(Post::STATUS_CLOSED);
        self::assertFalse($post->isOpen());
        self::assertTrue($post->isClosed());
        self::assertFalse($post->isHidden());
    }

    // Invalid status values must be rejected.
    public function testSetStatusThrowsForInvalidValue(): void
    {
        $post = new Post();

        $this->expectException(\InvalidArgumentException::class);
        $post->setStatus('INVALID_STATUS');
    }
}

