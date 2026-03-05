<?php

namespace App\Entity;

use App\Repository\SponsoringPackRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: SponsoringPackRepository::class)]
class SponsoringPack
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'id_pack')]
    private ?int $id = null;

    #[ORM\Column(name: 'nom_pack', length: 100)]
    private ?string $nomPack = null;

    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $description = null;

    #[ORM\Column(type: 'float')]
    private ?float $prix = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomPack(): ?string
    {
        return $this->nomPack;
    }

    public function setNomPack(string $nomPack): static
    {
        $this->nomPack = $nomPack;
        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): static
    {
        $this->description = $description;
        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): static
    {
        $this->prix = $prix;
        return $this;
    }

    public function __toString(): string
    {
        return $this->nomPack ?? '';
    }
}
