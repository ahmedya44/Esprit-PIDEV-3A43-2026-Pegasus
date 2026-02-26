<?php

namespace App\Repository;

use App\Entity\ArtFavoris;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ArtFavoris>
 */
class ArtFavorisRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ArtFavoris::class);
    }

    public function save(ArtFavoris $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(ArtFavoris $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function findByUserAndArt(string $userIdentifier, int $artId): ?ArtFavoris
    {
        return $this->createQueryBuilder('af')
            ->leftJoin('af.art', 'a')
            ->where('af.userIdentifier = :userIdentifier')
            ->andWhere('a.id = :artId')
            ->setParameter('userIdentifier', $userIdentifier)
            ->setParameter('artId', $artId)
            ->getQuery()
            ->getOneOrNullResult();
    }

    public function findByUser(string $userIdentifier): array
    {
        return $this->createQueryBuilder('af')
            ->leftJoin('af.art', 'a')
            ->addSelect('a')
            ->where('af.userIdentifier = :userIdentifier')
            ->setParameter('userIdentifier', $userIdentifier)
            ->orderBy('af.addedAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function countByUser(string $userIdentifier): int
    {
        return $this->createQueryBuilder('af')
            ->select('COUNT(af.id)')
            ->where('af.userIdentifier = :userIdentifier')
            ->setParameter('userIdentifier', $userIdentifier)
            ->getQuery()
            ->getSingleScalarResult();
    }
}
