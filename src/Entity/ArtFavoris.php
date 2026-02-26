<?php

namespace App\Entity;

use App\Repository\ArtFavorisRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ArtFavorisRepository::class)]
class ArtFavoris
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Art::class)]
    #[ORM\JoinColumn(nullable: false)]
    private ?Art $art = null;

    #[ORM\Column(length: 255)]
    private ?string $userIdentifier = null;

    #[ORM\Column(type: 'datetime')]
    private ?\DateTimeInterface $addedAt = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getArt(): ?Art
    {
        return $this->art;
    }

    public function setArt(?Art $art): static
    {
        $this->art = $art;

        return $this;
    }

    public function getUserIdentifier(): ?string
    {
        return $this->userIdentifier;
    }

    public function setUserIdentifier(string $userIdentifier): static
    {
        $this->userIdentifier = $userIdentifier;

        return $this;
    }

    public function getAddedAt(): ?\DateTimeInterface
    {
        return $this->addedAt;
    }

    public function setAddedAt(\DateTimeInterface $addedAt): static
    {
        $this->addedAt = $addedAt;

        return $this;
    }
}
