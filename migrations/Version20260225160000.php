<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260225160000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add optional gif_url to forum comments';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_commentaire')) {
            return;
        }

        $table = $schema->getTable('forum_commentaire');
        if (!$table->hasColumn('gif_url')) {
            $this->addSql('ALTER TABLE forum_commentaire ADD gif_url VARCHAR(500) DEFAULT NULL');
        }
    }

    public function down(Schema $schema): void
    {
        if (!$schema->hasTable('forum_commentaire')) {
            return;
        }

        $table = $schema->getTable('forum_commentaire');
        if ($table->hasColumn('gif_url')) {
            $this->addSql('ALTER TABLE forum_commentaire DROP gif_url');
        }
    }
}
