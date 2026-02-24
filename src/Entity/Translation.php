<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use SymfonyCasts\ObjectTranslationBundle\Model\Translation as BaseTranslation;

#[ORM\Entity]
#[ORM\Table(name: 'translation')]
#[ORM\Index(columns: ['object_type', 'object_id'])]
#[ORM\UniqueConstraint(name: 'uniq_object_translation', columns: ['object_type', 'object_id', 'locale', 'field'])]
class Translation extends BaseTranslation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    public int $id;
}
