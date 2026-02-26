<?php

namespace App\Repository;

use App\Entity\NormalUser;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<NormalUser>
 */
class NormalUserRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, NormalUser::class);
    }

    /**
     * @return list<NormalUser>
     */
    public function findAllForBackOffice(string $sortBy = 'createdAt', string $sortDir = 'DESC'): array
    {
        $allowed = ['createdAt', 'username', 'email', 'status', 'id'];
        if (!in_array($sortBy, $allowed, true)) {
            $sortBy = 'createdAt';
        }

        $sortDir = strtoupper($sortDir) === 'ASC' ? 'ASC' : 'DESC';

        return $this->createQueryBuilder('n')
            ->orderBy('n.'.$sortBy, $sortDir)
            ->addOrderBy('n.id', 'DESC')
            ->getQuery()
            ->getResult();
    }

    //    /**
    //     * @return NormalUser[] Returns an array of NormalUser objects
    //     */
    //    public function findByExampleField($value): array
    //    {
    //        return $this->createQueryBuilder('n')
    //            ->andWhere('n.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->orderBy('n.id', 'ASC')
    //            ->setMaxResults(10)
    //            ->getQuery()
    //            ->getResult()
    //        ;
    //    }

    //    public function findOneBySomeField($value): ?NormalUser
    //    {
    //        return $this->createQueryBuilder('n')
    //            ->andWhere('n.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
