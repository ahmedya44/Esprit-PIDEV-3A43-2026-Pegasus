<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224100000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Allow half-star ratings by storing forum_post_rating.value as decimal(2,1)';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_rating')) {
            return;
        }

        $this->addSql('ALTER TABLE forum_post_rating MODIFY value NUMERIC(2, 1) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_rating')) {
            return;
        }

        $this->addSql('ALTER TABLE forum_post_rating MODIFY value SMALLINT NOT NULL');
    }
}
