<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224103000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Create user table and link forum_post/forum_commentaire ownership to users';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('user')) {
            $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(180) NOT NULL, roles JSON NOT NULL, password VARCHAR(255) NOT NULL, display_name VARCHAR(120) NOT NULL, is_active TINYINT(1) NOT NULL, UNIQUE INDEX uniq_user_email (email), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        }

        if ($schema->hasTable('forum_post')) {
            $forumPost = $schema->getTable('forum_post');
            if (!$forumPost->hasColumn('owner_id')) {
                $this->addSql('ALTER TABLE forum_post ADD owner_id INT DEFAULT NULL');
            }
            if (!$forumPost->hasForeignKey('FK_D2B19BE67E3C61F9')) {
                $this->addSql('ALTER TABLE forum_post ADD CONSTRAINT FK_D2B19BE67E3C61F9 FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE SET NULL');
            }
            if (!$forumPost->hasIndex('IDX_D2B19BE67E3C61F9')) {
                $this->addSql('CREATE INDEX IDX_D2B19BE67E3C61F9 ON forum_post (owner_id)');
            }
        }

        if ($schema->hasTable('forum_commentaire')) {
            $forumComment = $schema->getTable('forum_commentaire');
            if (!$forumComment->hasColumn('owner_id')) {
                $this->addSql('ALTER TABLE forum_commentaire ADD owner_id INT DEFAULT NULL');
            }
            if (!$forumComment->hasForeignKey('FK_F37E3FAF7E3C61F9')) {
                $this->addSql('ALTER TABLE forum_commentaire ADD CONSTRAINT FK_F37E3FAF7E3C61F9 FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE SET NULL');
            }
            if (!$forumComment->hasIndex('IDX_F37E3FAF7E3C61F9')) {
                $this->addSql('CREATE INDEX IDX_F37E3FAF7E3C61F9 ON forum_commentaire (owner_id)');
            }
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_commentaire')) {
            $forumComment = $schema->getTable('forum_commentaire');
            if ($forumComment->hasForeignKey('FK_F37E3FAF7E3C61F9')) {
                $this->addSql('ALTER TABLE forum_commentaire DROP FOREIGN KEY FK_F37E3FAF7E3C61F9');
            }
            if ($forumComment->hasIndex('IDX_F37E3FAF7E3C61F9')) {
                $this->addSql('DROP INDEX IDX_F37E3FAF7E3C61F9 ON forum_commentaire');
            }
            if ($forumComment->hasColumn('owner_id')) {
                $this->addSql('ALTER TABLE forum_commentaire DROP owner_id');
            }
        }

        if ($schema->hasTable('forum_post')) {
            $forumPost = $schema->getTable('forum_post');
            if ($forumPost->hasForeignKey('FK_D2B19BE67E3C61F9')) {
                $this->addSql('ALTER TABLE forum_post DROP FOREIGN KEY FK_D2B19BE67E3C61F9');
            }
            if ($forumPost->hasIndex('IDX_D2B19BE67E3C61F9')) {
                $this->addSql('DROP INDEX IDX_D2B19BE67E3C61F9 ON forum_post');
            }
            if ($forumPost->hasColumn('owner_id')) {
                $this->addSql('ALTER TABLE forum_post DROP owner_id');
            }
        }
    }
}

