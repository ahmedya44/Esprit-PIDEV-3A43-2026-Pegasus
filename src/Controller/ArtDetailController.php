<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Entity\ArtComment;
use App\Form\ArtCommentType;
use App\Repository\ArtCommentRepository;
use App\Repository\ArtLikeRepository;
use App\Repository\ArtViewRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ArtDetailController extends AbstractController
{
    #[Route('/art/{id}', name: 'art_detail', methods: ['GET', 'POST'])]
    public function show(
        Art $art,
        Request $request,
        ArtViewRepository $viewRepository,
        ArtCommentRepository $commentRepository,
        ArtLikeRepository $likeRepo,
        EntityManagerInterface $em,
    ): Response {
        $artId = $art->getId();
        if ($artId === null) {
            throw $this->createNotFoundException('Artwork not found.');
        }

        // Track view (throttled to once per 30 s per session)
        $session = $request->getSession();
        $sessionKey = 'viewed_art_' . $artId;
        $now = time();
        $ipAddress = (string) ($request->getClientIp() ?? '0.0.0.0');
        if ($now - $session->get($sessionKey, 0) > 30) {
            $viewRepository->addView($artId, $ipAddress);
            $session->set($sessionKey, $now);
        }

        $comment = new ArtComment();
        $form = $this->createForm(ArtCommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user = $this->getUser();
            if ($user === null) {
                $this->addFlash('error', 'You must be logged in to comment.');
                return $this->redirectToRoute('app_login');
            }
            $comment->setArt($art);
            $comment->setUser($user);
            $comment->setUsername((string) ($user->getUsername() ?: $user->getUserIdentifier()));
            $em->persist($comment);
            $em->flush();

            $this->addFlash('success', 'Comment posted.');
            return $this->redirectToRoute('art_detail', ['id' => $artId]);
        }

        $user = $this->getUser();
        $likeIdentifier = $user ? (string) $user->getUserIdentifier() : ($request->getClientIp() ?? 'anon');

        return $this->render('front/art_detail.html.twig', [
            'art'         => $art,
            'totalViews'  => $viewRepository->countByArt($artId),
            'recentViews' => $viewRepository->findByArt($artId),
            'comments'    => $commentRepository->findByArt($artId),
            'commentForm' => $form->createView(),
            'likeCount'   => $likeRepo->countByArt($artId),
            'userLiked'   => $likeRepo->findByUserAndArt($likeIdentifier, $artId) !== null,
        ]);
    }
}
