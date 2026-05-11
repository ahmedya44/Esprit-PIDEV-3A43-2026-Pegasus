<?php

declare(strict_types=1);

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Twig\Error\LoaderError;

#[Route('/admin', name: 'back_')]
final class BackController extends AbstractController
{
    #[Route('', name: 'dashboard_root', methods: ['GET'])]
    public function dashboardRoot(): RedirectResponse
    {
        return $this->redirectToRoute('back_dashboard');
    }

    #[Route('/dashboard', name: 'dashboard', methods: ['GET'])]
    public function dashboard(): Response
    {
        return $this->render('back/index.html.twig');
    }

    #[Route('/{path}.html', name: 'page', requirements: ['path' => '[A-Za-z0-9_\-/]+'], methods: ['GET'])]
    public function page(string $path): Response
    {
        if (str_contains($path, '..')) {
            throw $this->createNotFoundException();
        }

        $template = sprintf('back/%s.html.twig', $path);

        try {
            return $this->render($template);
        } catch (LoaderError) {
            throw $this->createNotFoundException(sprintf('Back template "%s" was not found.', $template));
        }
    }
}