<?php

namespace App\Service;

use Symfony\Contracts\HttpClient\Exception\ExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class AiAutocompleteService
{
    public function __construct(
        private HttpClientInterface $httpClient,
        private string $apiKey,
        private string $model,
        private string $apiUrl,
    ) {
    }

    /**
     * @return array<int, string>
     */
    public function suggest(string $field, string $text, string $context = '', string $locale = 'fr', int $limit = 4): array
    {
        $text = trim($text);
        if ($text === '') {
            return [];
        }

        $limit = max(1, min(8, $limit));

        if ($this->apiKey === '') {
            return [];
        }

        try {
            $response = $this->httpClient->request('POST', $this->buildGeminiUrl(), [
                'query' => [
                    'key' => $this->apiKey,
                ],
                'json' => [
                    'contents' => [[
                        'parts' => [[
                            'text' => $this->buildPrompt($field, $text, $context, $locale, $limit),
                        ]],
                    ]],
                    'generationConfig' => [
                        'temperature' => 0.4,
                        'maxOutputTokens' => 140,
                    ],
                ],
                'timeout' => 8,
            ]);

            $data = $response->toArray(false);
            $raw = $this->extractGeminiText($data);
            $suggestions = $this->parseSuggestions($raw);
            $suggestions = $this->normalizeSuggestions($suggestions, $text);

            if ($suggestions !== []) {
                return array_slice($suggestions, 0, $limit);
            }
        } catch (ExceptionInterface|\Throwable) {
            return [];
        }

        return [];
    }

    private function buildGeminiUrl(): string
    {
        $baseUrl = rtrim(trim($this->apiUrl), '/');
        $model = trim($this->model);

        if ($model === '') {
            return $baseUrl . '/models/gemini-2.5-flash:generateContent';
        }

        if (!str_starts_with($model, 'models/')) {
            $model = 'models/' . $model;
        }

        if (str_ends_with($baseUrl, '/models') && str_starts_with($model, 'models/')) {
            $model = substr($model, strlen('models/'));
        }

        return $baseUrl . '/' . $model . ':generateContent';
    }

    private function buildPrompt(string $field, string $text, string $context, string $locale, int $limit): string
    {
        return <<<PROMPT
You generate short autocomplete continuations for forum content.
Rules:
- Keep the same language as the user text (locale hint: {$locale}).
- Keep a natural tone.
- Do not include harmful/offensive content.
- Return exactly {$limit} suggestions, one per line.
- No numbering. No markdown. No JSON.
- Each line must start with the current text and continue it naturally.
- If CURRENT_TEXT ends in the middle of a word, complete that word first before continuing.
- Never split a word with a space in the middle (bad: "Bon jour", good: "Bonjour").
- Suggestions must be strictly art-related only.
- Allowed art scope: painting, drawing, sculpture, photography, cinema, music, dance, theater, literature, design, architecture, art history, exhibitions, artistic techniques, creativity process.
- If the current text is broad, steer the continuation toward an art topic while preserving the current text prefix.
- Never suggest unrelated domains (sports, finance, politics, generic tech support, etc.) unless explicitly framed through art.

FIELD: {$field}
CURRENT_TEXT: {$text}
CONTEXT: {$context}
PROMPT;
    }

    /**
     * @param array<string, mixed> $data
     */
    private function extractGeminiText(array $data): string
    {
        $candidates = $data['candidates'] ?? null;
        if (!is_array($candidates)) {
            return '';
        }

        foreach ($candidates as $candidate) {
            if (!is_array($candidate)) {
                continue;
            }

            $parts = $candidate['content']['parts'] ?? null;
            if (!is_array($parts)) {
                continue;
            }

            $chunks = [];
            foreach ($parts as $part) {
                if (is_array($part) && is_string($part['text'] ?? null)) {
                    $chunks[] = $part['text'];
                }
            }

            if ($chunks !== []) {
                return implode("\n", $chunks);
            }
        }

        return '';
    }

    /**
     * @return array<int, string>
     */
    private function parseSuggestions(string $raw): array
    {
        $raw = trim($raw);
        if ($raw === '') {
            return [];
        }

        if (str_starts_with($raw, '```')) {
            $raw = trim(preg_replace('/^```(?:json)?\s*|\s*```$/i', '', $raw) ?? $raw);
        }

        $decoded = json_decode($raw, true);
        if (is_array($decoded)) {
            $list = $decoded['suggestions'] ?? null;
            if (is_array($list)) {
                return array_values(array_filter(array_map('strval', $list), static fn (string $v): bool => trim($v) !== ''));
            }
        }

        $rawNoTrailingComma = preg_replace('/,\s*([}\]])/m', '$1', $raw) ?? $raw;
        $decoded = json_decode($rawNoTrailingComma, true);
        if (is_array($decoded) && is_array($decoded['suggestions'] ?? null)) {
            return array_values(array_filter(array_map('strval', $decoded['suggestions']), static fn (string $v): bool => trim($v) !== ''));
        }

        if (preg_match('/\{.*\}/s', $raw, $matches) === 1) {
            $decoded = json_decode($matches[0], true);
            if (is_array($decoded) && is_array($decoded['suggestions'] ?? null)) {
                return array_values(array_filter(array_map('strval', $decoded['suggestions']), static fn (string $v): bool => trim($v) !== ''));
            }
        }

        if (preg_match('/"suggestions"\s*:\s*\[(.*?)\]/si', $raw, $m) === 1) {
            preg_match_all('/"((?:[^"\\\\]|\\\\.)*)"/', $m[1], $strMatches);
            $items = [];
            foreach ($strMatches[1] ?? [] as $jsonString) {
                $decodedString = json_decode('"' . $jsonString . '"');
                if (is_string($decodedString) && trim($decodedString) !== '') {
                    $items[] = $decodedString;
                }
            }

            return $items;
        }

        $lines = preg_split('/\r?\n/', $raw) ?: [];
        $items = [];
        foreach ($lines as $line) {
            $line = trim($line);
            $line = preg_replace('/^[-*\d\.\)\s]+/u', '', $line) ?? '';
            $line = trim($line, " \t\n\r\0\x0B\"'");

            if ($line === '') {
                continue;
            }
            if (str_contains($line, '{') || str_contains($line, '}') || str_contains($line, '[') || str_contains($line, ']')) {
                continue;
            }
            if (stripos($line, 'json') !== false || stripos($line, 'here is') === 0) {
                continue;
            }

            $items[] = $line;
        }

        return $items;
    }

    /**
     * @param array<int, string> $suggestions
     *
     * @return array<int, string>
     */
    private function normalizeSuggestions(array $suggestions, string $text): array
    {
        $result = [];
        $seen = [];

        foreach ($suggestions as $suggestion) {
            $suggestion = trim($suggestion);
            if ($suggestion === '') {
                continue;
            }

            $suggestion = $this->mergeWithInput($text, $suggestion);
            if ($suggestion === '') {
                continue;
            }

            $key = mb_strtolower($suggestion);
            if (isset($seen[$key])) {
                continue;
            }
            $seen[$key] = true;
            $result[] = $suggestion;
        }

        return $result;
    }

    private function mergeWithInput(string $input, string $suggestion): string
    {
        $input = trim($input);
        $suggestion = trim($suggestion);
        if ($input === '' || $suggestion === '') {
            return $suggestion;
        }

        if (str_starts_with(mb_strtolower($suggestion), mb_strtolower($input))) {
            return $suggestion;
        }

        $maxOverlap = min(mb_strlen($input), mb_strlen($suggestion));
        for ($len = $maxOverlap; $len >= 1; --$len) {
            $inputSuffix = mb_substr($input, -$len);
            $suggestionPrefix = mb_substr($suggestion, 0, $len);

            if (mb_strtolower($inputSuffix) === mb_strtolower($suggestionPrefix)) {
                return $input . mb_substr($suggestion, $len);
            }
        }

        return rtrim($input) . ' ' . ltrim($suggestion);
    }

}
