<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\Art;
use App\Entity\ArtLike;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ArtLike>
 */
class ArtLikeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ArtLike::class);
    }

    public function countByArt(int $artId): int
    {
        return (int) $this->createQueryBuilder('l')
            ->select('COUNT(l.id)')
            ->where('IDENTITY(l.art) = :artId')
            ->setParameter('artId', $artId)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function findByUserAndArt(string $userIdentifier, int $artId): ?ArtLike
    {
        return $this->createQueryBuilder('l')
            ->where('l.userIdentifier = :uid')
            ->andWhere('IDENTITY(l.art) = :artId')
            ->setParameter('uid', $userIdentifier)
            ->setParameter('artId', $artId)
            ->getQuery()
            ->getOneOrNullResult();
    }

    /**
     * Toggles a like. Returns true if the like was added, false if removed.
     */
    public function toggle(Art $art, string $userIdentifier): bool
    {
        $em = $this->getEntityManager();
        $existing = $this->findByUserAndArt($userIdentifier, (int) $art->getId());

        if ($existing) {
            $em->remove($existing);
            $em->flush();
            return false;
        }

        $like = new ArtLike();
        $like->setArt($art);
        $like->setUserIdentifier($userIdentifier);
        $em->persist($like);
        $em->flush();
        return true;
    }
}
