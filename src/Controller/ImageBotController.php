<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class ImageBotController extends AbstractController
{
    #[Route('/api/image-bot/generate', name: 'api_image_bot_generate', methods: ['POST'])]
    public function generate(Request $request, HttpClientInterface $http): JsonResponse
    {
        $data = json_decode($request->getContent(), true) ?? [];
        $prompt = trim((string)($data['prompt'] ?? ''));

        if ($prompt === '') {
            return $this->json(['error' => 'Prompt is required'], 400);
        }

        $apiKey = $_ENV['IMAGE_API_KEY'] ?? null;
        $apiUrl = $_ENV['IMAGE_API_URL'] ?? null;

        if (!$apiKey || !$apiUrl) {
            return $this->json(['error' => 'Server API key not configured'], 500);
        }

        try {
            $res = $http->request('POST', $apiUrl, [
                'headers' => [
                    'Authorization' => 'Bearer ' . $apiKey,
                    'Content-Type' => 'application/json',
                ],
                'json' => [
                    'prompt' => $prompt,
                ],
            ]);

            $statusCode = $res->getStatusCode();
            $headers = $res->getHeaders(false);
            $contentType = strtolower($headers['content-type'][0] ?? '');
            $body = $res->getContent(false);

            if (!str_contains($contentType, 'application/json')) {
                if ($statusCode >= 200 && $statusCode < 300 && $body !== '') {
                    return $this->json(['b64' => base64_encode($body)]);
                }

                return $this->json([
                    'error' => 'Image generation failed',
                    'details' => 'Non-JSON response from image provider',
                    'status' => $statusCode,
                ], 500);
            }

            $payload = json_decode($body, true);
            if (!is_array($payload)) {
                return $this->json([
                    'error' => 'Image generation failed',
                    'details' => 'Invalid JSON response from image provider',
                    'status' => $statusCode,
                ], 500);
            }

            $imageB64 = $payload['result']['image'] ?? null;

            if (is_string($imageB64) && $imageB64 !== '') {
                // Cloudflare may return raw image bytes; convert to base64 for JSON transport.
                if (!mb_check_encoding($imageB64, 'UTF-8')) {
                    $imageB64 = base64_encode($imageB64);
                }

                return $this->json(['b64' => $imageB64]);
            }

            return $this->json([
                'error' => 'No image returned',
                'meta' => [
                    'success' => $payload['success'] ?? null,
                    'errors' => $payload['errors'] ?? null,
                ],
            ], 500);

        } catch (\Throwable $e) {
            return $this->json(['error' => 'Image generation failed', 'details' => $e->getMessage()], 500);
        }
    }
}
