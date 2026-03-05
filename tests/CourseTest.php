<?php

declare(strict_types=1);

namespace App\Tests;

use App\Entity\Artiste;
use App\Entity\Course;
use App\Entity\CourseSection;
use PHPUnit\Framework\TestCase;

final class CourseTest extends TestCase
{
    // Course fields and section relation should be linked as expected.
    public function testCoursePropertiesAndSectionRelation(): void
    {
        $artist = new Artiste();
        $createdAt = new \DateTimeImmutable('2026-02-15 09:00:00');

        $course = new Course();
        $course
            ->setTitle('Intro to Art')
            ->setDescription('Learn basics of drawing and composition.')
            ->setStatus('PUBLISHED')
            ->setThumbnailUrl('uploads/courses/cover.jpg')
            ->setCreatedAt($createdAt)
            ->setArtist($artist);

        $section = (new CourseSection())
            ->setTitle('Section 1')
            ->setOrderIndex(1);

        $course->addCourseSection($section);

        self::assertSame('Intro to Art', $course->getTitle());
        self::assertSame('PUBLISHED', $course->getStatus());
        self::assertSame('uploads/courses/cover.jpg', $course->getThumbnailUrl());
        self::assertSame($createdAt, $course->getCreatedAt());
        self::assertSame($artist, $course->getArtist());
        self::assertCount(1, $course->getCourseSections());
        self::assertSame($course, $section->getCourse());
    }
}
