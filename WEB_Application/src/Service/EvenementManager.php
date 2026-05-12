<?php

declare(strict_types=1);

namespace App\Service;

use App\Entity\Evenement;

final class EvenementManager
{
    public function validate(Evenement $evenement): bool
    {
        if (empty(trim((string) $evenement->getTitre()))) {
            throw new \InvalidArgumentException('Le titre est obligatoire.');
        }

        $date = $evenement->getDate();
        if (!$date instanceof \DateTimeInterface) {
            throw new \InvalidArgumentException('La date est obligatoire.');
        }

        $today = new \DateTimeImmutable('today');
        $eventDate = \DateTimeImmutable::createFromInterface($date)->setTime(0, 0);

        if ($eventDate < $today) {
            throw new \InvalidArgumentException("La date de l'événement ne peut pas être passée.");
        }

        $capacite = $evenement->getCapaciteMax();
        if ($capacite === null || $capacite <= 0) {
            throw new \InvalidArgumentException('La capacité max doit être positive.');
        }

        $prix = $evenement->getPrix();
        if ($prix === null || (float) $prix <= 0) {
            throw new \InvalidArgumentException('Le prix doit être positif.');
        }

        return true;
    }
}
