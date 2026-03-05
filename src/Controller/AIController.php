<?php

namespace App\Controller;

use App\Repository\ProduitRepository;
use App\Service\AIService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class AIController extends AbstractController
{
    #[Route('/ai/product-advisor', name: 'app_ai_product_advisor', methods: ['POST'])]
    public function productAdvisor(Request $request, AIService $aiService, ProduitRepository $produitRepository): JsonResponse
    {
        $productName = '';
        $productDescription = '';
        $question = '';

        $content = $request->getContent();
        if ($content !== '' && $content !== false) {
            try {
                $data = json_decode($content, true, 512, \JSON_THROW_ON_ERROR);
                if (\is_array($data)) {
                    $productName = (string) ($data['productName'] ?? $data['name'] ?? '');
                    $productDescription = (string) ($data['productDescription'] ?? $data['description'] ?? '');
                    $question = (string) ($data['question'] ?? $data['userQuestion'] ?? '');
                }
            } catch (\JsonException $e) {
                return new JsonResponse(['answer' => 'Invalid request.'], Response::HTTP_BAD_REQUEST);
            }
        }

        $question = trim($question);
        if ($question === '') {
            return new JsonResponse(['answer' => 'Please ask a question.'], Response::HTTP_BAD_REQUEST);
        }

        $catalogContext = null;
        if ($productName === '' && $productDescription === '') {
            $catalogContext = $produitRepository->getCatalogSummary();
        }

        $answer = $aiService->getProductAdvisorAnswer($productName, $productDescription, $question, $catalogContext);

        return new JsonResponse(['answer' => $answer]);
    }

    /**
     * Debug route (dev only): test Gemini API and return raw response to diagnose errors.
     */
    #[Route('/ai/debug-gemini', name: 'app_ai_debug_gemini', methods: ['GET'])]
    public function debugGemini(HttpClientInterface $httpClient): JsonResponse
    {
        if ($this->getParameter('kernel.environment') !== 'dev') {
            return new JsonResponse(['error' => 'Not available'], Response::HTTP_NOT_FOUND);
        }

        $key = $this->getParameter('gemini_key');
        if (!$key || $key === '' || str_starts_with((string) $key, 'your_')) {
            return new JsonResponse(['error' => 'GEMINI_API_KEY not set or placeholder'], Response::HTTP_BAD_REQUEST);
        }

        $url = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=' . urlencode($key);
        $payload = [
            'contents' => [['parts' => [['text' => 'Reply with exactly: Hello']]]],
            'generationConfig' => ['maxOutputTokens' => 50],
        ];

        try {
            $response = $httpClient->request('POST', $url, [
                'headers' => ['Content-Type' => 'application/json'],
                'json' => $payload,
                'timeout' => 15,
            ]);
            $status = $response->getStatusCode();
            $body = $response->getContent(false);
            $data = json_decode($body, true);
            return new JsonResponse([
                'status' => $status,
                'body' => $data,
                'key_preview' => substr($key, 0, 8) . '...',
            ]);
        } catch (\Throwable $e) {
            return new JsonResponse([
                'error' => $e->getMessage(),
                'class' => \get_class($e),
            ], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
