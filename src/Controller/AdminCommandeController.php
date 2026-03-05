<?php

namespace App\Controller;

use App\Repository\CommandeRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/admin/commandes')]
// #[IsGranted('ROLE_ADMIN')]
final class AdminCommandeController extends AbstractController
{
    #[Route('/', name: 'admin_commandes_index', methods: ['GET'])]
    public function index(Request $request, CommandeRepository $commandeRepository, PaginatorInterface $paginator): Response
    {
        $qb = $commandeRepository->createQueryBuilder('c')->orderBy('c.id', 'DESC');
        $pagination = $paginator->paginate($qb, $request->query->getInt('page', 1), 15);

        return $this->render('back/commandes.html.twig', [
            'pagination' => $pagination,
        ]);
    }
}
