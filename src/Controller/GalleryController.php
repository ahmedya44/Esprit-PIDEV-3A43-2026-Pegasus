<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Art;
use App\Form\ArtType;
use App\Repository\ArtRepository;
use App\Service\FreeTranslationService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class GalleryController extends AbstractController
{
    #[Route('/gallery', name: 'front_gallery', methods: ['GET'])]
    public function index(Request $request, ArtRepository $artRepository): Response
    {
        $search = trim((string) $request->query->get('search', ''));
        $sortBy = (string) $request->query->get('sort', 'recent');
        
        $arts = $artRepository->findBy([], ['createdAt' => 'DESC']);
        
        // Filtrer UNIQUEMENT les œuvres publiées (visible dans front office)
        $arts = array_filter($arts, function($art) {
            return $art->getStatus() === 'active' || $art->getStatus() === 'published';
        });
        
        // Filtrage par recherche (titre/description)
        if (!empty($search)) {
            $arts = array_filter($arts, function($art) use ($search) {
                return stripos((string) $art->getTitle(), $search) !== false || 
                       stripos((string) $art->getDescription(), $search) !== false;
            });
        }
        
        // Tri selon le choix
        switch ($sortBy) {
            case 'oldest':
                $arts = array_reverse($arts);
                break;
            case 'recent':
            default:
                // déjà trié par date décroissante
                break;
        }

        return $this->render('front/gallery.html.twig', [
            'arts' => $arts,
            'search' => $search,
            'sortBy' => $sortBy,
        ]);
    }

    #[Route('/gallery/new', name: 'front_gallery_new', methods: ['GET', 'POST'])]
    public function new(
        Request $request,
        EntityManagerInterface $entityManager,
        FreeTranslationService $translationService
    ): Response
    {
        $art = new Art();
        $form = $this->createForm(ArtType::class, $art);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Ensure EN fields are filled even if front-end translation button fails.
            if (trim((string) $art->getTitleEn()) === '') {
                $translatedTitle = $translationService->translateText((string) $art->getTitle(), 'en', 'fr');
                if (is_string($translatedTitle) && trim($translatedTitle) !== '') {
                    $art->setTitleEn(trim($translatedTitle));
                }
            }
            if (trim((string) $art->getDescriptionEn()) === '') {
                $translatedDescription = $translationService->translateText((string) $art->getDescription(), 'en', 'fr');
                if (is_string($translatedDescription) && trim($translatedDescription) !== '') {
                    $art->setDescriptionEn(trim($translatedDescription));
                }
            }

            $art->setCreatedAt(new \DateTime());
            $art->setStatus('en attente'); // En attente de validation admin
            
            $entityManager->persist($art);
            $entityManager->flush();

            // Message de confirmation
            $this->addFlash('success', '✅ Votre œuvre a été soumise ! Elle sera publiée dès qu\'elle sera validée par l\'administrateur.');
            
            return $this->redirectToRoute('front_gallery');
        }

        return $this->render('front/gallery_new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/gallery/edit/{id}', name: 'front_gallery_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ArtType::class, $art);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            
            // Message de confirmation
            $this->addFlash('success', '✅ Votre œuvre a été modifiée !');
            
            return $this->redirectToRoute('front_gallery');
        }

        return $this->render('front/gallery_edit.html.twig', [
            'form' => $form->createView(),
            'art' => $art,
        ]);
    }

    #[Route('/gallery/delete/{id}', name: 'front_gallery_delete', methods: ['POST'])]
    public function delete(Request $request, Art $art, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$art->getId(), (string) $request->request->get('_token'))) {
            $entityManager->remove($art);
            $entityManager->flush();
            
            // Message de confirmation
            $this->addFlash('success', '✅ Votre œuvre a été supprimée !');
        }

        return $this->redirectToRoute('back_dashboard');
    }
}
