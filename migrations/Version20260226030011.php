<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260226030011 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE art_favoris (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, user_identifier VARCHAR(255) NOT NULL, added_at DATETIME NOT NULL, art_id INTEGER NOT NULL, CONSTRAINT FK_3D77C54E8C25E51A FOREIGN KEY (art_id) REFERENCES art (id) NOT DEFERRABLE INITIALLY IMMEDIATE)');
        $this->addSql('CREATE INDEX IDX_3D77C54E8C25E51A ON art_favoris (art_id)');
        $this->addSql('CREATE TEMPORARY TABLE __temp__art AS SELECT id, title, description, image_url, status, created_at, title_en, description_en, ai_generated_image, is_ai_generated FROM art');
        $this->addSql('DROP TABLE art');
        $this->addSql('CREATE TABLE art (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description CLOB NOT NULL, image_url VARCHAR(500) DEFAULT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en CLOB DEFAULT NULL, ai_generated_image VARCHAR(255) DEFAULT NULL, is_ai_generated BOOLEAN DEFAULT 0 NOT NULL)');
        $this->addSql('INSERT INTO art (id, title, description, image_url, status, created_at, title_en, description_en, ai_generated_image, is_ai_generated) SELECT id, title, description, image_url, status, created_at, title_en, description_en, ai_generated_image, is_ai_generated FROM __temp__art');
        $this->addSql('DROP TABLE __temp__art');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE art_favoris');
        $this->addSql('CREATE TEMPORARY TABLE __temp__art AS SELECT id, title, description, title_en, description_en, ai_generated_image, is_ai_generated, image_url, status, created_at FROM art');
        $this->addSql('DROP TABLE art');
        $this->addSql('CREATE TABLE art (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description CLOB NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en CLOB DEFAULT NULL, ai_generated_image VARCHAR(255) DEFAULT NULL, is_ai_generated BOOLEAN DEFAULT 0 NOT NULL, image_url VARCHAR(500) NOT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL)');
        $this->addSql('INSERT INTO art (id, title, description, title_en, description_en, ai_generated_image, is_ai_generated, image_url, status, created_at) SELECT id, title, description, title_en, description_en, ai_generated_image, is_ai_generated, image_url, status, created_at FROM __temp__art');
        $this->addSql('DROP TABLE __temp__art');
    }
}
