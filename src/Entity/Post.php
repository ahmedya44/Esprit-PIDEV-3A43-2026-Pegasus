<?php

namespace App\Entity;

use App\Repository\PostRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

#[Vich\Uploadable]
#[ORM\Entity(repositoryClass: PostRepository::class)]
#[ORM\Table(name: 'forum_post')]
#[ORM\Index(columns: ['status'])]
#[ORM\Index(columns: ['created_at'])]
class Post
{
    public const STATUS_IN_PROGRESS = 'IN_PROGRESS';
    public const STATUS_OPEN = 'OPEN';
    public const STATUS_CLOSED = 'CLOSED';
    public const STATUS_HIDDEN = 'HIDDEN';
    public const STATUS_DENIED = 'DENIED';

    public const ALLOWED_STATUSES = [
        self::STATUS_IN_PROGRESS,
        self::STATUS_OPEN,
        self::STATUS_CLOSED,
        self::STATUS_HIDDEN,
        self::STATUS_DENIED,
    ];

    public const REQUEST_TYPE_CREATE = 'CREATE';
    public const REQUEST_TYPE_EDIT = 'EDIT';

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\Column(type: Types::STRING, length: 180)]
    #[Assert\NotBlank(message: 'Le titre ne peut pas etre vide')]
    #[Assert\Length(max: 180, maxMessage: 'Le titre ne peut pas depasser {{ limit }} caracteres')]
    private string $title = '';

    #[ORM\Column(type: Types::TEXT)]
    #[Assert\NotBlank(message: 'Le contenu ne peut pas etre vide')]
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

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(nullable: true, onDelete: 'SET NULL')]
    private ?User $owner = null;

    #[ORM\Column(type: Types::STRING, length: 20)]
    private string $status = self::STATUS_OPEN;

    #[ORM\Column(type: Types::STRING, length: 16, options: ['default' => self::REQUEST_TYPE_CREATE])]
    private ?string $requestType = self::REQUEST_TYPE_CREATE;

    #[ORM\Column(name: 'is_banned', type: Types::BOOLEAN, options: ['default' => false])]
    private bool $bannedByAdmin = false;

    #[ORM\Column(type: Types::DATETIME_IMMUTABLE)]
    private \DateTimeImmutable $createdAt;

    #[ORM\Column(type: Types::DATETIME_IMMUTABLE, nullable: true)]
    private ?\DateTimeImmutable $updatedAt = null;

    #[Vich\UploadableField(mapping: 'forum_images', fileNameProperty: 'imageName')]
    #[Assert\Image(
        maxSize: '4M',
        mimeTypes: ['image/jpeg', 'image/png', 'image/webp', 'image/gif'],
        mimeTypesMessage: 'Formats autorises: jpg, png, webp, gif.'
    )]
    private ?File $imageFile = null;

    #[ORM\Column(type: Types::STRING, length: 255, nullable: true)]
    private ?string $imageName = null;

    /**
     * @var Collection<int, Commentaire>
     */
    #[ORM\OneToMany(targetEntity: Commentaire::class, mappedBy: 'post', orphanRemoval: true, cascade: ['persist', 'remove'])]
    #[ORM\OrderBy(['createdAt' => 'DESC'])]
    private Collection $commentaires;

    /**
     * @var Collection<int, PostRating>
     */
    #[ORM\OneToMany(targetEntity: PostRating::class, mappedBy: 'post', orphanRemoval: true, cascade: ['persist', 'remove'])]
    private Collection $ratings;

    /**
     * @var Collection<int, User>
     */
    #[ORM\ManyToMany(targetEntity: User::class)]
    #[ORM\JoinTable(name: 'forum_post_allowed_viewer')]
    private Collection $allowedViewers;

    public function __construct()
    {
        $this->createdAt = new \DateTimeImmutable();
        $this->commentaires = new ArrayCollection();
        $this->ratings = new ArrayCollection();
        $this->allowedViewers = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

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

    public function isOwnedBy(?UserInterface $user): bool
    {
        if (!$user instanceof User || $this->owner === null) {
            return false;
        }

        return $this->owner->getId() === $user->getId();
    }

    public function getStatus(): string
    {
        return $this->status;
    }

    public function setStatus(string $status): self
    {
        if (!in_array($status, self::ALLOWED_STATUSES, true)) {
            throw new \InvalidArgumentException(sprintf('Invalid status "%s".', $status));
        }

        $this->status = $status;

        return $this;
    }

    public function isOpen(): bool
    {
        return $this->status === self::STATUS_OPEN;
    }

    public function isClosed(): bool
    {
        return $this->status === self::STATUS_CLOSED;
    }

    public function isHidden(): bool
    {
        return $this->status === self::STATUS_HIDDEN;
    }

    public function isInProgress(): bool
    {
        return $this->status === self::STATUS_IN_PROGRESS;
    }

    public function isDenied(): bool
    {
        return $this->status === self::STATUS_DENIED;
    }

    public function getRequestType(): ?string
    {
        return $this->requestType;
    }

    public function setRequestType(?string $requestType): self
    {
        $this->requestType = $requestType;

        return $this;
    }

    public function isBannedByAdmin(): bool
    {
        return $this->bannedByAdmin;
    }

    public function setBannedByAdmin(bool $bannedByAdmin): self
    {
        $this->bannedByAdmin = $bannedByAdmin;

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

    public function setUpdatedAt(?\DateTimeImmutable $updatedAt): self
    {
        $this->updatedAt = $updatedAt;

        return $this;
    }

    public function setImageFile(?File $imageFile = null): self
    {
        $this->imageFile = $imageFile;

        if (null !== $imageFile) {
            $this->updatedAt = new \DateTimeImmutable();
        }

        return $this;
    }

    public function getImageFile(): ?File
    {
        return $this->imageFile;
    }

    public function setImageName(?string $imageName): self
    {
        $this->imageName = $imageName;

        return $this;
    }

    public function getImageName(): ?string
    {
        return $this->imageName;
    }

    /**
     * @return Collection<int, Commentaire>
     */
    public function getCommentaires(): Collection
    {
        return $this->commentaires;
    }

    public function addCommentaire(Commentaire $commentaire): self
    {
        if (!$this->commentaires->contains($commentaire)) {
            $this->commentaires->add($commentaire);
            $commentaire->setPost($this);
        }

        return $this;
    }

    public function removeCommentaire(Commentaire $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire) && $commentaire->getPost() === $this) {
            $commentaire->setPost(null);
        }

        return $this;
    }

    /**
     * @return Collection<int, PostRating>
     */
    public function getRatings(): Collection
    {
        return $this->ratings;
    }

    public function addRating(PostRating $rating): self
    {
        if (!$this->ratings->contains($rating)) {
            $this->ratings->add($rating);
            $rating->setPost($this);
        }

        return $this;
    }

    public function removeRating(PostRating $rating): self
    {
        if ($this->ratings->removeElement($rating) && $rating->getPost() === $this) {
            $rating->setPost(null);
        }

        return $this;
    }

    /**
     * @return Collection<int, User>
     */
    public function getAllowedViewers(): Collection
    {
        return $this->allowedViewers;
    }

    public function addAllowedViewer(User $user): self
    {
        if (!$this->allowedViewers->contains($user)) {
            $this->allowedViewers->add($user);
        }

        return $this;
    }

    public function removeAllowedViewer(User $user): self
    {
        $this->allowedViewers->removeElement($user);

        return $this;
    }

    public function clearAllowedViewers(): self
    {
        $this->allowedViewers->clear();

        return $this;
    }
}
