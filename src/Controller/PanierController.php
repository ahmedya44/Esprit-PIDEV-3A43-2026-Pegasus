<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\LignePanier;
use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/panier')]
final class PanierController extends AbstractController
{
    #[Route('', name: 'app_panier_index', methods: ['GET'])]
    public function index(SessionInterface $session, PanierRepository $panierRepository): Response
    {
        $panierId = $session->get('panier_id');
        $panier = null;
        $lignes = [];

        if ($panierId) {
            $panier = $panierRepository->find($panierId);
            if ($panier) {
                $lignes = $panier->getLignePaniers();
            }
        }

        return $this->render('panier/index.html.twig', [
            'panier' => $panier,
            'lignes' => $lignes,
        ]);
    }

    #[Route('/ajouter/{id}', name: 'app_panier_ajouter', methods: ['POST'])]
public function ajouter(int $id, Request $request, SessionInterface $session, ProduitRepository $produitRepository, PanierRepository $panierRepository, EntityManagerInterface $entityManager): Response
{
    $produit = $produitRepository->find($id);

    if (!$produit) {
        $this->addFlash('error', 'Produit introuvable.');
        return $this->redirectToRoute('app_produit_index');
    }

    $quantite = (int) $request->request->get('quantite', 1);

    // Récupérer ou créer le panier
    $panierId = $session->get('panier_id');
    $panier = null;

    if ($panierId) {
        $panier = $panierRepository->find($panierId);
    }

    if (!$panier) {
        $panier = new Panier();
        $panier->setDateCreation(new \DateTimeImmutable());
        $panier->setTotal(0);
        $entityManager->persist($panier);
        $entityManager->flush();
        $session->set('panier_id', $panier->getId());
    }

    // Vérifier si le produit est déjà dans le panier
    $ligneExistante = null;
    foreach ($panier->getLignePaniers() as $ligne) {
        if ($ligne->getProduit()->getId() === $produit->getId()) {
            $ligneExistante = $ligne;
            break;
        }
    }

    if ($ligneExistante) {
        // Produit déjà dans le panier → on augmente la quantité
        $ligneExistante->setQuantite($ligneExistante->getQuantite() + $quantite);
    } else {
        // Nouveau produit → on crée une nouvelle ligne
        $ligne = new LignePanier();
        $ligne->setPanier($panier);
        $ligne->setProduit($produit);
        $ligne->setQuantite($quantite);
        $ligne->setPrixUnitaire($produit->getPrix());
        $entityManager->persist($ligne);
    }

    // Flush pour sauvegarder la ligne
    $entityManager->flush();

    // Recharger le panier depuis la BDD pour avoir toutes les lignes à jour
    $entityManager->refresh($panier);

    // Recalculer le total avec les données fraîches
    $total = 0;
    foreach ($panier->getLignePaniers() as $ligne) {
        $total += $ligne->getPrixUnitaire() * $ligne->getQuantite();
    }
    $panier->setTotal($total);

    // Sauvegarder le total
    $entityManager->flush();

    $this->addFlash('success', 'Produit ajouté au panier !');
    return $this->redirectToRoute('app_panier_index');
}

    #[Route('/supprimer/{id}', name: 'app_panier_supprimer', methods: ['POST'])]
    public function supprimer(int $id, SessionInterface $session, PanierRepository $panierRepository, EntityManagerInterface $entityManager): Response
    {
        $panierId = $session->get('panier_id');
        $panier = $panierRepository->find($panierId);

        if ($panier) {
            $ligneASupprimer = null;
            foreach ($panier->getLignePaniers() as $ligne) {
                if ($ligne->getId() === $id) {
                    $ligneASupprimer = $ligne;
                    break;
                }
            }

            if ($ligneASupprimer) {
                $entityManager->remove($ligneASupprimer);
            }

            // Recalculer le total AVANT le flush
            $total = 0;
            foreach ($panier->getLignePaniers() as $ligne) {
                if ($ligne->getId() !== $id) {
                    $total += $ligne->getPrixUnitaire() * $ligne->getQuantite();
                }
            }
            $panier->setTotal($total);

            // Un seul flush
            $entityManager->flush();
        }

        $this->addFlash('success', 'Produit supprimé du panier !');
        return $this->redirectToRoute('app_panier_index');
    }

    #[Route('/vider', name: 'app_panier_vider', methods: ['POST'])]
    public function vider(SessionInterface $session, PanierRepository $panierRepository, EntityManagerInterface $entityManager): Response
    {
        $panierId = $session->get('panier_id');
        $panier = $panierRepository->find($panierId);

        if ($panier) {
            foreach ($panier->getLignePaniers() as $ligne) {
                $entityManager->remove($ligne);
            }
            $panier->setTotal(0);
            $entityManager->flush();
            $session->remove('panier_id');
        }

        $this->addFlash('success', 'Panier vidé !');
        return $this->redirectToRoute('app_produit_index');
    }
}