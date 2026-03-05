<?php

namespace App\Controller;

use App\Entity\Artiste;
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
        $user = $this->getUser();
        if (!$user instanceof Artiste) {
            throw $this->createAccessDeniedException();
        }

        $courses = $courseRepository->findBy(['artist' => $user], ['id' => 'DESC']);
        $quizzes = $quizRepository->findByArtist($user);

        return $this->render('front/dashboard.html.twig', [
            'courses' => $courses,
            'quizzes' => $quizzes,
        ]);
    }
}
