<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Security\Core\User\UserInterface;

#[ORM\Entity(repositoryClass: CommentaireRepository::class)]
#[ORM\Table(name: 'forum_commentaire')]
#[ORM\Index(columns: ['created_at'])]
class Commentaire
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Post::class, inversedBy: 'commentaires')]
    #[ORM\JoinColumn(nullable: false, onDelete: 'CASCADE')]
    private ?Post $post = null;

    #[ORM\Column(type: Types::TEXT)]
    private string $content = '';

    #[ORM\Column(type: Types::STRING, length: 120)]
    #[Assert\NotBlank(message: 'Le nom de l\'auteur ne peut pas etre vide')]
    #[Assert\Length(max: 120, maxMessage: 'Le nom ne peut pas depasser {{ limit }} caracteres')]
    private string $authorName = '';

    #[ORM\Column(type: Types::STRING, length: 150)]
    #[Assert\NotBlank(message: 'L\'email ne peut pas etre vide')]
    #[Assert\Email(message: 'L\'email doit etre valide')]
    #[Assert\Length(max: 150, maxMessage: 'L\'email ne peut pas depasser {{ limit }} caracteres')]
    private string $authorEmail = '';

    #[ORM\Column(type: Types::STRING, length: 500, nullable: true)]
    #[Assert\Url(message: 'L\'URL du GIF doit etre valide')]
    #[Assert\Length(max: 500, maxMessage: 'L\'URL du GIF ne peut pas depasser {{ limit }} caracteres')]
    private ?string $gifUrl = null;

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(nullable: true, onDelete: 'SET NULL')]
    private ?User $owner = null;

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

    public function getContent(): string
    {
        return $this->content;
    }

    public function setContent(string $content): self
    {
        $this->content = $content;

        return $this;
    }

    public function getAuthorName(): string
    {
        return $this->authorName;
    }

    public function setAuthorName(string $authorName): self
    {
        $this->authorName = $authorName;

        return $this;
    }

    public function getAuthorEmail(): string
    {
        return $this->authorEmail;
    }

    public function setAuthorEmail(string $authorEmail): self
    {
        $this->authorEmail = $authorEmail;

        return $this;
    }

    public function getOwner(): ?User
    {
        return $this->owner;
    }

    public function setOwner(?User $owner): self
    {
        $this->owner = $owner;

        return $this;
    }

    public function getGifUrl(): ?string
    {
        return $this->gifUrl;
    }

    public function setGifUrl(?string $gifUrl): self
    {
        $gifUrl = $gifUrl !== null ? trim($gifUrl) : null;
        $this->gifUrl = $gifUrl === '' ? null : $gifUrl;

        return $this;
    }

    public function isOwnedBy(?UserInterface $user): bool
    {
        if (!$user instanceof User || $this->owner === null) {
            return false;
        }

        return $this->owner->getId() === $user->getId();
    }

    public function getCreatedAt(): \DateTimeImmutable
    {
        return $this->createdAt;
    }

    public function getUpdatedAt(): ?\DateTimeImmutable
    {
        return $this->updatedAt;
    }

    public function setUpdatedAt(?\DateTimeImmutable $updatedAt): self
    {
        $this->updatedAt = $updatedAt;

        return $this;
    }

    #[Assert\Callback]
    public function validateBodyOrGif(ExecutionContextInterface $context): void
    {
        $content = html_entity_decode((string) $this->content, ENT_QUOTES | ENT_HTML5, 'UTF-8');
        $content = str_replace("\u{00A0}", ' ', $content);
        $content = trim(strip_tags($content));
        $gifUrl = trim((string) $this->gifUrl);

        if ($content === '' && $gifUrl === '') {
            $context->buildViolation('Ajoutez du texte ou choisissez un GIF.')
                ->atPath('content')
                ->addViolation();
        }
    }
}
