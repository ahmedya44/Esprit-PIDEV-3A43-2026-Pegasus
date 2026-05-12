<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Quiz;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/back/quizzes', name: 'admin_quizzes_')]
final class AdminQuizzesLegacyController extends AbstractController
{
    #[Route('/dashboard', name: 'dashboard', methods: ['GET'])]
    public function dashboard(): RedirectResponse
    {
        return $this->redirectToRoute('back_content_quizzes');
    }

    #[Route('', name: 'index', methods: ['GET'])]
    public function index(): RedirectResponse
    {
        return $this->redirectToRoute('back_content_quizzes');
    }

    #[Route('/new', name: 'new', methods: ['GET'])]
    public function new(): RedirectResponse
    {
        return $this->redirectToRoute('artist_quizzes_new');
    }

    #[Route('/{id}/edit', name: 'edit', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function edit(Quiz $quiz): RedirectResponse
    {
        return $this->redirectToRoute('artist_quizzes_edit', ['id' => $quiz->getId()]);
    }

    #[Route('/stats', name: 'stats', methods: ['GET'])]
    public function stats(): RedirectResponse
    {
        return $this->redirectToRoute('back_content_stats');
    }

    #[Route('/{id}/delete', name: 'delete', methods: ['POST'], requirements: ['id' => '\d+'])]
    public function delete(Quiz $quiz, Request $request, EntityManagerInterface $entityManager): RedirectResponse
    {
        $legacyToken = $this->isCsrfTokenValid('delete_quiz_' . $quiz->getId(), (string) $request->request->get('_token'));
        $backofficeToken = $this->isCsrfTokenValid('quizzes_delete_' . $quiz->getId(), (string) $request->request->get('_csrf_token'));

        if (!$legacyToken && !$backofficeToken) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_content_quizzes');
        }

        $entityManager->remove($quiz);
        $entityManager->flush();
        $this->addFlash('success', 'Quiz deleted successfully.');

        return $this->redirectToRoute('back_content_quizzes');
    }
}
