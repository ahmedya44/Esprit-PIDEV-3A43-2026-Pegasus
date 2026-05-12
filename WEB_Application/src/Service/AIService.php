<?php

declare(strict_types=1);

namespace App\Service;

class AIService
{
    /**
     * @param array{total: int, names: list<string>}|null $catalogContext
     */
    public function getProductAdvisorAnswer(string $productName, string $productDescription, string $question, ?array $catalogContext = null): string
    {
        $question = trim($question);
        if ($question === '') {
            return 'Posez-moi une question sur un produit, une categorie, un prix ou une idee cadeau.';
        }

        if ($catalogContext !== null) {
            $total = (int) ($catalogContext['total'] ?? 0);
            $names = $catalogContext['names'] ?? [];
            $sample = $names !== [] ? implode(', ', array_slice($names, 0, 6)) : 'aucun produit visible pour le moment';

            return sprintf(
                'Le catalogue contient %d produit(s). Quelques options: %s. Pour choisir, filtrez par categorie puis comparez le prix, le stock et la description selon votre besoin.',
                $total,
                $sample
            );
        }

        $name = trim($productName) !== '' ? trim($productName) : 'ce produit';
        $description = trim(strip_tags($productDescription));

        if (preg_match('/prix|cher|budget|cost|price/i', $question) === 1) {
            return sprintf('%s peut etre evalue selon votre budget et son usage. Comparez son prix avec des produits similaires et verifiez surtout la qualite, la rarete et le stock disponible.', $name);
        }

        if (preg_match('/cadeau|gift|offrir/i', $question) === 1) {
            return sprintf('%s peut faire un bon cadeau si le style correspond au destinataire. Regardez la description%s et privilegiez une piece facile a exposer ou utiliser.', $name, $description !== '' ? ' : '.$description : '');
        }

        return sprintf('%s semble interessant. %s Vous pouvez verifier la categorie, le stock et l\'etat de disponibilite avant de l\'ajouter au panier.', $name, $description !== '' ? $description : 'La fiche produit donne les informations principales.');
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
    /**
     * @return array{title: string, description: string}
     */
    public function analyzeImage(string $imageUrl): array
    {
        $analysis = $this->simulateImageAnalysis($imageUrl);
        
        return [
            'title' => $analysis['title'],
            'description' => $analysis['description']
        ];
    }

    private function generateWithPicsum(string $title, string $description): string
    {
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
        $keywords = $this->extractKeywords($title . ' ' . $description);
        $category = $this->getCategoryFromKeywords($keywords);
        
        return "https://placeimg.com/512/512/{$category}";
    }

    /**
     * @return array{title: string, description: string}
     */
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

    /**
     * @return list<string>
     */
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
        
        return 'any';
    }
}
