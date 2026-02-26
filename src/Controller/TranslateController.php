<?php

declare(strict_types=1);

namespace App\Controller;

use App\Service\FreeTranslationService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

final class TranslateController extends AbstractController
{
    private FreeTranslationService $translationService;

    public function __construct(FreeTranslationService $translationService)
    {
        $this->translationService = $translationService;
    }

    #[Route('/api/translate', name: 'api_translate', methods: ['POST'])]
    public function translate(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        
        if (!isset($data['text']) || empty($data['text'])) {
            return new JsonResponse(['error' => 'Text is required'], 400);
        }

        $text = $data['text'];
        $targetLang = $data['targetLang'] ?? 'en';
        $sourceLang = $data['sourceLang'] ?? 'fr';

        $translatedText = $this->translationService->translateText($text, $targetLang, $sourceLang);

        if ($translatedText === null) {
            return new JsonResponse([
                'error' => 'All translation services are currently unavailable. Please try again later.',
                'translatedText' => $text // Fallback to original text
            ], 200);
        }

        return new JsonResponse([
            'translatedText' => $translatedText,
            'originalText' => $text
        ]);
    }
}
