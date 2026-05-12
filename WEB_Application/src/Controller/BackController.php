<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\Admin;
use App\Entity\Artiste;
use App\Entity\Course;
use App\Entity\NormalUser;
use App\Entity\Quiz;
use App\Entity\Sponsor;
use App\Enum\AccountStatus;
use App\Repository\AdminRepository;
use App\Repository\ArtisteRepository;
use App\Repository\CourseRepository;
use App\Repository\CourseSectionRepository;
use App\Repository\NormalUserRepository;
use App\Repository\QuizAttemptRepository;
use App\Repository\QuizRepository;
use App\Repository\SponsorRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\ConstraintViolationListInterface;
use Symfony\Component\Validator\Validator\ValidatorInterface;
use Twig\Error\LoaderError;
use App\Entity\User;

#[Route('/back', name: 'back_')]
final class BackController extends AbstractController
{
    #[Route('', name: 'dashboard', methods: ['GET'])]
    public function dashboard(): Response
    {
        return $this->render('back/index.html.twig');
    }

    #[Route('/profile', name: 'profile', methods: ['GET'])]
    public function profile(): Response
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        return $this->render('back/profile.html.twig', [
            'profile_user' => $user,
            'edit_mode' => false,
        ]);
    }

    #[Route('/profile/edit', name: 'profile_edit', methods: ['GET'])]
    public function profileEdit(): Response
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        return $this->render('back/profile.html.twig', [
            'profile_user' => $user,
            'edit_mode' => true,
        ]);
    }

    #[Route('/users', name: 'users', methods: ['GET'])]
    public function users(
        Request $request,
        AdminRepository $adminRepository,
        ArtisteRepository $artisteRepository,
        SponsorRepository $sponsorRepository,
        NormalUserRepository $normalUserRepository
    ): Response {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        $isSuperAdmin = $user instanceof Admin && $user->isSuperAdmin();
        $sortBy = (string) $request->query->get('sort_by', 'createdAt');
        $sortDir = strtoupper((string) $request->query->get('sort_dir', 'DESC'));

        $allowedSortBy = ['createdAt', 'username', 'email', 'status', 'id'];
        if (!in_array($sortBy, $allowedSortBy, true)) {
            $sortBy = 'createdAt';
        }
        if (!in_array($sortDir, ['ASC', 'DESC'], true)) {
            $sortDir = 'DESC';
        }

        return $this->render('back/profile.html.twig', [
            'users_page' => true,
            'admins' => array_values(array_filter(
                $adminRepository->findAllForBackOffice($sortBy, $sortDir),
                static fn (mixed $user): bool => $user instanceof Admin
            )),
            'artistes' => array_values(array_filter(
                $artisteRepository->findAllForBackOffice($sortBy, $sortDir),
                static fn (mixed $user): bool => $user instanceof Artiste
            )),
            'sponsors' => array_values(array_filter(
                $sponsorRepository->findAllForBackOffice($sortBy, $sortDir),
                static fn (mixed $user): bool => $user instanceof Sponsor
            )),
            'normal_users' => array_values(array_filter(
                $normalUserRepository->findAllForBackOffice($sortBy, $sortDir),
                static fn (mixed $user): bool => $user instanceof NormalUser
            )),
            'status_options' => AccountStatus::cases(),
            'is_super_admin' => $isSuperAdmin,
            'sort_by' => $sortBy,
            'sort_dir' => $sortDir,
        ]);
    }

    #[Route('/content', name: 'content', methods: ['GET'])]
    public function content(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository,
        QuizAttemptRepository $quizAttemptRepository
    ): Response {
        return $this->renderContentDashboard($courseRepository, $quizRepository, $quizAttemptRepository, 'courses');
    }

    #[Route('/content/courses', name: 'content_courses', methods: ['GET'])]
    public function contentCourses(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository,
        QuizAttemptRepository $quizAttemptRepository
    ): Response {
        return $this->renderContentDashboard($courseRepository, $quizRepository, $quizAttemptRepository, 'courses');
    }

    #[Route('/content/quizzes', name: 'content_quizzes', methods: ['GET'])]
    public function contentQuizzes(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository,
        QuizAttemptRepository $quizAttemptRepository
    ): Response {
        return $this->renderContentDashboard($courseRepository, $quizRepository, $quizAttemptRepository, 'quizzes');
    }

    #[Route('/content/statistique', name: 'content_stats', methods: ['GET'])]
    public function contentStats(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository,
        QuizAttemptRepository $quizAttemptRepository
    ): Response {
        return $this->renderContentDashboard($courseRepository, $quizRepository, $quizAttemptRepository, 'stats');
    }

    #[Route('/courses/{id}/status', name: 'courses_status', methods: ['POST'])]
    public function coursesStatus(
        Course $course,
        Request $request,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        if (!$this->isCsrfTokenValid('courses_status_' . $course->getId(), (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_content_courses');
        }

        $status = strtoupper((string) $request->request->get('status', ''));
        if (!in_array($status, ['DRAFT', 'PUBLISHED', 'HIDDEN'], true)) {
            $this->addFlash('danger', 'Invalid course status.');

            return $this->redirectToRoute('back_content_courses');
        }

        $course->setStatus($status);
        $entityManager->flush();

        $this->addFlash('success', sprintf('Course "%s" is now %s.', $course->getTitle(), strtolower($status)));

        if ((string) $request->request->get('return_to') === 'show') {
            return $this->redirectToRoute('back_courses_show', ['id' => $course->getId()]);
        }

        return $this->redirectToRoute('back_content_courses');
    }

    #[Route('/content/courses/{id}', name: 'courses_show', methods: ['GET'], requirements: ['id' => '\d+'])]
    public function coursesShow(
        Course $course,
        CourseSectionRepository $courseSectionRepository,
        QuizAttemptRepository $quizAttemptRepository
    ): Response {
        $sections = $courseSectionRepository->findBy(['course' => $course], ['orderIndex' => 'ASC']);
        $totalVideos = 0;
        foreach ($sections as $section) {
            $totalVideos += $section->getCourseVideos()->count();
        }

        $attempts = $quizAttemptRepository->findBy(['course' => $course], ['submittedAt' => 'DESC']);
        $passedAttempts = 0;
        $scoreSum = 0;
        foreach ($attempts as $attempt) {
            if ($attempt->isPassed()) {
                ++$passedAttempts;
            }
            $scoreSum += $attempt->getScorePercent();
        }

        return $this->render('back/content/course_show.html.twig', [
            'course' => $course,
            'sections' => $sections,
            'total_videos' => $totalVideos,
            'attempts' => $attempts,
            'passed_attempts' => $passedAttempts,
            'pass_rate' => count($attempts) > 0 ? (int) round(($passedAttempts / count($attempts)) * 100) : 0,
            'average_score' => count($attempts) > 0 ? (int) round($scoreSum / count($attempts)) : 0,
        ]);
    }

    #[Route('/courses/{id}/delete', name: 'courses_delete', methods: ['POST'])]
    public function coursesDelete(
        int $id,
        Request $request,
        CourseRepository $courseRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        if (!$this->isCsrfTokenValid('courses_delete_' . $id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_content_courses');
        }

        $course = $courseRepository->find($id);
        if (!$course instanceof Course) {
            $this->addFlash('danger', 'Course not found.');

            return $this->redirectToRoute('back_content_courses');
        }

        $entityManager->remove($course);
        $entityManager->flush();
        $this->addFlash('success', 'Course deleted successfully.');

        return $this->redirectToRoute('back_content_courses');
    }

    #[Route('/quizzes/{id}/delete', name: 'quizzes_delete', methods: ['POST'])]
    public function quizzesDelete(
        int $id,
        Request $request,
        QuizRepository $quizRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        if (!$this->isCsrfTokenValid('quizzes_delete_' . $id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_content_quizzes');
        }

        $quiz = $quizRepository->find($id);
        if (!$quiz instanceof Quiz) {
            $this->addFlash('danger', 'Quiz not found.');

            return $this->redirectToRoute('back_content_quizzes');
        }

        $entityManager->remove($quiz);
        $entityManager->flush();
        $this->addFlash('success', 'Quiz deleted successfully.');

        return $this->redirectToRoute('back_content_quizzes');
    }

    #[Route('/users/{id}/status', name: 'users_change_status', methods: ['POST'])]
    public function usersChangeStatus(
        int $id,
        Request $request,
        UserRepository $userRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_status_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $user = $userRepository->find($id);
        if (!$user instanceof User) {
            $this->addFlash('danger', 'User not found.');

            return $this->redirectToRoute('back_users');
        }

        $status = AccountStatus::tryFrom((string) $request->request->get('status', ''));
        if (!$status instanceof AccountStatus) {
            $this->addFlash('danger', 'Invalid status value.');

            return $this->redirectToRoute('back_users');
        }

        $user->setStatus($status);
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'User status updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/promote-admin', name: 'users_promote_admin', methods: ['POST'])]
    public function usersPromoteAdmin(
        int $id,
        Request $request,
        UserRepository $userRepository
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_promote_admin_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $user = $userRepository->find($id);
        if (!$user instanceof User) {
            $this->addFlash('danger', 'User not found.');

            return $this->redirectToRoute('back_users');
        }

        $this->addFlash('warning', 'Promoting users to admin is disabled.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/make-super-admin', name: 'users_make_super_admin', methods: ['POST'])]
    public function usersMakeSuperAdmin(
        int $id,
        Request $request,
        AdminRepository $adminRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_make_super_admin_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $admin = $adminRepository->find($id);
        if (!$admin instanceof Admin) {
            $this->addFlash('danger', 'Admin not found.');

            return $this->redirectToRoute('back_users');
        }

        if ($admin->isSuperAdmin()) {
            $this->addFlash('success', 'This admin is already super admin.');

            return $this->redirectToRoute('back_users');
        }

        $admin->setSuperAdmin(true);
        $entityManager->persist($admin);
        $entityManager->flush();

        $this->addFlash('success', 'Admin upgraded to super admin.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/artiste-verified', name: 'users_change_artiste_verified', methods: ['POST'])]
    public function usersChangeArtisteVerified(
        int $id,
        Request $request,
        ArtisteRepository $artisteRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_artiste_verified_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $artiste = $artisteRepository->find($id);
        if (!$artiste instanceof Artiste) {
            $this->addFlash('danger', 'Artiste not found.');

            return $this->redirectToRoute('back_users');
        }

        $verifiedRaw = (string) $request->request->get('verified', '');
        if (!in_array($verifiedRaw, ['0', '1'], true)) {
            $this->addFlash('danger', 'Invalid verified value.');

            return $this->redirectToRoute('back_users');
        }

        $artiste->setVerified($verifiedRaw === '1');
        $entityManager->persist($artiste);
        $entityManager->flush();

        $this->addFlash('success', 'Artiste verification updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/users/{id}/sponsor-verified', name: 'users_change_sponsor_verified', methods: ['POST'])]
    public function usersChangeSponsorVerified(
        int $id,
        Request $request,
        SponsorRepository $sponsorRepository,
        EntityManagerInterface $entityManager
    ): RedirectResponse {
        $superAdmin = $this->requireSuperAdmin();
        if (!$superAdmin instanceof Admin) {
            return $this->redirectToRoute('back_users');
        }

        if (!$this->isCsrfTokenValid('users_change_sponsor_verified_'.$id, (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_users');
        }

        $sponsor = $sponsorRepository->find($id);
        if (!$sponsor instanceof Sponsor) {
            $this->addFlash('danger', 'Sponsor not found.');

            return $this->redirectToRoute('back_users');
        }

        $verifiedRaw = (string) $request->request->get('verified', '');
        if (!in_array($verifiedRaw, ['0', '1'], true)) {
            $this->addFlash('danger', 'Invalid verified value.');

            return $this->redirectToRoute('back_users');
        }

        $sponsor->setVerified($verifiedRaw === '1');
        $entityManager->persist($sponsor);
        $entityManager->flush();

        $this->addFlash('success', 'Sponsor verification updated.');

        return $this->redirectToRoute('back_users');
    }

    #[Route('/profile/update', name: 'profile_update', methods: ['POST'])]
    public function profileUpdate(
        Request $request,
        EntityManagerInterface $entityManager,
        ValidatorInterface $validator
    ): RedirectResponse
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        if (!$this->isCsrfTokenValid('back_profile_update', (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $username = trim((string) $request->request->get('username', ''));
        if ($username === '') {
            $this->addFlash('danger', 'Username cannot be empty.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $user->setUsername($username);

        $phone = trim((string) $request->request->get('phone', ''));
        $user->setPhone($phone === '' ? null : $phone);

        if ($user instanceof Admin) {
            $birthDateRaw = trim((string) $request->request->get('birthDate', ''));
            if ($birthDateRaw === '') {
                $user->setBirthDate(null);
            } else {
                $birthDate = \DateTimeImmutable::createFromFormat('Y-m-d', $birthDateRaw);
                $errors = \DateTimeImmutable::getLastErrors();
                if (
                    !$birthDate instanceof \DateTimeImmutable ||
                    (false !== $errors && ($errors['warning_count'] > 0 || $errors['error_count'] > 0))
                ) {
                    $this->addFlash('danger', 'Invalid birth date format.');

                    return $this->redirectToRoute('back_profile_edit');
                }

                $user->setBirthDate($birthDate);
            }
        }

        $violations = $validator->validate($user);
        if (count($violations) > 0) {
            $this->addValidationErrorsToFlash($violations);

            return $this->redirectToRoute('back_profile_edit');
        }

        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Profile updated successfully.');

        return $this->redirectToRoute('back_profile');
    }

    #[Route('/profile/change-password', name: 'profile_change_password', methods: ['POST'])]
    public function profileChangePassword(
        Request $request,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ): RedirectResponse {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        if (!$this->isCsrfTokenValid('back_profile_change_password', (string) $request->request->get('_csrf_token'))) {
            $this->addFlash('danger', 'Invalid request token.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $currentPassword = (string) $request->request->get('currentPassword', '');
        $newPassword = (string) $request->request->get('newPassword', '');
        $confirmPassword = (string) $request->request->get('confirmPassword', '');

        if ($currentPassword === '' || $newPassword === '' || $confirmPassword === '') {
            $this->addFlash('danger', 'All password fields are required.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if (!$passwordHasher->isPasswordValid($user, $currentPassword)) {
            $this->addFlash('danger', 'Current password is incorrect.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if (strlen($newPassword) < 6) {
            $this->addFlash('danger', 'New password must be at least 6 characters.');

            return $this->redirectToRoute('back_profile_edit');
        }

        if ($newPassword !== $confirmPassword) {
            $this->addFlash('danger', 'New password and confirmation do not match.');

            return $this->redirectToRoute('back_profile_edit');
        }

        $user->setPassword($passwordHasher->hashPassword($user, $newPassword));
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('success', 'Password changed successfully.');

        return $this->redirectToRoute('back_profile_edit');
    }

    #[Route('/{path}.html', name: 'page', requirements: ['path' => '[A-Za-z0-9_\-/]+'], methods: ['GET'])]
    public function page(string $path): Response
    {
        if (str_contains($path, '..')) {
            throw $this->createNotFoundException();
        }

        $template = sprintf('back/%s.html.twig', $path);

        try {
            return $this->render($template);
        } catch (LoaderError) {
            throw $this->createNotFoundException(sprintf('Back template "%s" was not found.', $template));
        }
    }

    private function renderContentDashboard(
        CourseRepository $courseRepository,
        QuizRepository $quizRepository,
        QuizAttemptRepository $quizAttemptRepository,
        string $activeTab
    ): Response {
        $user = $this->getUser();
        if (!$user instanceof User) {
            return $this->redirectToRoute('app_back_login');
        }

        $courses = $courseRepository->findBy([], ['id' => 'DESC']);
        $quizzes = $quizRepository->findBy([], ['id' => 'DESC']);
        $attempts = $quizAttemptRepository->findBy([], ['submittedAt' => 'DESC']);

        $statusCounts = ['DRAFT' => 0, 'PUBLISHED' => 0, 'HIDDEN' => 0];
        $totalSections = 0;
        $totalVideos = 0;

        foreach ($courses as $course) {
            $status = strtoupper((string) $course->getStatus());
            if (!array_key_exists($status, $statusCounts)) {
                $statusCounts[$status] = 0;
            }

            ++$statusCounts[$status];
            $totalSections += $course->getCourseSections()->count();

            foreach ($course->getCourseSections() as $section) {
                $totalVideos += $section->getCourseVideos()->count();
            }
        }

        $attemptsByQuiz = [];
        $attemptsByCourse = [];
        $passedAttemptsByQuiz = [];
        $scoreSum = 0;
        $timeSum = 0;
        $passedAttempts = 0;

        foreach ($attempts as $attempt) {
            $quizId = $attempt->getQuiz()?->getId();
            $courseId = $attempt->getCourse()?->getId();

            if ($quizId !== null) {
                $attemptsByQuiz[$quizId] = ($attemptsByQuiz[$quizId] ?? 0) + 1;
                if ($attempt->isPassed()) {
                    $passedAttemptsByQuiz[$quizId] = ($passedAttemptsByQuiz[$quizId] ?? 0) + 1;
                }
            }

            if ($courseId !== null) {
                $attemptsByCourse[$courseId] = ($attemptsByCourse[$courseId] ?? 0) + 1;
            }

            if ($attempt->isPassed()) {
                ++$passedAttempts;
            }

            $scoreSum += $attempt->getScorePercent();
            $timeSum += $attempt->getTimeSpentSec();
        }

        $totalAttempts = count($attempts);
        $quizPassRates = [];

        foreach ($attemptsByQuiz as $quizId => $attemptCount) {
            $quizPassRates[$quizId] = $attemptCount > 0
                ? (int) round((($passedAttemptsByQuiz[$quizId] ?? 0) / $attemptCount) * 100)
                : 0;
        }

        return $this->render('back/content/dashboard.html.twig', [
            'active_tab' => $activeTab,
            'courses' => $courses,
            'quizzes' => $quizzes,
            'recent_attempts' => array_slice($attempts, 0, 8),
            'status_counts' => $statusCounts,
            'total_sections' => $totalSections,
            'total_videos' => $totalVideos,
            'total_attempts' => $totalAttempts,
            'passed_attempts' => $passedAttempts,
            'pass_rate' => $totalAttempts > 0 ? (int) round(($passedAttempts / $totalAttempts) * 100) : 0,
            'average_score' => $totalAttempts > 0 ? (int) round($scoreSum / $totalAttempts) : 0,
            'average_time_min' => $totalAttempts > 0 ? (int) round(($timeSum / $totalAttempts) / 60) : 0,
            'attempts_by_course' => $attemptsByCourse,
            'attempts_by_quiz' => $attemptsByQuiz,
            'quiz_pass_rates' => $quizPassRates,
        ]);
    }

    private function requireSuperAdmin(): ?Admin
    {
        $user = $this->getUser();
        if (!$user instanceof Admin || !$user->isSuperAdmin()) {
            $this->addFlash('danger', 'Only super admins can perform this action.');

            return null;
        }

        return $user;
    }

    private function addValidationErrorsToFlash(ConstraintViolationListInterface $violations): void
    {
        foreach ($violations as $violation) {
            $this->addFlash('danger', $violation->getMessage());
        }
    }
}
