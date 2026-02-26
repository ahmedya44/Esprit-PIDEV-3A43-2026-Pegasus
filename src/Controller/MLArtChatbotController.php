<?php

declare(strict_types=1);

namespace App\Controller;

use App\Service\MLArtChatbotService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

final class MLArtChatbotController extends AbstractController
{
    private MLArtChatbotService $mlChatbotService;

    public function __construct(MLArtChatbotService $mlChatbotService)
    {
        $this->mlChatbotService = $mlChatbotService;
    }

    #[Route('/api/ml-chatbot', name: 'api_ml_chatbot', methods: ['POST'])]
    public function chat(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $question = $data['question'] ?? '';
        
        if (empty($question)) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Question requise',
                'stats' => $this->mlChatbotService->getLearningStats()
            ], 400);
        }

        $response = $this->mlChatbotService->generateResponse($question);

        return new JsonResponse([
            'success' => true,
            'question' => $question,
            'response' => $response['response'],
            'category' => $response['category'],
            'confidence' => $response['confidence'],
            'method' => $response['method'],
            'learning_type' => $response['learning_type'],
            'timestamp' => date('Y-m-d H:i:s'),
            'service' => 'ML Art Chatbot - Vraie IA avec Apprentissage'
        ]);
    }

    #[Route('/api/ml-chatbot/learn', name: 'api_ml_chatbot_learn', methods: ['POST'])]
    public function learn(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $input = $data['input'] ?? '';
        $output = $data['output'] ?? '';
        
        if (empty($input) || empty($output)) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Input et output requis'
            ], 400);
        }

        $this->mlChatbotService->addTrainingExample($input, $output);

        return new JsonResponse([
            'success' => true,
            'message' => 'Apprentissage réussi ! Le chatbot a appris cette nouvelle interaction.',
            'stats' => $this->mlChatbotService->getLearningStats(),
            'service' => 'ML Art Chatbot - Apprentissage en cours'
        ]);
    }

    #[Route('/api/ml-chatbot/stats', name: 'api_ml_chatbot_stats', methods: ['GET'])]
    public function getStats(): JsonResponse
    {
        $stats = $this->mlChatbotService->getLearningStats();

        return new JsonResponse([
            'success' => true,
            'stats' => $stats,
            'service' => 'ML Art Chatbot - Machine Learning Stats',
            'learning_types' => [
                'supervised_learning' => 'Apprentissage supervisé avec exemples',
                'reinforcement_learning' => 'Apprentissage par renforcement des interactions',
                'user_taught' => 'Enseignement direct par les utilisateurs'
            ]
        ]);
    }

    #[Route('/api/ml-chatbot/info', name: 'api_ml_chatbot_info', methods: ['GET'])]
    public function getInfo(): JsonResponse
    {
        return new JsonResponse([
            'success' => true,
            'name' => 'ML Art Chatbot Pegasus',
            'type' => 'Machine Learning avec Apprentissage Automatique',
            'algorithms' => [
                'Tokenisation de texte',
                'Vectorisation de mots',
                'Calcul de similarité cosinus',
                'Apprentissage supervisé',
                'Apprentissage par renforcement'
            ],
            'features' => [
                'Apprentissage continu des interactions',
                'Amélioration des réponses avec le temps',
                'Mémorisation des patterns',
                'Adaptation aux utilisateurs',
                'Sauvegarde des connaissances apprises'
            ],
            'learning_process' => 'Le système apprend de chaque interaction et s\'améliore continuellement',
            'data_persistence' => 'Les connaissances apprises sont sauvegardées dans des fichiers JSON',
            'version' => '2.0 ML',
            'free' => true,
            'real_ai' => true
        ]);
    }
}
