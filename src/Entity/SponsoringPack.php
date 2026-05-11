<?php

namespace App\Entity;

use App\Repository\SponsoringPackRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Evenement;
use App\Entity\User;

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

    #[ORM\ManyToOne(targetEntity: Evenement::class)]
    #[ORM\JoinColumn(name: 'id_evenement', referencedColumnName: 'id', nullable: true)]
    private ?Evenement $evenement = null;

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(name: 'id_sponsor', referencedColumnName: 'id', nullable: true)]
    private ?User $sponsor = null;

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

    public function getEvenement(): ?Evenement
    {
        return $this->evenement;
    }

    public function setEvenement(?Evenement $evenement): self
    {
        $this->evenement = $evenement;
        return $this;
    }

    public function getSponsor(): ?User
    {
        return $this->sponsor;
    }

    public function setSponsor(?User $sponsor): self
    {
        $this->sponsor = $sponsor;
        return $this;
    }
}
