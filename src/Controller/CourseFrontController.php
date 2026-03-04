<?php

namespace App\Controller;

use App\Entity\Course;
use App\Entity\CourseVideo;
use App\Repository\CourseRepository;
use App\Repository\CourseSectionRepository;
use App\Repository\CourseVideoRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Attribute\Route;

final class CourseFrontController extends AbstractController
{
    #[Route('/courses', name: 'app_courses', methods: ['GET'])]
    public function index(
        CourseRepository $courseRepository,
        Request $request,
        \App\Service\CourseCategoryClassifier $classifier
    ): Response {
        $activeCat = $request->query->get('cat'); // art|music|fantasy|other|null

        $allCourses = $courseRepository->findBy(['status' => 'PUBLISHED'], ['id' => 'DESC']);
        $filters = $classifier->buildAvailableFilters($allCourses);

        $courses = array_values(array_filter($allCourses, fn($c) => $classifier->courseMatchesCategory($c, $activeCat)));

        return $this->render('front/courses.html.twig', [
            'courses' => $courses,
            'filters' => $filters,
            'activeCat' => $activeCat,
            'classifier' => $classifier, // so twig can show badges
        ]);
    }

    #[Route('/courses/{id}', name: 'course_show', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function show(Course $course, CourseSectionRepository $sectionRepo): Response
    {
        if ($course->getStatus() !== 'PUBLISHED') {
            throw $this->createNotFoundException();
        }

        $sections = $sectionRepo->findBy(['course' => $course], ['orderIndex' => 'ASC']);

        $totalVideos = 0;
        foreach ($sections as $s) {
            $totalVideos += $s->getCourseVideos()->count();
        }

        return $this->render('course_front/show.html.twig', [
            'course' => $course,
            'sections' => $sections,
            'totalVideos' => $totalVideos,
        ]);
    }

    #[Route('/courses/{id}/learn', name: 'course_learn', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function learn(
        Course $course,
        Request $request,
        CourseSectionRepository $sectionRepo,
        CourseVideoRepository $videoRepo
    ): Response {
        if ($course->getStatus() !== 'PUBLISHED') {
            throw $this->createNotFoundException();
        }

        $sections = $sectionRepo->findBy(['course' => $course], ['orderIndex' => 'ASC']);

        // Session progress (video IDs marked completed)
        $session = $request->getSession();
        $progressKey = 'course_progress_' . $course->getId();
        $completed = $session->get($progressKey, []); // array like ["12" => true, "13" => true]

        // Build a GLOBAL ordered list of videos (section.orderIndex then video.orderIndex)
        $orderedVideos = [];
        foreach ($sections as $section) {
            $videos = $section->getCourseVideos()->toArray();
            usort($videos, fn (CourseVideo $a, CourseVideo $b) => $a->getOrderIndex() <=> $b->getOrderIndex());

            foreach ($videos as $v) {
                $orderedVideos[] = $v;
            }
        }

        // Determine which videos are unlocked (sequential gating)
        // Rule: first video is unlocked. Video N unlocked only if video N-1 is completed.
        $unlockedIds = [];
        $prevVideoId = null;

        foreach ($orderedVideos as $v) {
            $id = (string) $v->getId();

            if ($prevVideoId === null) {
                $unlockedIds[$id] = true; // first always unlocked
            } else {
                $unlockedIds[$id] = isset($completed[(string)$prevVideoId]);
            }

            $prevVideoId = $v->getId();
        }

        // Requested video
        $selectedVideo = null;
        $vId = $request->query->get('v');

        if ($vId) {
            $candidate = $videoRepo->find((int) $vId);

            // must exist + belong to this course
            if ($candidate && $candidate->getSection()->getCourse()->getId() === $course->getId()) {
                $candidateId = (string) $candidate->getId();

                // must be unlocked
                if (isset($unlockedIds[$candidateId]) && $unlockedIds[$candidateId] === true) {
                    $selectedVideo = $candidate;
                }
            }
        }

        // If none selected, pick the first unlocked one (usually first, or next after progress)
        if (!$selectedVideo) {
            foreach ($orderedVideos as $v) {
                if (!empty($unlockedIds[(string)$v->getId()])) {
                    $selectedVideo = $v;
                    break;
                }
            }
        }

        // Is selected completed?
        $selectedCompleted = $selectedVideo ? isset($completed[(string)$selectedVideo->getId()]) : false;

        return $this->render('course_front/learn.html.twig', [
            'course' => $course,
            'sections' => $sections,
            'selectedVideo' => $selectedVideo,
            'completed' => $completed,
            'unlockedIds' => $unlockedIds,
            'selectedCompleted' => $selectedCompleted,
        ]);
    }

    #[Route(
        '/courses/{courseId}/videos/{videoId}/complete',
        name: 'course_video_complete',
        methods: ['POST'],
        requirements: ['courseId' => '\d+', 'videoId' => '\d+']
    )]
    public function completeVideo(
        int $courseId,
        int $videoId,
        CourseRepository $courseRepo,
        CourseVideoRepository $videoRepo,
        SessionInterface $session
    ): JsonResponse {
        $course = $courseRepo->find($courseId);
        $video = $videoRepo->find($videoId);

        // basic safety
        if (!$course || !$video) {
            return new JsonResponse(['ok' => false, 'message' => 'Not found'], 404);
        }
        if ($course->getStatus() !== 'PUBLISHED') {
            return new JsonResponse(['ok' => false, 'message' => 'Course not available'], 403);
        }
        if ($video->getSection()->getCourse()->getId() !== $course->getId()) {
            return new JsonResponse(['ok' => false, 'message' => 'Invalid video'], 400);
        }

        $key = 'course_progress_' . $courseId;
        $completed = $session->get($key, []);
        $completed[(string) $videoId] = true;
        $session->set($key, $completed);

        return new JsonResponse(['ok' => true]);
    }
}