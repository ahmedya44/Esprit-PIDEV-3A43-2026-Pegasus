<?php

namespace App\Service;

use Symfony\Contracts\HttpClient\HttpClientInterface;

class BadWordsDetectorService
{
    private const LOCAL_BLACKLIST = ['fuck', 'shit', 'bitch', 'asshole', 'connard', 'salope', 'pute'];

    public function __construct(
        private HttpClientInterface $httpClient,
    ) {
    }

    /**
     * @return array<int, string>
     */
    public function findBadWords(string $text): array
    {
        $text = trim($text);
        if ($text == '') {
            return [];
        }

        $detected = [];

        foreach (self::LOCAL_BLACKLIST as $word) {
            if (preg_match('/\\b' . preg_quote($word, '/') . '\\b/i', $text)) {
                $detected[] = $word;
            }
        }

        try {
            $response = $this->httpClient->request('GET', 'https://www.purgomalum.com/service/containsprofanity', [
                'query' => ['text' => $text],
            ]);
            $contains = trim((string) $response->getContent(false));

            if (strtolower($contains) === 'true' && $detected === []) {
                $detected[] = 'profanity_detected';
            }
        } catch (\Throwable) {
            // Keep local detection result on API failure.
        }

        return array_values(array_unique($detected));
    }

    public function hasBadWords(string $text): bool
    {
        return $this->findBadWords($text) !== [];
    }
}
