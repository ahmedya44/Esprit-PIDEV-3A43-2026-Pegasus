<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Artiste;
use App\Entity\Quiz;
use App\Entity\QuizQuestion;
use App\Form\QuizQuestionType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ArtistQuizBuilderController extends AbstractController
{
    #[Route('/artist/quizzes/{id}/builder', name: 'artist_quizzes_builder', methods: ['GET'])]
    public function builder(Quiz $quiz): Response
    {
        $this->denyUnlessQuizOwner($quiz);

        // Questions are ordered by orderIndex (pro)
        $questions = $quiz->getQuizQuestions()->toArray();
        usort($questions, static fn(QuizQuestion $a, QuizQuestion $b) => $a->getOrderIndex() <=> $b->getOrderIndex());

        return $this->render('front/quizzes/builder.html.twig', [
            'quiz' => $quiz,
            'questions' => $questions,
        ]);
    }

    #[Route('/artist/quizzes/{id}/questions/new', name: 'artist_quiz_questions_new', methods: ['GET', 'POST'])]
    public function newQuestion(
        Quiz $quiz,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $this->denyUnlessQuizOwner($quiz);

        $question = new QuizQuestion();
        $question->setQuiz($quiz);

        // Default orderIndex: last + 1
        $max = 0;
        foreach ($quiz->getQuizQuestions() as $q) {
            $max = max($max, (int) $q->getOrderIndex());
        }
        $question->setOrderIndex($max + 1);

        $form = $this->createForm(QuizQuestionType::class, $question);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($question);
            $em->flush();

            return $this->redirectToRoute('artist_quizzes_builder', ['id' => $quiz->getId()]);
        }

        return $this->render('front/quizzes/question_form.html.twig', [
            'quiz' => $quiz,
            'question' => $question,
            'form' => $form->createView(),
            'mode' => 'create',
        ]);
    }

    #[Route('/artist/questions/{id}/edit', name: 'artist_quiz_questions_edit', methods: ['GET', 'POST'])]
    public function editQuestion(
        QuizQuestion $question,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $quiz = $question->getQuiz();
        if (!$quiz) {
            throw $this->createNotFoundException('Quiz not found for this question.');
        }
        $this->denyUnlessQuizOwner($quiz);

        $form = $this->createForm(QuizQuestionType::class, $question);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            return $this->redirectToRoute('artist_quizzes_builder', ['id' => $quiz->getId()]);
        }

        return $this->render('front/quizzes/question_form.html.twig', [
            'quiz' => $quiz,
            'question' => $question,
            'form' => $form->createView(),
            'mode' => 'edit',
        ]);
    }

    #[Route('/artist/questions/{id}/delete', name: 'artist_quiz_questions_delete', methods: ['POST'])]
    public function deleteQuestion(
        QuizQuestion $question,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $quiz = $question->getQuiz();
        if (!$quiz) {
            throw $this->createNotFoundException();
        }
        $this->denyUnlessQuizOwner($quiz);

        if (!$this->isCsrfTokenValid('delete_question_' . $question->getId(), (string) $request->request->get('_token'))) {
            $this->addFlash('error', 'Invalid CSRF token.');
            return $this->redirectToRoute('artist_quizzes_builder', ['id' => $quiz->getId()]);
        }

        $em->remove($question);
        $em->flush();

        $this->addFlash('success', 'Question deleted.');
        return $this->redirectToRoute('artist_quizzes_builder', ['id' => $quiz->getId()]);
    }

    private function denyUnlessQuizOwner(Quiz $quiz): void
    {
        $user = $this->getUser();
        if (!$user instanceof Artiste || $quiz->getCourse()?->getArtist()?->getId() !== $user->getId()) {
            throw $this->createAccessDeniedException();
        }
    }
}
