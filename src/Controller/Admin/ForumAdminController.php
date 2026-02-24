<?php

namespace App\Controller\Admin;

use App\Entity\Commentaire;
use App\Entity\Post;
use App\Entity\User;
use App\Form\CommentaireType;
use App\Form\PostType;
use App\Repository\CommentaireRepository;
use App\Repository\PostRepository;
use App\Service\BadWordsDetectorService;
use App\Service\TranslationApiService;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use SymfonyCasts\ObjectTranslationBundle\ObjectTranslator;
use SymfonyCasts\ObjectTranslationBundle\Model\Translation as BaseTranslation;

#[Route('/admin/forum', name: 'admin_forum_')]
class ForumAdminController extends AbstractController
{
    private const TRANSLATABLE_LOCALES = [
        'en' => 'English',
        'fr' => 'Francais',
        'es' => 'Espanol',
        'de' => 'Deutsch',
        'it' => 'Italiano',
        'ar' => 'Arabic',
    ];

    public function __construct(
        private PostRepository $postRepository,
        private CommentaireRepository $commentaireRepository,
        private EntityManagerInterface $entityManager,
        private PaginatorInterface $paginator,
        private ObjectTranslator $objectTranslator,
        private TranslationApiService $translationApi,
        private BadWordsDetectorService $badWordsDetector,
    ) {
    }

    #[Route('', name: 'index', methods: ['GET'])]
    #[Route('/', name: 'index_slash', methods: ['GET'])]
    public function index(): Response
    {
        return $this->redirectToRoute('admin_forum_posts_index');
    }

    #[Route('/posts', name: 'posts_index', methods: ['GET'])]
    public function postsIndex(Request $request): Response
    {
        $page = max(1, $request->query->getInt('page', 1));
        $q = trim((string) $request->query->get('q', ''));
        $status = (string) $request->query->get('status', '');

        if (!in_array($status, Post::ALLOWED_STATUSES, true)) {
            $status = '';
        }

        $pagination = $this->paginator->paginate(
            $this->postRepository->createAdminListQueryBuilder($q, $status),
            $page,
            10
        );

        return $this->render('admin/forum/posts_index.html.twig', [
            'pagination' => $pagination,
            'q' => $q,
            'status' => $status,
        ]);
    }

    #[Route('/post/new', name: 'post_new', methods: ['GET', 'POST'])]
    public function postNew(Request $request): Response
    {
        $user = $this->requireAuthenticatedUser();

        $post = new Post();
        $post->setOwner($user);
        $post->setAuthorName($user->getDisplayName());
        $post->setAuthorEmail($user->getEmail());

        $form = $this->createForm(PostType::class, $post, ['is_admin' => true]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $owner = $post->getOwner();
            if ($owner instanceof User) {
                $post->setAuthorName($owner->getDisplayName());
                $post->setAuthorEmail($owner->getEmail());
            }

            if ($this->hasForbiddenWords([$post->getTitle(), $post->getContent()])) {
                $this->addFlash('error', 'Le sujet contient des mots interdits.');

                return $this->render('admin/forum/post_new.html.twig', [
                    'form' => $form,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $this->entityManager->persist($post);
            $this->entityManager->flush();

            $this->addFlash('success', 'Le sujet a ete cree avec succes.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        return $this->render('admin/forum/post_new.html.twig', [
            'form' => $form,
        ]);
    }

    #[Route('/post/{id}', name: 'post_show', methods: ['GET'])]
    public function postShow(Post $post, Request $request): Response
    {
        $locale = $request->getLocale();

        $translatedComments = [];
        foreach ($post->getCommentaires() as $commentaire) {
            $translatedComments[$commentaire->getId()] = $this->objectTranslator->translate($commentaire, $locale);
        }

        return $this->render('admin/forum/post_show.html.twig', [
            'post' => $post,
            'translatedPost' => $this->objectTranslator->translate($post, $locale),
            'translatedComments' => $translatedComments,
            'translatableLocales' => self::TRANSLATABLE_LOCALES,
        ]);
    }

    #[Route('/post/{id}/edit', name: 'post_edit', methods: ['GET', 'POST'])]
    public function postEdit(Post $post, Request $request): Response
    {
        if ($post->getOwner() === null) {
            $post->setOwner($this->requireAuthenticatedUser());
        }

        $form = $this->createForm(PostType::class, $post, ['is_admin' => true]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $owner = $post->getOwner();
            if ($owner instanceof User) {
                $post->setAuthorName($owner->getDisplayName());
                $post->setAuthorEmail($owner->getEmail());
            }

            if ($this->hasForbiddenWords([$post->getTitle(), $post->getContent()])) {
                $this->addFlash('error', 'Le sujet contient des mots interdits.');

                return $this->render('admin/forum/post_edit.html.twig', [
                    'form' => $form,
                    'post' => $post,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $post->setUpdatedAt(new \DateTimeImmutable());
            $this->entityManager->flush();

            $this->addFlash('success', 'Le sujet a ete modifie avec succes.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        return $this->render('admin/forum/post_edit.html.twig', [
            'form' => $form,
            'post' => $post,
        ]);
    }

    #[Route('/post/{id}/delete', name: 'post_delete', methods: ['POST'])]
    public function postDelete(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('delete_post_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $this->entityManager->remove($post);
        $this->entityManager->flush();

        $this->addFlash('success', 'Le sujet a ete supprime.');

        return $this->redirectToRoute('admin_forum_posts_index');
    }

    #[Route('/post/{id}/status/{status}', name: 'post_status', methods: ['POST'])]
    public function postStatus(Post $post, string $status, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('status_' . $post->getId() . '_' . $status, (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        if (!in_array($status, Post::ALLOWED_STATUSES, true)) {
            $this->addFlash('error', 'Statut invalide.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        $post->setStatus($status);
        $post->setUpdatedAt(new \DateTimeImmutable());
        $this->entityManager->flush();

        $this->addFlash('success', sprintf('Statut mis a jour: %s.', $status));

        return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
    }

    #[Route('/post/{id}/translate', name: 'post_translate', methods: ['POST'])]
    public function translatePost(Post $post, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('translate_post_' . $post->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $targetLocale = (string) $request->request->get('target_locale');
        if (!array_key_exists($targetLocale, self::TRANSLATABLE_LOCALES)) {
            $this->addFlash('error', 'Langue cible invalide.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        $translatedTitle = trim((string) $request->request->get('translated_title', ''));
        $translatedContent = trim((string) $request->request->get('translated_content', ''));

        if ($translatedTitle === '') {
            $translatedTitle = (string) $this->translationApi->translate($post->getTitle(), $targetLocale, 'auto');
        }

        if ($translatedContent === '') {
            $translatedContent = (string) $this->translationApi->translate($post->getContent(), $targetLocale, 'auto');
        }

        if ($translatedTitle === '' && $translatedContent === '') {
            $this->addFlash('error', 'Aucune traduction a enregistrer.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        if ($this->hasForbiddenWords([$translatedTitle, $translatedContent])) {
            $this->addFlash('error', 'La traduction contient des mots interdits.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
        }

        if ($translatedTitle !== '') {
            $this->upsertTranslation('post', (string) $post->getId(), $targetLocale, 'title', $translatedTitle);
        }

        if ($translatedContent !== '') {
            $this->upsertTranslation('post', (string) $post->getId(), $targetLocale, 'content', $translatedContent);
        }

        $post->setUpdatedAt(new \DateTimeImmutable());
        $this->entityManager->flush();

        $this->addFlash('success', sprintf('Traduction du post en %s enregistree.', self::TRANSLATABLE_LOCALES[$targetLocale]));

        return $this->redirectToRoute('admin_forum_post_show', ['id' => $post->getId()]);
    }

    #[Route('/comments', name: 'comments_index', methods: ['GET'])]
    public function commentsIndex(Request $request): Response
    {
        $page = max(1, $request->query->getInt('page', 1));
        $limit = 20;
        $offset = ($page - 1) * $limit;
        $search = $request->query->get('search', '');

        $comments = $this->commentaireRepository->findRecent($search ?: null, $limit, $offset);
        $total = $this->commentaireRepository->countRecent($search ?: null);
        $totalPages = (int) ceil($total / $limit);

        return $this->render('admin/forum/comments_index.html.twig', [
            'comments' => $comments,
            'currentPage' => $page,
            'totalPages' => $totalPages,
            'search' => $search,
        ]);
    }

    #[Route('/comment/{id}/edit', name: 'comment_edit', methods: ['GET', 'POST'])]
    public function commentEdit(Commentaire $commentaire, Request $request): Response
    {
        if ($commentaire->getOwner() !== null) {
            $commentaire->setAuthorName($commentaire->getOwner()->getDisplayName());
            $commentaire->setAuthorEmail($commentaire->getOwner()->getEmail());
        }

        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if ($this->hasForbiddenWords([$commentaire->getContent()])) {
                $this->addFlash('error', 'Le commentaire contient des mots interdits.');

                return $this->render('admin/forum/comment_edit.html.twig', [
                    'form' => $form,
                    'commentaire' => $commentaire,
                    'post' => $commentaire->getPost(),
                    'translatableLocales' => self::TRANSLATABLE_LOCALES,
                ], new Response('', Response::HTTP_UNPROCESSABLE_ENTITY));
            }

            $commentaire->setUpdatedAt(new \DateTimeImmutable());
            $this->entityManager->flush();

            $this->addFlash('success', 'Le commentaire a ete modifie.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $commentaire->getPost()->getId()]);
        }

        return $this->render('admin/forum/comment_edit.html.twig', [
            'form' => $form,
            'commentaire' => $commentaire,
            'post' => $commentaire->getPost(),
            'translatableLocales' => self::TRANSLATABLE_LOCALES,
        ]);
    }

    #[Route('/comment/{id}/translate', name: 'comment_translate', methods: ['POST'])]
    public function translateComment(Commentaire $commentaire, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('translate_comment_' . $commentaire->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $targetLocale = (string) $request->request->get('target_locale');
        if (!array_key_exists($targetLocale, self::TRANSLATABLE_LOCALES)) {
            $this->addFlash('error', 'Langue cible invalide.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $commentaire->getPost()->getId()]);
        }

        $translatedContent = trim((string) $request->request->get('translated_content', ''));
        if ($translatedContent === '') {
            $translatedContent = (string) $this->translationApi->translate($commentaire->getContent(), $targetLocale, 'auto');
        }

        if ($translatedContent === '') {
            $this->addFlash('error', 'Le contenu traduit est vide.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $commentaire->getPost()->getId()]);
        }

        if ($this->hasForbiddenWords([$translatedContent])) {
            $this->addFlash('error', 'La traduction du commentaire contient des mots interdits.');

            return $this->redirectToRoute('admin_forum_post_show', ['id' => $commentaire->getPost()->getId()]);
        }

        $this->upsertTranslation('commentaire', (string) $commentaire->getId(), $targetLocale, 'content', $translatedContent);

        $commentaire->setUpdatedAt(new \DateTimeImmutable());
        $this->entityManager->flush();

        $this->addFlash('success', sprintf('Traduction du commentaire en %s enregistree.', self::TRANSLATABLE_LOCALES[$targetLocale]));

        return $this->redirectToRoute('admin_forum_post_show', ['id' => $commentaire->getPost()->getId()]);
    }

    #[Route('/comment/{id}/delete', name: 'comment_delete', methods: ['POST'])]
    public function commentDelete(Commentaire $commentaire, Request $request): Response
    {
        if (!$this->isCsrfTokenValid('delete_comment_' . $commentaire->getId(), (string) $request->request->get('_token'))) {
            throw $this->createAccessDeniedException('Invalid CSRF token');
        }

        $this->entityManager->remove($commentaire);
        $this->entityManager->flush();

        $this->addFlash('success', 'Le commentaire a ete supprime.');

        return $this->redirectToRoute('admin_forum_comments_index');
    }

    private function upsertTranslation(string $type, string $id, string $locale, string $field, string $value): void
    {
        /** @var BaseTranslation|null $translation */
        $translation = $this->entityManager->getRepository(\App\Entity\Translation::class)->findOneBy([
            'objectType' => $type,
            'objectId' => $id,
            'locale' => $locale,
            'field' => $field,
        ]);

        if (!$translation) {
            $translation = new \App\Entity\Translation();
            $translation->objectType = $type;
            $translation->objectId = $id;
            $translation->locale = $locale;
            $translation->field = $field;
        }

        $translation->value = $value;

        $this->entityManager->persist($translation);
    }

    /**
     * @param array<int, string> $texts
     */
    private function hasForbiddenWords(array $texts): bool
    {
        foreach ($texts as $text) {
            if ($text !== '' && $this->badWordsDetector->hasBadWords($text)) {
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
}
