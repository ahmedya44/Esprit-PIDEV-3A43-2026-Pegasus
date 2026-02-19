<?php

namespace App\Controller;

use App\Repository\CourseRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class CourseFrontController extends AbstractController
{
    #[Route('/courses', name: 'app_courses', methods: ['GET'])]
    public function index(CourseRepository $courseRepository): Response
    {
        return $this->render('front/courses.html.twig', [
            'courses' => $courseRepository->findBy(['status' => 'published'], ['id' => 'DESC']),
        ]);
    }
}