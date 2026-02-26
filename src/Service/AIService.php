<?php

declare(strict_types=1);

namespace App\Service;

use Symfony\Contracts\HttpClient\HttpClientInterface;

class AIService
{
    private HttpClientInterface $client;

    public function __construct(HttpClientInterface $client)
    {
        $this->client = $client;
    }

    public function generateProductDescription(string $name): string
    {
        $name = trim($name);
        if ($name === '') {
            return 'A quality product selected for its reliable performance and everyday usefulness.';
        }

        $prefixes = [
            'A carefully selected',
            'A modern',
            'A premium',
            'A practical',
            'A refined',
        ];
        $benefits = [
            'designed to improve comfort and daily use.',
            'crafted with attention to quality and durability.',
            'offering a balanced combination of style and performance.',
            'made for users looking for reliability and simplicity.',
            'adapted to both personal and professional needs.',
        ];
        $useCases = [
            'Ideal for everyday use.',
            'A solid choice for regular use at home or work.',
            'Suitable for people who value quality and efficiency.',
            'Easy to integrate into your daily routine.',
            'Built to deliver consistent results over time.',
        ];

        $seed = abs(crc32(mb_strtolower($name)));
        $prefix = $prefixes[$seed % count($prefixes)];
        $benefit = $benefits[$seed % count($benefits)];
        $useCase = $useCases[$seed % count($useCases)];

        return sprintf('%s %s %s %s', $prefix, $name, $benefit, $useCase);
    }

    // Génération d'images sans API - services gratuits
    public function generateImage(string $title, string $description): ?string
    {
        $services = [
            fn() => $this->generateWithPicsum($title, $description),
            fn() => $this->generateWithLoremPicsum($title, $description),
            fn() => $this->generateWithPlaceIMG($title, $description),
        ];

        foreach ($services as $service) {
            try {
                $result = $service();
                if ($result) {
                    return $result;
                }
            } catch (\Exception $e) {
                continue;
            }
        }

        return null;
    }

    // Analyse d'images sans API - simulation intelligente
    public function analyzeImage(string $imageUrl): ?array
    {
        $analysis = $this->simulateImageAnalysis($imageUrl);
        
        return [
            'title' => $analysis['title'],
            'description' => $analysis['description']
        ];
    }

    private function generateWithPicsum(string $title, string $description): ?string
    {
        $seed = $this->generateSeedFromText($title . ' ' . $description);
        $width = 512;
        $height = 512;
        
        return "https://picsum.photos/{$width}/{$height}?random={$seed}";
    }

    private function generateWithLoremPicsum(string $title, string $description): ?string
    {
        $seed = $this->generateSeedFromText($title . ' ' . $description);
        return "https://loremflickr.com/512/512/art?random={$seed}";
    }

    private function generateWithPlaceIMG(string $title, string $description): ?string
    {
        $keywords = $this->extractKeywords($title . ' ' . $description);
        $category = $this->getCategoryFromKeywords($keywords);
        
        return "https://placeimg.com/512/512/{$category}";
    }

    private function simulateImageAnalysis(string $imageUrl): array
    {
        $urlHash = md5($imageUrl);
        
        $titles = [
            'Éclat artistique', 'Harmonie visuelle', 'Création abstraite',
            'Symphonie de couleurs', 'Rêve éveillé', 'Mystère révélé',
            'Poésie visuelle', 'Équilibre parfait', 'Expression pure',
            'Mélodie des formes', 'Danse des couleurs', 'Quiétude profonde'
        ];
        
        $descriptions = [
            'Une œuvre captivante qui explore les profondeurs de la créativité humaine.',
            'Une composition équilibrée mêlant technique et émotion brute.',
            'Une interprétation moderne de thèmes classiques revisités.',
            'Une exploration visuelle des frontières entre réalité et imagination.',
            'Une célébration de la beauté à travers des formes et couleurs.',
            'Une méditation visuelle sur la nature et l\'existence.',
            'Un dialogue silencieux entre ombre et lumière.',
            'Une ode à la beauté éphémère du moment présent.'
        ];

        $titleIndex = hexdec(substr($urlHash, 0, 2)) % count($titles);
        $descIndex = hexdec(substr($urlHash, 2, 2)) % count($descriptions);
        
        return [
            'title' => $titles[$titleIndex],
            'description' => $descriptions[$descIndex]
        ];
    }

    private function generateSeedFromText(string $text): int
    {
        return crc32($text) % 10000;
    }

    private function extractKeywords(string $text): array
    {
        $keywords = [];
        $words = explode(' ', strtolower($text));
        
        $artKeywords = ['art', 'peinture', 'tableau', 'sculpture', 'photo', 'création', 'œuvre'];
        $natureKeywords = ['nature', 'paysage', 'mer', 'montagne', 'forêt', 'ciel', 'soleil'];
        $abstractKeywords = ['abstrait', 'géométrique', 'moderne', 'contemporain', 'couleur'];
        
        foreach ($words as $word) {
            if (in_array($word, $artKeywords)) $keywords[] = 'art';
            if (in_array($word, $natureKeywords)) $keywords[] = 'nature';
            if (in_array($word, $abstractKeywords)) $keywords[] = 'abstract';
        }
        
        return array_unique($keywords);
    }

    private function getCategoryFromKeywords(array $keywords): string
    {
        if (in_array('nature', $keywords)) return 'nature';
        if (in_array('art', $keywords)) return 'tech';
        if (in_array('abstract', $keywords)) return 'abstract';
        
        return 'any';
    }
}
