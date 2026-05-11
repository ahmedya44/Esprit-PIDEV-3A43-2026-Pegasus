<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Participation;
use App\Entity\SponsoringPack;
use App\Entity\ReservationPack;
use App\Repository\EvenementRepository;
use App\Repository\ParticipationRepository;
use App\Repository\SponsoringPackRepository;
use App\Repository\ReservationPackRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

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

    #[Route('/evenements', name: 'front_evenements_index', methods: ['GET'])]
    public function evenements(\App\Repository\EvenementRepository $evenementRepository): Response
    {
        $allEvents = $evenementRepository->findAll();
        $user = $this->getUser();
        $filteredEvents = [];
        
        foreach ($allEvents as $event) {
            if ($this->isGranted('ROLE_ADMIN')) {
                $filteredEvents[] = $event;
            } elseif (in_array(mb_strtolower($event->getStatut(), 'UTF-8'), ['acceptée', 'acceptee', 'accepté', 'accepte'])) {
                $filteredEvents[] = $event;
            } elseif ($user && $event->getArtiste() === $user) {
                $filteredEvents[] = $event;
            }
        }

        return $this->render('front/evenement/index.html.twig', [
            'evenements' => $filteredEvents,
        ]);
    }

    #[Route('/evenements/new', name: 'front_evenements_new', methods: ['GET', 'POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function new(Request $request, \Doctrine\ORM\EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        $evenement = new \App\Entity\Evenement();
        $form = $this->createForm(\App\Form\EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var \Symfony\Component\HttpFoundation\File\UploadedFile $imageFile */
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();

                try {
                    $imageFile->move(
                        $this->getParameter('evenements_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                $evenement->setImage($newFilename);
            }

            /** @var \App\Entity\User $user */
            $user = $this->getUser();
            $evenement->setArtiste($user);

            $entityManager->persist($evenement);
            $entityManager->flush();

            $this->addFlash('success', 'Votre événement a été créé avec succès. Il est actuellement en attente de validation par l\'administrateur.');

            return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('front/evenement/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/evenements/{id}', name: 'front_evenements_show', methods: ['GET'])]
    public function show(?\App\Entity\Evenement $evenement): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'L\'événement que vous cherchez n\'existe plus ou est introuvable.');
            return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('front/evenement/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }

    #[Route('/evenements/{id}/edit', name: 'front_evenements_edit', methods: ['GET', 'POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function edit(Request $request, ?\App\Entity\Evenement $evenement, \Doctrine\ORM\EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'L\'événement est introuvable.');
            return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
        }

        $form = $this->createForm(\App\Form\EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var \Symfony\Component\HttpFoundation\File\UploadedFile $imageFile */
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();

                try {
                    $imageFile->move(
                        $this->getParameter('evenements_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                $evenement->setImage($newFilename);
            }

            $entityManager->flush();

            return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('front/evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/evenements/{id}', name: 'front_evenements_delete', methods: ['POST'])]
    #[IsGranted('ROLE_ARTISTE')]
    public function delete(Request $request, ?\App\Entity\Evenement $evenement, \Doctrine\ORM\EntityManagerInterface $entityManager): Response
    {
        if (!$evenement) {
            return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
        }

        if ($this->isCsrfTokenValid('delete'.$evenement->getId(), $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
            $this->addFlash('success', 'Votre événement a été supprimé.');
        }

        return $this->redirectToRoute('front_evenements_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/evenements/{id}/sponsor', name: 'front_evenements_sponsor', methods: ['GET'])]
    public function sponsor(?\App\Entity\Evenement $evenement, SponsoringPackRepository $packRepository, ReservationPackRepository $reservationRepository): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Cet événement est introuvable.');
            return $this->redirectToRoute('front_evenements_index');
        }
        $user = $this->getUser();
        
        if ($user && $evenement->getArtiste() === $user) {
            return $this->redirectToRoute('front_sponsoring_pack_index', ['eventId' => $evenement->getId()]);
        }

        $packs = $packRepository->findAll();
        
        $reservations = [];
        if ($user) {
            $userReservations = $reservationRepository->findBy([
                'evenement' => $evenement,
                'user' => $user
            ]);
            foreach ($userReservations as $res) {
                $reservations[$res->getSponsoringPack()->getId()] = $res;
            }
        }

        return $this->render('front/evenement/sponsor.html.twig', [
            'evenement' => $evenement,
            'packs' => $packs,
            'reservedPackIds' => $reservations
        ]);
    }

    #[Route('/evenements/{id}/sponsor/reserve/{packId}', name: 'front_evenements_sponsor_reserve', methods: ['POST'])]
    #[IsGranted('ROLE_SPONSOR')]
    public function reserveSponsorPack(
        \App\Entity\Evenement $evenement, 
        int $packId,
        SponsoringPackRepository $packRepository,
        ReservationPackRepository $reservationRepository,
        EntityManagerInterface $entityManager,
        Request $request,
        MailerInterface $mailer
    ): Response {
        $user = $this->getUser();
        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        if (!$this->isCsrfTokenValid('reserve'.$packId, $request->request->get('_token'))) {
            $this->addFlash('error', 'Token CSRF Invalide.');
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $pack = $packRepository->find($packId);
        if (!$pack) {
            $this->addFlash('error', 'Pack introuvable.');
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        // Check if already reserved
        $existing = $reservationRepository->findOneBy([
            'evenement' => $evenement,
            'user' => $user,
            'sponsoringPack' => $pack
        ]);

        if ($existing) {
            $this->addFlash('warning', 'Vous avez déjà réservé ce pack pour cet événement.');
        } else {
            // Synchronize with the 'sponsor' table if necessary to avoid FK errors
            $conn = $entityManager->getConnection();
            $res = $conn->fetchOne('SELECT id FROM sponsor WHERE id = ?', [$user->getId()]);
            
            if (!$res) {
                // Insert a placeholder record in the sponsor table
                $conn->executeStatement(
                    'INSERT INTO sponsor (id, company_name, verified) VALUES (?, ?, ?)',
                    [$user->getId(), $user->getUserIdentifier(), 1]
                );
            }

            $reservation = new ReservationPack();
            $reservation->setEvenement($evenement);
            $reservation->setUser($user);
            $reservation->setSponsoringPack($pack);

            // Also update the pack itself as requested
            $pack->setEvenement($evenement);
            $pack->setSponsor($user);

            $entityManager->persist($reservation);
            $entityManager->flush();

            // Envoi de l'email à l'artiste
            $artiste = $evenement->getArtiste();
            if ($artiste && $artiste->getEmail()) {
                $email = (new Email())
                    ->from('contact@feane.com')
                    ->to($artiste->getEmail())
                    ->subject('🌟 Nouveau Sponsor pour ' . $evenement->getTitre())
                    ->html('
                        <h2>Excellente nouvelle !</h2>
                        <p>Le sponsor <strong>' . $user->getUserIdentifier() . '</strong> vient de réserver le pack <strong>' . $pack->getNomPack() . '</strong> pour votre événement <strong>' . $evenement->getTitre() . '</strong>.</p>
                        <p>Connectez-vous à votre espace pour voir les détails de cette réservation.</p>
                        <br>
                        <p>L\'équipe Feane</p>
                    ');

                try {
                    $mailer->send($email);
                    $this->addFlash('success', 'Votre réservation a été envoyée.');
                } catch (\Exception $e) {
                    $this->addFlash('success', 'Votre réservation a été envoyée.');
                    // Optionnel: log de l'erreur
                    // $logger->error($e->getMessage());
                }
            } else {
                $this->addFlash('success', 'Votre réservation a été envoyée.');
            }
        }

        return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/sponsor/cancel/{packId}', name: 'front_evenements_sponsor_cancel', methods: ['POST'])]
    #[IsGranted('ROLE_SPONSOR')]
    public function cancelSponsorReservation(
        \App\Entity\Evenement $evenement, 
        int $packId,
        SponsoringPackRepository $packRepository,
        ReservationPackRepository $reservationRepository,
        EntityManagerInterface $entityManager,
        Request $request
    ): Response {
        $user = $this->getUser();
        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        if (!$this->isCsrfTokenValid('cancel'.$packId, $request->request->get('_token'))) {
            $this->addFlash('error', 'Token CSRF Invalide.');
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $pack = $packRepository->find($packId);
        if (!$pack) {
            $this->addFlash('error', 'Pack introuvable.');
            return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
        }

        $existing = $reservationRepository->findOneBy([
            'evenement' => $evenement,
            'user' => $user,
            'sponsoringPack' => $pack
        ]);

        if ($existing) {
            // Vérification de la règle des 7 jours
            $now = new \DateTime();
            $eventDate = $evenement->getDate();
            $interval = $now->diff($eventDate);
            $daysRemaining = (int)$interval->format('%r%a');

            if ($daysRemaining < 7) {
                $this->addFlash('error', 'Désolé, vous ne pouvez plus annuler votre réservation à moins de 7 jours de l\'événement.');
                return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
            }

            // Libérer le pack
            $pack->setEvenement(null);
            $pack->setSponsor(null);

            $entityManager->remove($existing);
            $entityManager->flush();
            $this->addFlash('success', 'Votre réservation pour le pack ' . $pack->getNomPack() . ' a été annulée.');
        } else {
            $this->addFlash('warning', 'Aucune réservation trouvée à annuler.');
        }

        return $this->redirectToRoute('front_evenements_sponsor', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/participate', name: 'front_evenements_participate', methods: ['POST'])]
    public function participate(?\App\Entity\Evenement $evenement, EntityManagerInterface $entityManager, ParticipationRepository $participationRepository): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Cet événement est introuvable.');
            return $this->redirectToRoute('front_evenements_index');
        }

        /** @var \App\Entity\User $user */
        $user = $this->getUser();

        if (!$user) {
            $this->addFlash('error', 'Vous devez être connecté pour participer.');
            return $this->redirectToRoute('app_login');
        }

        // Check if already participating
        $existing = $participationRepository->findOneBy([
            'user' => $user,
            'evenement' => $evenement
        ]);

        if ($existing) {
            $this->addFlash('warning', 'Vous participez déjà à cet événement.');
        } elseif ($this->isGranted('ROLE_ARTISTE')) {
            $this->addFlash('error', 'Les artistes ne peuvent pas participer aux événements.');
        } elseif ($evenement->getCapaciteMax() <= 0) {
            $this->addFlash('error', 'Désolé, il n\'y a plus de places pour cet événement.');
        } else {
            $participation = new Participation();
            $participation->setUser($user);
            $participation->setEvenement($evenement);
            $participation->setCreatedAt(new \DateTime());
            
            // On décrémente le nombre de places
            $evenement->setCapaciteMax($evenement->getCapaciteMax() - 1);
            
            $entityManager->persist($participation);
            $entityManager->flush();

            $this->addFlash('success', 'Votre participation à "' . $evenement->getTitre() . '" a été enregistrée !');
        }

        return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/unparticipate', name: 'front_evenements_unparticipate', methods: ['POST'])]
    public function unparticipate(?\App\Entity\Evenement $evenement, EntityManagerInterface $entityManager, ParticipationRepository $participationRepository): Response
    {
        if (!$evenement) {
            $this->addFlash('error', 'Cet événement est introuvable.');
            return $this->redirectToRoute('front_evenements_index');
        }

        /** @var \App\Entity\User $user */
        $user = $this->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        $participation = $participationRepository->findOneBy([
            'user' => $user,
            'evenement' => $evenement
        ]);

        if ($participation) {
            // Vérification de la règle des 3 jours
            $now = new \DateTime();
            $eventDate = $evenement->getDate();
            $interval = $now->diff($eventDate);
            $daysRemaining = (int)$interval->format('%r%a');

            if ($daysRemaining < 3) {
                $this->addFlash('error', 'Désolé, vous ne pouvez plus annuler votre participation à moins de 3 jours de l\'événement.');
                return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
            }

            $entityManager->remove($participation);
            
            // On incrémente le nombre de places
            $evenement->setCapaciteMax($evenement->getCapaciteMax() + 1);
            
            $entityManager->flush();
            $this->addFlash('success', 'Votre participation a été annulée.');
        }

        return $this->redirectToRoute('front_evenements_show', ['id' => $evenement->getId()]);
    }

    #[Route('/evenements/{id}/participants', name: 'front_evenements_participants', methods: ['GET'])]
    public function participants(?Evenement $evenement): Response
    {
        return $this->render('front/evenement/participants.html.twig', [
            'evenement' => $evenement,
        ]);
    }

    #[Route('/evenements/{id}/participants/pdf', name: 'front_evenements_participants_pdf', methods: ['GET'])]
    public function exportParticipantsPdf(?Evenement $evenement): Response
    {
        if (!$evenement) {
            throw $this->createNotFoundException('Événement introuvable.');
        }

        $dompdf = new \Dompdf\Dompdf();
        $dompdf->setPaper('A4', 'portrait');

        $html = $this->renderView('front/evenement/participants_pdf.html.twig', [
            'evenement' => $evenement,
        ]);

        $dompdf->loadHtml($html);
        $dompdf->render();

        $output = $dompdf->output();
        $filename = 'Participants_' . $evenement->getTitre() . '.pdf';

        return new Response($output, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'attachment; filename="' . $filename . '"',
        ]);
    }

    #[Route('/evenements/participants/{id}/remove', name: 'front_evenements_participants_remove', methods: ['POST'])]
    public function removeParticipant(Participation $participation, EntityManagerInterface $entityManager, Request $request): Response
    {
        $evenement = $participation->getEvenement();
        
        if (!$this->isCsrfTokenValid('remove'.$participation->getId(), $request->request->get('_token'))) {
            $this->addFlash('error', 'Token CSRF invalide.');
            return $this->redirectToRoute('front_evenements_participants', ['id' => $evenement->getId()]);
        }

        // On libère une place
        $evenement->setCapaciteMax($evenement->getCapaciteMax() + 1);
        
        $entityManager->remove($participation);
        $entityManager->flush();

        $this->addFlash('success', 'Le participant a été retiré de la liste.');

        return $this->redirectToRoute('front_evenements_participants', ['id' => $evenement->getId()]);
    }
}
