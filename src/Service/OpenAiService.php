<?php

namespace App\Service;

use Symfony\Contracts\HttpClient\HttpClientInterface;

class OpenAiService
{
    private $httpClient;
    private $apiKey;

    public function __construct(HttpClientInterface $httpClient, string $apiKey)
    {
        $this->httpClient = $httpClient;
        $this->apiKey = $apiKey;
    }

    public function generateDescription(string $title): string
    {
        if (empty($this->apiKey) || $this->apiKey === 'your_openai_api_key_here') {
            $templates = [
                'jazz' => "Une soirée envoûtante où les notes de saxophone s'entremêlent pour créer une atmosphère feutrée et élégante. Découvrez le génie du jazz contemporain dans un cadre intimiste.",
                'exposition' => "Plongez dans un univers visuel unique où l'art et l'émotion se rencontrent. Cette exposition dévoile des œuvres audacieuses qui repoussent les frontières de la créativité.",
                'concert' => "Vibrez au rythme d'une performance live exceptionnelle. Une synergie parfaite entre les artistes et le public pour une expérience musicale gravée dans les mémoires.",
                'théâtre' => "Une pièce captivante qui explore avec finesse la condition humaine. Entre rires et émotions, laissez-vous transporter par le jeu puissant des comédiens.",
                'défaut' => "L'événement intitulé \"$title\" promet d'être une expérience inoubliable. Alliant passion et excellence, cette rencontre artistique est une invitation à la découverte et au partage dans une ambiance conviviale."
            ];

            $desc = $templates['défaut'];
            foreach ($templates as $keyword => $template) {
                if (stripos($title, $keyword) !== false) {
                    $desc = $template;
                    break;
                }
            }
            return $desc;
        }

        $response = $this->httpClient->request('POST', 'https://api.openai.com/v1/chat/completions', [
            'headers' => [
                'Authorization' => 'Bearer ' . $this->apiKey,
                'Content-Type' => 'application/json',
            ],
            'json' => [
                'model' => 'gpt-3.5-turbo',
                'messages' => [
                    [
                        'role' => 'system',
                        'content' => 'Tu es un assistant créatif pour une plateforme artistique. Ta tâche est de rédiger une description courte, captivante et professionnelle pour un événement ou une œuvre d\'art à partir de son titre. La description doit faire environ 3-4 phrases.'
                    ],
                    [
                        'role' => 'user',
                        'content' => 'Rédige une description pour : ' . $title
                    ]
                ],
                'temperature' => 0.7,
                'max_tokens' => 200,
            ],
        ]);

        $data = $response->toArray();

        if (!isset($data['choices'][0]['message']['content'])) {
            throw new \Exception('Réponse invalide de l\'API OpenAI.');
        }

        return trim($data['choices'][0]['message']['content']);
    }
}
