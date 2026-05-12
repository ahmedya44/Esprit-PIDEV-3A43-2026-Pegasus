<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Post;
use App\Repository\CommentaireRepository;
use App\Repository\PostRepository;
use App\Repository\PostRatingRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/back/forum', name: 'admin_forum_')]
class AdminForumController extends AbstractController
{
    public function __construct(
        private PostRepository $postRepository,
        private CommentaireRepository $commentaireRepository,
        private PostRatingRepository $postRatingRepository,
        private EntityManagerInterface $em,
    ) {
    }

    #[Route('', name: 'index', methods: ['GET'])]
    public function index(Request $request): Response
    {
        $q = trim((string) $request->query->get('q', ''));
        $status = (string) $request->query->get('status', '');
        if (!in_array($status, Post::ALLOWED_STATUSES, true)) {
            $status = '';
        }

        $posts = $this->postRepository->createAdminListQueryBuilder($q, $status)->getQuery()->getResult();

        $postIds = array_values(array_filter(
            array_map(static fn (Post $p): ?int => $p->getId(), $posts),
            static fn (?int $id): bool => $id !== null
        ));

        return $this->render('back/forum/index.html.twig', [
            'posts'          => $posts,
            'q'              => $q,
            'status'         => $status,
            'ratingSummaries' => $this->postRatingRepository->getSummariesForPostIds($postIds),
        ]);
    }

    #[Route('/requests', name: 'requests', methods: ['GET'])]
    public function requests(Request $request): Response
    {
        $requestType = (string) $request->query->get('type', '');
        if (!in_array($requestType, [Post::REQUEST_TYPE_CREATE, Post::REQUEST_TYPE_EDIT], true)) {
            $requestType = '';
        }

        $posts = $this->postRepository->findPendingRequests($requestType ?: null);

        return $this->render('back/forum/requests.html.twig', [
            'posts'       => $posts,
            'requestType' => $requestType,
            'counts'      => [
                'all'    => count($this->postRepository->findPendingRequests()),
                'create' => count($this->postRepository->findPendingRequests(Post::REQUEST_TYPE_CREATE)),
                'edit'   => count($this->postRepository->findPendingRequests(Post::REQUEST_TYPE_EDIT)),
            ],
        ]);
    }

    #[Route('/requests/{id}/accept', name: 'request_accept', methods: ['POST'])]
    public function acceptRequest(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('forum_request_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $post->setStatus(Post::STATUS_OPEN);
        $post->setRequestType(null);
        $this->em->flush();

        $this->addFlash('success', sprintf('Post "%s" approved and published.', $post->getTitle()));

        return $this->redirectToRoute('admin_forum_requests');
    }

    #[Route('/requests/{id}/deny', name: 'request_deny', methods: ['POST'])]
    public function denyRequest(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('forum_request_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $post->setStatus(Post::STATUS_DENIED);
        $post->setRequestType(null);
        $this->em->flush();

        $this->addFlash('success', sprintf('Post "%s" denied.', $post->getTitle()));

        return $this->redirectToRoute('admin_forum_requests');
    }

    #[Route('/post/{id}/ban', name: 'post_ban', methods: ['POST'])]
    public function banPost(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('forum_ban_post_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $post->setBannedByAdmin(!$post->isBannedByAdmin());
        $this->em->flush();

        $action = $post->isBannedByAdmin() ? 'banned' : 'unbanned';
        $this->addFlash('success', sprintf('Post "%s" has been %s.', $post->getTitle(), $action));

        return $this->redirectToRoute('admin_forum_index');
    }

    #[Route('/post/{id}/status', name: 'post_status', methods: ['POST'])]
    public function changePostStatus(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('forum_status_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $newStatus = (string) $request->request->get('status', '');
        if (in_array($newStatus, Post::ALLOWED_STATUSES, true)) {
            $post->setStatus($newStatus);
            if ($newStatus === Post::STATUS_OPEN) {
                $post->setRequestType(null);
            }
            $this->em->flush();
            $this->addFlash('success', sprintf('Post status changed to %s.', $newStatus));
        }

        return $this->redirectToRoute('admin_forum_index');
    }

    #[Route('/comment/{id}/ban', name: 'comment_ban', methods: ['POST'])]
    public function banComment(Commentaire $commentaire, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('forum_ban_comment_' . $commentaire->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $commentaire->setBannedByAdmin(!$commentaire->isBannedByAdmin());
        $this->em->flush();

        $action = $commentaire->isBannedByAdmin() ? 'banned' : 'unbanned';
        $this->addFlash('success', sprintf('Comment has been %s.', $action));

        $post = $commentaire->getPost();
        if ($post instanceof Post && $post->getId() !== null) {
            return $this->redirectToRoute('admin_forum_index', ['#' => 'post-' . $post->getId()]);
        }

        return $this->redirectToRoute('admin_forum_index');
    }

    #[Route('/moderation-stats', name: 'stats', methods: ['GET'])]
    public function stats(): Response
    {
        $countsByStatus = $this->postRepository->countByStatus();
        $bannedPosts = $this->postRepository->countBanned();
        $bannedComments = $this->commentaireRepository->countBanned();
        $topCommented = $this->postRepository->topCommented(5);

        $postIds = array_values(array_filter(
            array_map(static fn (array $row): ?int => $row['post']->getId(), $topCommented),
            static fn (?int $id): bool => $id !== null
        ));
        $ratingSummaries = $this->postRatingRepository->getSummariesForPostIds($postIds);

        return $this->render('back/forum/stats.html.twig', [
            'countsByStatus' => $countsByStatus,
            'bannedPosts'    => $bannedPosts,
            'bannedComments' => $bannedComments,
            'topCommented'   => $topCommented,
            'ratingSummaries' => $ratingSummaries,
            'totalComments'  => $this->commentaireRepository->totalCount(),
            'totalPosts'     => array_sum($countsByStatus),
        ]);
    }
}
