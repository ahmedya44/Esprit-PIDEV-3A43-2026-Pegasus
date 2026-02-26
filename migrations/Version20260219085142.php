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
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE art_view (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ip_address VARCHAR(45) NOT NULL, viewed_at DATETIME NOT NULL, art_id INTEGER NOT NULL, CONSTRAINT FK_F7DDDD118C25E51A FOREIGN KEY (art_id) REFERENCES art (id) NOT DEFERRABLE INITIALLY IMMEDIATE)');
        $this->addSql('CREATE INDEX IDX_F7DDDD118C25E51A ON art_view (art_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE art_view');
    }
}
