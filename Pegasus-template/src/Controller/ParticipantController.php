<?php

namespace App\Controller;

use App\Entity\Participant;
use App\Form\ParticipantType;
use App\Repository\ParticipantRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

use App\Service\PdfService;

#[Route('/admin/participant')]
final class ParticipantController extends AbstractController
{
    #[Route('/export/pdf', name: 'app_participant_export_pdf', methods: ['GET'])]
    public function exportPdf(ParticipantRepository $participantRepository, PdfService $pdfService): Response
    {
        $html = $this->renderView('participant/pdf.html.twig', [
            'participants' => $participantRepository->findAllSortedByEvent(),
        ]);
        
        $pdfService->showPdfFile($html);
        
        return new Response('', 200, [
            'Content-Type' => 'application/pdf',
        ]);
    }

    #[Route(name: 'app_participant_index', methods: ['GET'])]
    public function index(ParticipantRepository $participantRepository): Response
    {
        return $this->render('participant/index.html.twig', [
            'participants' => $participantRepository->findAllSortedByEvent(),
        ]);
    }

    #[Route('/new', name: 'app_participant_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $participant = new Participant();
        $form = $this->createForm(ParticipantType::class, $participant);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($participant);
            $entityManager->flush();

            return $this->redirectToRoute('app_participant_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('participant/new.html.twig', [
            'participant' => $participant,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_participant_show', methods: ['GET'])]
    public function show(Participant $participant): Response
    {
        return $this->render('participant/show.html.twig', [
            'participant' => $participant,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_participant_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Participant $participant, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ParticipantType::class, $participant);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_participant_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('participant/edit.html.twig', [
            'participant' => $participant,
            'form' => $form,
        ]);
    }

    #[Route('/{id}/status/{status}', name: 'app_participant_update_status', methods: ['POST'])]
    public function updateStatus(Request $request, Participant $participant, string $status, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('status'.$participant->getId(), $request->request->get('_token'))) {
            $validStatuses = ['En attente', 'Confirmé', 'Annulé'];
            if (in_array($status, $validStatuses)) {
                $participant->setStatut($status);
                $entityManager->flush();
                $this->addFlash('success', 'Le statut du participant a été mis à jour.');
            } else {
                $this->addFlash('danger', 'Statut invalide.');
            }
        }

        return $this->redirectToRoute('app_participant_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/{id}', name: 'app_participant_delete', methods: ['POST'])]
    public function delete(Request $request, Participant $participant, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$participant->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($participant);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_participant_index', [], Response::HTTP_SEE_OTHER);
    }
}
