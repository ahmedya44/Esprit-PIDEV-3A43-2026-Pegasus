<?php

namespace App\Controller;

use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use Stripe\Checkout\Session;
use Stripe\Stripe;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

#[Route('/commande')]
class PaymentController extends AbstractController
{
    #[Route('/checkout', name: 'app_payment_checkout', methods: ['POST'])]
    public function checkout(SessionInterface $session, PanierRepository $panierRepository, ProduitRepository $produitRepository): Response
    {
        $panierId = $session->get('panier_id');
        if (!$panierId) {
            return $this->redirectToRoute('app_panier_index');
        }

        $panier = $panierRepository->find($panierId);
        if (!$panier || count($panier->getLignePaniers()) === 0) {
            return $this->redirectToRoute('app_panier_index');
        }

        Stripe::setApiKey($this->getParameter('stripe_secret_key'));

        $lineItems = [];
        foreach ($panier->getLignePaniers() as $ligne) {
            $lineItems[] = [
                'price_data' => [
                    'currency' => 'eur',
                    'product_data' => [
                        'name' => $ligne->getProduit()->getNom(),
                    ],
                    'unit_amount' => $ligne->getPrixUnitaire() * 100, // Stripe expects amounts in cents
                ],
                'quantity' => $ligne->getQuantite(),
            ];
        }

        $checkoutSession = Session::create([
            'payment_method_types' => ['card'],
            'line_items' => $lineItems,
            'mode' => 'payment',
            'success_url' => $this->generateUrl('app_payment_success', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('app_payment_cancel', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return $this->redirect($checkoutSession->url, 303);
    }

    #[Route('/payment/success', name: 'app_payment_success')]
    public function success(SessionInterface $session, PanierRepository $panierRepository, EntityManagerInterface $entityManager): Response
    {
        $commande = null;
        $panierId = $session->get('panier_id');
        if ($panierId) {
            $panier = $panierRepository->find($panierId);
            if ($panier && count($panier->getLignePaniers()) > 0) {
                // 1. Créer une nouvelle Commande
                $commande = new \App\Entity\Commande();
                $commande->setDateCommande(new \DateTimeImmutable());
                $commande->setStatut('payée');
                $commande->setTotal($panier->getTotal());
                $entityManager->persist($commande);

                // 2. Transformer chaque LignePanier en LigneCommande et mettre à jour le stock
                foreach ($panier->getLignePaniers() as $lignePanier) {
                    $produit = $lignePanier->getProduit();
                    
                    // Création de la ligne de commande
                    $ligneCommande = new \App\Entity\LigneCommande();
                    $ligneCommande->setCommande($commande);
                    $ligneCommande->setProduit($produit);
                    $ligneCommande->setQuantite($lignePanier->getQuantite());
                    $ligneCommande->setPrixUnitaire($lignePanier->getPrixUnitaire());
                    $entityManager->persist($ligneCommande);

                    // Mise à jour du stock du produit
                    $nouveauStock = $produit->getStock() - $lignePanier->getQuantite();
                    $produit->setStock(max(0, $nouveauStock));

                    // Si le stock tombe à 0, on change le statut en 'rupture'
                    if ($produit->getStock() <= 0) {
                        $produit->setStatut('rupture');
                    }

                    // Supprimer la ligne du panier car l'achat est fait
                    $entityManager->remove($lignePanier);
                }

                // 3. Réinitialiser le panier
                $panier->setTotal(0);
                $entityManager->flush();
                
                // Nettoyer la session
                $session->remove('panier_id');
            }
        }

        return $this->render('payment/success.html.twig', [
            'commande' => $commande,
        ]);
    }

    #[Route('/payment/cancel', name: 'app_payment_cancel')]
    public function cancel(): Response
    {
        return $this->render('payment/cancel.html.twig');
    }
}
