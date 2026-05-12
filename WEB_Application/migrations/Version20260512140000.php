<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260512140000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Align forum moderation columns with the existing is_banned schema';
    }

    public function up(Schema $schema): void
    {
        if ($schema->hasTable('forum_post')) {
            $postTable = $schema->getTable('forum_post');

            if (!$postTable->hasColumn('is_banned')) {
                $this->addSql('ALTER TABLE forum_post ADD is_banned TINYINT(1) NOT NULL DEFAULT 0');
            }

            if (!$postTable->hasColumn('request_type')) {
                $this->addSql("ALTER TABLE forum_post ADD request_type VARCHAR(16) NOT NULL DEFAULT 'CREATE'");
            }
        }

        if ($schema->hasTable('forum_commentaire')) {
            $commentTable = $schema->getTable('forum_commentaire');

            if (!$commentTable->hasColumn('is_banned')) {
                $this->addSql('ALTER TABLE forum_commentaire ADD is_banned TINYINT(1) NOT NULL DEFAULT 0');
            }
        }

        if ($schema->hasTable('user')) {
            $this->addSql("UPDATE `user` SET dtype = 'normal' WHERE dtype = 'normal_user'");
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_commentaire')) {
            $commentTable = $schema->getTable('forum_commentaire');

            if ($commentTable->hasColumn('is_banned')) {
                $this->addSql('ALTER TABLE forum_commentaire DROP is_banned');
            }
        }

        if ($schema->hasTable('forum_post')) {
            $postTable = $schema->getTable('forum_post');

            if ($postTable->hasColumn('request_type')) {
                $this->addSql('ALTER TABLE forum_post DROP request_type');
            }

            if ($postTable->hasColumn('is_banned')) {
                $this->addSql('ALTER TABLE forum_post DROP is_banned');
            }
        }
    }
}
