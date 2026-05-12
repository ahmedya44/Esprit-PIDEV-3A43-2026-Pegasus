<?php

namespace App\Controller;

use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/favoris')]
final class FavorisController extends AbstractController
{
    #[Route('', name: 'app_favoris_index', methods: ['GET'])]
    public function index(SessionInterface $session, ProduitRepository $produitRepository): Response
    {
        $favorisIds = $session->get('favoris', []);
        $produits = [];

        if (!empty($favorisIds)) {
            $produits = $produitRepository->findBy(['id' => $favorisIds]);
        }

        return $this->render('favoris/index.html.twig', [
            'produits' => $produits,
        ]);
    }

    #[Route('/toggle/{id}', name: 'app_favoris_toggle', methods: ['POST'])]
    public function toggle(int $id, SessionInterface $session, ProduitRepository $produitRepository): JsonResponse
    {
        $produit = $produitRepository->find($id);
        if (!$produit) {
            return new JsonResponse(['error' => 'Produit introuvable'], Response::HTTP_NOT_FOUND);
        }

        $favoris = $session->get('favoris', []);

        if (in_array($id, $favoris)) {
            // Retirer des favoris
            $favoris = array_diff($favoris, [$id]);
            $isFavorite = false;
            $message = 'Produit retiré des favoris';
        } else {
            // Ajouter aux favoris
            $favoris[] = $id;
            $isFavorite = true;
            $message = 'Produit ajouté aux favoris';
        }

        $session->set('favoris', array_values($favoris));

        return new JsonResponse([
            'isFavorite' => $isFavorite,
            'message' => $message,
            'count' => count($favoris)
        ]);
    }

    #[Route('/supprimer/{id}', name: 'app_favoris_supprimer', methods: ['POST'])]
    public function supprimer(int $id, SessionInterface $session): Response
    {
        $favoris = $session->get('favoris', []);
        $favoris = array_diff($favoris, [$id]);
        $session->set('favoris', array_values($favoris));

        $this->addFlash('success', 'Produit retiré des favoris.');
        return $this->redirectToRoute('app_favoris_index');
    }
}
