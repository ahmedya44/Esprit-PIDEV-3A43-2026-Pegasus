<?php

declare(strict_types=1);

namespace App\Service;

class ClientAIService
{
    // Génération d'images avec des services gratuits sans API
    public function generateImage(string $title, string $description): ?string
    {
        // Utiliser plusieurs services gratuits sans clé API
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

    // Analyse d'images avec traitement côté client (simulation)
    /**
     * @return array{title: string, description: string}
     */
    public function analyzeImage(string $imageUrl): array
    {
        // Simulation d'analyse basée sur des patterns
        $analysis = $this->simulateImageAnalysis($imageUrl);
        
        return [
            'title' => $analysis['title'],
            'description' => $analysis['description']
        ];
    }

    private function generateWithPicsum(string $title, string $description): string
    {
        // Générer une image thématique basée sur les mots-clés
        $seed = $this->generateSeedFromText($title . ' ' . $description);
        $width = 512;
        $height = 512;
        
        return "https://picsum.photos/{$width}/{$height}?random={$seed}";
    }

    private function generateWithLoremPicsum(string $title, string $description): string
    {
        $seed = $this->generateSeedFromText($title . ' ' . $description);
        return "https://loremflickr.com/512/512/art?random={$seed}";
    }

    private function generateWithPlaceIMG(string $title, string $description): string
    {
        // Images thématiques basées sur des mots-clés
        $keywords = $this->extractKeywords($title . ' ' . $description);
        $category = $this->getCategoryFromKeywords($keywords);
        
        return "https://placeimg.com/512/512/{$category}";
    }

    /**
     * @return array{title: string, description: string}
     */
    private function simulateImageAnalysis(string $imageUrl): array
    {
        // Extraire des informations de l'URL et générer une analyse
        $urlHash = md5($imageUrl);
        
        // Générateur de titres créatifs
        $titles = [
            'Éclat artistique', 'Harmonie visuelle', 'Création abstraite',
            'Symphonie de couleurs', 'Rêve éveillé', 'Mystère révélé',
            'Poésie visuelle', 'Équilibre parfait', 'Expression pure',
            'Mélodie des formes', 'Danse des couleurs', 'Quiétude profonde'
        ];
        
        // Générateur de descriptions
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

        // Utiliser le hash pour sélectionner de manière consistante
        $titleIndex = hexdec(substr($urlHash, 0, 2)) % count($titles);
        $descIndex = hexdec(substr($urlHash, 2, 2)) % count($descriptions);
        
        return [
            'title' => $titles[$titleIndex],
            'description' => $descriptions[$descIndex]
        ];
    }

    private function generateSeedFromText(string $text): int
    {
        // Générer un seed consistant à partir du texte
        return crc32($text) % 10000;
    }

    /**
     * @return list<string>
     */
    private function extractKeywords(string $text): array
    {
        // Extraire des mots-clés simples
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
        
        return array_values(array_unique($keywords));
    }

    /**
     * @param list<string> $keywords
     */
    private function getCategoryFromKeywords(array $keywords): string
    {
        if (in_array('nature', $keywords)) return 'nature';
        if (in_array('art', $keywords)) return 'tech';
        if (in_array('abstract', $keywords)) return 'abstract';
        
        return 'any'; // Catégorie par défaut
    }
}
