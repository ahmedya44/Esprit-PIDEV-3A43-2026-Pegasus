<?php

declare(strict_types=1);

namespace App\Service;

class ArtChatbotService
{
    private array $knowledgeBase;
    private array $responsePatterns;

    public function __construct()
    {
        // Base de connaissances artistiques étendue
        $this->knowledgeBase = [
            'art_styles' => [
                'impressionnisme' => 'Style artistique du 19ème siècle caractérisé par des coups de pinceau visibles et des jeux de lumière. Artistes célèbres : Monet, Renoir, Degas.',
                'cubisme' => 'Style qui décompose les sujets en formes géométriques, créé par Picasso et Braque au 20ème siècle.',
                'surréalisme' => 'Mouvement artistique explorant l\'inconscient et le rêve, avec des images surréalistes. Salvador Dalí en est le maître.',
                'expressionnisme' => 'Art exprimant les émotions plutôt que la réalité objective, né en Allemagne au début du 20ème siècle.',
                'abstrait' => 'Art qui ne représente pas des objets réels mais utilise formes et couleurs pour exprimer des émotions.',
                'réalisme' => 'Style représentant la réalité avec précision et détails techniques, opposé à l\'idéalisation.',
                'baroque' => 'Style artistique du 17ème siècle avec beaucoup de mouvement, de richesse et d\'émotion intense.',
                'romantisme' => 'Mouvement artistique du 19ème siècle privilégiant l\'émotion, l\'imagination et la nature.',
                'néoclassicisme' => 'Style inspiré de l\'art antique grec et romain, caractérisé par l\'ordre et la simplicité.',
                'pop art' => 'Art populaire des années 1960 utilisant des images de la culture de masse, Andy Warhol en est le représentant.',
                'minimalisme' => 'Style utilisant des formes simples et épurées, avec un minimum d\'éléments.',
                'fauvisme' => 'Style avec des couleurs vives et non naturelles, créé par Matisse et ses amis.',
                'gothique' => 'Style architectural et artistique du Moyen Âge avec des arcs pointus et des vitraux colorés.',
                'renaissance' => 'Période de renouveau artistique du 15ème au 16ème siècle, Léonard de Vinci en est le génie.',
                'art contemporain' => 'Art créé de nos jours, expérimental et souvent conceptuel.'
            ],
            'techniques' => [
                'peinture à l\'huile' => 'Technique utilisant des pigments mélangés à l\'huile, séchage lent mais très durable.',
                'aquarelle' => 'Peinture sur papier avec des couleurs diluées à l\'eau, transparentes et lumineuses.',
                'acrylique' => 'Peinture rapide avec des pigments synthétiques, séchage très rapide et couleurs vives.',
                'pastel' => 'Crayons de couleur secs pour des effets doux et veloutés, très utilisé dans les portraits.',
                'gravure' => 'Technique d\'impression en creusant une plaque (bois, métal, pierre) pour créer des estampes.',
                'sculpture' => 'Art tridimensionnel créé en taillant, modelant ou assemblant des matériaux.',
                'photographie' => 'Art de capturer des images avec un appareil photo, créé au 19ème siècle.',
                'dessin' => 'Technique de base avec crayon, charbon ou encre pour créer des lignes et formes.',
                'collage' => 'Assemblage de différents matériaux sur une surface pour créer une composition.',
                'mosaïque' => 'Art d\'assembler de petits morceaux colorés pour former une image.',
                'fresque' => 'Peinture murale sur plâtre frais, technique utilisée dans les églises et palais.',
                'lithographie' => 'Technique d\'impression basée sur la répulsion eau/huile, inventée au 19ème siècle.',
                'sérigraphie' => 'Technique d\'impression à travers un écran de soie, très utilisée en art moderne.',
                'calligraphie' => 'Art de l\'écriture décorative, très important dans les cultures asiatiques.',
                'digitale' => 'Art créé avec des outils numériques, ordinateurs, tablettes et logiciels spécialisés.'
            ],
            'artistes' => [
                'davinci' => 'Léonard de Vinci, génie de la Renaissance (1452-1519), peintre, inventeur et scientifique. Œuvres : Mona Lisa, La Cène.',
                'picasso' => 'Pablo Picasso (1881-1973), cofondateur du cubisme, artiste prolifique du 20ème siècle. Œuvres : Guernica, Les Demoiselles d\'Avignon.',
                'monet' => 'Claude Monet (1840-1926), fondateur de l\'impressionnisme, maître des jeux de lumière. Œuvres : Les Nymphéas, Impression, soleil levant.',
                'van_gogh' => 'Vincent van Gogh (1853-1890), post-impressionniste, connu pour ses coups de pinceau émotionnels. Œuvres : Les Tournesols, La Nuit étoilée.',
                'dali' => 'Salvador Dalí (1904-1989), maître du surréalisme, artiste excentrique et génial. Œuvres : La Persistance de la mémoire.',
                'matisse' => 'Henri Matisse (1869-1954), fauviste, maître de la couleur et de la forme. Œuvres : La Danse, La Musique.',
                'rembrandt' => 'Rembrandt van Rijn (1606-1669), maître hollandais de la lumière et de l\'ombre. Œuvres : La Ronde de nuit.',
                'michel-ange' => 'Michel-Ange (1475-1564), sculpteur, peintre et architecte de la Renaissance. Œuvres : David, la chapelle Sixtine.',
                'warhol' => 'Andy Warhol (1928-1987), roi du pop art, célébrant la culture de masse. Œuvres : Campbell\'s Soup Cans, Marilyn Monroe.',
                'kandinsky' => 'Wassily Kandinsky (1866-1944), pionnier de l\'art abstrait, liant art et musique spirituelle.',
                'pollock' => 'Jackson Pollock (1912-1956), peintre abstrait américain, inventeur du dripping.',
                'cezanne' => 'Paul Cézanne (1839-1906), post-impressionniste, pont entre impressionnisme et cubisme.',
                'degas' => 'Edgar Degas (1834-1917), impressionniste spécialisé dans les ballets et scènes de vie parisienne.',
                'gauguin' => 'Paul Gauguin (1848-1903), post-impressionniste, explorateur de couleurs exotiques et Tahiti.',
                'frida_kahlo' => 'Frida Kahlo (1907-1954), peintre mexicaine, explorant la douleur et l\'identité dans ses autoportraits.',
                'banksy' => 'Banksy, artiste de rue britannique anonyme, critique sociale par le graffiti et le pochoir.',
                'vermeer' => 'Johannes Vermeer (1632-1675), maître hollandais de la lumière intime et des scènes domestiques.'
            ],
            'concepts' => [
                'art' => 'Expression humaine de la créativité, des émotions et des idées à travers diverses formes visuelles, sonores ou conceptuelles.',
                'composition' => 'Organisation des éléments visuels dans une œuvre pour créer un ensemble harmonieux et équilibré.',
                'perspective' => 'Technique donnant illusion de profondeur sur une surface bidimensionnelle, inventée à la Renaissance.',
                'couleur' => 'Élément fondamental de l\'art, utilisée pour exprimer des émotions, créer l\'harmonie ou le contraste.',
                'lumière' => 'Jeu d\'ombres et de lumières pour créer du volume, du relief et l\'atmosphère dans une œuvre.',
                'texture' => 'Qualité tactile d\'une surface, rugueuse, lisse, douce, créant des effets visuels et sensoriels.',
                'harmonie' => 'Équilibre visuel entre les différents éléments d\'une composition, créant une unité agréable.',
                'contraste' => 'Opposition entre éléments (couleurs, formes, tailles) pour créer du dynamisme et de l\'intérêt.',
                'rythme' => 'Répétition d\'éléments créant un mouvement visuel guidant l\'œil à travers l\'œuvre.',
                'équilibre' => 'Distribution visuelle des éléments pour créer une stabilité, symétrique ou asymétrique.',
                'proportion' => 'Relation de taille entre les différentes parties d\'une œuvre, basée sur les règles de la perspective.',
                'mouvement' => 'Sensation de dynamisme créée par les lignes, formes et couleurs dans une composition.',
                'forme' => 'Contour et structure des objets dans une œuvre, géométrique ou organique.',
                'espace' => 'Zone occupée par les éléments, positive (objets) ou négative (vides), créant la composition.',
                'symbolisme' => 'Utilisation d\'éléments représentant des idées, émotions ou concepts au-delà de leur apparence.'
            ],
            'periods' => [
                'antiquité' => 'Période de l\'art grec et romain, caractérisée par l\'idéalisation et la perfection des formes.',
                'moyen_age' => 'Période médiévale (5ème-15ème siècle), art religieux dominé par l\'Église, style gothique.',
                'renaissance' => '15ème-16ème siècle, renouveau de l\'art antique, humanisme et perfection technique.',
                'baroque' => '17ème siècle, art dramatique et mouvementé, glorifiant l\'Église catholique et les monarchies.',
                'classicisme' => '17ème-18ème siècle, retour à l\'ordre antique, rationalité et harmonie.',
                'romantisme' => 'Début 19ème siècle, émotion, nature, individualisme et rébellion contre le classicisme.',
                'impressionnisme' => 'Années 1870-1880, capture des impressions visuelles, lumière et moment présent.',
                'modernisme' => 'Début 20ème siècle, rupture avec la tradition, expérimentation et abstraction.',
                'contemporain' => 'Art d\'aujourd\'hui, diversité des styles, technologies et concepts.'
            ],
            'themes' => [
                'nature' => 'Sujets inspirés du monde naturel : paysages, animaux, plantes, éléments naturels.',
                'portrait' => 'Représentation d\'une personne, capturant son apparence, caractère et émotions.',
                'nu' => 'Représentation du corps humain, explorant la beauté, la vulnérabilité et l\'humanité.',
                'religion' => 'Thèmes spirituels et sacrés, explorant la foi, le divin et les questions existentielles.',
                'mythologie' => 'Sujets issus des mythes et légendes, explorant l\'imaginaire collectif.',
                'guerre' => 'Représentation des conflits, explorant la violence, le courage et les conséquences.',
                'amour' => 'Thème universel explorant les relations, la passion et les émotions humaines.',
                'mort' => 'Représentation de la fin de vie, explorant la mortalité et le passage.',
                'abstrait' => 'Art non figuratif, explorant les formes, couleurs et émotions purement.',
                'social' => 'Art critique de la société, explorant la politique, l\'injustice et les changements.'
            ]
        ];

        // Patterns de réponses intelligentes
        $this->responsePatterns = [
            'definition' => [
                'patterns' => ['c\'est quoi', 'qu\'est ce que', 'définition de', 'explique moi'],
                'responses' => [
                    '{concept} est {definition}.',
                    'Le {concept} se définit comme {definition}.',
                    'En art, le {concept} représente {definition}.'
                ]
            ],
            'comparison' => [
                'patterns' => ['différence entre', 'comparaison', 'distinguer'],
                'responses' => [
                    'La différence entre {concept1} et {concept2} est que {difference}.',
                    '{concept1} se distingue de {concept2} par {difference}.',
                    'Contrairement à {concept2}, {concept1} est {difference}.'
                ]
            ],
            'technique' => [
                'patterns' => ['comment faire', 'technique pour', 'méthode de'],
                'responses' => [
                    'Pour faire du {style}, il faut {technique}.',
                    'La technique du {style} consiste à {technique}.',
                    'Les artistes utilisent {technique} pour créer du {style}.'
                ]
            ],
            'history' => [
                'patterns' => ['qui a créé', 'origine de', 'histoire de', 'quand est né'],
                'responses' => [
                    '{artiste} a créé le {style} en {year}.',
                    'Le {style} est apparu au {period} grâce à {artiste}.',
                    '{artiste} est né en {year} et est connu pour {achievement}.'
                ]
            ],
            'advice' => [
                'patterns' => ['comment améliorer', 'conseil pour', 'astuce pour'],
                'responses' => [
                    'Pour améliorer en {domaine}, je vous conseille de {advice}.',
                    'Le meilleur conseil en {domaine} est de {advice}.',
                    'Astuce de pro : {advice}'
                ]
            ]
        ];
    }

    public function generateResponse(string $question): array
    {
        $question = strtolower(trim($question));
        
        // 1. Analyser la question
        $intent = $this->detectIntent($question);
        $entities = $this->extractEntities($question);
        
        // 2. Générer la réponse
        $response = $this->buildResponse($intent, $entities, $question);
        
        return [
            'question' => $question,
            'intent' => $intent,
            'entities' => $entities,
            'response' => $response,
            'confidence' => $this->calculateConfidence($intent, $entities),
            'timestamp' => date('Y-m-d H:i:s')
        ];
    }

    private function detectIntent(string $question): string
    {
        // Détection d'intention basée sur les mots-clés
        $intents = [
            'definition' => ['c\'est quoi', 'qu\'est ce que', 'définition', 'explique', 'définition de'],
            'comparison' => ['différence', 'comparaison', 'distinguer', 'versus', 'contre'],
            'technique' => ['comment faire', 'technique', 'méthode', 'faire du', 'réaliser'],
            'history' => ['qui a', 'origine', 'histoire', 'créé par', 'né en'],
            'advice' => ['conseil', 'améliorer', 'astuce', 'comment progresser', 'mieux']
        ];
        
        foreach ($intents as $intent => $keywords) {
            foreach ($keywords as $keyword) {
                if (strpos($question, $keyword) !== false) {
                    return $intent;
                }
            }
        }
        
        return 'general'; // Intent par défaut
    }

    private function extractEntities(string $input): array
    {
        $entities = [];
        $input = strtolower(trim($input));
        
        // Extraire les entités (artistes, styles, concepts, techniques, périodes, thèmes)
        foreach ($this->knowledgeBase as $category => $items) {
            foreach ($items as $key => $value) {
                // Créer plusieurs variantes de recherche pour chaque clé
                $searchVariants = [
                    strtolower(str_replace([' ', '_', '-'], '', $key)),
                    strtolower(str_replace(['_', '-'], ' ', $key)),
                    strtolower(str_replace([' ', '-'], '_', $key))
                ];
                
                $inputClean = strtolower(str_replace([' ', '_', '-'], '', $input));
                
                foreach ($searchVariants as $variant) {
                    if (strpos($inputClean, $variant) !== false) {
                        $entities[$category] = $key;
                        break 3; // Sortir des trois boucles
                    }
                }
            }
        }
        
        // Ajouter des détections spéciales pour des questions générales
        if (strpos($input, 'art') !== false) {
            $entities['concepts'] = 'art';
        }
        
        return $entities;
    }

    private function buildResponse(string $intent, array $entities, string $question): string
    {
        switch ($intent) {
            case 'definition':
                return $this->generateDefinitionResponse($entities);
                
            case 'comparison':
                return $this->generateComparisonResponse($entities);
                
            case 'technique':
                return $this->generateTechniqueResponse($entities);
                
            case 'history':
                return $this->generateHistoryResponse($entities);
                
            case 'advice':
                return $this->generateAdviceResponse($entities, $question);
                
            default:
                return $this->generateGeneralResponse($entities, $question);
        }
    }

    private function generateDefinitionResponse(array $entities): string
    {
        // Vérifier toutes les catégories possibles
        foreach ($entities as $category => $key) {
            if (isset($this->knowledgeBase[$category][$key])) {
                return $this->knowledgeBase[$category][$key];
            }
        }
        
        // Réponses générales si aucune entité spécifique trouvée
        $generalResponses = [
            'L\'art est une expression humaine qui prend de nombreuses formes : peinture, sculpture, musique, danse, etc.',
            'Le monde de l\'art est vaste et fascinant, avec des styles allant du classicisme à l\'art contemporain.',
            'L\'art évolue constamment, chaque période apportant sa vision unique du monde.',
            'Les artistes utilisent différentes techniques pour exprimer leur créativité et leurs émotions.'
        ];
        
        return $generalResponses[array_rand($generalResponses)];
    }

    private function generateComparisonResponse(array $entities): string
    {
        if (isset($entities['art_styles']) && count($entities) >= 2) {
            // Logique de comparaison simple
            return 'Chaque style artistique a ses propres caractéristiques et objectifs uniques.';
        }
        
        return 'Je peux comparer ces éléments si vous me donnez plus de détails.';
    }

    private function generateTechniqueResponse(array $entities): string
    {
        if (isset($entities['techniques'])) {
            $technique = $entities['techniques'];
            return $this->knowledgeBase['techniques'][$technique] ?? 
                'Cette technique artistique nécessite pratique et patience.';
        }
        
        return 'Pour cette technique, je vous recommande de consulter des tutoriels spécialisés.';
    }

    private function generateHistoryResponse(array $entities): string
    {
        if (isset($entities['artistes'])) {
            $artist = $entities['artistes'];
            return $this->knowledgeBase['artistes'][$artist] ?? 
                'Cet artiste a marqué l\'histoire de l\'art de manière significative.';
        }
        
        return 'Je n\'ai pas assez d\'informations sur ce sujet historique.';
    }

    private function generateAdviceResponse(array $entities, string $question): string
    {
        $advices = [
            'art' => 'pratiquer régulièrement, étudier les maîtres et expérimenter différentes techniques.',
            'créativité' => 'garder un carnet d\'idées, visiter des musées et s\'entourer d\'autres artistes.',
            'technique' => 'commencer par les bases, maîtriser les fondamentaux avant de complexifier.',
            'style' => 'étudier les caractéristiques du style qui vous intéresse et s\'inspirer des grands maîtres.'
        ];
        
        // Détecter le domaine pour un conseil pertinent
        if (isset($entities['art_styles'])) {
            return $advices['style'];
        }
        if (isset($entities['techniques'])) {
            return $advices['technique'];
        }
        
        return $advices['créativité'];
    }

    private function generateGeneralResponse(array $entities, string $question): string
    {
        // Réponses générales intelligentes
        $generalResponses = [
            'L\'art est un voyage infini où chaque œuvre est une destination.',
            'La créativité est comme un muscle : plus on l\'exerce, plus elle se développe.',
            'Chaque artiste trouve sa voix unique en explorant différentes techniques.',
            'L\'art nous connecte à notre humanité et transcende les barrières du langage.'
        ];
        
        // Tenter de trouver une réponse contextuelle
        if (!empty($entities)) {
            return $generalResponses[array_rand($generalResponses)];
        }
        
        // Réponses de fallback
        $fallbackResponses = [
            'C\'est une excellente question artistique !',
            'Je suis là pour vous aider dans votre démarche artistique.',
            'L\'art est un domaine fascinant plein de découvertes.',
            'N\'hésitez pas à explorer et à expérimenter dans votre création.'
        ];
        
        return $fallbackResponses[array_rand($fallbackResponses)];
    }

    private function calculateConfidence(string $intent, array $entities): int
    {
        $confidence = 50; // Base
        
        // Augmenter la confiance si on a détecté des entités
        if (!empty($entities)) {
            $confidence += 20;
        }
        
        // Augmenter si l'intention est claire
        if ($intent !== 'general') {
            $confidence += 15;
        }
        
        return min(95, $confidence);
    }

    public function getSuggestedQuestions(): array
    {
        return [
            'Qu\'est-ce que l\'impressionnisme ?',
            'Comment faire de la peinture à l\'huile ?',
            'Qui est Picasso ?',
            'Quelle est la différence entre l\'art abstrait et le réalisme ?',
            'Comment améliorer ma créativité artistique ?',
            'Quelles sont les bases de la composition ?',
            'Qui est le fondateur du surréalisme ?',
            'Comment choisir ma palette de couleurs ?',
            'Qu\'est-ce que la perspective en art ?'
        ];
    }
}
