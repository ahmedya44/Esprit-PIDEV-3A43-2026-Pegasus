<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260305034500 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Align evenement table with entity mapping (statut + artiste_id relation)';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('evenement')) {
            return;
        }

        $table = $schema->getTable('evenement');

        if (!$table->hasColumn('statut')) {
            $this->addSql("ALTER TABLE evenement ADD statut VARCHAR(50) NOT NULL DEFAULT 'en attente'");
        }

        if (!$table->hasColumn('artiste_id')) {
            $this->addSql('ALTER TABLE evenement ADD artiste_id INT DEFAULT NULL');
        }

        if ($schema->hasTable('user')) {
            $this->addSql('CREATE INDEX IF NOT EXISTS IDX_B26681E21D25844 ON evenement (artiste_id)');
            $this->addSql('ALTER TABLE evenement ADD CONSTRAINT FK_B26681E21D25844 FOREIGN KEY (artiste_id) REFERENCES `user` (id)');
        }
    }

    public function down(Schema $schema): void
    {
        if (!$schema->hasTable('evenement')) {
            return;
        }

        $table = $schema->getTable('evenement');

        if ($table->hasColumn('artiste_id')) {
            $this->addSql('ALTER TABLE evenement DROP FOREIGN KEY FK_B26681E21D25844');
            $this->addSql('DROP INDEX IDX_B26681E21D25844 ON evenement');
            $this->addSql('ALTER TABLE evenement DROP COLUMN artiste_id');
        }

        if ($table->hasColumn('statut')) {
            $this->addSql('ALTER TABLE evenement DROP COLUMN statut');
        }
    }
}
