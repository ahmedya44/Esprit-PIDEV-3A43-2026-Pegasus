<?php

declare(strict_types=1);

namespace App\Controller;

use App\Service\InspirationService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;

final class InspirationController extends AbstractController
{
    private InspirationService $inspirationService;

    public function __construct(InspirationService $inspirationService)
    {
        $this->inspirationService = $inspirationService;
    }

    #[Route('/api/daily-inspiration', name: 'api_daily_inspiration', methods: ['GET'])]
    public function getDailyInspiration(): JsonResponse
    {
        $inspiration = $this->inspirationService->getDailyInspiration();

        return new JsonResponse([
            'success' => true,
            'inspiration' => $inspiration,
            'date' => date('Y-m-d'),
            'day_of_year' => date('z'),
            'service' => 'Pegasus Inspiration - 100% Gratuit'
        ]);
    }

    #[Route('/api/random-inspiration', name: 'api_random_inspiration', methods: ['GET'])]
    public function getRandomInspiration(): JsonResponse
    {
        $inspiration = $this->inspirationService->getRandomInspiration();

        return new JsonResponse([
            'success' => true,
            'inspiration' => $inspiration,
            'service' => 'Pegasus Inspiration - 100% Gratuit'
        ]);
    }
}
