<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\LigneCommande;
use App\Repository\PanierRepository;
use App\Repository\CommandeRepository;
use App\Service\EmailService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/commande')]
final class CommandeController extends AbstractController
{
    #[Route('/recapitulatif', name: 'app_commande_recapitulatif', methods: ['GET'])]
    public function recapitulatif(SessionInterface $session, PanierRepository $panierRepository): Response
    {
        $panierId = $session->get('panier_id');

        if (!$panierId) {
            $this->addFlash('error', 'Votre panier est vide !');
            return $this->redirectToRoute('app_panier_index');
        }

        $panier = $panierRepository->find($panierId);

        if (!$panier || $panier->getLignePaniers()->isEmpty()) {
            $this->addFlash('error', 'Votre panier est vide !');
            return $this->redirectToRoute('app_panier_index');
        }

        return $this->render('commande/recapitulatif.html.twig', [
            'panier' => $panier,
            'lignes' => $panier->getLignePaniers(),
            'panierId' => $panierId,
        ]);
    }

    #[Route('/confirmer', name: 'app_commande_confirmer', methods: ['POST'])]
    public function confirmer(Request $request, SessionInterface $session, PanierRepository $panierRepository, EntityManagerInterface $entityManager, EmailService $emailService): Response
    {
        $panierId = $request->request->get('panier_id') ?? $session->get('panier_id');

        if (!$panierId) {
            $this->addFlash('error', 'Votre panier est vide !');
            return $this->redirectToRoute('app_panier_index');
        }

        $panier = $panierRepository->find($panierId);

        if (!$panier || $panier->getLignePaniers()->isEmpty()) {
            $this->addFlash('error', 'Votre panier est vide !');
            return $this->redirectToRoute('app_panier_index');
        }

        // Créer la commande
        $commande = new Commande();
        $commande->setDateCommande(new \DateTimeImmutable());
        $commande->setStatut('en_attente');
        $commande->setTotal($panier->getTotal());
        $entityManager->persist($commande);

        // Créer les lignes de commande
        foreach ($panier->getLignePaniers() as $lignePanier) {
            $ligneCommande = new LigneCommande();
            $ligneCommande->setCommande($commande);
            $ligneCommande->setProduit($lignePanier->getProduit());
            $ligneCommande->setQuantite($lignePanier->getQuantite());
            $ligneCommande->setPrixUnitaire($lignePanier->getPrixUnitaire());
            $entityManager->persist($ligneCommande);
        }

        $entityManager->flush();

        // Envoyer email de confirmation
        try {
            $emailService->sendNouvelleCommande(
                'admin@pegasus.com',
                $commande->getId(),
                $commande->getTotal()
            );
        } catch (\Exception $e) {
            // Email non bloquant
        }

        // Vider le panier
        foreach ($panier->getLignePaniers() as $ligne) {
            $entityManager->remove($ligne);
        }
        $panier->setTotal(0);
        $entityManager->flush();
        $session->remove('panier_id');

        $this->addFlash('success', 'Commande passée avec succès !');
        return $this->redirectToRoute('app_commande_confirmation', ['id' => $commande->getId()]);
    }

    #[Route('/confirmation/{id}', name: 'app_commande_confirmation', methods: ['GET'])]
    public function confirmation(int $id, CommandeRepository $commandeRepository): Response
    {
        $commande = $commandeRepository->find($id);

        if (!$commande) {
            return $this->redirectToRoute('app_produit_index');
        }

        return $this->render('commande/confirmation.html.twig', [
            'commande' => $commande,
        ]);
    }
}