<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260512120000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'art_comment: clean orphan rows, add user_id FK constraint and index';
    }

    public function up(Schema $schema): void
    {
        // Remove rows whose user_id doesn't reference a real user (e.g. rows with user_id = 0)
        $this->addSql('DELETE FROM art_comment WHERE user_id NOT IN (SELECT id FROM `user`)');

        $table = $schema->getTable('art_comment');

        if (!$table->hasForeignKey('FK_art_comment_user')) {
            $this->addSql('ALTER TABLE art_comment ADD CONSTRAINT FK_art_comment_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE');
        }

        if (!$table->hasIndex('IDX_art_comment_user')) {
            $this->addSql('CREATE INDEX IDX_art_comment_user ON art_comment (user_id)');
        }
    }

    public function down(Schema $schema): void
    {
        $table = $schema->getTable('art_comment');

        if ($table->hasForeignKey('FK_art_comment_user')) {
            $this->addSql('ALTER TABLE art_comment DROP FOREIGN KEY FK_art_comment_user');
        }

        if ($table->hasIndex('IDX_art_comment_user')) {
            $this->addSql('DROP INDEX IDX_art_comment_user ON art_comment');
        }
    }
}
