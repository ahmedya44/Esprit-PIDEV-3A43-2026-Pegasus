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
        if (!$schema->hasTable('art')) {
            $this->addSql('CREATE TABLE art (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, image_url VARCHAR(500) DEFAULT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en LONGTEXT DEFAULT NULL, ai_generated_image VARCHAR(255) DEFAULT NULL, is_ai_generated TINYINT(1) DEFAULT 0 NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        }

        if (!$schema->hasTable('art_favoris')) {
            $this->addSql('CREATE TABLE art_favoris (id INT AUTO_INCREMENT NOT NULL, art_id INT NOT NULL, user_identifier VARCHAR(255) NOT NULL, added_at DATETIME NOT NULL, INDEX IDX_3D77C54E8C25E51A (art_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
            $this->addSql('ALTER TABLE art_favoris ADD CONSTRAINT FK_3D77C54E8C25E51A FOREIGN KEY (art_id) REFERENCES art (id)');
        }

        if ($schema->hasTable('art') && $schema->getTable('art')->hasColumn('image_url')) {
            $this->addSql('ALTER TABLE art MODIFY image_url VARCHAR(500) DEFAULT NULL');
        }
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
