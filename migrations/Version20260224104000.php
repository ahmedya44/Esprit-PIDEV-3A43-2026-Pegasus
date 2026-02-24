<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224104000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Align forum_post_rating.value type with entity by switching to FLOAT';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_rating')) {
            return;
        }

        $this->addSql('ALTER TABLE forum_post_rating MODIFY value DOUBLE PRECISION NOT NULL');
    }

    public function down(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_rating')) {
            return;
        }

        $this->addSql('ALTER TABLE forum_post_rating MODIFY value NUMERIC(2, 1) NOT NULL');
    }
}

