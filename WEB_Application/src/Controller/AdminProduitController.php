<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/back/market/products')]
#[IsGranted('ROLE_ADMIN')]
final class AdminProduitController extends AbstractController
{
    #[Route('/', name: 'admin_produit_attente', methods: ['GET'])]
    public function index(Request $request, ProduitRepository $produitRepository, PaginatorInterface $paginator): Response
    {
        $qb = $produitRepository->createQueryBuilder('p')->orderBy('p.id', 'DESC');
        $pagination = $paginator->paginate($qb, $request->query->getInt('page', 1), 15);

        return $this->render('back/produits_attente.html.twig', [
            'pagination' => $pagination,
        ]);
    }

    #[Route('/{id}/accepter', name: 'admin_produit_accepter', methods: ['POST'])]
    public function accepter(Produit $produit, EntityManagerInterface $entityManager): Response
    {
        if ($produit->getStatut() === 'en_attente') {
            $produit->setStatut('disponible');
            $entityManager->flush();
            $this->addFlash('success', 'Le produit "' . $produit->getNom() . '" a été accepté et est maintenant visible dans la boutique.');
        }

        return $this->redirectToRoute('admin_produit_attente');
    }

    #[Route('/{id}/refuser', name: 'admin_produit_refuser', methods: ['POST'])]
    public function refuser(Produit $produit, EntityManagerInterface $entityManager): Response
    {
        if ($produit->getStatut() === 'en_attente') {
            $produit->setStatut('refuse');
            $entityManager->flush();
            $this->addFlash('warning', 'Le produit "' . $produit->getNom() . '" a été refusé.');
        }

        return $this->redirectToRoute('admin_produit_attente');
    }
}
