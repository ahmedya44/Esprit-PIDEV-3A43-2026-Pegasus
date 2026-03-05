<?php

declare(strict_types=1);

namespace App\Controller;

use App\Service\ArtChatbotService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

final class ArtChatbotController extends AbstractController
{
    private ArtChatbotService $chatbotService;

    public function __construct(ArtChatbotService $chatbotService)
    {
        $this->chatbotService = $chatbotService;
    }

    #[Route('/api/art-chatbot', name: 'api_art_chatbot', methods: ['POST'])]
    public function chat(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $question = $data['question'] ?? '';
        
        if (empty($question)) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Question requise',
                'suggestions' => $this->chatbotService->getSuggestedQuestions()
            ], 400);
        }

        $response = $this->chatbotService->generateResponse($question);

        return new JsonResponse([
            'success' => true,
            'question' => $question,
            'response' => $response['response'],
            'intent' => $response['intent'],
            'entities' => $response['entities'],
            'confidence' => $response['confidence'],
            'timestamp' => $response['timestamp'],
            'service' => 'Art Chatbot - IA Artistique Locale'
        ]);
    }

    #[Route('/api/chatbot-suggestions', name: 'api_chatbot_suggestions', methods: ['GET'])]
    public function getSuggestions(): JsonResponse
    {
        return new JsonResponse([
            'success' => true,
            'suggestions' => $this->chatbotService->getSuggestedQuestions(),
            'service' => 'Art Chatbot - IA Artistique Locale'
        ]);
    }

    #[Route('/api/chatbot-info', name: 'api_chatbot_info', methods: ['GET'])]
    public function getInfo(): JsonResponse
    {
        return new JsonResponse([
            'success' => true,
            'name' => 'Pegasus Art Chatbot',
            'description' => 'Chatbot artistique intelligent pour répondre à vos questions sur l\'art',
            'capabilities' => [
                'Définitions de styles artistiques',
                'Explications de techniques',
                'Histoire de l\'art',
                'Conseils pour artistes',
                'Comparaisons de mouvements'
            ],
            'knowledge_base_size' => '4 catégories principales',
            'response_patterns' => '5 types d\'intentions',
            'confidence_calculation' => 'Basée sur les entités détectées',
            'service' => 'Art Chatbot - IA Artistique Locale',
            'version' => '1.0',
            'free' => true
        ]);
    }
}
