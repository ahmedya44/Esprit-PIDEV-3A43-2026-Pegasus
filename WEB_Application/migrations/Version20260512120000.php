<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260512120000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Ensure art_comment user FK constraint and index';
    }

    public function up(Schema $schema): void
    {
        $table = $schema->getTable('art_comment');

        if (!$table->hasColumn('user_id')) {
            $this->addSql('ALTER TABLE art_comment ADD user_id INT DEFAULT NULL');
        }

        $this->addSql('UPDATE art_comment SET user_id = NULL WHERE user_id IS NOT NULL AND user_id NOT IN (SELECT id FROM `user`)');

        if (!$table->hasForeignKey('FK_art_comment_user')) {
            $this->addSql('ALTER TABLE art_comment ADD CONSTRAINT FK_art_comment_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE SET NULL');
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
