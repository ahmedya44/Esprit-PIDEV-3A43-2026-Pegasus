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

    /**
     * @param list<int> $artIds
     *
     * @return array<int, int>
     */
    public function countByArtIds(array $artIds): array
    {
        $artIds = array_values(array_unique(array_filter($artIds)));
        if ($artIds === []) {
            return [];
        }

        $rows = $this->createQueryBuilder('c')
            ->select('IDENTITY(c.art) AS artId, COUNT(c.id) AS commentCount')
            ->where('IDENTITY(c.art) IN (:artIds)')
            ->setParameter('artIds', $artIds)
            ->groupBy('c.art')
            ->getQuery()
            ->getArrayResult();

        $counts = [];
        foreach ($rows as $row) {
            $counts[(int) $row['artId']] = (int) $row['commentCount'];
        }

        return $counts;
    }
}
