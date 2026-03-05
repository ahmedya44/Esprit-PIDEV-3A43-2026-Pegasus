<?php

declare(strict_types=1);

namespace App\Tests\Service;

use App\Service\AIService;
use PHPUnit\Framework\TestCase;
use Symfony\Contracts\HttpClient\HttpClientInterface;

final class AIServiceTest extends TestCase
{
    // Image generation should return a deterministic Picsum URL format.
    public function testGenerateImageReturnsPicsumUrl(): void
    {
        $client = $this->createMock(HttpClientInterface::class);
        $service = new AIService($client);

        $url = $service->generateImage('Art title', 'A nice art description');

        self::assertIsString($url);
        self::assertStringStartsWith('https://picsum.photos/512/512?random=', $url);
    }

    // Image analysis should always provide title + description keys.
    public function testAnalyzeImageReturnsTitleAndDescription(): void
    {
        $client = $this->createMock(HttpClientInterface::class);
        $service = new AIService($client);

        $result = $service->analyzeImage('https://example.com/image.jpg');

        self::assertIsArray($result);
        self::assertArrayHasKey('title', $result);
        self::assertArrayHasKey('description', $result);
        self::assertNotSame('', trim((string) $result['title']));
        self::assertNotSame('', trim((string) $result['description']));
    }
}
