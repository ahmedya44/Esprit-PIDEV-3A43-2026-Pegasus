<?php

declare(strict_types=1);

namespace App\Service;

final class ObjectTranslatorCompat
{
    public function translate(object $object, string $locale): object
    {
        return $object;
    }
}
