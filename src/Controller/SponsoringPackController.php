<?php

namespace App\Controller;

use App\Entity\SponsoringPack;
use App\Form\SponsoringPackType;
use App\Repository\SponsoringPackRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/admin/sponsoring-pack')]
#[IsGranted('ROLE_ADMIN')]
class SponsoringPackController extends AbstractController
{
    #[Route('/', name: 'admin_sponsoring_pack_index', methods: ['GET'])]
    public function index(SponsoringPackRepository $repo): Response
    {
        return $this->render('admin/sponsoring_pack/index.html.twig', [
            'packs' => $repo->findAll(),
        ]);
    }

    #[Route('/new', name: 'admin_sponsoring_pack_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $pack = new SponsoringPack();
        $form = $this->createForm(SponsoringPackType::class, $pack);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($pack);
            $em->flush();
            $this->addFlash('success', 'Pack créé avec succès.');
            return $this->redirectToRoute('admin_sponsoring_pack_index');
        }

        return $this->render('admin/sponsoring_pack/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/edit', name: 'admin_sponsoring_pack_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, SponsoringPack $pack, EntityManagerInterface $em): Response
    {
        $form = $this->createForm(SponsoringPackType::class, $pack);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();
            $this->addFlash('success', 'Pack modifié avec succès.');
            return $this->redirectToRoute('admin_sponsoring_pack_index');
        }

        return $this->render('admin/sponsoring_pack/edit.html.twig', [
            'pack' => $pack,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}/delete', name: 'admin_sponsoring_pack_delete', methods: ['POST'])]
    public function delete(Request $request, SponsoringPack $pack, EntityManagerInterface $em): Response
    {
        if ($this->isCsrfTokenValid('delete'.$pack->getId(), $request->request->get('_token'))) {
            $em->remove($pack);
            $em->flush();
            $this->addFlash('success', 'Pack supprimé.');
        }
        return $this->redirectToRoute('admin_sponsoring_pack_index');
    }
}
