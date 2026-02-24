<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Post;
use App\Entity\PostRating;
use App\Entity\User;
use App\Form\CommentaireType;
use App\Form\PostType;
use App\Form\PostRatingType;
use App\Repository\CommentaireRepository;
use App\Repository\PostRepository;
use App\Repository\PostRatingRepository;
use App\Service\BadWordsDetectorService;
use App\Service\TranslationApiService;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use SymfonyCasts\ObjectTranslationBundle\ObjectTranslator;

#[Route('/forum', name: 'forum_')]
class ForumController extends AbstractController
{
    private const AVAILABLE_LOCALES = ['en', 'fr', 'es', 'de', 'it', 'ar'];

    public function __construct(
        private PostRepository $postRepository,
        private CommentaireRepository $commentaireRepository,
        private PostRatingRepository $postRatingRepository,
        private EntityManagerInterface $entityManager,
        private PaginatorInterface $paginator,
        private ObjectTranslator $objectTranslator,
        private BadWordsDetectorService $badWordsDetector,
        private TranslationApiService $translationApi,
    ) {
    }

    #[Route('', name: 'index', methods: ['GET'])]
    public function index(Request $request): Response
    {
        $page = max(1, $request->query->getInt('page', 1));
        $q = trim((string) $request->query->get('q', ''));
        $status = (string) $request->query->get('status', '');

        if (!in_array($status, [Post::STATUS_OPEN, Post::STATUS_CLOSED], true)) {
            $status = '';
        }

        $pagination = $this->paginator->paginate(
            $this->postRepository->createFrontListQueryBuilder($q, $status),
            $page,
            10
        );

        $postIds = [];
        foreach ($pagination as $post) {
            if ($post instanceof Post && $post->getId() !== null) {
                $postIds[] = $post->getId();
            }
        }

        return $this->render('forum/index.html.twig', [
            'pagination' => $pagination,
            'q' => $q,
            'status' => $status,
            'ratingSummaries' => $this->postRatingRepository->getSummariesForPostIds($postIds),
        ]);
    }

    #[Route('/stats', name: 'stats', methods: ['GET'])]
    public function stats(Request $request): Response
    {
        $status = (string) $request->query->get('status', '');
        if (!in_array($status, ['', Post::STATUS_OPEN, Post::STATUS_CLOSED], true)) {
            $status = '';
        }

        $countsByStatus = $this->postRepository->countByStatus();
        $topCommentedPosts = $this->postRepository->topCommented(10);

        if ($status !== '') {
            $topCommentedPosts = array_values(array_filter(
                $topCommentedPosts,
                static fn (array $row): bool => $row['post']->getStatus() === $status
            ));
        }

        return $this->render('forum/stats.html.twig', [
            'status' => $status,
            'countsByStatus' => $countsByStatus,
            'topCommentedPosts' => $topCommentedPosts,
            'totalComments' => $this->commentaireRepository->totalCount(),
            'generatedAt' => new \DateTimeImmutable(),
        ]);
    }

    #[Route('/new', name: 'new', methods: ['GET', 'POST'])]
    public function new(Request $request): Response
    {
        $user = $this->requireAuthenticatedUser();

        $post = new Post();
        $post->setStatus(Post::STATUS_OPEN);
        $post->setOwner($user);
        $post->setAuthorName($user->getDisplayName());
        $post->setAuthorEmail($user->getEmail());

        $form = $this->createForm(PostType::class, $post, ['is_admin' => false]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $owner = $post->getOwner();
            if ($owner instanceof User) {
                $post->setAuthorName($owner->getDisplayName());
                $post->setAuthorEmail($owner->getEmail());
            }

            if ($this->hasForbiddenWords([$post->getTitle(), $post->getContent()])) {
                $this->addFlash('error', 'Le sujet contient des mots interdits.');

                return $this->render('forum/new.html.twig', [
                    'form' => $form,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $this->entityManager->persist($post);
            $this->entityManager->flush();

            $this->addFlash('success', 'Votre sujet a ete cree avec succes.');

            return $this->redirectToRoute('forum_show', ['id' => $post->getId()]);
        }

        return $this->render('forum/new.html.twig', [
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'show', methods: ['GET'])]
    #[Route('/post/{id}', name: 'show_legacy', methods: ['GET'])]
    public function show(Post $post, Request $request): Response
    {
        if ($post->isHidden()) {
            throw $this->createNotFoundException('Ce sujet n\'existe pas.');
        }

        $locale = $this->selectedTranslationLocale($request);

        $commentFormView = null;
        if ($post->isOpen() && $this->getUser() instanceof User) {
            $commentaire = new Commentaire();
            $commentActionParams = ['id' => $post->getId()];
            if ($locale !== null) {
                $commentActionParams['lang'] = $locale;
            }
            $commentForm = $this->createForm(CommentaireType::class, $commentaire, [
                'action' => $this->generateUrl('forum_add_comment', $commentActionParams),
                'method' => 'POST',
            ]);
            $commentFormView = $commentForm->createView();
        }

        $rateActionParams = ['id' => $post->getId()];
        if ($locale !== null) {
            $rateActionParams['lang'] = $locale;
        }

        $ratingForm = $this->createForm(PostRatingType::class, new PostRating(), [
            'action' => $this->generateUrl('forum_rate', $rateActionParams),
            'method' => 'POST',
        ]);

        return $this->render('forum/show.html.twig', [
            'post' => $post,
            'translatedPost' => $this->translatedPostPayload($post, $locale, $request),
            'translatedComments' => $this->translatedComments($post, $locale),
            'commentForm' => $commentFormView,
            'ratingForm' => $ratingForm->createView(),
            'ratingSummary' => $this->postRatingRepository->getSummaryForPost($post),
            'displayLocale' => $locale,
            'availableLocales' => self::AVAILABLE_LOCALES,
        ]);
    }

    #[Route('/{id}/edit', name: 'edit', methods: ['GET', 'POST'])]
    public function edit(Post $post, Request $request): Response
    {
        if ($post->isHidden()) {
            throw $this->createNotFoundException('Ce sujet ne peut pas etre modifie.');
        }
        $this->denyUnlessOwnerOrAdmin($post->getOwner());

        $form = $this->createForm(PostType::class, $post, ['is_admin' => false]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if ($this->hasForbiddenWords([$post->getTitle(), $post->getContent()])) {
                $this->addFlash('error', 'Le sujet contient des mots interdits.');

                return $this->render('forum/edit.html.twig', [
                    'form' => $form,
                    'post' => $post,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $post->setUpdatedAt(new \DateTimeImmutable());
            $this->entityManager->flush();

            $this->addFlash('success', 'Votre sujet a ete modifie avec succes.');

            return $this->redirectToRoute('forum_show', ['id' => $post->getId()]);
        }

        return $this->render('forum/edit.html.twig', [
            'form' => $form,
            'post' => $post,
        ]);
    }

    #[Route('/{id}/delete', name: 'delete', methods: ['POST'])]
    public function delete(Post $post, Request $request): Response
    {
        $this->denyUnlessOwnerOrAdmin($post->getOwner());

        if (!$this->isCsrfTokenValid('delete_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $this->entityManager->remove($post);
        $this->entityManager->flush();

        $this->addFlash('success', 'Le sujet a ete supprime avec succes.');

        return $this->redirectToRoute('forum_index');
    }

    #[Route('/{id}/add-comment', name: 'add_comment', methods: ['POST'])]
    public function addComment(Post $post, Request $request): Response
    {
        $user = $this->requireAuthenticatedUser();

        if ($post->isHidden()) {
            throw $this->createNotFoundException('Ce sujet n\'existe pas.');
        }

        if ($post->isClosed()) {
            $this->addFlash('error', 'Ce sujet est ferme. Vous ne pouvez plus ajouter de commentaire.');

            $redirectParams = ['id' => $post->getId()];
            $locale = $this->selectedTranslationLocale($request);
            if ($locale !== null) {
                $redirectParams['lang'] = $locale;
            }

            return $this->redirectToRoute('forum_show', [
                ...$redirectParams,
            ]);
        }

        $commentaire = new Commentaire();
        $commentaire->setPost($post);
        $commentaire->setOwner($user);
        $commentaire->setAuthorName($user->getDisplayName());
        $commentaire->setAuthorEmail($user->getEmail());
        $locale = $this->selectedTranslationLocale($request);
        $commentActionParams = ['id' => $post->getId()];
        if ($locale !== null) {
            $commentActionParams['lang'] = $locale;
        }

        $form = $this->createForm(CommentaireType::class, $commentaire, [
            'action' => $this->generateUrl('forum_add_comment', $commentActionParams),
            'method' => 'POST',
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $owner = $commentaire->getOwner();
            if ($owner instanceof User) {
                $commentaire->setAuthorName($owner->getDisplayName());
                $commentaire->setAuthorEmail($owner->getEmail());
            }

            if ($this->hasForbiddenWords([$commentaire->getContent()])) {
                $this->addFlash('error', 'Le commentaire contient des mots interdits.');
                $rateActionParams = ['id' => $post->getId()];
                if ($locale !== null) {
                    $rateActionParams['lang'] = $locale;
                }

                $ratingForm = $this->createForm(PostRatingType::class, new PostRating(), [
                    'action' => $this->generateUrl('forum_rate', $rateActionParams),
                    'method' => 'POST',
                ]);

                $locale = $this->selectedTranslationLocale($request);

                return $this->render('forum/show.html.twig', [
                    'post' => $post,
                    'translatedPost' => $this->translatedPostPayload($post, $locale, $request),
                    'translatedComments' => $this->translatedComments($post, $locale),
                    'commentForm' => $form->createView(),
                    'ratingForm' => $ratingForm->createView(),
                    'ratingSummary' => $this->postRatingRepository->getSummaryForPost($post),
                    'displayLocale' => $locale,
                    'availableLocales' => self::AVAILABLE_LOCALES,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $this->entityManager->persist($commentaire);
            $this->entityManager->flush();

            $this->addFlash('success', 'Votre commentaire a ete ajoute.');

            $redirectParams = ['id' => $post->getId()];
            if ($locale !== null) {
                $redirectParams['lang'] = $locale;
            }

            return $this->redirectToRoute('forum_show', $redirectParams);
        }

        $rateActionParams = ['id' => $post->getId()];
        if ($locale !== null) {
            $rateActionParams['lang'] = $locale;
        }

        $ratingForm = $this->createForm(PostRatingType::class, new PostRating(), [
            'action' => $this->generateUrl('forum_rate', $rateActionParams),
            'method' => 'POST',
        ]);

        return $this->render('forum/show.html.twig', [
            'post' => $post,
            'translatedPost' => $this->translatedPostPayload($post, $locale, $request),
            'translatedComments' => $this->translatedComments($post, $locale),
            'commentForm' => $form->createView(),
            'ratingForm' => $ratingForm->createView(),
            'ratingSummary' => $this->postRatingRepository->getSummaryForPost($post),
            'displayLocale' => $locale,
            'availableLocales' => self::AVAILABLE_LOCALES,
        ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
    }

    #[Route('/{id}/rate', name: 'rate', methods: ['POST'])]
    public function rate(Post $post, Request $request): Response
    {
        $user = $this->requireAuthenticatedUser();

        if ($post->isHidden()) {
            throw $this->createNotFoundException('Ce sujet n\'existe pas.');
        }

        $locale = $this->selectedTranslationLocale($request);
        $rateActionParams = ['id' => $post->getId()];
        if ($locale !== null) {
            $rateActionParams['lang'] = $locale;
        }

        $submittedRating = new PostRating();
        $submittedRating->setRaterEmail($user->getEmail());
        $form = $this->createForm(PostRatingType::class, $submittedRating, [
            'action' => $this->generateUrl('forum_rate', $rateActionParams),
            'method' => 'POST',
        ]);
        $form->handleRequest($request);

        if (!$form->isSubmitted() || !$form->isValid()) {
            $this->addFlash('error', 'Veuillez saisir une note valide entre 0.5 et 5.');

            return $this->redirectToRoute('forum_show', $rateActionParams);
        }

        $email = mb_strtolower(trim($user->getEmail()));
        $existingRating = $this->postRatingRepository->findOneBy([
            'post' => $post,
            'raterEmail' => $email,
        ]);

        if ($existingRating instanceof PostRating) {
            $existingRating->setValue($submittedRating->getValue());
            $existingRating->setRaterEmail($email);
            $this->addFlash('success', 'Votre note a ete mise a jour.');
        } else {
            $submittedRating->setPost($post);
            $submittedRating->setRaterEmail($email);
            $this->entityManager->persist($submittedRating);
            $this->addFlash('success', 'Merci, votre note a ete enregistree.');
        }

        $this->entityManager->flush();

        return $this->redirectToRoute('forum_show', $rateActionParams);
    }

    #[Route('/comment/{id}/edit', name: 'comment_edit', methods: ['GET', 'POST'])]
    public function editComment(Commentaire $commentaire, Request $request): Response
    {
        $this->denyUnlessOwnerOrAdmin($commentaire->getOwner());

        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if ($this->hasForbiddenWords([$commentaire->getContent()])) {
                $this->addFlash('error', 'Le commentaire contient des mots interdits.');

                return $this->render('forum/comment_edit.html.twig', [
                    'form' => $form,
                    'commentaire' => $commentaire,
                    'post' => $commentaire->getPost(),
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $commentaire->setUpdatedAt(new \DateTimeImmutable());
            $this->entityManager->flush();

            $this->addFlash('success', 'Votre commentaire a ete modifie.');

            return $this->redirectToRoute('forum_show', ['id' => $commentaire->getPost()->getId()]);
        }

        return $this->render('forum/comment_edit.html.twig', [
            'form' => $form,
            'commentaire' => $commentaire,
            'post' => $commentaire->getPost(),
        ]);
    }

    #[Route('/comment/{id}/delete', name: 'comment_delete', methods: ['POST'])]
    public function deleteComment(Commentaire $commentaire, Request $request): Response
    {
        $this->denyUnlessOwnerOrAdmin($commentaire->getOwner());

        if (!$this->isCsrfTokenValid('delete_comment_' . $commentaire->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $postId = $commentaire->getPost()->getId();
        $this->entityManager->remove($commentaire);
        $this->entityManager->flush();

        $this->addFlash('success', 'Le commentaire a ete supprime.');

        return $this->redirectToRoute('forum_show', ['id' => $postId]);
    }

    /**
     * @return array<int, object>
     */
    private function translatedComments(Post $post, ?string $locale): array
    {
        if ($locale === null) {
            return [];
        }

        $translated = [];

        foreach ($post->getCommentaires() as $commentaire) {
            $translated[$commentaire->getId()] = $this->objectTranslator->translate($commentaire, $locale);
        }

        return $translated;
    }

    private function selectedTranslationLocale(Request $request): ?string
    {
        $requested = trim((string) $request->query->get('lang', ''));

        if ($requested === '' || $requested === 'orig') {
            return null;
        }

        return in_array($requested, self::AVAILABLE_LOCALES, true) ? $requested : null;
    }

    private function translatedPostPayload(Post $post, ?string $locale, Request $request): object
    {
        if ($locale === null) {
            return (object) [
                'title' => (string) $post->getTitle(),
                'content' => (string) $post->getContent(),
            ];
        }

        $translated = $this->objectTranslator->translate($post, $locale);

        $payload = (object) [
            'title' => (string) $translated->getTitle(),
            'content' => (string) $translated->getContent(),
        ];

        if ($payload->title === $post->getTitle()) {
            $apiTitle = $this->translationApi->translate($post->getTitle(), $locale, 'auto');
            if ($apiTitle) {
                $payload->title = $apiTitle;
            }
        }

        if ($payload->content === $post->getContent()) {
            $apiContent = $this->translationApi->translate($post->getContent(), $locale, 'auto');
            if ($apiContent) {
                $payload->content = $apiContent;
            }
        }

        return $payload;
    }

    /**
     * @param array<int, string> $texts
     */
    private function hasForbiddenWords(array $texts): bool
    {
        foreach ($texts as $text) {
            if ($this->badWordsDetector->hasBadWords($text)) {
                return true;
            }
        }

        return false;
    }

    private function requireAuthenticatedUser(): User
    {
        $user = $this->getUser();
        if (!$user instanceof User) {
            throw $this->createAccessDeniedException('Vous devez etre connecte.');
        }

        return $user;
    }

    private function denyUnlessOwnerOrAdmin(?User $owner): void
    {
        if ($this->isGranted('ROLE_ADMIN')) {
            return;
        }

        $user = $this->getUser();
        if (!$user instanceof User || !$owner instanceof User || $user->getId() !== $owner->getId()) {
            throw $this->createAccessDeniedException('Acces refuse: vous n\'etes pas proprietaire de ce contenu.');
        }
    }
}
