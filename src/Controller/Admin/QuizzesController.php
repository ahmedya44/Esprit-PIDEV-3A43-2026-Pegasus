<?php

namespace App\Controller\Admin;

use App\Entity\Quiz;
use App\Form\QuizType;
use App\Repository\QuizRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/quizzes')]
final class QuizzesController extends AbstractController
{
    // ✅ 1) Dashboard page with 3 cards
    #[Route('', name: 'admin_quizzes_dashboard', methods: ['GET'])]
    public function dashboard(QuizRepository $quizRepository): Response
    {
        return $this->render('back/quizzes/dashboard.html.twig', [
            'totalQuizzes' => $quizRepository->count([]),
        ]);
    }

    // ✅ 2) Manage page (table list)
    #[Route('/list', name: 'admin_quizzes_index', methods: ['GET'])]
    public function index(QuizRepository $quizRepository): Response
    {
        return $this->render('back/quizzes/index.html.twig', [
            'quizzes' => $quizRepository->findBy([], ['id' => 'DESC']),
        ]);
    }

    // ✅ 3) Create quiz (1 final quiz per course)
    #[Route('/new', name: 'admin_quizzes_new', methods: ['GET', 'POST'])]
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
            // ✅ enforce: 1 final quiz per course
            $course = $quiz->getCourse();
            if ($course !== null) {
                $existing = $quizRepository->findOneBy(['course' => $course]);
                if ($existing !== null) {
                    $this->addFlash('error', 'This course already has a final quiz. Edit it instead.');
                    return $this->redirectToRoute('admin_quizzes_edit', ['id' => $existing->getId()]);
                }
            }

            $em->persist($quiz);
            $em->flush();

            $this->addFlash('success', 'Quiz created successfully.');
            return $this->redirectToRoute('admin_quizzes_index'); // /admin/quizzes/list
        }

        return $this->render('back/quizzes/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    // ✅ 4) Edit quiz
    #[Route('/admin/quizzes/{id}/edit', name: 'admin_quizzes_edit')]
    public function edit(Request $request, Quiz $quiz, EntityManagerInterface $em): Response
    {
        $form = $this->createForm(QuizType::class, $quiz);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            $this->addFlash('success', 'Quiz updated successfully.');
            return $this->redirectToRoute('admin_quizzes_index');
        }

        return $this->render('back/quizzes/dashboard.html.twig', [
            'form' => $form->createView(),
            'quiz' => $quiz,
        ]);
    }

    // ✅ 5) Delete quiz (CSRF protected)
    #[Route('/admin/quizzes/{id}/delete', name: 'admin_quizzes_delete', methods: ['POST'])]
    public function delete(Request $request, Quiz $quiz, EntityManagerInterface $em): Response
    {
        if ($this->isCsrfTokenValid('delete'.$quiz->getId(), $request->request->get('_token'))) {
            $em->remove($quiz);
            $em->flush();
            $this->addFlash('success', 'Quiz deleted successfully.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('admin_quizzes_index');
    }

    // ✅ 6) Stats page (placeholder now, real stats later)
    #[Route('/stats', name: 'admin_quizzes_stats', methods: ['GET'])]
    public function stats(QuizRepository $quizRepository): Response
    {
        return $this->render('back/quizzes/stats.html.twig', [
            'totalQuizzes' => $quizRepository->count([]),
        ]);
    }
}