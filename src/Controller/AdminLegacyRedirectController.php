<?php

declare(strict_types=1);

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[IsGranted('ROLE_ADMIN')]
final class AdminLegacyRedirectController extends AbstractController
{
    #[Route('/admin', name: 'admin_legacy_index', methods: ['GET'])]
    #[Route('/admin/', name: 'admin_legacy_index_slash', methods: ['GET'])]
    #[Route('/admin/dashboard', name: 'admin_legacy_dashboard', methods: ['GET'])]
    public function __invoke(): RedirectResponse
    {
        return $this->redirectToRoute('back_dashboard');
    }
}
