<?php

namespace App\Controller\Admin;

use App\Repository\CommentaireRepository;
use App\Repository\PostRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/back/forum/stats', name: 'admin_forum_stats_')]
class ForumStatsController extends AbstractController
{
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(PostRepository $postRepository, CommentaireRepository $commentaireRepository): Response
    {
        return $this->render('admin/forum/stats.html.twig', [
            'countsByStatus' => $postRepository->countByStatus(),
            'topCommentedPosts' => $postRepository->topCommented(5),
            'totalComments' => $commentaireRepository->totalCount(),
        ]);
    }
}
