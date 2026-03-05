<?php

namespace App\Controller;

use App\Entity\Quiz;
use App\Form\QuizType;
use App\Repository\QuizRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ArtistQuizzesController extends AbstractController
{
    #[Route('/artist/quizzes/new', name: 'artist_quizzes_new', methods: ['GET', 'POST'])]
    public function new(
        Request $request,
        EntityManagerInterface $em,
        QuizRepository $quizRepository
    ): Response {
        $quiz = new Quiz();

        $form = $this->createForm(QuizType::class, $quiz, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // ✅ 1 quiz per course
            $course = $quiz->getCourse();
            if ($course !== null) {
                $existing = $quizRepository->findOneBy(['course' => $course]);
                if ($existing !== null) {
                    $this->addFlash('error', 'This course already has a final quiz. Edit it instead.');
                    return $this->redirectToRoute('artist_quizzes_edit', [
                        'id' => $existing->getId(),
                    ]);
                }
            }

            $em->persist($quiz);
            $em->flush();

            $this->addFlash('success', 'Quiz created successfully.');
            return $this->redirectToRoute('artist_quizzes_builder', [
                'id' => $quiz->getId(),
            ]);
        }

        // ✅ use a FRONT template (not back)
        return $this->render('front/quizzes/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/artist/quizzes/{id}/edit', name: 'artist_quizzes_edit', methods: ['GET', 'POST'])]
    public function edit(
        Request $request,
        Quiz $quiz,
        EntityManagerInterface $em,
        QuizRepository $quizRepository
    ): Response {
        $form = $this->createForm(QuizType::class, $quiz, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // ✅ keep 1 quiz per course rule even on edit
            $course = $quiz->getCourse();
            if ($course !== null) {
                $existing = $quizRepository->findOneBy(['course' => $course]);
                if ($existing !== null && $existing->getId() !== $quiz->getId()) {
                    $this->addFlash('error', 'This course already has a final quiz. Choose another course.');
                    return $this->redirectToRoute('artist_quizzes_edit', ['id' => $quiz->getId()]);
                }
            }

            $em->flush();

            $this->addFlash('success', 'Quiz updated successfully.');
            return $this->redirectToRoute('artist_quizzes_builder', [
                'id' => $quiz->getId(),
            ]);
        }

        return $this->render('front/quizzes/edit.html.twig', [
            'form' => $form->createView(),
            'quiz' => $quiz,
        ]);
    }

    #[Route('/artist/quizzes/{id}/delete', name: 'artist_quizzes_delete', methods: ['POST'])]
    public function delete(
        Request $request,
        Quiz $quiz,
        EntityManagerInterface $em
    ): Response {
        if ($this->isCsrfTokenValid('delete_quiz_' . $quiz->getId(), (string) $request->request->get('_token'))) {
            $em->remove($quiz);
            $em->flush();
            $this->addFlash('success', 'Quiz deleted successfully.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('artist_quizzes_builder', [
            'id' => $quiz->getId(),
        ]);
    }
}