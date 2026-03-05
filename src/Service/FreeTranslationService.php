<?php

declare(strict_types=1);

namespace App\Service;

use Symfony\Contracts\HttpClient\HttpClientInterface;

class FreeTranslationService
{
    private HttpClientInterface $client;

    public function __construct(HttpClientInterface $client)
    {
        $this->client = $client;
    }

    public function translateText(string $text, string $targetLang = 'en', string $sourceLang = 'fr'): ?string
    {
        // Essayer plusieurs API gratuites dans l'ordre
        $translations = [
            fn() => $this->translateWithMyMemory($text, $targetLang, $sourceLang),
            fn() => $this->translateWithLibreTranslate($text, $targetLang, $sourceLang),
            fn() => $this->translateWithGoogleTranslate($text, $targetLang, $sourceLang),
        ];

        foreach ($translations as $translationFunction) {
            try {
                $result = $translationFunction();
                if ($result !== null && $result !== '') {
                    return $result;
                }
            } catch (\Exception $e) {
                continue;
            }
        }

        return null;
    }

    private function translateWithMyMemory(string $text, string $targetLang, string $sourceLang): ?string
    {
        $response = $this->client->request('GET', 'https://api.mymemory.translated.net/get', [
            'query' => [
                'q' => $text,
                'langpair' => $sourceLang . '|' . $targetLang
            ]
        ]);

        $data = $response->toArray();
        return $data['responseData']['translatedText'] ?? null;
    }

    private function translateWithLibreTranslate(string $text, string $targetLang, string $sourceLang): ?string
    {
        $response = $this->client->request('POST', 'https://libretranslate.de/translate', [
            'headers' => [
                'Content-Type' => 'application/json',
            ],
            'json' => [
                'q' => $text,
                'source' => $sourceLang,
                'target' => $targetLang,
                'format' => 'text'
            ]
        ]);

        $data = $response->toArray();
        return $data['translatedText'] ?? null;
    }

    private function translateWithGoogleTranslate(string $text, string $targetLang, string $sourceLang): ?string
    {
        // Utilisation de l'API Google Translate non officielle mais gratuite
        $response = $this->client->request('GET', 'https://translate.googleapis.com/translate_a/single', [
            'query' => [
                'client' => 'gtx',
                'sl' => $sourceLang,
                'tl' => $targetLang,
                'dt' => 't',
                'q' => $text
            ]
        ]);

        $data = $response->toArray();
        
        if (isset($data[0])) {
            $translatedText = '';
            foreach ($data[0] as $segment) {
                if (isset($segment[0])) {
                    $translatedText .= $segment[0];
                }
            }
            return $translatedText;
        }
        
        return null;
    }

    /**
     * @param array<array-key, string> $texts
     *
     * @return array<array-key, string|null>
     */
    public function translateArray(array $texts, string $targetLang = 'en'): array
    {
        $results = [];
        foreach ($texts as $key => $text) {
            $results[$key] = $this->translateText($text, $targetLang);
        }
        return $results;
    }
}
