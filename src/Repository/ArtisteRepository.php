<?php

namespace App\Repository;

use App\Entity\Artiste;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Artiste>
 */
class ArtisteRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Artiste::class);
    }

    /**
     * @return list<Artiste>
     */
    public function findAllForBackOffice(string $sortBy = 'createdAt', string $sortDir = 'DESC'): array
    {
        $allowed = ['createdAt', 'username', 'email', 'status', 'id'];
        if (!in_array($sortBy, $allowed, true)) {
            $sortBy = 'createdAt';
        }

        $sortDir = strtoupper($sortDir) === 'ASC' ? 'ASC' : 'DESC';

        return $this->createQueryBuilder('a')
            ->orderBy('a.'.$sortBy, $sortDir)
            ->addOrderBy('a.id', 'DESC')
            ->getQuery()
            ->getResult();
    }

    //    /**
    //     * @return Artiste[] Returns an array of Artiste objects
    //     */
    //    public function findByExampleField($value): array
    //    {
    //        return $this->createQueryBuilder('a')
    //            ->andWhere('a.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->orderBy('a.id', 'ASC')
    //            ->setMaxResults(10)
    //            ->getQuery()
    //            ->getResult()
    //        ;
    //    }

    //    public function findOneBySomeField($value): ?Artiste
    //    {
    //        return $this->createQueryBuilder('a')
    //            ->andWhere('a.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
