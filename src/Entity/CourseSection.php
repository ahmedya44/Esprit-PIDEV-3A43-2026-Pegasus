<?php

namespace App\Entity;

use App\Entity\Course;
use App\Repository\CourseSectionRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CourseSectionRepository::class)]
class CourseSection
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $title = null;

    #[ORM\Column]
    private ?int $orderIndex = null;

    #[ORM\ManyToOne(inversedBy: 'courseSections')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Course $course = null;

    /**
     * @var Collection<int, CourseVideo>
     */
    #[ORM\OneToMany(targetEntity: CourseVideo::class, mappedBy: 'section', orphanRemoval: true)]
    private Collection $courseVideos;

    public function __construct()
    {
        $this->courseVideos = new ArrayCollection();
    }

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

    public function getOrderIndex(): ?int
    {
        return $this->orderIndex;
    }

    public function setOrderIndex(int $orderIndex): static
    {
        $this->orderIndex = $orderIndex;

        return $this;
    }

    public function getCourse(): ?Course
    {
        return $this->course;
    }

    public function setCourse(?Course $course): static
    {
        $this->course = $course;

        return $this;
    }

    /**
     * @return Collection<int, CourseVideo>
     */
    public function getCourseVideos(): Collection
    {
        return $this->courseVideos;
    }

    public function addCourseVideo(CourseVideo $courseVideo): static
    {
        if (!$this->courseVideos->contains($courseVideo)) {
            $this->courseVideos->add($courseVideo);
            $courseVideo->setSection($this);
        }

        return $this;
    }

    public function removeCourseVideo(CourseVideo $courseVideo): static
    {
        if ($this->courseVideos->removeElement($courseVideo)) {
            // set the owning side to null (unless already changed)
            if ($courseVideo->getSection() === $this) {
                $courseVideo->setSection(null);
            }
        }

        return $this;
    }
}
