<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Participant;
use App\Form\ParticipantType;
use App\Repository\EvenementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class FrontController extends AbstractController
{
    #[Route('/', name: 'front_home', methods: ['GET'])]
    public function home(EvenementRepository $evenementRepository): Response
    {
        return $this->render('front/index.html.twig', [
            'evenements' => $evenementRepository->findAll(),
        ]);
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

    #[Route('/evenement', methods: ['GET'])]
    public function evenementRedirect(): Response
    {
        return $this->redirectToRoute('front_evenements');
    }

    #[Route('/evenements', name: 'front_evenements', methods: ['GET'])]
    public function evenements(EvenementRepository $evenementRepository): Response
    {
        return $this->render('front/evenements.html.twig', [
            'evenements' => $evenementRepository->findAll(),
        ]);
    }

    #[Route('/evenement/{id}', name: 'front_evenement_show', methods: ['GET', 'POST'])]
    public function show(Request $request, Evenement $evenement, EntityManagerInterface $entityManager, \App\Repository\ParticipantRepository $participantRepository): Response
    {
        $participant = new Participant();
        $form = $this->createForm(ParticipantType::class, $participant);
        
        // Remove fields that shouldn't be edited by the user in the front
        $form->remove('date_inscription');
        $form->remove('statut');
        $form->remove('evenement');

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Check if participant already registered for this event
            $existingParticipant = $participantRepository->findOneBy([
                'email' => $participant->getEmail(),
                'evenement' => $evenement
            ]);

            if ($existingParticipant) {
                $this->addFlash('danger', 'Vous avez déjà participé à cet événement.');
                return $this->redirectToRoute('front_evenement_show', ['id' => $evenement->getId()]);
            }

            $participant->setEvenement($evenement);
            $participant->setDateInscription(new \DateTime());
            $participant->setStatut('En attente');

            $entityManager->persist($participant);
            $entityManager->flush();

            $this->addFlash('success', 'Votre participation a été enregistrée avec succès !');

            return $this->redirectToRoute('front_evenement_show', ['id' => $evenement->getId()]);
        }

        return $this->render('front/evenement_show.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/evenement/{id}/participation/cancel', name: 'front_participation_cancel', methods: ['POST'])]
    public function cancelParticipation(Request $request, Evenement $evenement, EntityManagerInterface $entityManager, \App\Repository\ParticipantRepository $participantRepository): Response
    {
        $email = $request->request->get('email');

        if (!$email) {
            $this->addFlash('danger', 'Veuillez renseigner votre email pour annuler votre participation.');
            return $this->redirectToRoute('front_evenement_show', ['id' => $evenement->getId()]);
        }

        $participant = $participantRepository->findOneBy([
            'email' => $email,
            'evenement' => $evenement
        ]);

        if (!$participant) {
            $this->addFlash('danger', 'Aucune participation trouvée avec cet email pour cet événement.');
            return $this->redirectToRoute('front_evenement_show', ['id' => $evenement->getId()]);
        }

        $entityManager->remove($participant);
        $entityManager->flush();

        $this->addFlash('success', 'Votre participation a été annulée avec succès.');

        return $this->redirectToRoute('front_evenement_show', ['id' => $evenement->getId()]);
    }
}