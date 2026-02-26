<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use App\Repository\CategorieRepository;
use App\Service\AIService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/produit')]
final class ProduitController extends AbstractController
{
    #[Route(name: 'app_produit_index', methods: ['GET'])]
    public function index(Request $request, ProduitRepository $produitRepository, CategorieRepository $categorieRepository): Response
    {
        $search = $request->query->get('search', '');
        $categorieId = $request->query->get('categorie', '');
        $tri = $request->query->get('tri', '');

        $produits = $produitRepository->findByFilters($search, $categorieId, $tri);
        $categories = $categorieRepository->findAll();

        return $this->render('produit/index.html.twig', [
            'produits' => $produits,
            'categories' => $categories,
            'search' => $search,
            'categorieId' => $categorieId,
            'tri' => $tri,
        ]);
    }

    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($produit);
            $entityManager->flush();

            $this->addFlash('success', 'Produit ajouté avec succès !');
            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }

    #[Route('/ai/description', name: 'app_produit_ai_description', methods: ['POST'])]
    public function aiDescription(Request $request, AIService $aiService): JsonResponse
    {
        try {
            $payload = $request->toArray();
        } catch (\Throwable) {
            return $this->json(['error' => 'Invalid request payload.'], Response::HTTP_BAD_REQUEST);
        }

        $name = trim((string) ($payload['name'] ?? ''));
        if ($name === '') {
            return $this->json(['error' => 'Product name is required.'], Response::HTTP_BAD_REQUEST);
        }

        return $this->json([
            'description' => $aiService->generateProductDescription($name),
        ]);
    }

    #[Route('/{id}', name: 'app_produit_show', methods: ['GET'])]
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_produit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Produit $produit, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            $this->addFlash('success', 'Produit modifié avec succès !');
            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_produit_delete', methods: ['POST'])]
    public function delete(Request $request, Produit $produit, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$produit->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($produit);
            $entityManager->flush();
            $this->addFlash('success', 'Produit supprimé avec succès !');
        }

        return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
    }
}
