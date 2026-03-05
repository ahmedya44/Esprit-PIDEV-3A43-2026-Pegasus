<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Participation;
use App\Entity\ReservationPack;
use App\Entity\User;
use App\Form\EvenementType;
use App\Repository\EvenementRepository;
use App\Repository\ParticipationRepository;
use App\Repository\ReservationPackRepository;
use App\Repository\SponsoringPackRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;
use Symfony\Component\String\Slugger\SluggerInterface;

final class FrontEvenementController extends AbstractController
{
    #[Route('/evenements', name: 'front_evenements_index', methods: ['GET'])]
    public function index(EvenementRepository $evenementRepository): Response
    {
        $allEvents = $evenementRepository->findAll();
        $user = $this->getUser();
        $userId = \is_object($user) && method_exists($user, 'getId') ? $user->getId() : null;
        $events = [];

        foreach ($allEvents as $event) {
            if (\in_array($event->getStatut(), ['acceptée', 'acceptee'], true)) {
                $events[] = $event;
                continue;
            }
            $artiste = $event->getArtiste();
            $artisteId = $artiste ? $artiste->getId() : null;
            if ($userId !== null && $artisteId !== null && $artisteId === $userId) {
                $events[] = $event;
            }
        }

        return $this->render('front/evenement/index.html.twig', [
            'evenements' => $events,
        ]);
    }

    #[Route('/evenements/new', name: 'front_evenements_new', methods: ['GET', 'POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function new(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('image')->getData();
            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();
                try {
                    $imageFile->move($this->getParameter('evenements_directory'), $newFilename);
                    $evenement->setImage($newFilename);
                } catch (FileException) {
                    $this->addFlash('error', 'Image upload failed.');
                }
            }

            $currentUser = $this->getUser();
            if ($currentUser instanceof User) {
                $evenement->setArtiste($currentUser);
            }
            $entityManager->persist($evenement);
            $entityManager->flush();

            $this->addFlash('success', 'Event created and pending validation.');

            return $this->redirectToRoute('front_evenements_index');
        }

        return $this->render('front/evenement/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/evenements/{id}', name: 'front_evenements_show', methods: ['GET'])]
    public function show(?Evenement $evenement): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Event not found.');
            return $this->redirectToRoute('front_evenements_index');
        }

        return $this->render('front/evenement/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }

    #[Route('/evenements/{id}/edit', name: 'front_evenements_edit', methods: ['GET', 'POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function edit(Request $request, ?Evenement $evenement, EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Event not found.');
            return $this->redirectToRoute('front_evenements_index');
        }

        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('image')->getData();
            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();
                try {
                    $imageFile->move($this->getParameter('evenements_directory'), $newFilename);
                    $evenement->setImage($newFilename);
                } catch (FileException) {
                    $this->addFlash('error', 'Image upload failed.');
                }
            }

            $entityManager->flush();
            $this->addFlash('success', 'Event updated.');

            return $this->redirectToRoute('front_evenements_index');
        }

        return $this->render('front/evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/evenements/{id}', name: 'front_evenements_delete', methods: ['POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function delete(Request $request, ?Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        if ($evenement && $this->isCsrfTokenValid('delete'.$evenement->getId(), (string) $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
            $this->addFlash('success', 'Event deleted.');
        }

        return $this->redirectToRoute('front_evenements_index');
    }

    #[Route('/evenements/{id}/sponsor', name: 'front_evenements_sponsor', methods: ['GET'])]
    public function sponsor(?Evenement $evenement, SponsoringPackRepository $packRepository, ReservationPackRepository $reservationRepository): Response
    {
        if (!$evenement) {
            return $this->redirectToRoute('front_evenements_index');
        }

        $reservedPackIds = [];
        $user = $this->getUser();
        if ($user) {
            $reservations = $reservationRepository->findBy([
                'evenement' => $evenement,
                'user' => $user,
            ]);
            foreach ($reservations as $reservation) {
                $pack = $reservation->getSponsoringPack();
                if ($pack && $pack->getId() !== null) {
                    $reservedPackIds[$pack->getId()] = $reservation;
                }
            }
        }

        return $this->render('front/evenement/sponsor.html.twig', [
            'evenement' => $evenement,
            'packs' => $packRepository->findAll(),
            'reservedPackIds' => $reservedPackIds,
        ]);
    }

    #[Route('/evenements/{id}/sponsor/reserve/{packId}', name: 'front_evenements_sponsor_reserve', methods: ['POST'])]
    #[IsGranted('ROLE_SPONSOR')]
    public function reserve(
        Evenement $evenement,
        int $packId,
        SponsoringPackRepository $packRepository,
        ReservationPackRepository $reservationRepository,
        EntityManagerInterface $entityManager,
        Request $request,
        MailerInterface $mailer
    ): Response {
        if (!$this->isCsrfTokenValid('reserve'.$packId, (string) $request->request->get('_token'))) {
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $user = $this->getUser();
        $pack = $packRepository->find($packId);
        if (!$user instanceof User || !$pack) {
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $existing = $reservationRepository->findOneBy([
            'evenement' => $evenement,
            'user' => $user,
            'sponsoringPack' => $pack,
        ]);

        if (!$existing) {
            $reservation = new ReservationPack();
            $reservation->setEvenement($evenement);
            $reservation->setUser($user);
            $reservation->setSponsoringPack($pack);
            $entityManager->persist($reservation);
            $entityManager->flush();

            $artiste = $evenement->getArtiste();
            if ($artiste && $artiste->getEmail()) {
                try {
                    $mailer->send((new Email())
                        ->from('contact@feane.com')
                        ->to((string) $artiste->getEmail())
                        ->subject('New sponsor reservation')
                        ->text('A sponsor reserved a pack for your event.'));
                } catch (\Throwable) {
                    // Ignore email transport failure for reservation flow.
                }
            }
        }

        return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/sponsor/cancel/{packId}', name: 'front_evenements_sponsor_cancel', methods: ['POST'])]
    #[IsGranted('ROLE_SPONSOR')]
    public function cancelReserve(
        Evenement $evenement,
        int $packId,
        SponsoringPackRepository $packRepository,
        ReservationPackRepository $reservationRepository,
        EntityManagerInterface $entityManager,
        Request $request
    ): Response {
        if (!$this->isCsrfTokenValid('cancel'.$packId, (string) $request->request->get('_token'))) {
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $user = $this->getUser();
        $pack = $packRepository->find($packId);
        if (!$user instanceof User || !$pack) {
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $existing = $reservationRepository->findOneBy([
            'evenement' => $evenement,
            'user' => $user,
            'sponsoringPack' => $pack,
        ]);

        if ($existing) {
            $entityManager->remove($existing);
            $entityManager->flush();
        }

        return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/participate', name: 'front_evenements_participate', methods: ['POST'])]
    public function participate(?Evenement $evenement, EntityManagerInterface $entityManager, ParticipationRepository $participationRepository): Response
    {
        if (!$evenement) {
            return $this->redirectToRoute('front_evenements_index');
        }

        $user = $this->getUser();
        if (!$user instanceof User || $this->isGranted('ROLE_ARTISTE') || $evenement->getCapaciteMax() <= 0) {
            return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
        }

        $existing = $participationRepository->findOneBy(['user' => $user, 'evenement' => $evenement]);
        if (!$existing) {
            $participation = new Participation();
            $participation->setUser($user);
            $participation->setEvenement($evenement);
            $participation->setCreatedAt(new \DateTime());
            $evenement->setCapaciteMax($evenement->getCapaciteMax() - 1);
            $entityManager->persist($participation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/unparticipate', name: 'front_evenements_unparticipate', methods: ['POST'])]
    public function unparticipate(?Evenement $evenement, EntityManagerInterface $entityManager, ParticipationRepository $participationRepository): Response
    {
        if (!$evenement) {
            return $this->redirectToRoute('front_evenements_index');
        }

        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_login');
        }

        $existing = $participationRepository->findOneBy(['user' => $user, 'evenement' => $evenement]);
        if ($existing) {
            $entityManager->remove($existing);
            $evenement->setCapaciteMax($evenement->getCapaciteMax() + 1);
            $entityManager->flush();
        }

        return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/participants', name: 'front_evenements_participants', methods: ['GET'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function participants(?Evenement $evenement): Response
    {
        $user = $this->getUser();
        $userId = \is_object($user) && method_exists($user, 'getId') ? $user->getId() : null;
        $artiste = $evenement?->getArtiste();
        $artisteId = $artiste ? $artiste->getId() : null;

        if (!$evenement || $userId === null || $artisteId === null || $artisteId !== $userId) {
            return $this->redirectToRoute('front_evenements_index');
        }

        return $this->render('front/evenement/participants.html.twig', [
            'evenement' => $evenement,
            'participations' => $evenement->getParticipations(),
        ]);
    }
}


