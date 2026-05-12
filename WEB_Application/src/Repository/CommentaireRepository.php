<?php

namespace App\Repository;

use App\Entity\Commentaire;
use App\Entity\Post;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Commentaire>
 */
class CommentaireRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commentaire::class);
    }

    /**
     * @return Commentaire[]
     */
    public function findByPost(Post $post): array
    {
        return $this->createQueryBuilder('c')
            ->where('c.post = :post')
            ->setParameter('post', $post)
            ->orderBy('c.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    /**
     * @return Commentaire[]
     */
    public function findRecent(?string $search = null, int $limit = 20, int $offset = 0): array
    {
        $qb = $this->createQueryBuilder('c');

        if ($search) {
            $qb->where('c.authorEmail LIKE :search')
                ->setParameter('search', '%' . $search . '%');
        }

        $qb->orderBy('c.createdAt', 'DESC')
            ->setMaxResults($limit)
            ->setFirstResult($offset);

        return $qb->getQuery()->getResult();
    }

    public function countRecent(?string $search = null): int
    {
        $qb = $this->createQueryBuilder('c');

        $qb->select('COUNT(c.id)');

        if ($search) {
            $qb->where('c.authorEmail LIKE :search')
                ->setParameter('search', '%' . $search . '%');
        }

        return (int) $qb->getQuery()->getSingleScalarResult();
    }

    public function totalCount(): int
    {
        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function countBanned(): int
    {
        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.bannedByAdmin = true')
            ->getQuery()
            ->getSingleScalarResult();
    }
}
