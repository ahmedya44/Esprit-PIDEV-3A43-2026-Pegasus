<?php

declare(strict_types=1);
namespace App\Tests;

use App\Entity\Evenement;
use App\Service\EvenementManager;
use PHPUnit\Framework\TestCase;

final class EvenementTest extends TestCase
{
    // Valid event data should pass business validation.
    public function testValidEvenement(): void
    {
        $evenement = (new Evenement())
            ->setTitre('Concert test')
            ->setDate(new \DateTimeImmutable('+2 days'))
            ->setHeure(new \DateTimeImmutable('14:00'))
            ->setLieu('Tunis')
            ->setDescription('Description valide pour le test.')
            ->setCapaciteMax(100)
            ->setPrix('20.00');

        $manager = new EvenementManager();

        $this->assertTrue($manager->validate($evenement));
    }

    // Missing title must raise an exception.
    public function testEvenementWithoutTitreThrowsException(): void
    {
        $this->expectException(\InvalidArgumentException::class);

        $evenement = (new Evenement())
            ->setTitre('')
            ->setDate(new \DateTimeImmutable('+2 days'))
            ->setHeure(new \DateTimeImmutable('14:00'))
            ->setLieu('Tunis')
            ->setDescription('Description valide pour le test.')
            ->setCapaciteMax(100)
            ->setPrix('20.00');

        (new EvenementManager())->validate($evenement);
    }

    // Past event date must raise an exception.
    public function testEvenementWithPastDateThrowsException(): void
    {
        $this->expectException(\InvalidArgumentException::class);

        $evenement = (new Evenement())
            ->setTitre('Evenement passé')
            ->setDate(new \DateTimeImmutable('-1 day'))
            ->setHeure(new \DateTimeImmutable('14:00'))
            ->setLieu('Tunis')
            ->setDescription('Description valide pour le test.')
            ->setCapaciteMax(100)
            ->setPrix('20.00');

        (new EvenementManager())->validate($evenement);
    }
}
