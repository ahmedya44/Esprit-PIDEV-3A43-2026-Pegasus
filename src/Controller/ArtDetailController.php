<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Repository\ArtViewRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ArtDetailController extends AbstractController
{
    #[Route('/art/{id}', name: 'art_detail', methods: ['GET'])]
    public function show(Art $art, Request $request, ArtViewRepository $viewRepository, EntityManagerInterface $entityManager): Response
    {
        $session = $request->getSession();
        $sessionKey = 'viewed_art_' . $art->getId();
        $now = time();
        
        // Vérifier si déjà vu dans les dernières 30 secondes
        $lastViewTime = $session->get($sessionKey, 0);
        if ($now - $lastViewTime > 30) {
            // Enregistrer la vue seulement si 30 secondes écoulées
            $viewRepository->addView($art->getId(), $request->getClientIp());
            $session->set($sessionKey, $now);
        }
        
        // Récupérer les vues récentes
        $recentViews = $viewRepository->findByArt($art->getId());
        $totalViews = $viewRepository->countByArt($art->getId());
        
        return $this->render('front/art_detail.html.twig', [
            'art' => $art,
            'totalViews' => $totalViews,
            'recentViews' => $recentViews,
        ]);
    }
}
