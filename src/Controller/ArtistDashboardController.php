<?php

namespace App\Controller;

use App\Repository\CourseRepository;
use App\Repository\QuizRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class ArtistDashboardController extends AbstractController
{
    #[Route('/artist', name: 'artist_dashboard', methods: ['GET'])]
    public function index(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository
    ): Response {
        $courses = $courseRepository->findBy([], ['id' => 'DESC']);
        $quizzes = $quizRepository->findBy([], ['id' => 'DESC']);

        return $this->render('front/dashboard.html.twig', [
            'courses' => $courses,
            'quizzes' => $quizzes,
        ]);
    }
}