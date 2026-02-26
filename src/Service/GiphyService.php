<?php

namespace App\Service;

use GPH\Api\DefaultApi;
use GPH\ApiException;
use GPH\Model\Gif;
use GPH\Model\GifImages;
use GPH\Model\InlineResponse200;
use Symfony\Contracts\HttpClient\Exception\ExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class GiphyService
{
    private DefaultApi $api;

    public function __construct(
        private HttpClientInterface $httpClient,
        private string $provider,
        private string $klipyApiKey,
        private string $klipyClientKey,
        private string $klipyApiUrl,
        private string $giphyApiKey,
        private string $rating,
    ) {
        $this->provider = strtolower(trim($this->provider));
        $this->api = new DefaultApi();
    }

    /**
     * @return array<int, array{url: string, preview: string, title: string}>
     */
    public function search(string $query, int $limit = 16): array
    {
        $limit = max(1, min(25, $limit));
        $query = trim($query);

        if ($this->provider === 'klipy') {
            return $this->searchKlipy($query, $limit);
        }

        if ($this->provider === 'giphy') {
            return $this->searchGiphy($query, $limit);
        }

        if ($this->klipyApiKey !== '') {
            return $this->searchKlipy($query, $limit);
        }

        if ($this->giphyApiKey !== '') {
            return $this->searchGiphy($query, $limit);
        }

        return [];
    }

    /**
     * @return array<int, array{url: string, preview: string, title: string}>
     */
    private function searchGiphy(string $query, int $limit): array
    {
        if ($this->giphyApiKey === '') {
            return [];
        }

        try {
            $result = $query === ''
                ? $this->api->gifsTrendingGet($this->giphyApiKey, $limit, $this->rating, 'json')
                : $this->api->gifsSearchGet(
                    $this->giphyApiKey,
                    $query,
                    $limit,
                    0,
                    $this->rating,
                    'fr',
                    'json'
                );

            return $this->mapResponse($result);
        } catch (ApiException|\Throwable) {
            return [];
        }
    }

    /**
     * @return array<int, array{url: string, preview: string, title: string}>
     */
    private function searchKlipy(string $query, int $limit): array
    {
        if ($this->klipyApiKey === '') {
            return [];
        }

        $endpoint = $query === '' ? '/featured' : '/search';
        $params = [
            'key' => $this->klipyApiKey,
            'client_key' => $this->klipyClientKey !== '' ? $this->klipyClientKey : 'pegasus_forum',
            'limit' => max(1, min(20, $limit)),
            'media_filter' => 'gif,tinygif',
            'contentfilter' => $this->klipyContentFilter(),
        ];

        if ($query !== '') {
            $params['q'] = $query;
        }

        try {
            $response = $this->httpClient->request('GET', rtrim($this->klipyApiUrl, '/') . $endpoint, [
                'query' => $params,
                'timeout' => 8,
            ]);

            $payload = $response->toArray(false);
            $results = is_array($payload['results'] ?? null) ? $payload['results'] : [];
            $items = [];

            foreach ($results as $result) {
                if (!is_array($result)) {
                    continue;
                }

                $mediaFormats = is_array($result['media_formats'] ?? null) ? $result['media_formats'] : [];
                $gif = is_array($mediaFormats['gif'] ?? null) ? $mediaFormats['gif'] : [];
                $tiny = is_array($mediaFormats['tinygif'] ?? null) ? $mediaFormats['tinygif'] : [];

                $url = (string) ($gif['url'] ?? '');
                if ($url === '') {
                    $url = (string) ($tiny['url'] ?? '');
                }
                if ($url === '') {
                    continue;
                }

                $preview = (string) ($tiny['url'] ?? $url);
                $title = trim((string) ($result['content_description'] ?? $result['title'] ?? 'GIF'));
                if ($title === '') {
                    $title = 'GIF';
                }

                $items[] = [
                    'url' => $url,
                    'preview' => $preview !== '' ? $preview : $url,
                    'title' => $title,
                ];
            }

            return $items;
        } catch (ExceptionInterface|\Throwable) {
            return [];
        }
    }

    private function klipyContentFilter(): string
    {
        return match (strtolower(trim($this->rating))) {
            'g', 'pg' => 'low',
            'r', 'nc-17' => 'high',
            default => 'medium',
        };
    }

    /**
     * @return array<int, array{url: string, preview: string, title: string}>
     */
    private function mapResponse(mixed $result): array
    {
        if (!$result instanceof InlineResponse200) {
            return [];
        }

        $rows = $result->getData();
        if (!is_array($rows)) {
            return [];
        }

        $items = [];

        foreach ($rows as $gif) {
            if (!$gif instanceof Gif) {
                continue;
            }

            $images = $gif->getImages();
            $originalUrl = $this->extractOriginalUrl($gif, $images);
            if ($originalUrl === '') {
                continue;
            }

            $previewUrl = $this->extractPreviewUrl($images);

            $items[] = [
                'url' => $originalUrl,
                'preview' => $previewUrl !== '' ? $previewUrl : $originalUrl,
                'title' => $this->extractTitle($gif),
            ];
        }

        return $items;
    }

    private function extractOriginalUrl(Gif $gif, mixed $images): string
    {
        if ($images instanceof GifImages) {
            $original = $images->getOriginal();
            if (is_object($original) && method_exists($original, 'getUrl')) {
                $url = (string) $original->getUrl();
                if ($url !== '') {
                    return $url;
                }
            }
        }

        return (string) $gif->getUrl();
    }

    private function extractPreviewUrl(mixed $images): string
    {
        if (!$images instanceof GifImages) {
            return '';
        }

        $fixedWidthSmall = $images->getFixedWidthSmall();
        if (is_object($fixedWidthSmall) && method_exists($fixedWidthSmall, 'getUrl')) {
            return (string) $fixedWidthSmall->getUrl();
        }

        $preview = $images->getPreviewGif();
        if (is_object($preview) && method_exists($preview, 'getUrl')) {
            return (string) $preview->getUrl();
        }

        return '';
    }

    private function extractTitle(Gif $gif): string
    {
        $slug = trim((string) $gif->getSlug());
        if ($slug !== '') {
            return str_replace('-', ' ', $slug);
        }

        $username = trim((string) $gif->getUsername());
        if ($username !== '') {
            return $username;
        }

        return 'GIF';
    }
}
