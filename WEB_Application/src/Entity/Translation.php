<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'translation')]
#[ORM\Index(columns: ['object_type', 'object_id'])]
#[ORM\UniqueConstraint(name: 'uniq_object_translation', columns: ['object_type', 'object_id', 'locale', 'field'])]
class Translation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    public int $id;

    #[ORM\Column(name: 'object_type', length: 64)]
    public string $objectType;

    #[ORM\Column(name: 'object_id', length: 64)]
    public string $objectId;

    #[ORM\Column(length: 10)]
    public string $locale;

    #[ORM\Column(length: 64)]
    public string $field;

    #[ORM\Column(type: 'text')]
    public string $value;
}
