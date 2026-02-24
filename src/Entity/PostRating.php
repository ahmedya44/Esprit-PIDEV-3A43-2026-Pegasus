<?php

namespace App\Entity;

use App\Repository\PostRatingRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: PostRatingRepository::class)]
#[ORM\Table(name: 'forum_post_rating')]
#[ORM\UniqueConstraint(name: 'uniq_post_rater_email', columns: ['post_id', 'rater_email'])]
#[ORM\Index(columns: ['post_id'])]
class PostRating
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Post::class, inversedBy: 'ratings')]
    #[ORM\JoinColumn(nullable: false, onDelete: 'CASCADE')]
    private ?Post $post = null;

    #[ORM\Column(type: Types::FLOAT)]
    #[Assert\NotNull(message: 'La note est obligatoire.')]
    #[Assert\Range(
        min: 0.5,
        max: 5,
        notInRangeMessage: 'La note doit etre comprise entre {{ min }} et {{ max }}.'
    )]
    private ?float $value = null;

    #[ORM\Column(type: Types::STRING, length: 150)]
    #[Assert\NotBlank(message: 'Votre email est obligatoire.')]
    #[Assert\Email(message: 'Veuillez saisir un email valide.')]
    #[Assert\Length(max: 150, maxMessage: 'L\'email ne peut pas depasser {{ limit }} caracteres.')]
    private string $raterEmail = '';

    #[ORM\Column(type: Types::DATETIME_IMMUTABLE)]
    private \DateTimeImmutable $createdAt;

    #[ORM\Column(type: Types::DATETIME_IMMUTABLE, nullable: true)]
    private ?\DateTimeImmutable $updatedAt = null;

    public function __construct()
    {
        $this->createdAt = new \DateTimeImmutable();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPost(): ?Post
    {
        return $this->post;
    }

    public function setPost(?Post $post): self
    {
        $this->post = $post;

        return $this;
    }

    public function getValue(): ?float
    {
        return $this->value;
    }

    public function setValue(?float $value): self
    {
        $this->value = $value;
        $this->updatedAt = new \DateTimeImmutable();

        return $this;
    }

    public function getRaterEmail(): string
    {
        return $this->raterEmail;
    }

    public function setRaterEmail(string $raterEmail): self
    {
        $this->raterEmail = $raterEmail;

        return $this;
    }

    public function getCreatedAt(): \DateTimeImmutable
    {
        return $this->createdAt;
    }

    public function getUpdatedAt(): ?\DateTimeImmutable
    {
        return $this->updatedAt;
    }
}
