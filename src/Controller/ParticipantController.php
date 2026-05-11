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

#[Route('/admin/participants')]
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
    public function stats(EvenementRepository $evenementRepository, ParticipationRepository $participationRepository, \App\Repository\ReservationPackRepository $reservationRepository): Response
    {
        $evenements = $evenementRepository->findAll();
        $participations = $participationRepository->findAll();
        $reservations = $reservationRepository->findAll();

        $totalEvenements = count($evenements);
        $totalParticipationsCount = count($participations);
        $totalSponsors = count($reservations);
        
        $totalRevenue = 0;
        $packPopularity = [];
        foreach ($reservations as $res) {
            $totalRevenue += $res->getSponsoringPack()->getPrix();
            
            $packName = $res->getSponsoringPack()->getNomPack();
            if (!isset($packPopularity[$packName])) {
                $packPopularity[$packName] = 0;
            }
            $packPopularity[$packName]++;
        }

        $eventsByVenue = [];
        $participantsByEvent = [];
        foreach ($evenements as $ev) {
            $lieu = $ev->getLieu() ?: 'Inconnu';
            if (!isset($eventsByVenue[$lieu])) {
                $eventsByVenue[$lieu] = 0;
            }
            $eventsByVenue[$lieu]++;

            $participantsByEvent[$ev->getTitre()] = count($ev->getParticipations());
        }

        // Sort Top 5 Events by Participants
        arsort($participantsByEvent);
        $top5Events = array_slice($participantsByEvent, 0, 5, true);

        return $this->render('back/participant/stats.html.twig', [
            'totalEvenements' => $totalEvenements,
            'totalParticipations' => $totalParticipationsCount,
            'totalSponsors' => $totalSponsors,
            'totalRevenue' => $totalRevenue,
            
            // For Chart: Events by Venue (Pie)
            'venueLabels' => array_keys($eventsByVenue),
            'venueData' => array_values($eventsByVenue),

            // For Chart: Top 5 Events (Bar)
            'topEventsLabels' => array_keys($top5Events),
            'topEventsData' => array_values($top5Events),

            // For Chart: Pack Popularity (Bar)
            'packLabels' => array_keys($packPopularity),
            'packData' => array_values($packPopularity),
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
