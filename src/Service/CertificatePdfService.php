<?php

declare(strict_types=1);

namespace App\Service;

use App\Entity\Course;
use App\Entity\QuizAttempt;
use App\Entity\User;
use Dompdf\Dompdf;
use Dompdf\Options;
use Twig\Environment;

class CertificatePdfService
{
    public function __construct(private readonly Environment $twig) {}

    public function generateCourseCertificate(User $user, Course $course, QuizAttempt $attempt): string
    {
        $options = new Options();
        $options->set('defaultFont', 'Arial');
        $options->set('isHtml5ParserEnabled', true);

        $dompdf = new Dompdf($options);

        $html = $this->twig->render('pdf/course_certificate.html.twig', [
            'user'    => $user,
            'course'  => $course,
            'attempt' => $attempt,
        ]);

        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'landscape');
        $dompdf->render();

        return (string) $dompdf->output();
    }
}
