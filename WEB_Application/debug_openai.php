<?php

require_once 'vendor/autoload.php';

use Symfony\Component\HttpClient\HttpClient;

// Charger les variables d'environnement
if (file_exists('.env')) {
    $lines = file('.env', FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
    foreach ($lines as $line) {
        if (strpos($line, '#') === 0) continue;
        if (strpos($line, '=') === false) continue;
        
        list($key, $value) = explode('=', $line, 2);
        $_ENV[trim($key)] = trim($value);
        $_SERVER[trim($key)] = trim($value);
    }
}

$apiKey = $_ENV['OPENAI_API_KEY'];
$client = HttpClient::create();

$imageUrl = 'https://wallpapers.com/images/hd/caption-the-enigmatic-smile-mona-lisa-by-da-vinci-nkvqnd38jsbo9swt.jpg';

echo "Test direct de l'API OpenAI Vision...\n";

try {
    $response = $client->request('POST', 'https://api.openai.com/v1/chat/completions', [
        'headers' => [
            'Authorization' => 'Bearer ' . $apiKey,
            'Content-Type' => 'application/json',
        ],
        'json' => [
            'model' => 'gpt-4-vision-preview',
            'messages' => [
                [
                    'role' => 'user',
                    'content' => [
                        [
                            'type' => 'text',
                            'text' => 'Analyze this artwork image and generate: 1) A creative, artistic title in French 2) A detailed, poetic description in French (2-3 sentences). Make it sound professional for an art gallery. Respond in JSON format: {"title": "Titre ici", "description": "Description ici"}'
                        ],
                        [
                            'type' => 'image_url',
                            'image_url' => [
                                'url' => $imageUrl
                            ]
                        ]
                    ]
                ]
            ],
            'max_tokens' => 300
        ]
    ]);

    echo "Status: " . $response->getStatusCode() . "\n";
    
    if ($response->getStatusCode() !== 200) {
        echo "Erreur: " . $response->getContent(false) . "\n";
    } else {
        $data = $response->toArray();
        $content = $data['choices'][0]['message']['content'] ?? '{}';
        echo "Réponse brute: " . $content . "\n";
        
        // Nettoyer et parser le JSON
        $content = preg_replace('/```json\s*|\s*```/', '', $content);
        $result = json_decode($content, true);
        
        echo "Résultat parsé:\n";
        print_r($result);
    }
    
} catch (\Exception $e) {
    echo "Exception: " . $e->getMessage() . "\n";
}
