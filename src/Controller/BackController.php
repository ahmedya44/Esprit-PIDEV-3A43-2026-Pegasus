<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Repository\ArtRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class BackController extends AbstractController
{
    #[Route('/admin', name: 'back_')]
    public function dashboard(): Response
    {
        return $this->redirectToRoute('back_dashboard');
    }

    #[Route('/admin/dashboard', name: 'back_dashboard', methods: ['GET'])]
    public function adminDashboard(ArtRepository $artRepository): Response
    {
        $arts = $artRepository->findBy([], ['createdAt' => 'DESC']);
        
        return $this->render('back/art/index.html.twig', [
            'arts' => $arts,
        ]);
    }

    #[Route('/admin/art/{id}/edit', name: 'back_art_edit', methods: ['GET', 'POST'])]
    public function editArt(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(\App\Form\ArtType::class, $art);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            $this->addFlash('success', '✅ Œuvre modifiée avec succès !');
            return $this->redirectToRoute('back_dashboard');
        }

        return $this->render('back/art/edit.html.twig', [
            'form' => $form->createView(),
            'art' => $art,
        ]);
    }

    #[Route('/admin/art/{id}/delete', name: 'back_art_delete', methods: ['POST'])]
    public function deleteArt(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete' . $art->getId(), $request->request->get('_token'))) {
            $entityManager->remove($art);
            $entityManager->flush();
            $this->addFlash('success', '✅ Œuvre supprimée avec succès !');
        }

        return $this->redirectToRoute('back_dashboard');
    }

    #[Route('/admin/art/{id}/status', name: 'back_art_update_status', methods: ['POST'])]
    public function updateStatus(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('update_status' . $art->getId(), $request->request->get('_token'))) {
            $status = $request->request->get('status');
            $art->setStatus($status);
            $entityManager->flush();
            
            $statusMessage = match($status) {
                'active' => '✅ Œuvre publiée !',
                'en attente' => '⏳ Œuvre mise en attente',
                'archived' => '📁 Œuvre archivée',
                default => '🔄 Statut mis à jour'
            };
            
            $this->addFlash('success', $statusMessage);
        }

        return $this->redirectToRoute('back_dashboard');
    }
}