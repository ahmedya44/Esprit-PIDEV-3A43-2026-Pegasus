<?php

namespace App\Controller\Admin;

use App\Entity\Course;
use App\Form\CourseType;
use App\Repository\CourseRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/courses')]
final class CourseController extends AbstractController
{
    #[Route('', name: 'admin_courses_index', methods: ['GET'])]
    public function index(CourseRepository $courseRepository): Response
    {
        return $this->render('back/course/index.html.twig', [
            'courses' => $courseRepository->findBy([], ['id' => 'DESC']),
        ]);
    }

    #[Route('/new', name: 'admin_courses_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $course = new Course();
        $course->setCreatedAt(new \DateTimeImmutable());

        $form = $this->createForm(CourseType::class, $course);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($course);
            $em->flush();

            $this->addFlash('success', 'Course created successfully.');
            return $this->redirectToRoute('admin_courses_index');
        }

        return $this->render('back/course/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/edit', name: 'admin_courses_edit', methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
    public function edit(Course $course, Request $request, EntityManagerInterface $em): Response
    {
        $form = $this->createForm(CourseType::class, $course);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            $this->addFlash('success', 'Course updated successfully.');
            return $this->redirectToRoute('admin_courses_index');
        }

        return $this->render('back/course/edit.html.twig', [
            'form' => $form->createView(),
            'course' => $course,
        ]);
    }

    #[Route('/{id}/delete', name: 'admin_courses_delete', methods: ['POST'], requirements: ['id' => '\d+'])]
    public function delete(Course $course, Request $request, EntityManagerInterface $em): Response
    {
        $token = (string) $request->request->get('_token');

        if ($this->isCsrfTokenValid('delete_course_' . $course->getId(), $token)) {
            $em->remove($course);
            $em->flush();
            $this->addFlash('success', 'Course deleted successfully.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('admin_courses_index');
    }
}