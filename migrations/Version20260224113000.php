<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224113000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Align user table with user-branch model (username, status, dtype, tokens, profile fields)';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('user')) {
            return;
        }

        $table = $schema->getTable('user');

        if ($table->hasColumn('display_name') && !$table->hasColumn('username')) {
            $this->addSql('ALTER TABLE `user` CHANGE display_name username VARCHAR(180) NOT NULL');
        } elseif (!$table->hasColumn('username')) {
            $this->addSql('ALTER TABLE `user` ADD username VARCHAR(180) NOT NULL DEFAULT \'user\'');
        }

        if ($table->hasColumn('is_active')) {
            $this->addSql('ALTER TABLE `user` DROP is_active');
        }

        if (!$table->hasColumn('phone')) {
            $this->addSql('ALTER TABLE `user` ADD phone VARCHAR(30) DEFAULT NULL');
        }

        if (!$table->hasColumn('avatar_url')) {
            $this->addSql('ALTER TABLE `user` ADD avatar_url VARCHAR(255) DEFAULT NULL');
        }

        if (!$table->hasColumn('created_at')) {
            $this->addSql('ALTER TABLE `user` ADD created_at DATETIME DEFAULT NULL');
            $this->addSql('UPDATE `user` SET created_at = NOW() WHERE created_at IS NULL');
            $this->addSql('ALTER TABLE `user` CHANGE created_at created_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\'');
        }

        if (!$table->hasColumn('status')) {
            $this->addSql('ALTER TABLE `user` ADD status VARCHAR(255) NOT NULL DEFAULT \'PENDING\'');
        }

        if (!$table->hasColumn('dtype')) {
            $this->addSql('ALTER TABLE `user` ADD dtype VARCHAR(255) NOT NULL DEFAULT \'normal\'');
        }

        if (!$table->hasColumn('reset_token')) {
            $this->addSql('ALTER TABLE `user` ADD reset_token VARCHAR(64) DEFAULT NULL');
        }

        if (!$table->hasColumn('reset_token_expires_at')) {
            $this->addSql('ALTER TABLE `user` ADD reset_token_expires_at DATETIME DEFAULT NULL COMMENT \'(DC2Type:datetime_immutable)\'');
        }

        if (!$table->hasColumn('email_verification_token')) {
            $this->addSql('ALTER TABLE `user` ADD email_verification_token VARCHAR(64) DEFAULT NULL');
        }

        if (!$table->hasColumn('email_verification_token_expires_at')) {
            $this->addSql('ALTER TABLE `user` ADD email_verification_token_expires_at DATETIME DEFAULT NULL COMMENT \'(DC2Type:datetime_immutable)\'');
        }

        $table = $schema->getTable('user');
        if (!$table->hasIndex('UNIQ_8D93D6491C0C4FCF')) {
            $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D6491C0C4FCF ON `user` (reset_token)');
        }
        if (!$table->hasIndex('UNIQ_8D93D649D7B5A66B')) {
            $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D649D7B5A66B ON `user` (email_verification_token)');
        }
    }

    public function down(Schema $schema): void
    {
        if (!$schema->hasTable('user')) {
            return;
        }

        $table = $schema->getTable('user');
        if ($table->hasIndex('UNIQ_8D93D6491C0C4FCF')) {
            $this->addSql('DROP INDEX UNIQ_8D93D6491C0C4FCF ON `user`');
        }
        if ($table->hasIndex('UNIQ_8D93D649D7B5A66B')) {
            $this->addSql('DROP INDEX UNIQ_8D93D649D7B5A66B ON `user`');
        }

        if ($table->hasColumn('email_verification_token_expires_at')) {
            $this->addSql('ALTER TABLE `user` DROP email_verification_token_expires_at');
        }
        if ($table->hasColumn('email_verification_token')) {
            $this->addSql('ALTER TABLE `user` DROP email_verification_token');
        }
        if ($table->hasColumn('reset_token_expires_at')) {
            $this->addSql('ALTER TABLE `user` DROP reset_token_expires_at');
        }
        if ($table->hasColumn('reset_token')) {
            $this->addSql('ALTER TABLE `user` DROP reset_token');
        }
        if ($table->hasColumn('dtype')) {
            $this->addSql('ALTER TABLE `user` DROP dtype');
        }
        if ($table->hasColumn('status')) {
            $this->addSql('ALTER TABLE `user` DROP status');
        }
        if ($table->hasColumn('created_at')) {
            $this->addSql('ALTER TABLE `user` DROP created_at');
        }
        if ($table->hasColumn('avatar_url')) {
            $this->addSql('ALTER TABLE `user` DROP avatar_url');
        }
        if ($table->hasColumn('phone')) {
            $this->addSql('ALTER TABLE `user` DROP phone');
        }
        if ($table->hasColumn('username') && !$table->hasColumn('display_name')) {
            $this->addSql('ALTER TABLE `user` CHANGE username display_name VARCHAR(120) NOT NULL');
        }
        if (!$table->hasColumn('is_active')) {
            $this->addSql('ALTER TABLE `user` ADD is_active TINYINT(1) NOT NULL DEFAULT 1');
        }
    }
}

