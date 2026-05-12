<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\RoleRequest;
use App\Form\RoleRequestType;
use App\Repository\RoleRequestRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[IsGranted('IS_AUTHENTICATED_FULLY')]
final class RoleRequestController extends AbstractController
{
    #[Route('/role-request', name: 'role_request_new', methods: ['GET', 'POST'])]
    public function new(Request $request, RoleRequestRepository $repo, EntityManagerInterface $em): Response
    {
        $user = $this->getUser();

        if ($repo->hasPendingRequest($user)) {
            $this->addFlash('warning', 'You already have a pending role request. Please wait for admin review.');
            return $this->redirectToRoute('role_request_status');
        }

        $roleRequest = new RoleRequest();
        $form = $this->createForm(RoleRequestType::class, $roleRequest);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $roleRequest->setUser($user);
            $em->persist($roleRequest);
            $em->flush();

            $this->addFlash('success', 'Your role request has been submitted. An admin will review it shortly.');
            return $this->redirectToRoute('role_request_status');
        }

        return $this->render('front/role_request/form.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/role-request/status', name: 'role_request_status', methods: ['GET'])]
    public function status(RoleRequestRepository $repo): Response
    {
        return $this->render('front/role_request/status.html.twig', [
            'requests' => $repo->findByUser($this->getUser()),
        ]);
    }
}
