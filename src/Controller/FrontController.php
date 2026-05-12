<?php

declare(strict_types=1);

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class FrontController extends AbstractController
{
    #[Route('/', name: 'front_home', methods: ['GET'])]
    public function home(): Response
    {
        return $this->redirectToRoute('app_produit_index');
    }

    #[Route('/menu', name: 'front_menu', methods: ['GET'])]
    public function menu(): Response
    {
        return $this->redirectToRoute('app_produit_index');
    }

    // Ces anciennes routes ont été retirées car elles rentrent en conflit avec le ProduitController
    // #[Route('/produit', name: 'front_produit_legacy', methods: ['GET'])]
    // #[Route('/produit/', name: 'front_produit_legacy_slash', methods: ['GET'])]
    #[Route('/produits', name: 'front_produits_legacy', methods: ['GET'])]
    #[Route('/produits/', name: 'front_produits_legacy_slash', methods: ['GET'])]
    public function produitsLegacy(): Response
    {
        return $this->redirectToRoute('front_menu');
    }

    #[Route('/about', name: 'front_about', methods: ['GET'])]
    public function about(): Response
    {
        return $this->redirectToRoute('app_produit_index');
    }

    #[Route('/book', name: 'front_book', methods: ['GET'])]
    public function book(): Response
    {
        return $this->redirectToRoute('app_produit_index');
    }
}
