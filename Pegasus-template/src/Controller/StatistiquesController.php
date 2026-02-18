<?php

namespace App\Controller;

use App\Repository\EvenementRepository;
use App\Repository\ParticipantRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class StatistiquesController extends AbstractController
{
    #[Route('/statistiques', name: 'app_statistiques')]
    public function index(EvenementRepository $evenementRepo, ParticipantRepository $participantRepo): Response
    {
        $evenements = $evenementRepo->findAll();
        $participants = $participantRepo->findAll();

        $totalRevenue = 0;
        $totalActiveParticipants = 0;
        $statusCounts = [
            'Confirmé' => 0,
            'En attente' => 0,
            'Annulé' => 0
        ];

        $eventLabels = [];
        $eventData = [];
        $eventOccupancy = [];

        foreach ($evenements as $event) {
            $activeCount = $event->getNbInscritsActifs();
            $totalActiveParticipants += $activeCount;
            
            // Revenue only from Confirmed (or active as per count logic)
            // Using active count for revenue assuming active means potentially paid/reserved
            $totalRevenue += ($activeCount * $event->getPrix());

            $eventLabels[] = $event->getTitre();
            $eventData[] = $activeCount;
            
            $occupancy = $event->getCapaciteMax() > 0 
                ? round(($activeCount / $event->getCapaciteMax()) * 100) 
                : 0;
            $eventOccupancy[] = [
                'titre' => $event->getTitre(),
                'percentage' => $occupancy,
                'active' => $activeCount,
                'max' => $event->getCapaciteMax()
            ];
        }

        foreach ($participants as $participant) {
            $statut = $participant->getStatut();
            if (isset($statusCounts[$statut])) {
                $statusCounts[$statut]++;
            }
        }

        return $this->render('statistiques/index.html.twig', [
            'totalRevenue' => $totalRevenue,
            'totalActiveParticipants' => $totalActiveParticipants,
            'totalEvents' => count($evenements),
            'statusCounts' => $statusCounts,
            'eventLabels' => $eventLabels,
            'eventData' => $eventData,
            'eventOccupancy' => $eventOccupancy,
        ]);
    }
}
