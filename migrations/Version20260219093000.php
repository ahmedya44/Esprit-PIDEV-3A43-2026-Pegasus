<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260219093000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add image_name column to forum_post for post picture upload';
    }

    public function up(Schema $schema): void
    {
        $postTable = $schema->getTable('forum_post');

        if (!$postTable->hasColumn('image_name')) {
            $postTable->addColumn('image_name', 'string', [
                'length' => 255,
                'notnull' => false,
            ]);
        }
    }

    public function down(Schema $schema): void
    {
        $postTable = $schema->getTable('forum_post');

        if ($postTable->hasColumn('image_name')) {
            $postTable->dropColumn('image_name');
        }
    }
}
