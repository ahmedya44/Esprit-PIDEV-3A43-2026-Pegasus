<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260219085142 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        if ($schema->hasTable('art_view')) {
            return;
        }

        $platform = $this->connection->getDatabasePlatform()->getName();
        if ($platform === 'sqlite') {
            $this->addSql('CREATE TABLE art_view (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ip_address VARCHAR(45) NOT NULL, viewed_at DATETIME NOT NULL, art_id INTEGER NOT NULL, CONSTRAINT FK_F7DDDD118C25E51A FOREIGN KEY (art_id) REFERENCES art (id) NOT DEFERRABLE INITIALLY IMMEDIATE)');
            $this->addSql('CREATE INDEX IDX_F7DDDD118C25E51A ON art_view (art_id)');

            return;
        }

        $this->addSql('CREATE TABLE art_view (id INT AUTO_INCREMENT NOT NULL, art_id INT NOT NULL, ip_address VARCHAR(45) NOT NULL, viewed_at DATETIME NOT NULL, INDEX IDX_F7DDDD118C25E51A (art_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');

        if ($schema->hasTable('art')) {
            $this->addSql('ALTER TABLE art_view ADD CONSTRAINT FK_F7DDDD118C25E51A FOREIGN KEY (art_id) REFERENCES art (id)');
        }
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE art_view');
    }
}
