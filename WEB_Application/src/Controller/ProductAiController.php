<?php

declare(strict_types=1);

namespace App\Controller;

use App\Repository\ProduitRepository;
use App\Service\AIService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ProductAiController extends AbstractController
{
    #[Route('/ai/product-advisor', name: 'app_ai_product_advisor', methods: ['POST'])]
    public function productAdvisor(Request $request, AIService $aiService, ProduitRepository $produitRepository): JsonResponse
    {
        try {
            $data = json_decode($request->getContent() ?: '{}', true, 512, \JSON_THROW_ON_ERROR);
        } catch (\JsonException) {
            return new JsonResponse(['answer' => 'Requete invalide.'], Response::HTTP_BAD_REQUEST);
        }

        if (!is_array($data)) {
            return new JsonResponse(['answer' => 'Requete invalide.'], Response::HTTP_BAD_REQUEST);
        }

        $productName = trim((string) ($data['productName'] ?? $data['name'] ?? ''));
        $productDescription = trim((string) ($data['productDescription'] ?? $data['description'] ?? ''));
        $question = trim((string) ($data['question'] ?? $data['userQuestion'] ?? ''));

        if ($question === '') {
            return new JsonResponse(['answer' => 'Posez une question pour recevoir un conseil.'], Response::HTTP_BAD_REQUEST);
        }

        $catalogContext = null;
        if ($productName === '' && $productDescription === '') {
            $catalogContext = $produitRepository->getCatalogSummary();
        }

        return new JsonResponse([
            'answer' => $aiService->getProductAdvisorAnswer($productName, $productDescription, $question, $catalogContext),
        ]);
    }
}
