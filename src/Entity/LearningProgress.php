<?php

declare(strict_types=1);

namespace App\Entity;

use App\Repository\LearningProgressRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LearningProgressRepository::class)]
#[ORM\Table(name: 'learning_progress')]
#[ORM\UniqueConstraint(name: 'UNIQ_learning_progress', columns: ['user_id', 'course_id'])]
class LearningProgress
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(name: 'user_id', nullable: false, onDelete: 'CASCADE')]
    private ?User $user = null;

    #[ORM\ManyToOne(targetEntity: Course::class)]
    #[ORM\JoinColumn(name: 'course_id', nullable: false, onDelete: 'CASCADE')]
    private ?Course $course = null;

    #[ORM\Column(length: 20)]
    private string $status = 'in-progress';

    #[ORM\Column(name: 'progress_percent')]
    private int $progressPercent = 0;

    #[ORM\Column(name: 'completed_video_ids', type: 'json')]
    private array $completedVideoIds = [];

    #[ORM\Column(name: 'started_at', type: 'datetime_immutable')]
    private \DateTimeImmutable $startedAt;

    #[ORM\Column(name: 'completed_at', type: 'datetime_immutable', nullable: true)]
    private ?\DateTimeImmutable $completedAt = null;

    public function __construct()
    {
        $this->startedAt = new \DateTimeImmutable();
    }

    public function getId(): ?int { return $this->id; }

    public function getUser(): ?User { return $this->user; }
    public function setUser(?User $user): static { $this->user = $user; return $this; }

    public function getCourse(): ?Course { return $this->course; }
    public function setCourse(?Course $course): static { $this->course = $course; return $this; }

    public function getStatus(): string { return $this->status; }
    public function setStatus(string $status): static { $this->status = $status; return $this; }

    public function getProgressPercent(): int { return $this->progressPercent; }
    public function setProgressPercent(int $progressPercent): static { $this->progressPercent = $progressPercent; return $this; }

    public function getCompletedVideoIds(): array { return $this->completedVideoIds; }
    public function setCompletedVideoIds(array $completedVideoIds): static { $this->completedVideoIds = $completedVideoIds; return $this; }

    public function addCompletedVideoId(int $videoId): static
    {
        if (!in_array($videoId, $this->completedVideoIds, true)) {
            $this->completedVideoIds[] = $videoId;
        }
        return $this;
    }

    public function hasCompletedVideo(int $videoId): bool
    {
        return in_array($videoId, $this->completedVideoIds, true);
    }

    public function getStartedAt(): \DateTimeImmutable { return $this->startedAt; }

    public function getCompletedAt(): ?\DateTimeImmutable { return $this->completedAt; }
    public function setCompletedAt(?\DateTimeImmutable $completedAt): static { $this->completedAt = $completedAt; return $this; }

    public function isCompleted(): bool { return $this->status === 'completed'; }
}
