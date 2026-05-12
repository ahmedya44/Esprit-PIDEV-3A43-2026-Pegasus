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
        if (!$schema->hasTable('art')) {
            $this->addSql('CREATE TABLE art (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, image_url VARCHAR(500) NOT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en LONGTEXT DEFAULT NULL, ai_generated_image VARCHAR(255) DEFAULT NULL, is_ai_generated TINYINT(1) DEFAULT 0 NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');

            return;
        }

        $table = $schema->getTable('art');
        if (!$table->hasColumn('ai_generated_image')) {
            $this->addSql('ALTER TABLE art ADD ai_generated_image VARCHAR(255) DEFAULT NULL');
        }
        if (!$table->hasColumn('is_ai_generated')) {
            $this->addSql('ALTER TABLE art ADD is_ai_generated TINYINT(1) DEFAULT 0 NOT NULL');
        }
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
