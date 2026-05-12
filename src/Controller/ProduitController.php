<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use App\Repository\CategorieRepository;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/produit')]
final class ProduitController extends AbstractController
{
    #[Route(name: 'app_produit_index', methods: ['GET'])]
    public function index(Request $request, ProduitRepository $produitRepository, CategorieRepository $categorieRepository, PaginatorInterface $paginator): Response
    {
        $search = $request->query->get('search', '');
        $categorieId = $request->query->get('categorie', '');
        $tri = $request->query->get('tri', '');

        $qb = $produitRepository->getQueryBuilderForFilters($search, $categorieId, $tri);
        $pagination = $paginator->paginate($qb, $request->query->getInt('page', 1), 6, [
            'defaultSortFieldName' => ['p.id'],
            'defaultSortDirection' => 'desc',
            'routeParams' => array_filter([
                'search' => $search,
                'categorie' => $categorieId,
                'tri' => $tri,
            ]),
        ]);

        return $this->render('produit/index.html.twig', [
            'pagination' => $pagination,
            'categories' => $categorieRepository->findAll(),
            'search' => $search,
            'categorieId' => $categorieId,
            'tri' => $tri,
        ]);
    }

    #[Route('/mes-produits', name: 'app_produit_mes_produits', methods: ['GET'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function mesProduits(Request $request, ProduitRepository $produitRepository, PaginatorInterface $paginator): Response
    {
        $qb = $produitRepository->createQueryBuilder('p')->orderBy('p.id', 'DESC');
        $pagination = $paginator->paginate($qb, $request->query->getInt('page', 1), 10);

        return $this->render('produit/mes_produits.html.twig', [
            'pagination' => $pagination,
        ]);
    }

    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $produit = new Produit();
        // Le produit est en attente de validation par défaut
        $produit->setStatut('en_attente');

        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Force the status to 'en_attente' when an artiste creates a product
            $produit->setStatut('en_attente');
            
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
        if (!$this->isGranted('ROLE_ADMIN') && !$this->isGranted('ROLE_ARTISTE')) {
            throw $this->createAccessDeniedException('Only artists and admins can edit products.');
        }

        // Read 'from' from POST (hidden field on form submit) or GET (first page load)
        $from = $request->request->get('_from', $request->query->get('from', ''));
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Automatic stock/status synchronisation
            if ($produit->getStock() <= 0) {
                // If stock runs out and product is currently available → rupture
                if ($produit->getStatut() === 'disponible') {
                    $produit->setStatut('rupture');
                }
                // Force stock to 0 if negative
                $produit->setStock(0);
            } else {
                // If stock restored → mark as available (only if was 'rupture')
                if ($produit->getStatut() === 'rupture') {
                    $produit->setStatut('disponible');
                }
            }

            $entityManager->flush();

            $this->addFlash('success', 'Produit modifié avec succès !');

            if ($from === 'admin') {
                return $this->redirectToRoute('admin_produit_attente', [], Response::HTTP_SEE_OTHER);
            }
            if ($from === 'artiste') {
                return $this->redirectToRoute('app_produit_mes_produits', [], Response::HTTP_SEE_OTHER);
            }
            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
            'from' => $from,
        ]);
    }

    #[Route('/{id}', name: 'app_produit_delete', methods: ['POST'])]
    public function delete(Request $request, Produit $produit, EntityManagerInterface $entityManager): Response
    {
        if (!$this->isGranted('ROLE_ADMIN') && !$this->isGranted('ROLE_ARTISTE')) {
            throw $this->createAccessDeniedException('Only artists and admins can delete products.');
        }

        if ($this->isCsrfTokenValid('delete'.$produit->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($produit);
            $entityManager->flush();
            $this->addFlash('success', 'Produit supprimé avec succès !');
        }

        return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
    }
}
