<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\Course;
use App\Entity\LearningProgress;
use App\Entity\User;
use Doctrine\DBAL\Connection;
use Doctrine\ORM\EntityManagerInterface;

class LearningProgressRepository
{
    public function __construct(private readonly Connection $connection)
    {
    }

    public function findByUserAndCourse(User $user, Course $course): ?LearningProgress
    {
        $row = $this->connection->fetchAssociative(
            'SELECT * FROM course_progress WHERE user_id = :user_id AND course_id = :course_id',
            [
                'user_id' => $user->getId(),
                'course_id' => $course->getId(),
            ],
        );

        if ($row === false) {
            return null;
        }

        return $this->hydrateProgress($user, $course, $row);
    }

    public function findOrCreate(User $user, Course $course, ?EntityManagerInterface $em = null): LearningProgress
    {
        $progress = $this->findByUserAndCourse($user, $course);
        if ($progress === null) {
            $this->connection->executeStatement(
                'INSERT INTO course_progress (user_id, course_id) VALUES (:user_id, :course_id)',
                [
                    'user_id' => $user->getId(),
                    'course_id' => $course->getId(),
                ],
            );

            $progress = $this->findByUserAndCourse($user, $course);
        }

        return $progress ?? $this->newProgressDto($user, $course);
    }

    public function findByUser(User $user): array
    {
        return [];
    }

    /**
     * Returns a map of courseId => LearningProgress for a user.
     *
     * @return array<int, LearningProgress>
     */
    public function getProgressMapForUser(User $user): array
    {
        $rows = $this->connection->fetchAllAssociative(
            'SELECT cp.*, c.id AS existing_course_id
             FROM course_progress cp
             INNER JOIN course c ON c.id = cp.course_id
             WHERE cp.user_id = :user_id
             ORDER BY cp.started_at DESC',
            ['user_id' => $user->getId()],
        );

        $map = [];
        foreach ($rows as $row) {
            $course = new Course();
            $this->setEntityId($course, (int) $row['course_id']);
            $map[(int) $row['course_id']] = $this->hydrateProgress($user, $course, $row);
        }

        return $map;
    }

    /**
     * Mark a video complete using the existing course_video_progress table from the imported database.
     */
    public function markVideoCompleted(User $user, Course $course, int $videoId): LearningProgress
    {
        $this->findOrCreate($user, $course);

        $video = $this->connection->fetchAssociative(
            'SELECT cv.id, cs.id AS section_id
             FROM course_video cv
             INNER JOIN course_section cs ON cs.id = cv.section_id
             WHERE cv.id = :video_id AND cs.course_id = :course_id',
            [
                'video_id' => $videoId,
                'course_id' => $course->getId(),
            ],
        );

        if ($video !== false) {
            $this->connection->executeStatement(
                'INSERT INTO course_video_progress (user_id, course_id, section_id, video_id, completed_at)
                 VALUES (:user_id, :course_id, :section_id, :video_id, NOW())
                 ON DUPLICATE KEY UPDATE completed_at = COALESCE(completed_at, NOW())',
                [
                    'user_id' => $user->getId(),
                    'course_id' => $course->getId(),
                    'section_id' => $video['section_id'],
                    'video_id' => $videoId,
                ],
            );

            $this->connection->executeStatement(
                'UPDATE course_progress SET last_opened_at = NOW() WHERE user_id = :user_id AND course_id = :course_id',
                [
                    'user_id' => $user->getId(),
                    'course_id' => $course->getId(),
                ],
            );
        }

        return $this->findByUserAndCourse($user, $course) ?? $this->newProgressDto($user, $course);
    }

    public function markCourseCompleted(User $user, Course $course): void
    {
        $this->connection->executeStatement(
            'INSERT INTO course_progress (user_id, course_id, completed_at)
             VALUES (:user_id, :course_id, NOW())
             ON DUPLICATE KEY UPDATE completed_at = NOW(), last_opened_at = NOW()',
            [
                'user_id' => $user->getId(),
                'course_id' => $course->getId(),
            ],
        );
    }

    private function hydrateProgress(User $user, Course $course, array $row): LearningProgress
    {
        $progress = $this->newProgressDto($user, $course);
        $completedVideoIds = $this->getCompletedVideoIds((int) $user->getId(), (int) $course->getId());
        $totalVideos = $this->countCourseVideos((int) $course->getId());
        $progress->setCompletedVideoIds($completedVideoIds);
        $progress->setProgressPercent($totalVideos > 0 ? (int) round((count($completedVideoIds) * 100) / $totalVideos) : 0);
        $progress->setStatus(empty($row['completed_at']) ? 'in-progress' : 'completed');

        if (!empty($row['completed_at'])) {
            $progress->setCompletedAt(new \DateTimeImmutable((string) $row['completed_at']));
        }

        return $progress;
    }

    private function newProgressDto(User $user, Course $course): LearningProgress
    {
        $progress = new LearningProgress();
        $progress->setUser($user);
        $progress->setCourse($course);

        return $progress;
    }

    /**
     * @return list<int>
     */
    private function getCompletedVideoIds(int $userId, int $courseId): array
    {
        return array_map(
            'intval',
            $this->connection->fetchFirstColumn(
                'SELECT video_id FROM course_video_progress
                 WHERE user_id = :user_id AND course_id = :course_id AND completed_at IS NOT NULL',
                [
                    'user_id' => $userId,
                    'course_id' => $courseId,
                ],
            ),
        );
    }

    private function countCourseVideos(int $courseId): int
    {
        return (int) $this->connection->fetchOne(
            'SELECT COUNT(*)
             FROM course_video cv
             INNER JOIN course_section cs ON cs.id = cv.section_id
             WHERE cs.course_id = :course_id',
            ['course_id' => $courseId],
        );
    }

    private function setEntityId(object $entity, int $id): void
    {
        $reflection = new \ReflectionProperty($entity, 'id');
        $reflection->setAccessible(true);
        $reflection->setValue($entity, $id);
    }
}
