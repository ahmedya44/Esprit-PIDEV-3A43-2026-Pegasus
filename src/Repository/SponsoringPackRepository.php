<?php

namespace App\Repository;

use App\Entity\SponsoringPack;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<SponsoringPack>
 */
class SponsoringPackRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, SponsoringPack::class);
    }
}
