<?php

declare(strict_types=1);

namespace App\Service;

use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Contracts\HttpClient\HttpClientInterface;

final class CloudflareStylizerService
{
    private const MODEL = '@cf/runwayml/stable-diffusion-v1-5-img2img';

    private const STYLE_PROMPTS = [
        'anime' => 'Anime portrait style, keep the same person identity and facial structure, clean line art, cel shading.',
        'comic' => 'Comic book portrait style, keep same identity, inked outlines, graphic novel rendering.',
        'pixar' => '3D cartoon movie portrait style, keep same identity, expressive lighting and polished render.',
    ];

    public function __construct(private readonly HttpClientInterface $httpClient)
    {
    }

    public function generateStyle(UploadedFile $sourceImage, string $style): string
    {
        $style = strtolower(trim($style));
        if (!isset(self::STYLE_PROMPTS[$style])) {
            throw new \InvalidArgumentException('Invalid style.');
        }

        $accountId = $this->readEnv('CLOUDFLARE_ACCOUNT_ID');
        $token = $this->readEnv('CLOUDFLARE_API_TOKEN');
        if ('' === $accountId || '' === $token) {
            throw new \RuntimeException('CLOUDFLARE_ACCOUNT_ID or CLOUDFLARE_API_TOKEN is missing.');
        }

        $imageBase64 = $this->prepareBase64Image($sourceImage);
        $url = sprintf(
            'https://api.cloudflare.com/client/v4/accounts/%s/ai/run/%s',
            rawurlencode($accountId),
            self::MODEL
        );

        try {
            $response = $this->httpClient->request('POST', $url, [
                'headers' => [
                    'Authorization' => 'Bearer '.$token,
                    'Content-Type' => 'application/json',
                    'Accept' => 'image/png',
                ],
                'json' => [
                    'prompt' => self::STYLE_PROMPTS[$style],
                    'negative_prompt' => 'blurry, low quality, distorted face, extra eyes, watermark, text',
                    'image_b64' => $imageBase64,
                    'width' => 512,
                    'height' => 512,
                    'num_steps' => 16,
                    'strength' => 0.65,
                    'guidance' => 7.5,
                ],
                'timeout' => 70,
                'max_duration' => 85,
            ]);
        } catch (\Throwable) {
            throw new \RuntimeException('Network timeout while contacting Cloudflare AI.');
        }

        $statusCode = $response->getStatusCode();
        $headers = $response->getHeaders(false);
        $contentType = strtolower((string) ($headers['content-type'][0] ?? ''));
        $body = $response->getContent(false);

        if ($statusCode >= 200 && $statusCode < 300 && str_starts_with($contentType, 'image/')) {
            if ('' === $body) {
                throw new \RuntimeException('Cloudflare returned an empty image.');
            }

            return $body;
        }

        $errorDetail = $this->extractErrorFromJsonBody($body);

        if (401 === $statusCode || 403 === $statusCode) {
            throw new \RuntimeException('Invalid Cloudflare API token or account permissions.');
        }
        if (429 === $statusCode) {
            throw new \RuntimeException('Cloudflare rate limit or free neurons exhausted.');
        }
        if ($statusCode >= 500) {
            throw new \RuntimeException('Cloudflare AI server error.');
        }

        throw new \RuntimeException('Cloudflare AI request failed'.('' !== $errorDetail ? ': '.$errorDetail : '.'));
    }

    private function readEnv(string $key): string
    {
        $value = $_ENV[$key] ?? $_SERVER[$key] ?? '';

        return trim((string) $value);
    }

    private function extractErrorFromJsonBody(string $body): string
    {
        $decoded = json_decode($body, true);
        if (!is_array($decoded)) {
            return '';
        }

        $errors = $decoded['errors'] ?? null;
        if (is_array($errors) && isset($errors[0]) && is_array($errors[0])) {
            $message = (string) ($errors[0]['message'] ?? '');
            if ('' !== $message) {
                return $message;
            }
        }

        return (string) ($decoded['result']['error'] ?? $decoded['error'] ?? '');
    }

    private function prepareBase64Image(UploadedFile $sourceImage): string
    {
        $raw = @file_get_contents($sourceImage->getPathname());
        if (false === $raw || '' === $raw) {
            throw new \RuntimeException('Could not read source image.');
        }

        if (strlen($raw) <= 1500000) {
            return base64_encode($raw);
        }

        if (!function_exists('imagecreatefromstring')) {
            throw new \RuntimeException('GD extension is required for large image resizing.');
        }

        $img = @imagecreatefromstring($raw);
        if (false === $img) {
            throw new \RuntimeException('Invalid image format.');
        }

        $w = imagesx($img);
        $h = imagesy($img);
        $targetMax = 768;
        $scale = min($targetMax / max($w, $h), 1.0);
        $nw = max(64, (int) floor($w * $scale));
        $nh = max(64, (int) floor($h * $scale));

        $resized = imagecreatetruecolor($nw, $nh);
        imagecopyresampled($resized, $img, 0, 0, 0, 0, $nw, $nh, $w, $h);

        ob_start();
        imagejpeg($resized, null, 82);
        $jpg = (string) ob_get_clean();

        imagedestroy($img);
        imagedestroy($resized);

        if ('' === $jpg) {
            throw new \RuntimeException('Could not compress image.');
        }

        return base64_encode($jpg);
    }
}
