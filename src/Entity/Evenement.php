<?php

namespace App\Entity;

use App\Repository\EvenementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: EvenementRepository::class)]
class Evenement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le titre est obligatoire")]
    #[Assert\Length(min: 3, max: 255, minMessage: "Le titre doit faire au moins {{ limit }} caractères")]
    private ?string $titre = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\NotBlank(message: "La date est obligatoire")]
    #[Assert\Type("\DateTimeInterface")]
    #[Assert\GreaterThanOrEqual("today", message: "La date de l'événement ne peut pas être passée")]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(type: Types::TIME_MUTABLE)]
    #[Assert\NotBlank(message: "L'heure est obligatoire")]
    #[Assert\Type("\DateTimeInterface")]
    private ?\DateTimeInterface $heure = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le lieu est obligatoire")]
    private ?string $lieu = null;

    #[ORM\Column(type: Types::TEXT)]
    #[Assert\NotBlank(message: "La description est obligatoire")]
    #[Assert\Length(min: 10, minMessage: "La description doit faire au moins {{ limit }} caractères")]
    private ?string $description = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $image = null;

    #[ORM\Column]
    #[Assert\NotBlank(message: "La capacité max est obligatoire")]
    #[Assert\Positive(message: "La capacité doit être un nombre positif")]
    private ?int $capacite_max = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    #[Assert\NotBlank(message: "Le prix est obligatoire")]
    #[Assert\Positive(message: "Le prix doit être un nombre positif")]
    private ?string $prix = null;

    /**
     * @var Collection<int, Participation>
     */
    #[ORM\OneToMany(mappedBy: 'evenement', targetEntity: Participation::class, orphanRemoval: true)]
    private Collection $participations;

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(nullable: true)]
    private ?User $artiste = null;

    #[ORM\Column(length: 50)]
    private ?string $statut = 'en attente';

    public function __construct()
    {
        $this->participations = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(?string $titre): static
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(?\DateTimeInterface $date): static
    {
        $this->date = $date;

        return $this;
    }

    public function getHeure(): ?\DateTimeInterface
    {
        return $this->heure;
    }

    public function setHeure(?\DateTimeInterface $heure): static
    {
        $this->heure = $heure;

        return $this;
    }

    public function getLieu(): ?string
    {
        return $this->lieu;
    }

    public function setLieu(?string $lieu): static
    {
        $this->lieu = $lieu;

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

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image): static
    {
        $this->image = $image;

        return $this;
    }

    public function getCapaciteMax(): ?int
    {
        return $this->capacite_max;
    }

    public function setCapaciteMax(?int $capacite_max): static
    {
        $this->capacite_max = $capacite_max;

        return $this;
    }

    public function getPrix(): ?string
    {
        return $this->prix;
    }

    public function setPrix(?string $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * @return Collection<int, Participation>
     */
    public function getParticipations(): Collection
    {
        return $this->participations;
    }

    public function addParticipation(Participation $participation): self
    {
        if (!$this->participations->contains($participation)) {
            $this->participations->add($participation);
            $participation->setEvenement($this);
        }

        return $this;
    }

    public function removeParticipation(Participation $participation): self
    {
        if ($this->participations->removeElement($participation)) {
            // set the owning side to null (unless already changed)
            if ($participation->getEvenement() === $this) {
                $participation->setEvenement(null);
            }
        }

        return $this;
    }

    public function getArtiste(): ?User
    {
        return $this->artiste;
    }

    public function setArtiste(?User $artiste): static
    {
        $this->artiste = $artiste;

        return $this;
    }

    public function getStatut(): ?string
    {
        return $this->statut;
    }

    public function setStatut(?string $statut): static
    {
        $this->statut = $statut;

        return $this;
    }
}
