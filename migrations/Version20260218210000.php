<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260218210000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add verified boolean field to sponsor';
    }

    public function up(Schema $schema): void
    {
        $this->addSql('ALTER TABLE sponsor ADD verified TINYINT(1) NOT NULL DEFAULT 0');
    }

    public function down(Schema $schema): void
    {
        $this->addSql('ALTER TABLE sponsor DROP verified');
    }
}

