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
        return $this->render('front/index.html.twig');
    }

    #[Route('/menu', name: 'front_menu', methods: ['GET'])]
    public function menu(): Response
    {
        return $this->render('front/menu.html.twig');
    }

    #[Route('/about', name: 'front_about', methods: ['GET'])]
    public function about(): Response
    {
        return $this->render('front/about.html.twig');
    }

    #[Route('/book', name: 'front_book', methods: ['GET'])]
    public function book(): Response
    {
        return $this->render('front/book.html.twig');
    }
}
