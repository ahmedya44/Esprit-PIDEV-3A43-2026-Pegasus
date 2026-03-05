<?php

namespace App\Service;

use Symfony\Contracts\HttpClient\Exception\ExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class AIService
{
    private const GEMINI_BASE_URL = 'https://generativelanguage.googleapis.com/v1beta/models';
    /** @var list<string> Models to try in order (free-tier friendly first) */
    private const GEMINI_MODELS = [
        'gemini-2.5-flash-lite',
        'gemini-2.5-flash-lite-preview-09-2025',
        'gemini-2.5-flash',
        'gemini-2.0-flash',
        'gemini-1.5-flash',
        'gemini-pro',
    ];

    private const SYSTEM_PROMPT = <<<'PROMPT'
You are an intelligent AI assistant integrated in an artistic product marketplace.

You can answer ANY user question.

If the question is about the product, analyze the product information carefully.

If the question is general, answer normally.

Always respond clearly and helpfully.
PROMPT;

    public function __construct(
        private HttpClientInterface $httpClient,
        private string $geminiKey,
    ) {
    }

    /**
     * Calls Google Gemini API. Answers any question: product-related, general, artistic, conversational.
     * When $catalogContext is provided (e.g. from the product listing page), the AI can answer about the catalog (count, products).
     *
     * @param array{total: int, names: list<string>}|null $catalogContext
     */
    public function getProductAdvisorAnswer(string $productName, string $productDescription, string $question, ?array $catalogContext = null): string
    {
        $userContent = '';

        if ($catalogContext !== null && isset($catalogContext['total'], $catalogContext['names'])) {
            $total = (int) $catalogContext['total'];
            $names = $catalogContext['names'];
            $namesList = \is_array($names) && $names !== [] ? implode(', ', array_map('trim', $names)) : '(aucun nom fourni)';
            $userContent .= "Catalogue actuel : " . $total . " produit(s) listé(s). Noms (échantillon) : " . $namesList . ".\n\n";
        }

        $userContent .= "Product name: " . ($productName !== '' ? $productName : '(none – question sur le catalogue)')
            . "\n\nProduct description:\n" . ($productDescription !== '' ? $productDescription : '(none)')
            . "\n\nUser question:\n" . $question;

        if ($this->geminiKey === '' || str_starts_with($this->geminiKey, 'your_')) {
            return 'Le conseiller IA est indisponible. Vérifiez la configuration.';
        }

        // Combine system instruction with user content (reliable across Gemini API versions)
        $fullPrompt = self::SYSTEM_PROMPT . "\n\n---\n\n" . $userContent;

        $payload = [
            'contents' => [
                [
                    'parts' => [
                        ['text' => $fullPrompt],
                    ],
                ],
            ],
            'generationConfig' => [
                'maxOutputTokens' => 600,
                'temperature' => 0.7,
            ],
        ];

        $quotaExceeded = false;

        try {
            foreach (self::GEMINI_MODELS as $model) {
                $url = self::GEMINI_BASE_URL . '/' . $model . ':generateContent?key=' . urlencode($this->geminiKey);

                $response = $this->httpClient->request('POST', $url, [
                    'headers' => ['Content-Type' => 'application/json'],
                    'json' => $payload,
                    'timeout' => 30,
                ]);

                $statusCode = $response->getStatusCode();
                $rawContent = $response->getContent(false);

                $data = \is_string($rawContent) && $rawContent !== ''
                    ? json_decode($rawContent, true)
                    : [];

                if (!\is_array($data)) {
                    continue;
                }

                if ($statusCode === 429 || (isset($data['error']['status']) && $data['error']['status'] === 'RESOURCE_EXHAUSTED')) {
                    $quotaExceeded = true;
                    continue;
                }

                if ($statusCode >= 400) {
                    continue;
                }

                $content = $this->extractAnswer($data);
                if ($content !== '') {
                    return $content;
                }
            }

            return $quotaExceeded
                ? 'Le conseiller a atteint sa limite de requêtes. Réessayez dans une minute.'
                : 'Désolé, le conseiller est temporairement indisponible. Réessayez plus tard.';
        } catch (ExceptionInterface $e) {
            return 'Désolé, le conseiller est temporairement indisponible. Réessayez plus tard.';
        } catch (\Throwable $e) {
            return 'Désolé, le conseiller est temporairement indisponible. Réessayez plus tard.';
        }
    }

    private function extractAnswer(array $data): string
    {
        if (isset($data['error']['message'])) {
            return '';
        }
        if (empty($data['candidates'][0])) {
            return '';
        }
        $candidate = $data['candidates'][0];
        if (empty($candidate['content']['parts'][0]['text'])) {
            return '';
        }

        $content = $candidate['content']['parts'][0]['text'];
        return \is_string($content) ? trim($content) : '';
    }

}
