<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224123000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add allowed viewers relation for hidden forum posts';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_allowed_viewer')) {
            $this->addSql('CREATE TABLE forum_post_allowed_viewer (post_id INT NOT NULL, user_id INT NOT NULL, INDEX IDX_8A6B18A24B89032C (post_id), INDEX IDX_8A6B18A2A76ED395 (user_id), PRIMARY KEY(post_id, user_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
            $this->addSql('ALTER TABLE forum_post_allowed_viewer ADD CONSTRAINT FK_8A6B18A24B89032C FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE');
            $this->addSql('ALTER TABLE forum_post_allowed_viewer ADD CONSTRAINT FK_8A6B18A2A76ED395 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE');
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_post_allowed_viewer')) {
            $this->addSql('DROP TABLE forum_post_allowed_viewer');
        }
    }
}
