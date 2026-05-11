<?php

namespace App\Controller;

use App\Repository\EvenementRepository;
use App\Repository\ReservationPackRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/sponsors')]
class AdminSponsorController extends AbstractController
{
    #[Route('/', name: 'app_admin_sponsor_index', methods: ['GET'])]
    public function index(EvenementRepository $evenementRepository, ReservationPackRepository $reservationRepository): Response
    {
        return $this->render('back/sponsor/index.html.twig', [
            'evenements' => $evenementRepository->findAll(),
            'reservations' => $reservationRepository->findAll(),
        ]);
    }

    #[Route('/evenement/{id}', name: 'app_admin_sponsor_list', methods: ['GET'])]
    public function listSponsors(int $id, EvenementRepository $evenementRepository, ReservationPackRepository $reservationRepository): Response
    {
        $evenement = $evenementRepository->find($id);
        if (!$evenement) {
            $this->addFlash('error', 'Événement introuvable.');
            return $this->redirectToRoute('app_admin_sponsor_index');
        }

        $reservations = $reservationRepository->findBy(['evenement' => $evenement]);

        return $this->render('back/sponsor/list.html.twig', [
            'evenement' => $evenement,
            'reservations' => $reservations,
        ]);
    }
}
