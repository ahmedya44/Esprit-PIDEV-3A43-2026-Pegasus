<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260224084247 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('art')) {
            $this->addSql('CREATE TABLE art (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, image_url VARCHAR(500) NOT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL, title_en VARCHAR(255) DEFAULT NULL, description_en LONGTEXT DEFAULT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');

            return;
        }

        $table = $schema->getTable('art');
        if (!$table->hasColumn('title_en')) {
            $this->addSql('ALTER TABLE art ADD title_en VARCHAR(255) DEFAULT NULL');
        }
        if (!$table->hasColumn('description_en')) {
            $this->addSql('ALTER TABLE art ADD description_en LONGTEXT DEFAULT NULL');
        }
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TEMPORARY TABLE __temp__art AS SELECT id, title, description, image_url, status, created_at FROM art');
        $this->addSql('DROP TABLE art');
        $this->addSql('CREATE TABLE art (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description CLOB NOT NULL, image_url VARCHAR(500) NOT NULL, status VARCHAR(50) NOT NULL, created_at DATETIME NOT NULL)');
        $this->addSql('INSERT INTO art (id, title, description, image_url, status, created_at) SELECT id, title, description, image_url, status, created_at FROM __temp__art');
        $this->addSql('DROP TABLE __temp__art');
    }
}
