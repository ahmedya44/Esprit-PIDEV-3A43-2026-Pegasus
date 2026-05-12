<?php

declare(strict_types=1);

namespace App\Controller\Admin;

use App\Entity\RoleRequest;
use App\Repository\RoleRequestRepository;
use Doctrine\DBAL\Connection;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[IsGranted('ROLE_ADMIN')]
#[Route('/back/role-requests')]
final class RoleRequestAdminController extends AbstractController
{
    #[Route('', name: 'admin_role_requests_index', methods: ['GET'])]
    public function index(RoleRequestRepository $repo): Response
    {
        return $this->render('back/role_request/index.html.twig', [
            'pending'  => $repo->findPendingRequests(),
            'all'      => $repo->findBy([], ['createdAt' => 'DESC'], 50),
        ]);
    }

    #[Route('/{id}/approve', name: 'admin_role_requests_approve', methods: ['POST'])]
    public function approve(
        RoleRequest $roleRequest,
        Request $request,
        EntityManagerInterface $em,
        Connection $conn,
        MailerInterface $mailer,
    ): Response {
        if (!$this->isCsrfTokenValid('approve' . $roleRequest->getId(), (string) $request->request->get('_token'))) {
            $this->addFlash('error', 'Invalid CSRF token.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        if (!$roleRequest->isPending()) {
            $this->addFlash('warning', 'This request has already been processed.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        $user = $roleRequest->getUser();
        if (!$user) {
            $this->addFlash('error', 'User not found.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        $requestedRole = $roleRequest->getRequestedRole();
        $userId = $user->getId();

        // Update dtype and roles via raw DBAL (Doctrine joined-table inheritance limitation)
        if ($requestedRole === 'ROLE_ARTISTE') {
            $conn->executeStatement('UPDATE `user` SET dtype = :dtype WHERE id = :id', ['dtype' => 'artiste', 'id' => $userId]);
            // Insert child row if not already present
            $exists = $conn->fetchOne('SELECT 1 FROM artiste WHERE id = :id', ['id' => $userId]);
            if (!$exists) {
                $conn->executeStatement('INSERT INTO artiste (id) VALUES (:id)', ['id' => $userId]);
            }
            $newRoles = array_unique(array_merge($user->getRoles(), ['ROLE_ARTISTE']));
        } elseif ($requestedRole === 'ROLE_SPONSOR') {
            $conn->executeStatement('UPDATE `user` SET dtype = :dtype WHERE id = :id', ['dtype' => 'sponsor', 'id' => $userId]);
            $exists = $conn->fetchOne('SELECT 1 FROM sponsor WHERE id = :id', ['id' => $userId]);
            if (!$exists) {
                $conn->executeStatement(
                    'INSERT INTO sponsor (id, company_name) VALUES (:id, :name)',
                    ['id' => $userId, 'name' => $user->getUsername() . ' Co.']
                );
            }
            $newRoles = array_unique(array_merge($user->getRoles(), ['ROLE_SPONSOR']));
        } else {
            $this->addFlash('error', 'Unknown requested role.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        // Update the roles JSON column
        $conn->executeStatement('UPDATE `user` SET roles = :roles WHERE id = :id', [
            'roles' => json_encode(array_values($newRoles)),
            'id'    => $userId,
        ]);

        // Update request status
        $roleRequest->setStatus('approved');
        $roleRequest->setReviewedBy($this->getUser());
        $roleRequest->setReviewedAt(new \DateTimeImmutable());
        $em->flush();

        // Notify user
        try {
            $mailer->send((new Email())
                ->from('contact@pegasus.art')
                ->to((string) $user->getEmail())
                ->subject('Your role request has been approved')
                ->text(sprintf(
                    "Hello %s,\n\nYour request to become %s has been approved. You can now access all features available to that role.\n\nThe Pegasus Team",
                    $user->getUsername(),
                    $requestedRole === 'ROLE_ARTISTE' ? 'an Artiste' : 'a Sponsor'
                )));
        } catch (\Throwable) {
            // Email failure is non-critical
        }

        $this->addFlash('success', 'Role request approved successfully.');
        return $this->redirectToRoute('admin_role_requests_index');
    }

    #[Route('/{id}/reject', name: 'admin_role_requests_reject', methods: ['POST'])]
    public function reject(
        RoleRequest $roleRequest,
        Request $request,
        EntityManagerInterface $em,
        MailerInterface $mailer,
    ): Response {
        if (!$this->isCsrfTokenValid('reject' . $roleRequest->getId(), (string) $request->request->get('_token'))) {
            $this->addFlash('error', 'Invalid CSRF token.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        if (!$roleRequest->isPending()) {
            $this->addFlash('warning', 'This request has already been processed.');
            return $this->redirectToRoute('admin_role_requests_index');
        }

        $reason = (string) $request->request->get('reason', '');
        $roleRequest->setStatus('rejected');
        $roleRequest->setRejectionReason($reason ?: null);
        $roleRequest->setReviewedBy($this->getUser());
        $roleRequest->setReviewedAt(new \DateTimeImmutable());
        $em->flush();

        $user = $roleRequest->getUser();
        if ($user) {
            try {
                $mailer->send((new Email())
                    ->from('contact@pegasus.art')
                    ->to((string) $user->getEmail())
                    ->subject('Your role request has been reviewed')
                    ->text(sprintf(
                        "Hello %s,\n\nYour role request has been declined.%s\n\nThe Pegasus Team",
                        $user->getUsername(),
                        $reason ? "\n\nReason: $reason" : ''
                    )));
            } catch (\Throwable) {
            }
        }

        $this->addFlash('success', 'Role request rejected.');
        return $this->redirectToRoute('admin_role_requests_index');
    }
}
