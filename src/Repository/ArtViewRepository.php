<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\ArtView;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ArtView>
 */
final class ArtViewRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ArtView::class);
    }

    public function addView(int $artId, string $ipAddress): void
    {
        $art = $this->getEntityManager()->getReference('App\Entity\Art', $artId);
        
        $artView = new ArtView();
        $artView->setArt($art);
        $artView->setIpAddress($ipAddress);
        $artView->setViewedAt(new \DateTime());
        
        $this->getEntityManager()->persist($artView);
        $this->getEntityManager()->flush();
    }

    public function countByArt(int $artId): int
    {
        return $this->count(['art' => $artId]);
    }

    public function findByArt(int $artId): array
    {
        return $this->findBy(['art' => $artId], ['viewedAt' => 'DESC']);
    }
}
