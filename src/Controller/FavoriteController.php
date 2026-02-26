<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Entity\ArtFavoris;
use App\Repository\ArtFavorisRepository;
use App\Repository\ArtRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

final class FavoriteController extends AbstractController
{
    private ArtFavorisRepository $artFavorisRepository;
    private ArtRepository $artRepository;
    private EntityManagerInterface $entityManager;

    public function __construct(
        ArtFavorisRepository $artFavorisRepository,
        ArtRepository $artRepository,
        EntityManagerInterface $entityManager
    ) {
        $this->artFavorisRepository = $artFavorisRepository;
        $this->artRepository = $artRepository;
        $this->entityManager = $entityManager;
    }

    #[Route('/api/favorites/add', name: 'api_favorites_add', methods: ['POST'])]
    public function addFavorite(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $artId = $data['art_id'] ?? null;
        $artTitle = $data['art_title'] ?? null;
        
        if (!$artId || !$artTitle) {
            return new JsonResponse([
                'success' => false,
                'error' => 'ID et titre de l\'œuvre requis'
            ], 400);
        }

        // Récupérer l'œuvre
        $art = $this->artRepository->find($artId);
        if (!$art) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Œuvre non trouvée'
            ], 404);
        }

        // Identifier l'utilisateur (session IP pour l'instant)
        $userIdentifier = $request->getClientIp() ?? 'anonymous';

        // Vérifier si déjà en favoris
        $existingFavorite = $this->artFavorisRepository->findByUserAndArt($userIdentifier, $artId);
        if ($existingFavorite) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Cette œuvre est déjà dans vos favoris',
                'already_favorite' => true
            ], 400);
        }

        // Créer le favori
        $artFavoris = new ArtFavoris();
        $artFavoris->setArt($art);
        $artFavoris->setUserIdentifier($userIdentifier);
        $artFavoris->setAddedAt(new \DateTime());

        $this->entityManager->persist($artFavoris);
        $this->entityManager->flush();

        return new JsonResponse([
            'success' => true,
            'message' => 'Œuvre ajoutée aux favoris !',
            'favorites_count' => $this->artFavorisRepository->countByUser($userIdentifier),
            'is_favorite' => true
        ]);
    }

    #[Route('/api/favorites/remove', name: 'api_favorites_remove', methods: ['POST'])]
    public function removeFavorite(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $artId = $data['art_id'] ?? null;
        
        if (!$artId) {
            return new JsonResponse([
                'success' => false,
                'error' => 'ID de l\'œuvre requis'
            ], 400);
        }

        // Identifier l'utilisateur
        $userIdentifier = $request->getClientIp() ?? 'anonymous';

        // Trouver le favori à supprimer
        $artFavoris = $this->artFavorisRepository->findByUserAndArt($userIdentifier, $artId);
        if (!$artFavoris) {
            return new JsonResponse([
                'success' => false,
                'error' => 'Ce favori n\'existe pas'
            ], 404);
        }

        // Supprimer le favori
        $this->entityManager->remove($artFavoris);
        $this->entityManager->flush();

        return new JsonResponse([
            'success' => true,
            'message' => 'Œuvre retirée des favoris',
            'favorites_count' => $this->artFavorisRepository->countByUser($userIdentifier),
            'is_favorite' => false
        ]);
    }

    #[Route('/api/favorites/check/{artId}', name: 'api_favorites_check', methods: ['GET'])]
    public function checkFavorite(int $artId, Request $request): JsonResponse
    {
        $userIdentifier = $request->getClientIp() ?? 'anonymous';
        
        $artFavoris = $this->artFavorisRepository->findByUserAndArt($userIdentifier, $artId);
        $isFavorite = $artFavoris !== null;

        return new JsonResponse([
            'success' => true,
            'is_favorite' => $isFavorite,
            'art_id' => $artId
        ]);
    }

    #[Route('/api/favorites', name: 'api_favorites_list', methods: ['GET'])]
    public function listFavorites(Request $request): JsonResponse
    {
        $userIdentifier = $request->getClientIp() ?? 'anonymous';
        
        $artFavorisList = $this->artFavorisRepository->findByUser($userIdentifier);
        
        $favorites = [];
        foreach ($artFavorisList as $artFavoris) {
            $art = $artFavoris->getArt();
            if ($art) {
                $favorites[] = [
                    'id' => $artFavoris->getId(),
                    'art_id' => $art->getId(),
                    'art_title' => $art->getTitle(),
                    'art_image' => $art->getImageUrl() ? '/images/products/' . $art->getImageUrl() : null,
                    'added_at' => $artFavoris->getAddedAt()->format('Y-m-d H:i:s')
                ];
            }
        }

        return new JsonResponse([
            'success' => true,
            'favorites' => $favorites,
            'total_count' => count($favorites)
        ]);
    }

    #[Route('/mes-favoris', name: 'favorites_page', methods: ['GET'])]
    public function favoritesPage(): \Symfony\Component\HttpFoundation\Response
    {
        return $this->render('front/favorites.html.twig', [
            'favorites' => array_values($this->favorites),
            'count' => count($this->favorites)
        ]);
    }
}
