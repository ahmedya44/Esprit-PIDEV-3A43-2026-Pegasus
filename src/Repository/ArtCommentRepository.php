<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\ArtComment;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ArtComment>
 */
class ArtCommentRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ArtComment::class);
    }

    public function findByArt(int $artId): array
    {
        return $this->createQueryBuilder('c')
            ->where('IDENTITY(c.art) = :artId')
            ->setParameter('artId', $artId)
            ->orderBy('c.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }
}
