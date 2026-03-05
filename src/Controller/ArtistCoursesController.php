<?php

namespace App\Controller;

use App\Entity\Artiste;
use App\Entity\Course;
use App\Entity\CourseSection;
use App\Entity\CourseVideo;
use App\Form\CourseSectionType;
use App\Form\CourseType;
use App\Form\CourseVideoType;
use App\Repository\CourseSectionRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

final class ArtistCoursesController extends AbstractController
{
    #[Route('/artist/courses/new', name: 'artist_courses_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $em, SluggerInterface $slugger): Response
    {
        $course = new Course();
        $user = $this->getUser();
        if (!$user instanceof Artiste) {
            throw $this->createAccessDeniedException();
        }
        $course->setArtist($user);

        // Ensure createdAt is set (your entity requires it)
        $course->setCreatedAt(new \DateTimeImmutable());

        $form = $this->createForm(CourseType::class, $course, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->handleThumbnailUpload($form, $course, $slugger);
            $em->persist($course);
            $em->flush();

            $this->addFlash('success', 'Course created successfully.');

            // ✅ Go directly to builder after creation
            return $this->redirectToRoute('artist_courses_builder', [
                'id' => $course->getId(),
            ]);
        }

        return $this->render('front/courses/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/artist/courses/{id}/edit', name: 'artist_courses_edit', methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
    public function edit(Course $course, Request $request, EntityManagerInterface $em, SluggerInterface $slugger): Response
    {
        $user = $this->getUser();
        if (!$user instanceof Artiste || $course->getArtist()?->getId() !== $user->getId()) {
            throw $this->createAccessDeniedException();
        }

        $form = $this->createForm(CourseType::class, $course, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->handleThumbnailUpload($form, $course, $slugger);
            $em->flush();

            $this->addFlash('success', 'Course updated successfully.');

            // ✅ After edit, go back to builder (not dashboard)
            return $this->redirectToRoute('artist_courses_builder', [
                'id' => $course->getId(),
            ]);
        }

        return $this->render('front/courses/edit.html.twig', [
            'form' => $form->createView(),
            'course' => $course,
        ]);
    }

    #[Route('/artist/courses/{id}/delete', name: 'artist_courses_delete', methods: ['POST'], requirements: ['id' => '\d+'])]
    public function delete(Course $course, Request $request, EntityManagerInterface $em): Response
    {
        $user = $this->getUser();
        if (!$user instanceof Artiste || $course->getArtist()?->getId() !== $user->getId()) {
            throw $this->createAccessDeniedException();
        }

        if ($this->isCsrfTokenValid('delete_course_' . $course->getId(), (string) $request->request->get('_token'))) {
            $em->remove($course);
            $em->flush();
            $this->addFlash('success', 'Course deleted successfully.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('artist_dashboard', ['tab' => 'courses']);
    }

    #[Route('/artist/courses/{id}/builder', name: 'artist_courses_builder', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function builder(Course $course, CourseSectionRepository $sectionRepo): Response
    {
        $sections = $sectionRepo->findBy(['course' => $course], ['orderIndex' => 'ASC']);

        return $this->render('front/courses/builder.html.twig', [
            'course' => $course,
            'sections' => $sections,
        ]);
    }

    #[Route('/artist/courses/{id}/sections/new', name: 'artist_course_sections_new', methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
    public function newSection(Course $course, Request $request, EntityManagerInterface $em): Response
    {
        $section = new CourseSection();
        $section->setCourse($course);

        // default orderIndex (append)
        if ($section->getOrderIndex() === null) {
            $section->setOrderIndex((int) ($course->getCourseSections()->count() + 1));
        }

        $form = $this->createForm(CourseSectionType::class, $section, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($section);
            $em->flush();

            $this->addFlash('success', 'Section added successfully.');

            return $this->redirectToRoute('artist_courses_builder', [
                'id' => $course->getId(),
            ]);
        }

        return $this->render('front/courses/sections_new.html.twig', [
            'course' => $course,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/artist/sections/{id}/delete', name: 'artist_course_sections_delete', methods: ['POST'], requirements: ['id' => '\d+'])]
    public function deleteSection(CourseSection $section, Request $request, EntityManagerInterface $em): Response
    {
        $course = $section->getCourse();
        $courseId = $course?->getId();
        if ($courseId === null) {
            return $this->redirectToRoute('artist_dashboard', ['tab' => 'courses']);
        }

        if ($this->isCsrfTokenValid('delete_section_' . $section->getId(), (string) $request->request->get('_token'))) {
            $em->remove($section);
            $em->flush();
            $this->addFlash('success', 'Section deleted.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('artist_courses_builder', [
            'id' => $courseId,
        ]);
    }

    #[Route('/artist/sections/{id}/videos/new', name: 'artist_course_videos_new', methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
    public function newVideo(CourseSection $section, Request $request, EntityManagerInterface $em): Response
    {
        $video = new CourseVideo();
        $video->setSection($section);

        // default orderIndex (append)
        if ($video->getOrderIndex() === null) {
            $video->setOrderIndex((int) ($section->getCourseVideos()->count() + 1));
        }

        // default isPreview = false (avoid null)
        if ($video->isPreview() === null) {
            $video->setIsPreview(false);
        }

        $form = $this->createForm(CourseVideoType::class, $video, [
            'attr' => ['novalidate' => 'novalidate'],
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($video);
            $em->flush();

            $this->addFlash('success', 'Video added successfully.');
            $course = $section->getCourse();
            $courseId = $course?->getId();
            if ($courseId === null) {
                return $this->redirectToRoute('artist_dashboard', ['tab' => 'courses']);
            }

            return $this->redirectToRoute('artist_courses_builder', [
                'id' => $courseId,
            ]);
        }

        return $this->render('front/courses/videos_new.html.twig', [
            'section' => $section,
            'course' => $section->getCourse(),
            'form' => $form->createView(),
        ]);
    }

    #[Route('/artist/videos/{id}/delete', name: 'artist_course_videos_delete', methods: ['POST'], requirements: ['id' => '\d+'])]
    public function deleteVideo(CourseVideo $video, Request $request, EntityManagerInterface $em): Response
    {
        $course = $video->getSection()?->getCourse();
        $courseId = $course?->getId();
        if ($courseId === null) {
            return $this->redirectToRoute('artist_dashboard', ['tab' => 'courses']);
        }

        if ($this->isCsrfTokenValid('delete_video_' . $video->getId(), (string) $request->request->get('_token'))) {
            $em->remove($video);
            $em->flush();
            $this->addFlash('success', 'Video deleted.');
        } else {
            $this->addFlash('error', 'Invalid CSRF token.');
        }

        return $this->redirectToRoute('artist_courses_builder', [
            'id' => $courseId,
        ]);
    }

    private function handleThumbnailUpload(FormInterface $form, Course $course, SluggerInterface $slugger): void
    {
        $thumbnailFile = $form->get('thumbnailUrl')->getData();
        if (!$thumbnailFile instanceof UploadedFile) {
            return;
        }

        $uploadsDir = $this->getParameter('courses_directory');
        if (!is_string($uploadsDir)) {
            return;
        }

        if (!is_dir($uploadsDir)) {
            mkdir($uploadsDir, 0775, true);
        }

        $originalFilename = pathinfo($thumbnailFile->getClientOriginalName(), PATHINFO_FILENAME);
        $safeFilename = $slugger->slug($originalFilename);
        $extension = $thumbnailFile->guessExtension() ?: 'bin';
        $newFilename = sprintf('%s-%s.%s', $safeFilename, uniqid(), $extension);

        $thumbnailFile->move($uploadsDir, $newFilename);
        $course->setThumbnailUrl('uploads/courses/' . $newFilename);
    }
}
