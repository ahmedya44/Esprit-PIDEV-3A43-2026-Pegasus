<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\Quiz;
use App\Entity\QuizAttempt;
use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<QuizAttempt>
 */
class QuizAttemptRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, QuizAttempt::class);
    }

    public function findByUserAndQuiz(User $user, Quiz $quiz): array
    {
        return $this->createQueryBuilder('a')
            ->where('a.user = :user')
            ->andWhere('a.quiz = :quiz')
            ->setParameter('user', $user)
            ->setParameter('quiz', $quiz)
            ->orderBy('a.submittedAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function countByUserAndQuiz(User $user, Quiz $quiz): int
    {
        return (int) $this->createQueryBuilder('a')
            ->select('COUNT(a.id)')
            ->where('a.user = :user')
            ->andWhere('a.quiz = :quiz')
            ->setParameter('user', $user)
            ->setParameter('quiz', $quiz)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function findBestPassingAttempt(User $user, Quiz $quiz): ?QuizAttempt
    {
        return $this->createQueryBuilder('a')
            ->where('a.user = :user')
            ->andWhere('a.quiz = :quiz')
            ->andWhere('a.passed = true')
            ->setParameter('user', $user)
            ->setParameter('quiz', $quiz)
            ->orderBy('a.scorePercent', 'DESC')
            ->setMaxResults(1)
            ->getQuery()
            ->getOneOrNullResult();
    }
}
