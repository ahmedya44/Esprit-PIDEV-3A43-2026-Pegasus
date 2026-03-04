<?php

namespace App\Controller;

use App\Service\OpenAiService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/ai')]
#[IsGranted('ROLE_ARTISTE')]
class AiController extends AbstractController
{
    #[Route('/generate-description', name: 'ai_generate_description', methods: ['POST'])]
    public function generateDescription(Request $request, OpenAiService $openAiService): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $title = $data['title'] ?? '';

        if (empty($title)) {
            return new JsonResponse(['error' => 'Le titre est requis.'], 400);
        }

        try {
            $description = $openAiService->generateDescription($title);
            return new JsonResponse(['description' => $description]);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], 500);
        }
    }
}
