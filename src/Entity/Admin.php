<?php

namespace App\Entity;

use App\Repository\AdminRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: AdminRepository::class)]
#[ORM\Table(name: '`admin`')]
class Admin extends User
{
    #[ORM\Column(type: Types::DATE_IMMUTABLE, nullable: true)]
    #[Assert\LessThanOrEqual('today', message: 'Birth date cannot be in the future.')]
    private ?\DateTimeImmutable $birthDate = null;

    #[ORM\Column]
    private ?bool $superAdmin = null;

    public function getBirthDate(): ?\DateTimeImmutable
    {
        return $this->birthDate;
    }

    public function setBirthDate(?\DateTimeImmutable $birthDate): static
    {
        $this->birthDate = $birthDate;

        return $this;
    }

    public function isSuperAdmin(): ?bool
    {
        return $this->superAdmin;
    }

    public function setSuperAdmin(bool $superAdmin): static
    {
        $this->superAdmin = $superAdmin;

        return $this;
    }
}
