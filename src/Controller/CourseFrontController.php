<?php

namespace App\Controller;

use App\Entity\Course;
use App\Entity\CourseVideo;
use App\Entity\QuizAttempt;
use App\Repository\CourseRepository;
use App\Repository\CourseSectionRepository;
use App\Repository\CourseVideoRepository;
use App\Repository\LearningProgressRepository;
use App\Repository\QuizAttemptRepository;
use App\Repository\QuizRepository;
use App\Service\CertificatePdfService;
use Doctrine\ORM\EntityManagerInterface;
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
        LearningProgressRepository $progressRepo,
        Request $request,
        \App\Service\CourseCategoryClassifier $classifier
    ): Response {
        $rawActiveCat = $request->query->get('cat');
        $activeCat = is_string($rawActiveCat) && $rawActiveCat !== '' ? $rawActiveCat : null;

        $allCourses = $courseRepository->findBy(['status' => 'PUBLISHED'], ['id' => 'DESC']);
        $filters = $classifier->buildAvailableFilters($allCourses);
        $courses = array_values(array_filter($allCourses, fn($c) => $classifier->courseMatchesCategory($c, $activeCat)));

        $progressMap = [];
        $user = $this->getUser();
        if ($user) {
            $progressMap = $progressRepo->getProgressMapForUser($user);
        }

        return $this->render('front/courses.html.twig', [
            'courses'     => $courses,
            'filters'     => $filters,
            'activeCat'   => $activeCat,
            'classifier'  => $classifier,
            'progressMap' => $progressMap,
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
            'course'      => $course,
            'sections'    => $sections,
            'totalVideos' => $totalVideos,
        ]);
    }

    #[Route('/courses/{id}/learn', name: 'course_learn', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function learn(
        Course $course,
        Request $request,
        CourseSectionRepository $sectionRepo,
        CourseVideoRepository $videoRepo,
        QuizRepository $quizRepo,
        LearningProgressRepository $progressRepo,
        EntityManagerInterface $em,
    ): Response {
        if ($course->getStatus() !== 'PUBLISHED') {
            throw $this->createNotFoundException();
        }

        $sections = $sectionRepo->findBy(['course' => $course], ['orderIndex' => 'ASC']);

        // Load progress: DB for logged-in users, session as fallback for guests
        $user = $this->getUser();
        if ($user) {
            $progress = $progressRepo->findOrCreate($user, $course, $em);
            $em->flush();
            $completedIds = $progress->getCompletedVideoIds();
            $completed = array_fill_keys(array_map('strval', $completedIds), true);
        } else {
            $progressKey = 'course_progress_' . $course->getId();
            $completed = $request->getSession()->get($progressKey, []);
        }

        // Build ordered video list
        $orderedVideos = [];
        foreach ($sections as $section) {
            $videos = $section->getCourseVideos()->toArray();
            usort($videos, fn (CourseVideo $a, CourseVideo $b) => $a->getOrderIndex() <=> $b->getOrderIndex());
            foreach ($videos as $v) {
                $orderedVideos[] = $v;
            }
        }

        // Sequential unlock gating
        $unlockedIds = [];
        $prevVideoId = null;
        foreach ($orderedVideos as $v) {
            $id = (string) $v->getId();
            $unlockedIds[$id] = $prevVideoId === null || isset($completed[(string)$prevVideoId]);
            $prevVideoId = $v->getId();
        }

        // Requested video
        $selectedVideo = null;
        $vId = $request->query->get('v');
        if ($vId) {
            $candidate = $videoRepo->find((int) $vId);
            $candidateCourse = $candidate?->getSection()?->getCourse();
            if ($candidate instanceof CourseVideo && $candidateCourse?->getId() === $course->getId()) {
                $candidateId = (string) $candidate->getId();
                if (!empty($unlockedIds[$candidateId])) {
                    $selectedVideo = $candidate;
                }
            }
        }
        if (!$selectedVideo) {
            foreach ($orderedVideos as $v) {
                if (!empty($unlockedIds[(string)$v->getId()])) {
                    $selectedVideo = $v;
                    break;
                }
            }
        }

        $selectedCompleted = $selectedVideo ? isset($completed[(string)$selectedVideo->getId()]) : false;
        $allVideoCount = count($orderedVideos);
        $completedCount = 0;
        foreach ($orderedVideos as $video) {
            if (isset($completed[(string) $video->getId()])) {
                $completedCount++;
            }
        }

        $courseCompleted = $allVideoCount > 0 && $completedCount === $allVideoCount;
        $courseQuiz = $quizRepo->findOneBy(['course' => $course]);

        return $this->render('course_front/learn.html.twig', [
            'course'           => $course,
            'sections'         => $sections,
            'selectedVideo'    => $selectedVideo,
            'completed'        => $completed,
            'unlockedIds'      => $unlockedIds,
            'selectedCompleted'=> $selectedCompleted,
            'courseCompleted'  => $courseCompleted,
            'courseQuiz'       => $courseQuiz,
            'completedCount'   => $completedCount,
            'allVideoCount'    => $allVideoCount,
        ]);
    }

    #[Route('/courses/{id}/quiz', name: 'course_quiz', methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
    public function quiz(
        Course $course,
        Request $request,
        CourseSectionRepository $sectionRepo,
        QuizRepository $quizRepo,
        QuizAttemptRepository $attemptRepo,
        LearningProgressRepository $progressRepo,
        SessionInterface $session,
        EntityManagerInterface $em,
    ): Response {
        if ($course->getStatus() !== 'PUBLISHED') {
            throw $this->createNotFoundException();
        }

        $quiz = $quizRepo->findOneBy(['course' => $course]);
        if ($quiz === null) {
            $this->addFlash('error', 'This course has no quiz yet.');
            return $this->redirectToRoute('course_learn', ['id' => $course->getId()]);
        }

        $sections = $sectionRepo->findBy(['course' => $course], ['orderIndex' => 'ASC']);
        $orderedVideos = [];
        foreach ($sections as $section) {
            $videos = $section->getCourseVideos()->toArray();
            usort($videos, fn (CourseVideo $a, CourseVideo $b) => $a->getOrderIndex() <=> $b->getOrderIndex());
            foreach ($videos as $video) {
                $orderedVideos[] = $video;
            }
        }

        // Determine completion (DB for logged-in, session for guests)
        $user = $this->getUser();
        if ($user) {
            $progress = $progressRepo->findByUserAndCourse($user, $course);
            $completedIds = $progress ? $progress->getCompletedVideoIds() : [];
            $completed = array_fill_keys(array_map('strval', $completedIds), true);
        } else {
            $completed = $session->get('course_progress_' . $course->getId(), []);
        }

        $courseCompleted = count($orderedVideos) > 0;
        foreach ($orderedVideos as $video) {
            if (!isset($completed[(string) $video->getId()])) {
                $courseCompleted = false;
                break;
            }
        }

        if (!$courseCompleted) {
            $this->addFlash('error', 'Complete all course lessons first to unlock the final quiz.');
            return $this->redirectToRoute('course_learn', ['id' => $course->getId()]);
        }

        $questions = $quiz->getQuizQuestions()->toArray();
        usort($questions, static fn ($a, $b) => $a->getOrderIndex() <=> $b->getOrderIndex());

        $result = null;
        $savedAttempt = null;

        if ($request->isMethod('POST')) {
            if (!$this->isCsrfTokenValid('course_quiz_' . $quiz->getId(), (string) $request->request->get('_token'))) {
                throw $this->createAccessDeniedException('Invalid request token.');
            }

            $answers = $request->request->all('answers');
            $totalPoints = 0;
            $earnedPoints = 0;

            foreach ($questions as $question) {
                $totalPoints += (int) $question->getPoints();
                $selectedChoiceId = (int) ($answers[(string) $question->getId()] ?? 0);
                if ($selectedChoiceId <= 0) continue;

                foreach ($question->getQuizChoices() as $choice) {
                    if ($choice->getId() === $selectedChoiceId && $choice->isCorrect()) {
                        $earnedPoints += (int) $question->getPoints();
                        break;
                    }
                }
            }

            $score = $totalPoints > 0 ? (int) round(($earnedPoints * 100) / $totalPoints) : 0;
            $passing = (int) ($quiz->getPassingScore() ?? 0);
            $passed = $score >= $passing;

            $result = [
                'score'       => $score,
                'passing'     => $passing,
                'passed'      => $passed,
                'earnedPoints'=> $earnedPoints,
                'totalPoints' => $totalPoints,
            ];

            // Persist quiz attempt for logged-in users
            if ($user) {
                $attempt = new QuizAttempt();
                $attempt->setUser($user);
                $attempt->setQuiz($quiz);
                $attempt->setCourse($course);
                $attempt->setScorePercent($score);
                $attempt->setEarnedPoints($earnedPoints);
                $attempt->setTotalPoints($totalPoints);
                $attempt->setPassed($passed);
                $em->persist($attempt);

                // Mark course completed if passed
                if ($passed) {
                    $prog = $progressRepo->findOrCreate($user, $course, $em);
                    $prog->setStatus('completed');
                    $prog->setCompletedAt(new \DateTimeImmutable());
                }

                $em->flush();
                $savedAttempt = $attempt;
            }
        }

        // Pass best passing attempt (for certificate link)
        $bestAttempt = $user && $quiz ? $attemptRepo->findBestPassingAttempt($user, $quiz) : null;

        return $this->render('course_front/quiz.html.twig', [
            'course'      => $course,
            'quiz'        => $quiz,
            'questions'   => $questions,
            'result'      => $result,
            'bestAttempt' => $bestAttempt,
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
        LearningProgressRepository $progressRepo,
        SessionInterface $session,
        EntityManagerInterface $em,
    ): JsonResponse {
        $course = $courseRepo->find($courseId);
        $video = $videoRepo->find($videoId);

        if (!$course || !$video) {
            return new JsonResponse(['ok' => false, 'message' => 'Not found'], 404);
        }
        if ($course->getStatus() !== 'PUBLISHED') {
            return new JsonResponse(['ok' => false, 'message' => 'Course not available'], 403);
        }
        $videoCourse = $video->getSection()?->getCourse();
        if (!$videoCourse instanceof Course || $videoCourse->getId() !== $course->getId()) {
            return new JsonResponse(['ok' => false, 'message' => 'Invalid video'], 400);
        }

        $user = $this->getUser();

        if ($user) {
            // DB-persisted progress
            $progress = $progressRepo->findOrCreate($user, $course, $em);
            $progress->addCompletedVideoId($videoId);

            // Recalculate percent
            $totalVideos = 0;
            foreach ($course->getCourseSections() as $section) {
                $totalVideos += $section->getCourseVideos()->count();
            }
            $completedCount = count($progress->getCompletedVideoIds());
            $percent = $totalVideos > 0 ? (int) round(($completedCount * 100) / $totalVideos) : 0;
            $progress->setProgressPercent($percent);

            if ($completedCount >= $totalVideos && $totalVideos > 0) {
                // All videos done — mark in-progress; completed only set after quiz pass
                if ($progress->getStatus() === 'in-progress') {
                    // keep status as in-progress until quiz is passed
                }
            }
            $em->flush();
        } else {
            // Session fallback for guests
            $key = 'course_progress_' . $courseId;
            $completed = $session->get($key, []);
            $completed[(string) $videoId] = true;
            $session->set($key, $completed);
        }

        return new JsonResponse(['ok' => true]);
    }

    #[Route('/courses/{id}/certificate', name: 'course_certificate', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function certificate(
        Course $course,
        QuizAttemptRepository $attemptRepo,
        LearningProgressRepository $progressRepo,
        CertificatePdfService $pdfService,
    ): Response {
        $user = $this->getUser();
        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        $quiz = null;
        foreach ($course->getCourseSections() as $section) {
            // quiz is on the course, not section — handled via quizRepo below
            break;
        }

        // Find the course quiz via the quiz repository (relationship)
        $quizzes = $course->getQuizzes();
        $quiz = $quizzes->isEmpty() ? null : $quizzes->first();

        if (!$quiz) {
            $this->addFlash('error', 'This course has no quiz.');
            return $this->redirectToRoute('course_learn', ['id' => $course->getId()]);
        }

        $bestAttempt = $attemptRepo->findBestPassingAttempt($user, $quiz);
        if (!$bestAttempt) {
            $this->addFlash('error', 'You must pass the quiz to download your certificate.');
            return $this->redirectToRoute('course_quiz', ['id' => $course->getId()]);
        }

        $pdf = $pdfService->generateCourseCertificate($user, $course, $bestAttempt);
        $filename = 'certificate_' . preg_replace('/[^a-zA-Z0-9_-]/', '_', $course->getTitle()) . '.pdf';

        return new Response($pdf, 200, [
            'Content-Type'        => 'application/pdf',
            'Content-Disposition' => 'attachment; filename="' . $filename . '"',
        ]);
    }
}
