<?php

declare(strict_types=1);

namespace App\Tests;

use App\Entity\Art;
use PHPUnit\Framework\TestCase;

final class ArtTest extends TestCase
{
    // Art entity should keep all assigned gallery fields correctly.
    public function testGalleryArtSettersAndGetters(): void
    {
        $createdAt = new \DateTimeImmutable('2026-01-10 12:00:00');

        $art = new Art();
        $art
            ->setTitle('Sunset')
            ->setDescription('Acrylic painting on canvas')
            ->setTitleEn('Sunset')
            ->setDescriptionEn('Acrylic painting on canvas')
            ->setImageUrl('uploads/gallery/sunset.jpg')
            ->setStatus('published')
            ->setCreatedAt($createdAt)
            ->setAiGeneratedImage('uploads/ai/sunset-ai.jpg')
            ->setIsAiGenerated(true);

        self::assertSame('Sunset', $art->getTitle());
        self::assertSame('Acrylic painting on canvas', $art->getDescription());
        self::assertSame('Sunset', $art->getTitleEn());
        self::assertSame('Acrylic painting on canvas', $art->getDescriptionEn());
        self::assertSame('uploads/gallery/sunset.jpg', $art->getImageUrl());
        self::assertSame('published', $art->getStatus());
        self::assertSame($createdAt, $art->getCreatedAt());
        self::assertSame('uploads/ai/sunset-ai.jpg', $art->getAiGeneratedImage());
        self::assertTrue($art->isIsAiGenerated());
    }
}
