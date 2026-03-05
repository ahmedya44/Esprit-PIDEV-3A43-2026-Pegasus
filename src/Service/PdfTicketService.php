<?php

namespace App\Service;

use App\Entity\Commande;
use Knp\Snappy\Pdf;
use Symfony\Component\HttpFoundation\Response;
use Twig\Environment;

class PdfTicketService
{
    public function __construct(
        private Pdf $snappyPdf,
        private Environment $twig,
    ) {
    }

    /**
     * Génère le contenu binaire du PDF ticket/facture pour une commande.
     */
    public function generateTicketPdf(Commande $commande): string
    {
        $html = $this->twig->render('pdf/ticket.html.twig', [
            'commande' => $commande,
            'lignes' => $commande->getLigneCommandes(),
        ]);

        return $this->snappyPdf->getOutputFromHtml($html);
    }

    /**
     * Retourne une Response pour télécharger le ticket en PDF.
     */
    public function createTicketResponse(Commande $commande): Response
    {
        $pdf = $this->generateTicketPdf($commande);
        $filename = sprintf('ticket-commande-%d.pdf', $commande->getId());

        return new Response($pdf, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => sprintf('attachment; filename="%s"', $filename),
            'Content-Length' => \strlen($pdf),
        ]);
    }
}
