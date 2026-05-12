<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260512100000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add nullable user ownership to art comments while keeping legacy usernames';
    }

    public function up(Schema $schema): void
    {
        $table = $schema->getTable('art_comment');

        if (!$table->hasColumn('user_id')) {
            $this->addSql('ALTER TABLE art_comment ADD user_id INT DEFAULT NULL');
            $this->addSql('ALTER TABLE art_comment ADD CONSTRAINT FK_art_comment_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE SET NULL');
            $this->addSql('CREATE INDEX IDX_art_comment_user ON art_comment (user_id)');
        }

        if ($table->hasColumn('username')) {
            $this->addSql("ALTER TABLE art_comment MODIFY username VARCHAR(100) DEFAULT NULL");
        }

        if (!$table->hasForeignKey('FK_art_comment_art') && !$table->hasForeignKey('art_comment_ibfk_1')) {
            $this->addSql('ALTER TABLE art_comment ADD CONSTRAINT FK_art_comment_art FOREIGN KEY (art_id) REFERENCES art (id) ON DELETE CASCADE');
        }
    }

    public function down(Schema $schema): void
    {
        $table = $schema->getTable('art_comment');

        if ($table->hasColumn('user_id')) {
            $this->addSql('ALTER TABLE art_comment DROP FOREIGN KEY FK_art_comment_user');
            $this->addSql('DROP INDEX IDX_art_comment_user ON art_comment');
            $this->addSql('ALTER TABLE art_comment DROP COLUMN user_id');
        }
    }
}
