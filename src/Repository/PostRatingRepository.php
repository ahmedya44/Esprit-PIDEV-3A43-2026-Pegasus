<?php

namespace App\Repository;

use App\Entity\Post;
use App\Entity\PostRating;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<PostRating>
 */
class PostRatingRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, PostRating::class);
    }

    /**
     * @return array{avg: float, count: int}
     */
    public function getSummaryForPost(Post $post): array
    {
        $row = $this->createQueryBuilder('r')
            ->select('AVG(r.value) AS avgRating, COUNT(r.id) AS ratingCount')
            ->where('r.post = :post')
            ->setParameter('post', $post)
            ->getQuery()
            ->getSingleResult();

        return [
            'avg' => isset($row['avgRating']) ? round((float) $row['avgRating'], 2) : 0.0,
            'count' => (int) ($row['ratingCount'] ?? 0),
        ];
    }

    /**
     * @param array<int, int> $postIds
     * @return array<int, array{avg: float, count: int}>
     */
    public function getSummariesForPostIds(array $postIds): array
    {
        $postIds = array_values(array_filter(array_map('intval', $postIds), static fn (int $id): bool => $id > 0));
        if ($postIds === []) {
            return [];
        }

        $rows = $this->createQueryBuilder('r')
            ->select('IDENTITY(r.post) AS postId, AVG(r.value) AS avgRating, COUNT(r.id) AS ratingCount')
            ->where('r.post IN (:postIds)')
            ->setParameter('postIds', $postIds)
            ->groupBy('r.post')
            ->getQuery()
            ->getArrayResult();

        $result = [];
        foreach ($rows as $row) {
            $postId = (int) $row['postId'];
            $result[$postId] = [
                'avg' => round((float) $row['avgRating'], 2),
                'count' => (int) $row['ratingCount'],
            ];
        }

        return $result;
    }
}

