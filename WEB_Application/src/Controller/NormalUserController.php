<?php

namespace App\Controller;

use App\Entity\NormalUser;
use App\Form\NormalUserType;
use App\Repository\NormalUserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/normal/user')]
final class NormalUserController extends AbstractController
{
    #[Route(name: 'app_normal_user_index', methods: ['GET'])]
    public function index(NormalUserRepository $normalUserRepository): Response
    {
        return $this->render('normal_user/index.html.twig', [
            'normal_users' => $normalUserRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_normal_user_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $normalUser = new NormalUser();
        $form = $this->createForm(NormalUserType::class, $normalUser);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($normalUser);
            $entityManager->flush();

            return $this->redirectToRoute('app_normal_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('normal_user/new.html.twig', [
            'normal_user' => $normalUser,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_normal_user_show', methods: ['GET'])]
    public function show(NormalUser $normalUser): Response
    {
        return $this->render('normal_user/show.html.twig', [
            'normal_user' => $normalUser,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_normal_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, NormalUser $normalUser, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(NormalUserType::class, $normalUser);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_normal_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('normal_user/edit.html.twig', [
            'normal_user' => $normalUser,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_normal_user_delete', methods: ['POST'])]
    public function delete(Request $request, NormalUser $normalUser, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$normalUser->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($normalUser);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_normal_user_index', [], Response::HTTP_SEE_OTHER);
    }
}
