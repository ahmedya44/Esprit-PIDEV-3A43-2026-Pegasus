<?php

declare(strict_types=1);

namespace App\Repository;

use App\Entity\Art;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Art>
 */
final class ArtRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Art::class);
    }

    public function countByStatus(string $status): int
    {
        return $this->count(['status' => $status]);
    }

    public function countTotal(): int
    {
        return $this->count([]);
    }

    public function countPublished(): int
    {
        $published = $this->count(['status' => 'published']);
        $active = $this->count(['status' => 'active']);
        return $published + $active;
    }
}
