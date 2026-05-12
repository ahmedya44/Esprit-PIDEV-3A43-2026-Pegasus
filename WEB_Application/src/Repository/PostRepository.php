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

    public function createFrontListQueryBuilder(?string $q = null, ?string $status = null, ?User $viewer = null, bool $isAdmin = false, string $sort = 'newest'): QueryBuilder
    {
        $qb = $this->createQueryBuilder('p')
            ->leftJoin('p.commentaires', 'c')
            ->addSelect('COUNT(c.id) AS HIDDEN commentCount')
            ->groupBy('p.id');

        if (!$isAdmin) {
            $nonPublicStatuses = [Post::STATUS_HIDDEN, Post::STATUS_IN_PROGRESS, Post::STATUS_DENIED];

            if ($viewer instanceof User) {
                $qb->where(
                    $qb->expr()->orX(
                        $qb->expr()->notIn('p.status', ':nonPublic'),
                        $qb->expr()->eq('p.owner', ':viewer'),
                        $qb->expr()->isMemberOf(':viewer', 'p.allowedViewers')
                    )
                )
                ->setParameter('nonPublic', $nonPublicStatuses)
                ->setParameter('viewer', $viewer);
            } else {
                $qb->where($qb->expr()->notIn('p.status', ':nonPublic'))
                    ->setParameter('nonPublic', $nonPublicStatuses);
            }

            $qb->andWhere('p.bannedByAdmin = false');
        }

        if (in_array($status, Post::ALLOWED_STATUSES, true)) {
            $qb->andWhere('p.status = :status')
                ->setParameter('status', $status);
        }

        $this->applySearchFilter($qb, $q);

        return match ($sort) {
            'oldest'   => $qb->orderBy('p.createdAt', 'ASC'),
            'title_az' => $qb->orderBy('p.title', 'ASC'),
            'title_za' => $qb->orderBy('p.title', 'DESC'),
            'comments' => $qb->orderBy('commentCount', 'DESC')->addOrderBy('p.createdAt', 'DESC'),
            default    => $qb->orderBy('p.createdAt', 'DESC'),
        };
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
     * @return Post[]
     */
    public function findPendingRequests(?string $requestType = null): array
    {
        $qb = $this->createQueryBuilder('p')
            ->where('p.status = :status')
            ->setParameter('status', Post::STATUS_IN_PROGRESS)
            ->orderBy('p.createdAt', 'DESC');

        if (in_array($requestType, [Post::REQUEST_TYPE_CREATE, Post::REQUEST_TYPE_EDIT], true)) {
            $qb->andWhere('p.requestType = :requestType')
                ->setParameter('requestType', $requestType);
        }

        return $qb->getQuery()->getResult();
    }

    /**
     * @return Post[]
     */
    public function findByOwner(User $user): array
    {
        return $this->createQueryBuilder('p')
            ->where('p.owner = :user')
            ->setParameter('user', $user)
            ->orderBy('p.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
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
            Post::STATUS_IN_PROGRESS => 0,
            Post::STATUS_OPEN        => 0,
            Post::STATUS_CLOSED      => 0,
            Post::STATUS_HIDDEN      => 0,
            Post::STATUS_DENIED      => 0,
        ];

        foreach ($rows as $row) {
            if (array_key_exists($row['status'], $result)) {
                $result[$row['status']] = (int) $row['nb'];
            }
        }

        return $result;
    }

    public function countBanned(): int
    {
        return (int) $this->createQueryBuilder('p')
            ->select('COUNT(p.id)')
            ->where('p.bannedByAdmin = true')
            ->getQuery()
            ->getSingleScalarResult();
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
                'post'       => $row[0],
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

        $qb->andWhere('LOWER(p.title) LIKE :q OR LOWER(p.content) LIKE :q OR LOWER(p.authorName) LIKE :q')
            ->setParameter('q', '%' . mb_strtolower($q) . '%');
    }
}
