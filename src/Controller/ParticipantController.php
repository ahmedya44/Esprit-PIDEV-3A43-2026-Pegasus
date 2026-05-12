<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Participation;
use App\Form\ParticipationType;
use App\Repository\EvenementRepository;
use App\Repository\ParticipationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

#[Route('/back/participants')]
class ParticipantController extends AbstractController
{
    #[Route('/', name: 'app_participant_index', methods: ['GET'])]
    public function index(EvenementRepository $evenementRepository): Response
    {
        return $this->render('back/participant/index.html.twig', [
            'evenements' => $evenementRepository->findAll(),
        ]);
    }

    #[Route('/{id}/export/pdf', name: 'app_participant_export_pdf', methods: ['GET'])]
    public function exportPdf(?Evenement $evenement): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Événement introuvable.');
            return $this->redirectToRoute('app_participant_index');
        }

        $options = new Options();
        $options->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($options);

        $html = $this->renderView('back/participant/pdf.html.twig', [
            'evenement' => $evenement,
        ]);

        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();

        return new Response($dompdf->output(), 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'attachment; filename="participants_'.$evenement->getTitre().'.pdf"',
        ]);
    }

    #[Route('/stats', name: 'app_participant_stats', methods: ['GET'])]
    public function stats(EvenementRepository $evenementRepository, ParticipationRepository $participationRepository): Response
    {
        $evenements = $evenementRepository->findAll();
        $totalParticipationsCount = count($participationRepository->findAll());
        $totalEvenements = count($evenements);
        
        $totalRevenue = 0;
        $upcomingEventsCount = 0;
        $now = new \DateTime();
        
        $statusCounts = [
            'acceptée' => 0,
            'en attente' => 0,
            'refusée' => 0
        ];

        $topEvent = null;
        $maxParticipants = -1;

        $labels = [];
        $data = [];
        
        foreach ($evenements as $evenement) {
            $count = count($evenement->getParticipations());
            $labels[] = $evenement->getTitre();
            $data[] = $count;

            // Revenue calculation
            $totalRevenue += ($count * (float)$evenement->getPrix());

            // Upcoming events
            if ($evenement->getDate() > $now) {
                $upcomingEventsCount++;
            }

            // Status counts
            $statut = $evenement->getStatut();
            if (isset($statusCounts[$statut])) {
                $statusCounts[$statut]++;
            }

            // Top event
            if ($count > $maxParticipants) {
                $maxParticipants = $count;
                $topEvent = $evenement;
            }
        }

        return $this->render('back/participant/stats.html.twig', [
            'totalEvenements' => $totalEvenements,
            'totalParticipations' => $totalParticipationsCount,
            'totalRevenue' => $totalRevenue,
            'upcomingEventsCount' => $upcomingEventsCount,
            'statusCounts' => $statusCounts,
            'topEvent' => $topEvent,
            'labels' => $labels,
            'data' => $data,
        ]);
    }
    #[Route('/evenement/{id}', name: 'app_participant_list', methods: ['GET'])]
    public function listParticipants(?Evenement $evenement): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Événement introuvable.');
            return $this->redirectToRoute('app_participant_index');
        }

        return $this->render('back/participant/list.html.twig', [
            'evenement' => $evenement,
            'participations' => $evenement->getParticipations()
        ]);
    }
}
