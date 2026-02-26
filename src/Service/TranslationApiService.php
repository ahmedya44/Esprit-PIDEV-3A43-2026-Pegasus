<?php

namespace App\Service;

use Symfony\Contracts\HttpClient\Exception\ExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class TranslationApiService
{
    /**
     * @var array<int, string>
     */
    private const KNOWN_LOCALES = ['fr', 'en', 'es', 'de', 'it', 'ar'];

    public function __construct(
        private HttpClientInterface $httpClient,
    ) {
    }

    public function translate(string $text, string $targetLocale, string $sourceLocale = 'auto'): ?string
    {
        $text = trim($text);
        if ($text == '') {
            return '';
        }

        $targetLocale = $this->normalizeLocale($targetLocale) ?? 'en';
        $detectedSource = $this->detectSourceLocale($text, $sourceLocale);
        if ($this->sameLanguage($detectedSource, $targetLocale)) {
            return $text;
        }

        $sourceCandidates = $this->buildSourceCandidates($detectedSource);

        try {
            foreach ($sourceCandidates as $candidate) {
                if ($this->sameLanguage($candidate, $targetLocale)) {
                    continue;
                }

                $data = $this->requestTranslation($text, $candidate, $targetLocale);
                $translated = $this->extractTranslatedText($data);

                if ($translated !== null) {
                    return $translated;
                }
            }
        } catch (ExceptionInterface|\Throwable) {
            return null;
        }

        return $text;
    }

    /**
     * @return array<string, mixed>
     */
    private function requestTranslation(string $text, string $sourceLocale, string $targetLocale): array
    {
        $response = $this->httpClient->request('GET', 'https://api.mymemory.translated.net/get', [
            'query' => [
                'q' => $text,
                'langpair' => sprintf('%s|%s', $sourceLocale, $targetLocale),
            ],
        ]);

        return $response->toArray(false);
    }

    /**
     * @return array<int, string>
     */
    private function buildSourceCandidates(string $sourceLocale): array
    {
        $sources = [$sourceLocale];
        foreach (self::KNOWN_LOCALES as $locale) {
            if (!in_array($locale, $sources, true)) {
                $sources[] = $locale;
            }
        }

        return $sources;
    }

    private function normalizeLocale(string $locale): ?string
    {
        $locale = trim($locale);
        if ($locale === '' || strtolower($locale) === 'auto') {
            return null;
        }

        if (!preg_match('/^[a-z]{2}(?:-[a-z]{2})?$/i', $locale)) {
            return null;
        }

        return strtolower($locale);
    }

    private function sameLanguage(string $a, string $b): bool
    {
        return explode('-', strtolower($a))[0] === explode('-', strtolower($b))[0];
    }

    private function detectSourceLocale(string $text, string $sourceLocale): string
    {
        $hint = $this->normalizeLocale($sourceLocale);
        if ($hint !== null) {
            return $hint;
        }

        if (preg_match('/[\x{0600}-\x{06FF}]/u', $text) === 1) {
            return 'ar';
        }

        $sample = ' ' . mb_strtolower(strip_tags($text)) . ' ';

        $weights = [
            'fr' => [' le ', ' la ', ' les ', ' des ', ' un ', ' une ', ' est ', ' et ', ' que ', ' pour '],
            'en' => [' the ', ' and ', ' is ', ' are ', ' this ', ' that ', ' with ', ' for ', ' you ', ' not '],
            'es' => [' el ', ' la ', ' los ', ' las ', ' que ', ' de ', ' y ', ' para ', ' con ', ' una '],
            'de' => [' der ', ' die ', ' das ', ' und ', ' ist ', ' mit ', ' nicht ', ' ein ', ' eine ', ' zu '],
            'it' => [' il ', ' la ', ' lo ', ' gli ', ' che ', ' e ', ' per ', ' con ', ' una ', ' non '],
        ];

        $scores = [];
        foreach ($weights as $locale => $terms) {
            $score = 0;
            foreach ($terms as $term) {
                $score += substr_count($sample, $term);
            }
            $scores[$locale] = $score;
        }

        arsort($scores);
        $bestLocale = (string) array_key_first($scores);
        $bestScore = $scores[$bestLocale] ?? 0;

        return $bestScore > 0 ? $bestLocale : 'en';
    }

    /**
     * @param array<string, mixed> $data
     */
    private function extractTranslatedText(array $data): ?string
    {
        $details = (string) ($data['responseDetails'] ?? '');
        if (stripos($details, 'invalid source language') !== false) {
            return null;
        }

        $translated = $data['responseData']['translatedText'] ?? null;
        if (!is_string($translated)) {
            return null;
        }

        $translated = trim($translated);
        if ($translated === '') {
            return null;
        }

        if (stripos($translated, 'invalid source language') !== false) {
            return null;
        }
        if (stripos($translated, 'please select two distinct languages') !== false) {
            return null;
        }

        return $translated;
    }
}
