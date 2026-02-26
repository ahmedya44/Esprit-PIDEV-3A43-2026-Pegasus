<?php

declare(strict_types=1);

namespace App\Service;

class StockedImageService
{
    // Base d'images stockées pour les mots courants
    private array $stockedImages = [
        // Art et peinture
        'peinture' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'tableau' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'sculpture' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'art' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'artistique' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'création' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'œuvre' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'oeuvre' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'dessin' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'croquis' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'esquisse' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'toile' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'pinceau' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'couleur' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'palette' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'galerie' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'exposition' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'musée' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'musee' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        
        // Artistes célèbres
        'mona lisa' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'joconde' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'picasso' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'davinci' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'van gogh' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'cézanne' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'monet' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'rembrandt' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        
        // Styles artistiques
        'abstrait' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'abstract' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'moderne' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'contemporain' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'classique' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'impressionniste' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'cubisme' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'surréalisme' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'réalisme' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'pop art' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        
        // Matériaux artistiques
        'canevas' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'marbre' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'bronze' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'argile' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'céramique' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        
        // Mouvements artistiques
        'renaissance' => 'https://images.unsplash.com/photo-1541961017774-2003afea521c?w=512&h=512&fit=crop',
        'baroque' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'romantisme' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'art nouveau' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        'art déco' => 'https://images.unsplash.com/photo-1578321272176-b7bbc0670d47?w=512&h=512&fit=crop',
        
        // Fruits
        'pomme' => 'https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=512&h=512&fit=crop',
        'banane' => 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=512&h=512&fit=crop',
        'orange' => 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=512&h=512&fit=crop',
        'fraise' => 'https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=512&h=512&fit=crop',
        'raisin' => 'https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=512&h=512&fit=crop',
        'kiwi' => 'https://images.unsplash.com/photo-1618897996318-5a901fa2ca19?w=512&h=512&fit=crop',
        'mangue' => 'https://images.unsplash.com/photo-1553279768-865429fa0078?w=512&h=512&fit=crop',
        'ananas' => 'https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=512&h=512&fit=crop',
        'citron' => 'https://images.unsplash.com/photo-1590502593747-42a996133562?w=512&h=512&fit=crop',
        'poire' => 'https://images.unsplash.com/photo-1519995689-1ec5ccc7b1c7?w=512&h=512&fit=crop',
        'peche' => 'https://images.unsplash.com/photo-1527323856883-eb5d4e5426c9?w=512&h=512&fit=crop',
        'cerise' => 'https://images.unsplash.com/photo-1528821128474-27f963b062bf?w=512&h=512&fit=crop',
        
        // Drapeaux
        'drapeau algerie' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'drapeau france' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'drapeau maroc' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'drapeau tunisie' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'drapeau italie' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'drapeau espagne' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'drapeau allemagne' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'drapeau angleterre' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'drapeau amerique' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'drapeau chine' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'drapeau japon' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        
        // Animaux
        'chat' => 'https://images.unsplash.com/photo-1514888286970-605009a5e9c7?w=512&h=512&fit=crop',
        'chien' => 'https://images.unsplash.com/photo-1552053831-7f94e1c39298?w=512&h=512&fit=crop',
        'lion' => 'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=512&h=512&fit=crop',
        'tigre' => 'https://images.unsplash.com/photo-1544551763-462c3a2350fa?w=512&h=512&fit=crop',
        'elephant' => 'https://images.unsplash.com/photo-1564349683136-96e1e2be3980?w=512&h=512&fit=crop',
        'girafe' => 'https://images.unsplash.com/photo-1544735716-392fdc244438?w=512&h=512&fit=crop',
        'singe' => 'https://images.unsplash.com/photo-1548199963-036b6304a5cf?w=512&h=512&fit=crop',
        'cheval' => 'https://images.unsplash.com/photo-1517553319941-973c8c8e9c8e?w=512&h=512&fit=crop',
        'vache' => 'https://images.unsplash.com/photo-1586236392546-12ac2eca69b7?w=512&h=512&fit=crop',
        'mouton' => 'https://images.unsplash.com/photo-1548199963-036b6304a5cf?w=512&h=512&fit=crop',
        'cochon' => 'https://images.unsplash.com/photo-1518173946687-a4c8892bbd9f?w=512&h=512&fit=crop',
        'poulet' => 'https://images.unsplash.com/photo-1586236392546-12ac2eca69b7?w=512&h=512&fit=crop',
        
        // Véhicules
        'voiture' => 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=512&h=512&fit=crop',
        'camion' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'moto' => 'https://images.unsplash.com/photo-1558981286-61aa553274c6?w=512&h=512&fit=crop',
        'velo' => 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=512&h=512&fit=crop',
        'bus' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'train' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'avion' => 'https://images.unsplash.com/photo-1436491865332-7a61a7cc6c1c?w=512&h=512&fit=crop',
        'bateau' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        
        // Objets
        'table' => 'https://images.unsplash.com/photo-1506439773639-5c69172bcd1e?w=512&h=512&fit=crop',
        'chaise' => 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=512&h=512&fit=crop',
        'lit' => 'https://images.unsplash.com/photo-1505691938855-f5b9871df744?w=512&h=512&fit=crop',
        'porte' => 'https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=512&h=512&fit=crop',
        'fenetre' => 'https://images.unsplash.com/photo-1522771739315-8be2f6cd9a6c?w=512&h=512&fit=crop',
        'livre' => 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=512&h=512&fit=crop',
        'telephone' => 'https://images.unsplash.com/photo-1511707171634-cf48310dc015?w=512&h=512&fit=crop',
        'ordinateur' => 'https://images.unsplash.com/photo-1498049794561-7785e6235aee?w=512&h=512&fit=crop',
        'television' => 'https://images.unsplash.com/photo-1592478411213-6153e4b8dff8?w=512&h=512&fit=crop',
        'radio' => 'https://images.unsplash.com/photo-1559280766-1a8b823d8e3d?w=512&h=512&fit=crop',
        
        // Nature
        'arbre' => 'https://images.unsplash.com/photo-1540979388785-8c9f4c06b2c8?w=512&h=512&fit=crop',
        'fleur' => 'https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=512&h=512&fit=crop',
        'plante' => 'https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=512&h=512&fit=crop',
        'montagne' => 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=512&h=512&fit=crop',
        'mer' => 'https://images.unsplash.com/photo-1439412588836-7a1a8c0b8d5c?w=512&h=512&fit=crop',
        'ocean' => 'https://images.unsplash.com/photo-1439412588836-7a1a8c0b8d5c?w=512&h=512&fit=crop',
        'riviere' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'lac' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'foret' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        'desert' => 'https://images.unsplash.com/photo-1506315922002-e23c854da477?w=512&h=512&fit=crop',
        
        // Couleurs
        'rouge' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'bleu' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'vert' => 'https://images.unsplash.com/photo-1540979388785-8c9f4c06b2c8?w=512&h=512&fit=crop',
        'jaune' => 'https://images.unsplash.com/photo-1596420258438-4f1c6793c6cc?w=512&h=512&fit=crop',
        'noir' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'blanc' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
        'violet' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'rose' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'marron' => 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=512&h=512&fit=crop',
        'gris' => 'https://images.unsplash.com/photo-1564476173271-9c60b5d1c7b4?w=512&h=512&fit=crop',
    ];

    public function generateImage(string $title, string $description): ?string
    {
        $titleLower = strtolower(trim($title));
        
        // Recherche exacte d'abord
        if (isset($this->stockedImages[$titleLower])) {
            return $this->stockedImages[$titleLower];
        }
        
        // Recherche partielle
        foreach ($this->stockedImages as $keyword => $imageUrl) {
            if (strpos($titleLower, $keyword) !== false) {
                return $imageUrl;
            }
        }
        
        // Recherche par mots individuels
        $words = explode(' ', $titleLower);
        foreach ($words as $word) {
            if (isset($this->stockedImages[$word])) {
                return $this->stockedImages[$word];
            }
        }
        
        // Fallback: image thématique basée sur le hash
        return $this->generateThematicImage($titleLower);
    }

    private function generateThematicImage(string $title): string
    {
        // Catégories thématiques de fallback
        $categories = [
            'nature' => 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=512&h=512&fit=crop',
            'abstract' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
            'technology' => 'https://images.unsplash.com/photo-1498049794561-7785e6235aee?w=512&h=512&fit=crop',
            'people' => 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=512&h=512&fit=crop',
            'architecture' => 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=512&h=512&fit=crop',
            'food' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
            'animals' => 'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=512&h=512&fit=crop',
            'colors' => 'https://images.unsplash.com/photo-1549398246-4ad5d6a2e5b7?w=512&h=512&fit=crop',
        ];
        
        $categoryKeys = array_keys($categories);
        $index = abs(crc32($title)) % count($categoryKeys);
        
        return $categories[$categoryKeys[$index]];
    }

    public function getAvailableKeywords(): array
    {
        return array_keys($this->stockedImages);
    }
}
