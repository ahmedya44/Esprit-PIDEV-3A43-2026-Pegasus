<?php

namespace App\Entity;

use App\Repository\CourseVideoRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CourseVideoRepository::class)]
class CourseVideo
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $title = null;

    #[ORM\Column(length: 255)]
    private ?string $videoUrl = null;

    #[ORM\Column]
    private ?int $durationSec = null;

    #[ORM\Column]
    private ?int $orderIndex = null;

    #[ORM\Column]
    private ?bool $isPreview = null;

    #[ORM\ManyToOne(inversedBy: 'courseVideos')]
    #[ORM\JoinColumn(nullable: false)]
    private ?CourseSection $section = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }

    public function getVideoUrl(): ?string
    {
        return $this->videoUrl;
    }

    public function setVideoUrl(string $videoUrl): static
    {
        $this->videoUrl = $videoUrl;

        return $this;
    }

    public function getDurationSec(): ?int
    {
        return $this->durationSec;
    }

    public function setDurationSec(int $durationSec): static
    {
        $this->durationSec = $durationSec;

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

    public function isPreview(): ?bool
    {
        return $this->isPreview;
    }

    public function setIsPreview(bool $isPreview): static
    {
        $this->isPreview = $isPreview;

        return $this;
    }

    public function getSection(): ?CourseSection
    {
        return $this->section;
    }

    public function setSection(?CourseSection $section): static
    {
        $this->section = $section;

        return $this;
    }
}
