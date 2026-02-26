<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Form\ArtType;
use App\Repository\ArtRepository;
use App\Repository\ArtViewRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/back/art', name: 'back_art_')]
final class BackArtController extends AbstractController
{
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(ArtRepository $artRepository, ArtViewRepository $artViewRepository): Response
    {
        $arts = $artRepository->findBy([], ['createdAt' => 'DESC']);

        $viewsByArt = [];
        foreach ($arts as $art) {
            if (!$art instanceof Art || $art->getId() === null) {
                continue;
            }
            $viewsByArt[$art->getId()] = $artViewRepository->countByArt($art->getId());
        }

        return $this->render('back/art/index.html.twig', [
            'arts' => $arts,
            'views_by_art' => $viewsByArt,
            'total_arts' => $artRepository->countTotal(),
            'published_arts' => $artRepository->countPublished(),
            'pending_arts' => $artRepository->countByStatus('en attente'),
            'archived_arts' => $artRepository->countByStatus('archived'),
        ]);
    }

    #[Route('/{id}/edit', name: 'edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ArtType::class, $art);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            $this->addFlash('success', 'Artwork updated.');

            return $this->redirectToRoute('back_art_index');
        }

        return $this->render('back/art/edit.html.twig', [
            'art' => $art,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/status', name: 'update_status', methods: ['POST'])]
    public function updateStatus(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        if (!$this->isCsrfTokenValid('update_status'.$art->getId(), (string) $request->request->get('_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_art_index');
        }

        $status = trim((string) $request->request->get('status', ''));
        $allowed = ['published', 'active', 'en attente', 'archived'];
        if (!in_array($status, $allowed, true)) {
            $this->addFlash('danger', 'Invalid status value.');

            return $this->redirectToRoute('back_art_index');
        }

        $art->setStatus($status);
        $entityManager->flush();
        $this->addFlash('success', 'Artwork status updated.');

        return $this->redirectToRoute('back_art_index');
    }

    #[Route('/{id}/delete', name: 'delete', methods: ['POST'])]
    public function delete(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$art->getId(), (string) $request->request->get('_token'))) {
            $entityManager->remove($art);
            $entityManager->flush();
            $this->addFlash('success', 'Artwork deleted.');
        } else {
            $this->addFlash('danger', 'Invalid request token.');
        }

        return $this->redirectToRoute('back_art_index');
    }
}

