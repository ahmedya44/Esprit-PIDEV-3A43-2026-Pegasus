<?php

namespace App\Service;

use App\Entity\Course;

final class CourseCategoryClassifier
{
    /**
     * Sonata-like categories: slug + label + keywords.
     * You can add more categories/keywords anytime.
     */
    private array $categories = [
        [
            'slug' => 'art',
            'label' => 'Art',
            'keywords' => ['art','draw','drawing','paint','painting','sketch','illustrat','design','color','portrait','anime','concept'],
        ],
        [
            'slug' => 'music',
            'label' => 'Music',
            'keywords' => ['music','song','guitar','piano','violin','beat','melody','drum','rap','orchest','soundtrack','vocal'],
        ],
        [
            'slug' => 'fantasy',
            'label' => 'Fantasy',
            'keywords' => ['fantasy','magic','dragon','elf','wizard','myth','creature','kingdom','lore','spell','dungeon'],
        ],
    ];

    public function getAllCategories(): array
    {
        // Add "Other" at the end
        return array_merge($this->categories, [
            ['slug' => 'other', 'label' => 'Other', 'keywords' => []],
        ]);
    }

    /**
     * MULTI-label classification: returns multiple slugs for a course.
     * If nothing matches => ["other"].
     */
    public function detectSlugs(Course $course): array
    {
        $text = mb_strtolower(trim(($course->getTitle() ?? '') . ' ' . ($course->getDescription() ?? '')));

        $scores = [];
        foreach ($this->categories as $cat) {
            $score = 0;
            foreach ($cat['keywords'] as $kw) {
                // partial matching: "illustrat" matches "illustration"
                if ($kw !== '' && str_contains($text, $kw)) {
                    $score++;
                }
            }

            if ($score > 0) {
                $scores[$cat['slug']] = $score;
            }
        }

        if (!$scores) {
            return ['other'];
        }

        // Multi-mode:
        // - keep all categories with score >= 60% of max score (so it can be Art+Fantasy)
        $max = max($scores);
        $threshold = (int) ceil($max * 0.60);

        $result = [];
        foreach ($scores as $slug => $score) {
            if ($score >= $threshold) {
                $result[] = $slug;
            }
        }

        return $result ?: ['other'];
    }

    /**
     * Build the filter buttons list based on actual courses.
     * Only show categories that exist + always show "Other".
     */
    public function buildAvailableFilters(iterable $courses): array
    {
        $found = [];
        foreach ($courses as $course) {
            foreach ($this->detectSlugs($course) as $slug) {
                $found[$slug] = true;
            }
        }

        $all = $this->getAllCategories();

        // Keep order of defined categories, show only those found (plus Other if found or you said always yes)
        $filters = [];
        foreach ($all as $cat) {
            if ($cat['slug'] === 'other') {
                // you said "yes" => always show Other
                $filters[] = $cat;
                continue;
            }

            if (isset($found[$cat['slug']])) {
                $filters[] = $cat;
            }
        }

        return $filters;
    }

    public function courseMatchesCategory(Course $course, ?string $activeCat): bool
    {
        if (!$activeCat || $activeCat === 'all') {
            return true;
        }

        return in_array($activeCat, $this->detectSlugs($course), true);
    }
}