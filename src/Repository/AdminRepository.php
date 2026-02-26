<?php

namespace App\Repository;

use App\Entity\Admin;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Admin>
 */
class AdminRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Admin::class);
    }

    /**
     * @return list<Admin>
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
    //     * @return Admin[] Returns an array of Admin objects
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

    //    public function findOneBySomeField($value): ?Admin
    //    {
    //        return $this->createQueryBuilder('a')
    //            ->andWhere('a.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
