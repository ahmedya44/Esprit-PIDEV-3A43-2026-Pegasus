<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260224091512 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE art ADD COLUMN ai_generated_image VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE art ADD COLUMN is_ai_generated BOOLEAN DEFAULT 0 NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TEMPORARY TABLE __temp__art AS SELECT id, title, description, title_en, description_en, image_url, status, created_at FROM art');
        $this->addSql('DROP TABLE art');
        $this->addSql('CREATE TABLE art (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description CLOB NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en CLOB DEFAULT NULL, image_url VARCHAR(500) NOT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL)');
        $this->addSql('INSERT INTO art (id, title, description, title_en, description_en, image_url, status, created_at) SELECT id, title, description, title_en, description_en, image_url, status, created_at FROM __temp__art');
        $this->addSql('DROP TABLE __temp__art');
    }
}
