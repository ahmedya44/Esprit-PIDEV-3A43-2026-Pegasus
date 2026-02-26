<?php

namespace App\Repository;

use App\Entity\Post;
use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\QueryBuilder;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Post>
 */
class PostRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Post::class);
    }

    public function createFrontListQueryBuilder(?string $q = null, ?string $status = null, ?User $viewer = null, bool $isAdmin = false): QueryBuilder
    {
        $qb = $this->createQueryBuilder('p');

        if (!$isAdmin) {
            if ($viewer instanceof User) {
                $qb->where('p.status != :hidden OR p.owner = :viewer OR :viewer MEMBER OF p.allowedViewers')
                    ->setParameter('hidden', Post::STATUS_HIDDEN)
                    ->setParameter('viewer', $viewer);
            } else {
                $qb->where('p.status != :hidden')
                    ->setParameter('hidden', Post::STATUS_HIDDEN);
            }
        }

        if (in_array($status, [Post::STATUS_OPEN, Post::STATUS_HIDDEN], true)) {
            $qb->andWhere('p.status = :status')
                ->setParameter('status', $status);
        }

        $this->applySearchFilter($qb, $q);

        return $qb->orderBy('p.createdAt', 'DESC');
    }

    public function createAdminListQueryBuilder(?string $q = null, ?string $status = null): QueryBuilder
    {
        $qb = $this->createQueryBuilder('p');

        if (in_array($status, Post::ALLOWED_STATUSES, true)) {
            $qb->andWhere('p.status = :status')
                ->setParameter('status', $status);
        }

        $this->applySearchFilter($qb, $q);

        return $qb->orderBy('p.createdAt', 'DESC');
    }

    /**
     * @return array<string, int>
     */
    public function countByStatus(): array
    {
        $rows = $this->createQueryBuilder('p')
            ->select('p.status AS status, COUNT(p.id) AS nb')
            ->groupBy('p.status')
            ->getQuery()
            ->getArrayResult();

        $result = [
            Post::STATUS_OPEN => 0,
            Post::STATUS_CLOSED => 0,
            Post::STATUS_HIDDEN => 0,
        ];

        foreach ($rows as $row) {
            $result[$row['status']] = (int) $row['nb'];
        }

        return $result;
    }

    /**
     * @return array<int, array{post: Post, nbComments: int}>
     */
    public function topCommented(int $limit = 5): array
    {
        $rows = $this->createQueryBuilder('p')
            ->leftJoin('p.commentaires', 'c')
            ->addSelect('COUNT(c.id) AS nbComments')
            ->groupBy('p.id')
            ->orderBy('nbComments', 'DESC')
            ->addOrderBy('p.createdAt', 'DESC')
            ->setMaxResults($limit)
            ->getQuery()
            ->getResult();

        $result = [];
        foreach ($rows as $row) {
            $result[] = [
                'post' => $row[0],
                'nbComments' => (int) $row['nbComments'],
            ];
        }

        return $result;
    }

    private function applySearchFilter(QueryBuilder $qb, ?string $q): void
    {
        $q = trim((string) $q);
        if ($q === '') {
            return;
        }

        $qb->andWhere('LOWER(p.title) LIKE :q OR LOWER(p.content) LIKE :q')
            ->setParameter('q', '%' . mb_strtolower($q) . '%');
    }
}
