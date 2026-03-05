<?php

namespace App\Repository;

use App\Entity\Sponsor;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Sponsor>
 */
class SponsorRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Sponsor::class);
    }

    /**
     * @return list<Sponsor>
     */
    public function findAllForBackOffice(string $sortBy = 'createdAt', string $sortDir = 'DESC'): array
    {
        $allowed = ['createdAt', 'username', 'email', 'status', 'id'];
        if (!in_array($sortBy, $allowed, true)) {
            $sortBy = 'createdAt';
        }

        $sortDir = strtoupper($sortDir) === 'ASC' ? 'ASC' : 'DESC';

        return $this->createQueryBuilder('s')
            ->orderBy('s.'.$sortBy, $sortDir)
            ->addOrderBy('s.id', 'DESC')
            ->getQuery()
            ->getResult();
    }

    //    /**
    //     * @return Sponsor[] Returns an array of Sponsor objects
    //     */
    //    public function findByExampleField($value): array
    //    {
    //        return $this->createQueryBuilder('s')
    //            ->andWhere('s.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->orderBy('s.id', 'ASC')
    //            ->setMaxResults(10)
    //            ->getQuery()
    //            ->getResult()
    //        ;
    //    }

    //    public function findOneBySomeField($value): ?Sponsor
    //    {
    //        return $this->createQueryBuilder('s')
    //            ->andWhere('s.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
