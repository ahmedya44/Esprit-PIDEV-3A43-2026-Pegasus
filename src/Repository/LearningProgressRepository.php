<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\Course;
use App\Entity\LearningProgress;
use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<LearningProgress>
 */
class LearningProgressRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, LearningProgress::class);
    }

    public function findByUserAndCourse(User $user, Course $course): ?LearningProgress
    {
        return $this->findOneBy(['user' => $user, 'course' => $course]);
    }

    public function findOrCreate(User $user, Course $course, EntityManagerInterface $em): LearningProgress
    {
        $progress = $this->findByUserAndCourse($user, $course);
        if ($progress === null) {
            $progress = new LearningProgress();
            $progress->setUser($user);
            $progress->setCourse($course);
            $em->persist($progress);
        }
        return $progress;
    }

    public function findByUser(User $user): array
    {
        return $this->createQueryBuilder('p')
            ->where('p.user = :user')
            ->setParameter('user', $user)
            ->orderBy('p.startedAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Returns a map of courseId => LearningProgress for a user.
     *
     * @return array<int, LearningProgress>
     */
    public function getProgressMapForUser(User $user): array
    {
        $results = $this->findByUser($user);
        $map = [];
        foreach ($results as $p) {
            $map[(int) $p->getCourse()->getId()] = $p;
        }
        return $map;
    }
}
