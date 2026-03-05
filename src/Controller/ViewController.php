<?php

declare(strict_types=1);

namespace App\Controller;

use App\Repository\ArtViewRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ViewController extends AbstractController
{
    #[Route('/art/{id}/view', name: 'record_view', methods: ['POST'])]
    public function recordView(int $id, Request $request, ArtViewRepository $viewRepository): JsonResponse
    {
        $session = $request->getSession();
        $sessionKey = 'viewed_art_' . $id;
        $now = time();
        $ipAddress = (string) ($request->getClientIp() ?? '0.0.0.0');
        
        // Vérifier si déjà vu dans les dernières 30 secondes
        $lastViewTime = $session->get($sessionKey, 0);
        if ($now - $lastViewTime > 30) {
            // Enregistrer la vue seulement si 30 secondes écoulées
            $viewRepository->addView($id, $ipAddress);
            $session->set($sessionKey, $now);
            
            // Retourner le nouveau nombre de vues
            $totalViews = $viewRepository->countByArt($id);
            return $this->json(['success' => true, 'viewsCount' => $totalViews]);
        }
        
        // Si déjà vu récemment, retourner le nombre actuel
        $totalViews = $viewRepository->countByArt($id);
        return $this->json(['success' => false, 'message' => 'Vue déjà enregistrée récemment', 'viewsCount' => $totalViews]);
    }

    #[Route('/api/art/{id}/views', name: 'get_views', methods: ['GET'])]
    public function getViews(int $id, ArtViewRepository $viewRepository): JsonResponse
    {
        $totalViews = $viewRepository->countByArt($id);
        return $this->json(['viewsCount' => $totalViews]);
    }
}
