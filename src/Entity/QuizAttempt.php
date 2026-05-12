<?php

declare(strict_types=1);

namespace App\Entity;

use App\Repository\QuizAttemptRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: QuizAttemptRepository::class)]
#[ORM\Table(name: 'quiz_attempt')]
class QuizAttempt
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: User::class)]
    #[ORM\JoinColumn(name: 'user_id', nullable: false, onDelete: 'CASCADE')]
    private ?User $user = null;

    #[ORM\ManyToOne(targetEntity: Quiz::class)]
    #[ORM\JoinColumn(name: 'quiz_id', nullable: false, onDelete: 'CASCADE')]
    private ?Quiz $quiz = null;

    #[ORM\ManyToOne(targetEntity: Course::class)]
    #[ORM\JoinColumn(name: 'course_id', nullable: false, onDelete: 'CASCADE')]
    private ?Course $course = null;

    #[ORM\Column(name: 'score_percent')]
    private int $scorePercent = 0;

    #[ORM\Column(name: 'earned_points')]
    private int $earnedPoints = 0;

    #[ORM\Column(name: 'total_points')]
    private int $totalPoints = 0;

    #[ORM\Column]
    private bool $passed = false;

    #[ORM\Column(name: 'time_spent_sec')]
    private int $timeSpentSec = 0;

    #[ORM\Column(name: 'time_remaining_sec', nullable: true)]
    private ?int $timeRemainingSec = null;

    #[ORM\Column(name: 'submitted_at', type: 'datetime_immutable')]
    private \DateTimeImmutable $submittedAt;

    public function __construct()
    {
        $this->submittedAt = new \DateTimeImmutable();
    }

    public function getId(): ?int { return $this->id; }

    public function getUser(): ?User { return $this->user; }
    public function setUser(?User $user): static { $this->user = $user; return $this; }

    public function getQuiz(): ?Quiz { return $this->quiz; }
    public function setQuiz(?Quiz $quiz): static { $this->quiz = $quiz; return $this; }

    public function getCourse(): ?Course { return $this->course; }
    public function setCourse(?Course $course): static { $this->course = $course; return $this; }

    public function getScorePercent(): int { return $this->scorePercent; }
    public function setScorePercent(int $scorePercent): static { $this->scorePercent = $scorePercent; return $this; }

    public function getEarnedPoints(): int { return $this->earnedPoints; }
    public function setEarnedPoints(int $earnedPoints): static { $this->earnedPoints = $earnedPoints; return $this; }

    public function getTotalPoints(): int { return $this->totalPoints; }
    public function setTotalPoints(int $totalPoints): static { $this->totalPoints = $totalPoints; return $this; }

    public function isPassed(): bool { return $this->passed; }
    public function setPassed(bool $passed): static { $this->passed = $passed; return $this; }

    public function getTimeSpentSec(): int { return $this->timeSpentSec; }
    public function setTimeSpentSec(int $timeSpentSec): static { $this->timeSpentSec = $timeSpentSec; return $this; }

    public function getTimeRemainingSec(): ?int { return $this->timeRemainingSec; }
    public function setTimeRemainingSec(?int $timeRemainingSec): static { $this->timeRemainingSec = $timeRemainingSec; return $this; }

    public function getSubmittedAt(): \DateTimeImmutable { return $this->submittedAt; }
}
