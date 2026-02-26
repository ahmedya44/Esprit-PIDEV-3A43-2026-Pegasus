<?php

declare(strict_types=1);

namespace App\Controller;

use App\Repository\ArtRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;

final class StatsController extends AbstractController
{
    #[Route('/api/stats', name: 'api_stats', methods: ['GET'])]
    public function getStats(ArtRepository $artRepository): JsonResponse
    {
        $total = $artRepository->countTotal();
        $published = $artRepository->countPublished();
        $pending = $artRepository->countByStatus('en attente');
        $archived = $artRepository->countByStatus('archived');

        return $this->json([
            'total' => $total,
            'published' => $published,
            'pending' => $pending,
            'archived' => $archived,
            'last_updated' => date('Y-m-d H:i:s')
        ]);
    }
}
