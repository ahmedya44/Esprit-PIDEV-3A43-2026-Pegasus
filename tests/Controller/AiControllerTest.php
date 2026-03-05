<?php

declare(strict_types=1);

namespace App\Tests\Controller;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

final class AiControllerTest extends WebTestCase
{
    // POST route is protected: anonymous users are redirected to login.
    public function testGenerateDescriptionRequiresArtistAuthentication(): void
    {
        $client = static::createClient();

        $client->request(
            'POST',
            '/ai/generate-description',
            server: ['CONTENT_TYPE' => 'application/json'],
            content: json_encode(['title' => 'Any title'], JSON_THROW_ON_ERROR)
        );

        self::assertResponseStatusCodeSame(302);
    }

    // Route only accepts POST; GET should be rejected.
    public function testGenerateDescriptionGetMethodIsNotAllowed(): void
    {
        $client = static::createClient();

        $client->request('GET', '/ai/generate-description');

        self::assertResponseStatusCodeSame(405);
    }
}
