<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Repository\ArtLikeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

final class ArtLikeController extends AbstractController
{
    #[Route('/api/art/{id}/like', name: 'api_art_like', methods: ['POST'])]
    public function toggle(Art $art, Request $request, ArtLikeRepository $likeRepo): JsonResponse
    {
        $user = $this->getUser();
        $identifier = $user ? (string) $user->getUserIdentifier() : ($request->getClientIp() ?? 'anon');

        $liked = $likeRepo->toggle($art, $identifier);
        $count = $likeRepo->countByArt((int) $art->getId());

        return new JsonResponse(['liked' => $liked, 'count' => $count]);
    }

    #[Route('/api/art/{id}/like', name: 'api_art_like_state', methods: ['GET'])]
    public function state(Art $art, Request $request, ArtLikeRepository $likeRepo): JsonResponse
    {
        $user = $this->getUser();
        $identifier = $user ? (string) $user->getUserIdentifier() : ($request->getClientIp() ?? 'anon');

        $liked = $likeRepo->findByUserAndArt($identifier, (int) $art->getId()) !== null;
        $count = $likeRepo->countByArt((int) $art->getId());

        return new JsonResponse(['liked' => $liked, 'count' => $count]);
    }
}
