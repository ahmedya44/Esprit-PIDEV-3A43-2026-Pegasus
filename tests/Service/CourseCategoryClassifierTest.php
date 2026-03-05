<?php

declare(strict_types=1);

namespace App\Tests\Service;

use App\Entity\Course;
use App\Service\CourseCategoryClassifier;
use PHPUnit\Framework\TestCase;

final class CourseCategoryClassifierTest extends TestCase
{
    // Art-related text should map to the "art" category.
    public function testDetectSlugsReturnsCategoryForArtCourse(): void
    {
        $course = (new Course())
            ->setTitle('Painting Basics')
            ->setDescription('Learn drawing, color, and illustration techniques.');

        $classifier = new CourseCategoryClassifier();
        $slugs = $classifier->detectSlugs($course);

        self::assertContains('art', $slugs);
    }

    // Non-matching text should fallback to "other".
    public function testDetectSlugsReturnsOtherWhenNoMatch(): void
    {
        $course = (new Course())
            ->setTitle('Accounting 101')
            ->setDescription('Finance and taxes fundamentals.');

        $classifier = new CourseCategoryClassifier();
        $slugs = $classifier->detectSlugs($course);

        self::assertSame(['other'], $slugs);
    }

    // Filter list should contain matched categories and always include "other".
    public function testBuildAvailableFiltersAlwaysContainsOther(): void
    {
        $courses = [
            (new Course())->setTitle('Music Theory')->setDescription('Piano and melody.'),
        ];

        $classifier = new CourseCategoryClassifier();
        $filters = $classifier->buildAvailableFilters($courses);
        $slugs = array_column($filters, 'slug');

        self::assertContains('music', $slugs);
        self::assertContains('other', $slugs);
    }
}
