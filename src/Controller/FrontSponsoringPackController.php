<?php

namespace App\Controller;

use App\Entity\SponsoringPack;
use App\Entity\ReservationPack;
use App\Form\SponsoringPackType;
use App\Repository\SponsoringPackRepository;
use App\Repository\ReservationPackRepository;
use App\Repository\EvenementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

#[Route('/front/sponsoring-pack')]
#[IsGranted('ROLE_ARTISTE')]
class FrontSponsoringPackController extends AbstractController
{
    #[Route('/', name: 'front_sponsoring_pack_index', methods: ['GET', 'POST'])]
    public function index(Request $request, SponsoringPackRepository $repo, ReservationPackRepository $reservationRepo, EvenementRepository $eventRepo, EntityManagerInterface $em): Response
    {
        $user = $this->getUser();
        $eventId = $request->query->get('eventId');
        $event = $eventId ? $eventRepo->find($eventId) : null;
        
        // Handle New Pack Form
        $newPack = new SponsoringPack();
        if ($event) {
            $newPack->setEvenement($event);
        }

        $form = $this->createForm(SponsoringPackType::class, $newPack, [
            'artiste' => $user,
        ]);
        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($newPack);
            $em->flush();
            return $this->redirectToRoute('front_sponsoring_pack_index', ['eventId' => $eventId]);
        }

        $events = $eventRepo->findBy(['artiste' => $user]);
        $reservations = [];
        
        if ($event) {
            // If we are in an event context, only show reservations for this event
            $reservations = $reservationRepo->findBy(['evenement' => $event], ['dateReservation' => 'DESC']);
        } elseif (!empty($events)) {
            // Otherwise show all reservations for all artist events
            $reservations = $reservationRepo->findBy(['evenement' => $events], ['dateReservation' => 'DESC']);
        }

        $packs = $event ? $repo->findBy(['evenement' => $event]) : $repo->findAll();

        return $this->render('front/sponsoring_pack/index.html.twig', [
            'packs' => $packs,
            'reservations' => $reservations,
            'form' => $form->createView(),
            'event' => $event,
            'eventId' => $eventId,
        ]);
    }

    #[Route('/new', name: 'front_sponsoring_pack_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $em, EvenementRepository $eventRepo): Response
    {
        $eventId = $request->query->get('eventId');
        $event = $eventId ? $eventRepo->find($eventId) : null;

        $pack = new SponsoringPack();
        if ($event) {
            $pack->setEvenement($event);
        }

        $form = $this->createForm(SponsoringPackType::class, $pack, [
            'artiste' => $this->getUser(),
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($pack);
            $em->flush();
            return $this->redirectToRoute('front_sponsoring_pack_index', ['eventId' => $eventId]);
        }

        return $this->render('front/sponsoring_pack/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/edit', name: 'front_sponsoring_pack_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, SponsoringPack $pack, EntityManagerInterface $em): Response
    {
        $form = $this->createForm(SponsoringPackType::class, $pack, [
            'artiste' => $this->getUser(),
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('front_sponsoring_pack_index');
        }

        return $this->render('front/sponsoring_pack/edit.html.twig', [
            'pack' => $pack,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/delete', name: 'front_sponsoring_pack_delete', methods: ['POST'])]
    public function delete(Request $request, SponsoringPack $pack, EntityManagerInterface $em): Response
    {
        if ($this->isCsrfTokenValid('delete'.$pack->getId(), $request->request->get('_token'))) {
            $em->remove($pack);
            $em->flush();
        }
        return $this->redirectToRoute('front_sponsoring_pack_index');
    }

    #[Route('/reservations', name: 'front_sponsoring_reservations', methods: ['GET'])]
    public function reservations(ReservationPackRepository $reservationRepo, EvenementRepository $eventRepo): Response
    {
        $user = $this->getUser();
        // Get events created by this artist
        $events = $eventRepo->findBy(['artiste' => $user]);
        
        // Get all reservations for these events
        $reservations = [];
        if (!empty($events)) {
            $reservations = $reservationRepo->findBy(['evenement' => $events], ['dateReservation' => 'DESC']);
        }

        return $this->render('front/sponsoring_pack/reservations.html.twig', [
            'reservations' => $reservations,
        ]);
    }

    #[Route('/reservations/{id}/accept', name: 'front_sponsoring_reservations_accept', methods: ['POST'])]
    public function acceptReservation(Request $request, ReservationPack $reservation, EntityManagerInterface $em): Response
    {
        if ($this->isCsrfTokenValid('accept'.$reservation->getId(), $request->request->get('_token'))) {
            // Verify this artist owns the event
            if ($reservation->getEvenement()->getArtiste() !== $this->getUser()) {
                throw $this->createAccessDeniedException();
            }

            $reservation->setStatut('acceptée');
            $em->flush();
            $this->addFlash('success', 'La réservation a été acceptée.');
        }

        return $this->redirectToRoute('front_sponsoring_reservations');
    }

    #[Route('/reservations/{id}/reject', name: 'front_sponsoring_reservations_reject', methods: ['POST'])]
    public function rejectReservation(Request $request, ReservationPack $reservation, EntityManagerInterface $em, MailerInterface $mailer): Response
    {
        if ($this->isCsrfTokenValid('reject'.$reservation->getId(), $request->request->get('_token'))) {
            // Verify this artist owns the event
            if ($reservation->getEvenement()->getArtiste() !== $this->getUser()) {
                throw $this->createAccessDeniedException();
            }

            $sponsorEmail = $reservation->getUser()->getEmail();
            $eventName = $reservation->getEvenement()->getTitre();
            $packName = $reservation->getSponsoringPack()->getNomPack();

            // Either set status to 'rejetée' or just remove it
            $em->remove($reservation);
            $em->flush();

            if ($sponsorEmail) {
                $email = (new Email())
                    ->from('contact@feane.com')
                    ->to($sponsorEmail)
                    ->subject('Réponse à votre demande de Sponsoring - ' . $eventName)
                    ->html('
                        <h2>Demande de réservation refusée</h2>
                        <p>Bonjour,</p>
                        <p>Nous vous informons que l\'artiste a malheureusement refusé votre demande de parrainage pour le pack <strong>' . $packName . '</strong> concernant l\'événement <strong>' . $eventName . '</strong>.</p>
                        <p>N\'hésitez pas à consulter nos autres événements !</p>
                        <br>
                        <p>L\'équipe Feane</p>
                    ');

                try {
                    $mailer->send($email);
                } catch (\Exception $e) {
                    // Log error if needed
                }
            }

            $this->addFlash('success', 'La réservation a été rejetée et supprimée.');
        }

        return $this->redirectToRoute('front_sponsoring_reservations');
    }

    #[Route('/export/pdf', name: 'front_sponsoring_export_pdf', methods: ['GET'])]
    public function exportSponsorsPdf(Request $request, EvenementRepository $eventRepo, ReservationPackRepository $reservationRepo): Response
    {
        $eventId = $request->query->get('eventId');
        $event = $eventId ? $eventRepo->find($eventId) : null;

        if (!$event || $event->getArtiste() !== $this->getUser()) {
            throw $this->createAccessDeniedException();
        }

        $reservations = $reservationRepo->findBy(['evenement' => $event], ['dateReservation' => 'DESC']);

        $dompdf = new \Dompdf\Dompdf();
        $dompdf->setPaper('A4', 'portrait');

        $html = $this->renderView('front/sponsoring_pack/sponsors_pdf.html.twig', [
            'evenement' => $event,
            'reservations' => $reservations,
        ]);

        $dompdf->loadHtml($html);
        $dompdf->render();

        $output = $dompdf->output();
        $filename = 'Sponsors_' . $event->getTitre() . '.pdf';

        return new Response($output, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'attachment; filename="' . $filename . '"',
        ]);
    }
}
