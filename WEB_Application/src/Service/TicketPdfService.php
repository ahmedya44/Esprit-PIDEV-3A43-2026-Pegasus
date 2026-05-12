<?php

declare(strict_types=1);

namespace App\Service;

use App\Entity\Participation;
use Dompdf\Dompdf;
use Dompdf\Options;
use Twig\Environment;

class TicketPdfService
{
    public function __construct(private readonly Environment $twig) {}

    public function generateEventTicket(Participation $participation): string
    {
        $user  = $participation->getUser();
        $event = $participation->getEvenement();

        $code = strtoupper(substr(
            hash('sha256', $participation->getId() . $event->getId() . $user->getEmail()),
            0,
            12
        ));

        $options = new Options();
        $options->set('defaultFont', 'Arial');
        $options->set('isHtml5ParserEnabled', true);

        $dompdf = new Dompdf($options);
        $html = $this->twig->render('pdf/event_ticket.html.twig', [
            'participation' => $participation,
            'user'          => $user,
            'event'         => $event,
            'code'          => $code,
        ]);

        $dompdf->loadHtml($html);
        $dompdf->setPaper('A5', 'landscape');
        $dompdf->render();

        return (string) $dompdf->output();
    }
}
