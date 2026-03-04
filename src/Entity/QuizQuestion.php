<?php

namespace App\Entity;

use App\Repository\QuizQuestionRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: QuizQuestionRepository::class)]
class QuizQuestion
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::TEXT)]
    private ?string $questionText = null;

    #[ORM\Column]
    private ?int $points = null;

    #[ORM\Column]
    private ?int $orderIndex = null;

    #[ORM\ManyToOne(inversedBy: 'quizQuestions')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Quiz $quiz = null;

    /**
     * @var Collection<int, QuizChoice>
     */
    #[ORM\OneToMany(
        mappedBy: 'question',
        targetEntity: QuizChoice::class,
        cascade: ['persist', 'remove'],
        orphanRemoval: true
    )]
    private Collection $quizChoices;

    public function __construct()
    {
        $this->quizChoices = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuestionText(): ?string
    {
        return $this->questionText;
    }

    public function setQuestionText(string $questionText): static
    {
        $this->questionText = $questionText;

        return $this;
    }

    public function getPoints(): ?int
    {
        return $this->points;
    }

    public function setPoints(int $points): static
    {
        $this->points = $points;

        return $this;
    }

    public function getOrderIndex(): ?int
    {
        return $this->orderIndex;
    }

    public function setOrderIndex(int $orderIndex): static
    {
        $this->orderIndex = $orderIndex;

        return $this;
    }

    public function getQuiz(): ?Quiz
    {
        return $this->quiz;
    }

    public function setQuiz(?Quiz $quiz): static
    {
        $this->quiz = $quiz;

        return $this;
    }

    /**
     * @return Collection<int, QuizChoice>
     */
    public function getQuizChoices(): Collection
    {
        return $this->quizChoices;
    }

    public function addQuizChoice(QuizChoice $choice): static
    {
        if (!$this->quizChoices->contains($choice)) {
            $this->quizChoices->add($choice);
            $choice->setQuestion($this); // ✅ owning side
        }

        return $this;
    }

    public function removeQuizChoice(QuizChoice $choice): static
    {
        if ($this->quizChoices->removeElement($choice)) {
            if ($choice->getQuestion() === $this) {
                $choice->setQuestion(null);
            }
        }

        return $this;
    }
}
