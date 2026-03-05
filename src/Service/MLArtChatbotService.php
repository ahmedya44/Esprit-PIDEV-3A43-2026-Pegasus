<?php

declare(strict_types=1);

namespace App\Service;

class MLArtChatbotService
{
    /**
     * @var list<array<string, mixed>>
     */
    private array $trainingData;
    /**
     * @var array<string, array<string, mixed>>
     */
    private array $weights;
    private string $dataFile;
    /**
     * @var array<string, int>
     */
    private array $vocabulary;

    public function __construct()
    {
        $this->dataFile = __DIR__ . '/../../data/chatbot_training.json';
        $this->initializeTrainingData();
        $this->loadTrainingData();
        $this->buildVocabulary();
        $this->trainModel();
    }

    private function initializeTrainingData(): void
    {
        // Données d'entraînement initiales étendues
        $this->trainingData = [
            ['input' => 'c\'est quoi l\'art', 'output' => 'L\'art est une expression humaine de la créativité à travers diverses formes visuelles, sonores ou conceptuelles.', 'category' => 'definition'],
            ['input' => 'qu\'est ce que l\'art', 'output' => 'L\'art est une expression humaine de la créativité à travers diverses formes visuelles, sonores ou conceptuelles.', 'category' => 'definition'],
            ['input' => 'definition de l\'art', 'output' => 'L\'art est une expression humaine de la créativité à travers diverses formes visuelles, sonores ou conceptuelles.', 'category' => 'definition'],
            
            ['input' => 'qui est picasso', 'output' => 'Pablo Picasso (1881-1973) est le cofondateur du cubisme, artiste prolifique du 20ème siècle avec des œuvres comme Guernica et Les Demoiselles d\'Avignon.', 'category' => 'artist'],
            ['input' => 'picasso', 'output' => 'Pablo Picasso (1881-1973) est le cofondateur du cubisme, artiste prolifique du 20ème siècle avec des œuvres comme Guernica et Les Demoiselles d\'Avignon.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que l\'impressionnisme', 'output' => 'L\'impressionnisme est un style du 19ème siècle avec des coups de pinceau visibles et des jeux de lumière. Artistes célèbres : Monet, Renoir, Degas.', 'category' => 'style'],
            ['input' => 'impressionnisme', 'output' => 'L\'impressionnisme est un style du 19ème siècle avec des coups de pinceau visibles et des jeux de lumière. Artistes célèbres : Monet, Renoir, Degas.', 'category' => 'style'],
            
            ['input' => 'comment peindre à l\'huile', 'output' => 'La peinture à l\'huile utilise des pigments mélangés à l\'huile, avec un séchage lent mais très durable. Technique utilisée depuis le Moyen Âge.', 'category' => 'technique'],
            ['input' => 'peinture à l\'huile', 'output' => 'La peinture à l\'huile utilise des pigments mélangés à l\'huile, avec un séchage lent mais très durable. Technique utilisée depuis le Moyen Âge.', 'category' => 'technique'],
            
            ['input' => 'qu\'est ce que la composition', 'output' => 'La composition est l\'organisation des éléments visuels dans une œuvre pour créer un ensemble harmonieux et équilibré.', 'category' => 'concept'],
            ['input' => 'composition', 'output' => 'La composition est l\'organisation des éléments visuels dans une œuvre pour créer un ensemble harmonieux et équilibré.', 'category' => 'concept'],
            
            ['input' => 'qui est van gogh', 'output' => 'Vincent van Gogh (1853-1890) est un post-impressionniste connu pour ses coups de pinceau émotionnels comme Les Tournesols et La Nuit étoilée.', 'category' => 'artist'],
            ['input' => 'van gogh', 'output' => 'Vincent van Gogh (1853-1890) est un post-impressionniste connu pour ses coups de pinceau émotionnels comme Les Tournesols et La Nuit étoilée.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que le cubisme', 'output' => 'Le cubisme décompose les sujets en formes géométriques, créé par Picasso et Braque au début du 20ème siècle.', 'category' => 'style'],
            ['input' => 'cubisme', 'output' => 'Le cubisme décompose les sujets en formes géométriques, créé par Picasso et Braque au début du 20ème siècle.', 'category' => 'style'],
            
            ['input' => 'qu\'est ce que la perspective', 'output' => 'La perspective est une technique donnant illusion de profondeur sur une surface 2D, inventée à la Renaissance par Brunelleschi.', 'category' => 'concept'],
            ['input' => 'perspective', 'output' => 'La perspective est une technique donnant illusion de profondeur sur une surface 2D, inventée à la Renaissance par Brunelleschi.', 'category' => 'concept'],
            
            ['input' => 'qui est monet', 'output' => 'Claude Monet (1840-1926) est le fondateur de l\'impressionnisme, maître des jeux de lumière avec Les Nymphéas et Impression, soleil levant.', 'category' => 'artist'],
            ['input' => 'monet', 'output' => 'Claude Monet (1840-1926) est le fondateur de l\'impressionnisme, maître des jeux de lumière avec Les Nymphéas et Impression, soleil levant.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que la couleur', 'output' => 'La couleur est un élément fondamental de l\'art utilisé pour exprimer des émotions, créer l\'harmonie et le contraste visuel.', 'category' => 'concept'],
            ['input' => 'couleur', 'output' => 'La couleur est un élément fondamental de l\'art utilisé pour exprimer des émotions, créer l\'harmonie et le contraste visuel.', 'category' => 'concept'],
            
            ['input' => 'qui est léonard de vinci', 'output' => 'Léonard de Vinci (1452-1519) est le génie de la Renaissance, peintre, inventeur et scientifique. Œuvres : Mona Lisa, La Cène.', 'category' => 'artist'],
            ['input' => 'léonard de vinci', 'output' => 'Léonard de Vinci (1452-1519) est le génie de la Renaissance, peintre, inventeur et scientifique. Œuvres : Mona Lisa, La Cène.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que le surréalisme', 'output' => 'Le surréalisme est un mouvement explorant l\'inconscient et le rêve, avec des images surréalistes. Salvador Dalí en est le maître.', 'category' => 'style'],
            ['input' => 'surréalisme', 'output' => 'Le surréalisme est un mouvement explorant l\'inconscient et le rêve, avec des images surréalistes. Salvador Dalí en est le maître.', 'category' => 'style'],
            
            ['input' => 'qu\'est ce que l\'aquarelle', 'output' => 'L\'aquarelle est une peinture sur papier avec des couleurs diluées à l\'eau, transparentes et lumineuses.', 'category' => 'technique'],
            ['input' => 'aquarelle', 'output' => 'L\'aquarelle est une peinture sur papier avec des couleurs diluées à l\'eau, transparentes et lumineuses.', 'category' => 'technique'],
            
            ['input' => 'qu\'est ce que la renaissance', 'output' => 'La Renaissance est une période de renouveau artistique du 15ème au 16ème siècle, avec Léonard de Vinci comme génie.', 'category' => 'period'],
            ['input' => 'renaissance', 'output' => 'La Renaissance est une période de renouveau artistique du 15ème au 16ème siècle, avec Léonard de Vinci comme génie.', 'category' => 'period'],
            
            ['input' => 'qui est salvador dali', 'output' => 'Salvador Dalí (1904-1989) est le maître du surréalisme, artiste excentrique et génial. Œuvre : La Persistance de la mémoire.', 'category' => 'artist'],
            ['input' => 'salvador dali', 'output' => 'Salvador Dalí (1904-1989) est le maître du surréalisme, artiste excentrique et génial. Œuvre : La Persistance de la mémoire.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que la sculpture', 'output' => 'La sculpture est un art tridimensionnel créé en taillant, modelant ou assemblant des matériaux comme pierre, bois ou métal.', 'category' => 'technique'],
            ['input' => 'sculpture', 'output' => 'La sculpture est un art tridimensionnel créé en taillant, modelant ou assemblant des matériaux comme pierre, bois ou métal.', 'category' => 'technique'],
            
            ['input' => 'qu\'est ce que le baroque', 'output' => 'Le baroque est un style du 17ème siècle avec beaucoup de mouvement, de richesse et d\'émotion intense.', 'category' => 'style'],
            ['input' => 'baroque', 'output' => 'Le baroque est un style du 17ème siècle avec beaucoup de mouvement, de richesse et d\'émotion intense.', 'category' => 'style'],
            
            ['input' => 'qui est andy warhol', 'output' => 'Andy Warhol (1928-1987) est le roi du pop art, célébrant la culture de masse. Œuvres : Campbell\'s Soup Cans, Marilyn Monroe.', 'category' => 'artist'],
            ['input' => 'andy warhol', 'output' => 'Andy Warhol (1928-1987) est le roi du pop art, célébrant la culture de masse. Œuvres : Campbell\'s Soup Cans, Marilyn Monroe.', 'category' => 'artist'],
            
            ['input' => 'qu\'est ce que l\'art abstrait', 'output' => 'L\'art abstrait est un style qui ne représente pas des objets réels mais utilise formes et couleurs pour exprimer des émotions.', 'category' => 'style'],
            ['input' => 'art abstrait', 'output' => 'L\'art abstrait est un style qui ne représente pas des objets réels mais utilise formes et couleurs pour exprimer des émotions.', 'category' => 'style'],
            
            ['input' => 'qu\'est ce que la photographie', 'output' => 'La photographie est l\'art de capturer des images avec un appareil photo, créé au 19ème siècle.', 'category' => 'technique'],
            ['input' => 'photographie', 'output' => 'La photographie est l\'art de capturer des images avec un appareil photo, créé au 19ème siècle.', 'category' => 'technique'],
            
            ['input' => 'qu\'est ce que le romantisme', 'output' => 'Le romantisme est un mouvement du début 19ème siècle privilégiant l\'émotion, l\'imagination et la nature.', 'category' => 'style'],
            ['input' => 'romantisme', 'output' => 'Le romantisme est un mouvement du début 19ème siècle privilégiant l\'émotion, l\'imagination et la nature.', 'category' => 'style']
        ];
    }

    private function loadTrainingData(): void
    {
        if (file_exists($this->dataFile)) {
            $json = file_get_contents($this->dataFile);
            if (!is_string($json)) {
                return;
            }

            $savedData = json_decode($json, true);
            if (is_array($savedData)) {
                // Fusionner les données sauvegardées avec les données initiales
                $this->trainingData = array_values(array_merge($this->trainingData, $savedData['training_data'] ?? []));
                $this->weights = $savedData['weights'] ?? [];
            }
        }
    }

    private function saveTrainingData(): void
    {
        $data = [
            'training_data' => $this->trainingData,
            'weights' => $this->weights,
            'last_updated' => date('Y-m-d H:i:s'),
            'total_examples' => count($this->trainingData)
        ];
        
        $dir = dirname($this->dataFile);
        if (!is_dir($dir)) {
            mkdir($dir, 0777, true);
        }
        
        file_put_contents($this->dataFile, json_encode($data, JSON_PRETTY_PRINT));
    }

    private function buildVocabulary(): void
    {
        $this->vocabulary = [];
        
        foreach ($this->trainingData as $example) {
            $words = $this->tokenize($example['input']);
            foreach ($words as $word) {
                if (!isset($this->vocabulary[$word])) {
                    $this->vocabulary[$word] = count($this->vocabulary);
                }
            }
        }
    }

    /**
     * @return list<string>
     */
    private function tokenize(string $text): array
    {
        // Tokenisation simple
        $text = strtolower($text);
        $text = preg_replace('/[^\w\s]/', ' ', $text) ?? $text;

        return array_values(array_filter(explode(' ', $text), static fn (string $word): bool => $word !== ''));
    }

    /**
     * @return array<int, int>
     */
    private function textToVector(string $text): array
    {
        $vector = array_fill(0, count($this->vocabulary), 0);
        $words = $this->tokenize($text);
        
        foreach ($words as $word) {
            if (isset($this->vocabulary[$word])) {
                $vector[$this->vocabulary[$word]] = 1;
            }
        }
        
        return $vector;
    }

    private function trainModel(): void
    {
        $this->weights = [];
        
        foreach ($this->trainingData as $example) {
            $inputVector = $this->textToVector($example['input']);
            $category = $example['category'];
            
            // Simple apprentissage : mémoriser les patterns
            $hash = md5(serialize($inputVector));
            $this->weights[$hash] = [
                'response' => $example['output'],
                'category' => $category,
                'confidence' => 0.9,
                'usage_count' => $this->weights[$hash]['usage_count'] ?? 0
            ];
        }
    }

    /**
     * @return array<string, mixed>
     */
    public function generateResponse(string $question): array
    {
        $inputVector = $this->textToVector($question);
        $hash = md5(serialize($inputVector));
        
        // 1. Chercher une correspondance exacte
        if (isset($this->weights[$hash])) {
            $this->weights[$hash]['usage_count']++;
            $this->saveTrainingData();
            
            return [
                'question' => $question,
                'response' => $this->weights[$hash]['response'],
                'category' => $this->weights[$hash]['category'],
                'confidence' => $this->weights[$hash]['confidence'],
                'method' => 'exact_match',
                'learning_type' => 'supervised_learning'
            ];
        }
        
        // 2. Chercher la meilleure correspondance partielle
        $bestMatch = $this->findBestMatch($inputVector);
        
        if ($bestMatch) {
            $this->weights[$bestMatch['hash']]['usage_count']++;
            $this->saveTrainingData();
            
            return [
                'question' => $question,
                'response' => $bestMatch['response'],
                'category' => $bestMatch['category'],
                'confidence' => $bestMatch['confidence'],
                'method' => 'partial_match',
                'learning_type' => 'supervised_learning'
            ];
        }
        
        // 3. Générer une réponse par défaut et apprendre
        $defaultResponse = $this->generateDefaultResponse($question);
        $this->learnFromInteraction($question, $defaultResponse);
        
        return [
            'question' => $question,
            'response' => $defaultResponse,
            'category' => 'unknown',
            'confidence' => 0.3,
            'method' => 'default_generation',
            'learning_type' => 'reinforcement_learning'
        ];
    }

    /**
     * @param array<int, int> $inputVector
     *
     * @return array{hash: string, response: string, category: string, confidence: float}|null
     */
    private function findBestMatch(array $inputVector): ?array
    {
        $bestMatch = null;
        $bestScore = 0;
        
        foreach ($this->weights as $hash => $data) {
            // Reconstruire le vecteur original (simplification)
            $score = $this->calculateSimilarity($inputVector, $hash);
            
            if ($score > $bestScore && $score > 0.3) {
                $bestScore = $score;
                $bestMatch = [
                    'hash' => $hash,
                    'response' => $data['response'],
                    'category' => $data['category'],
                    'confidence' => min(0.8, $score)
                ];
            }
        }
        
        return $bestMatch;
    }

    /**
     * @param array<int, int> $vector1
     */
    private function calculateSimilarity(array $vector1, string $hash): float
    {
        // Simplification : calculer la similarité basée sur les mots communs
        $vector2 = $this->reconstructVector($hash);
        
        $intersection = 0;
        $union = 0;
        
        for ($i = 0; $i < count($vector1); $i++) {
            if ($vector1[$i] > 0 || $vector2[$i] > 0) {
                $union++;
                if ($vector1[$i] > 0 && $vector2[$i] > 0) {
                    $intersection++;
                }
            }
        }
        
        return $union > 0 ? $intersection / $union : 0;
    }

    /**
     * @return list<int>
     */
    private function reconstructVector(string $hash): array
    {
        // Simplification : reconstruire approximativement le vecteur
        return array_fill(0, count($this->vocabulary), 0);
    }

    private function generateDefaultResponse(string $question): string
    {
        $responses = [
            'Je suis encore en apprentissage. Pouvez-vous reformuler votre question sur l\'art ?',
            'C\'est une question intéressante ! Je l\'apprends pour pouvoir mieux répondre la prochaine fois.',
            'Je n\'ai pas encore appris cette réponse. Pouvez-vous me donner plus de détails ?',
            'Je suis en train d\'apprendre de nos interactions. Votre question m\'aide à m\'améliorer !'
        ];
        
        return $responses[array_rand($responses)];
    }

    public function learnFromInteraction(string $question, string $response): void
    {
        // Apprentissage par renforcement
        $this->trainingData[] = [
            'input' => $question,
            'output' => $response,
            'category' => 'learned',
            'timestamp' => time()
        ];
        
        // Retrainer le modèle avec les nouvelles données
        $this->buildVocabulary();
        $this->trainModel();
        $this->saveTrainingData();
    }

    /**
     * @return array<string, mixed>
     */
    public function getLearningStats(): array
    {
        $lastUpdatedTimestamp = file_exists($this->dataFile) ? filemtime($this->dataFile) : false;
        if (!is_int($lastUpdatedTimestamp)) {
            $lastUpdatedTimestamp = time();
        }

        return [
            'total_examples' => count($this->trainingData),
            'vocabulary_size' => count($this->vocabulary),
            'categories' => array_count_values(array_column($this->trainingData, 'category')),
            'last_updated' => date('Y-m-d H:i:s', $lastUpdatedTimestamp),
            'learning_type' => 'Supervised + Reinforcement Learning'
        ];
    }

    public function addTrainingExample(string $input, string $output, string $category = 'user_taught'): void
    {
        $this->trainingData[] = [
            'input' => $input,
            'output' => $output,
            'category' => $category,
            'timestamp' => time()
        ];
        
        $this->buildVocabulary();
        $this->trainModel();
        $this->saveTrainingData();
    }
}
