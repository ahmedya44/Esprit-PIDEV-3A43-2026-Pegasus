<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260226193000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add passkey credentials table for WebAuthn login';
    }

    public function up(Schema $schema): void
    {
        $this->addSql('CREATE TABLE passkey_credential (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, credential_id VARCHAR(255) NOT NULL, public_key_pem LONGTEXT NOT NULL, sign_count INT NOT NULL, transports VARCHAR(255) DEFAULT NULL, label VARCHAR(120) DEFAULT NULL, created_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', last_used_at DATETIME DEFAULT NULL COMMENT \'(DC2Type:datetime_immutable)\', UNIQUE INDEX uniq_passkey_credential_id (credential_id), INDEX IDX_2EAA5890A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE passkey_credential ADD CONSTRAINT FK_2EAA5890A76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        $this->addSql('DROP TABLE passkey_credential');
    }
}
