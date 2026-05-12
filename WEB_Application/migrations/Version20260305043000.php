<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260305043000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Reconcile missing event-related tables (participation, sponsoring_pack, reservation_pack, favorite)';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('favorite')) {
            $this->addSql('CREATE TABLE favorite (id INT AUTO_INCREMENT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        }

        if (!$schema->hasTable('sponsoring_pack')) {
            $this->addSql('CREATE TABLE sponsoring_pack (id_pack INT AUTO_INCREMENT NOT NULL, nom_pack VARCHAR(100) NOT NULL, description LONGTEXT DEFAULT NULL, prix DOUBLE PRECISION NOT NULL, PRIMARY KEY(id_pack)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        }

        if (!$schema->hasTable('participation')) {
            $this->addSql('CREATE TABLE participation (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, evenement_id INT NOT NULL, created_at DATETIME NOT NULL, INDEX IDX_AB55E24FA76ED395 (user_id), INDEX IDX_AB55E24FFD02F13 (evenement_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
            $this->addSql('ALTER TABLE participation ADD CONSTRAINT FK_AB55E24FA76ED395 FOREIGN KEY (user_id) REFERENCES `user` (id)');
            $this->addSql('ALTER TABLE participation ADD CONSTRAINT FK_AB55E24FFD02F13 FOREIGN KEY (evenement_id) REFERENCES evenement (id)');
        }

        if (!$schema->hasTable('reservation_pack')) {
            $this->addSql('CREATE TABLE reservation_pack (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, evenement_id INT NOT NULL, sponsoring_pack_id INT NOT NULL, date_reservation DATETIME NOT NULL, statut VARCHAR(50) NOT NULL, INDEX IDX_81E7934BA76ED395 (user_id), INDEX IDX_81E7934BFD02F13 (evenement_id), INDEX IDX_81E7934B9B9D85B1 (sponsoring_pack_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
            $this->addSql('ALTER TABLE reservation_pack ADD CONSTRAINT FK_81E7934BA76ED395 FOREIGN KEY (user_id) REFERENCES `user` (id)');
            $this->addSql('ALTER TABLE reservation_pack ADD CONSTRAINT FK_81E7934BFD02F13 FOREIGN KEY (evenement_id) REFERENCES evenement (id)');
            $this->addSql('ALTER TABLE reservation_pack ADD CONSTRAINT FK_81E7934B9B9D85B1 FOREIGN KEY (sponsoring_pack_id) REFERENCES sponsoring_pack (id_pack)');
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('reservation_pack')) {
            $this->addSql('DROP TABLE reservation_pack');
        }

        if ($schema->hasTable('participation')) {
            $this->addSql('DROP TABLE participation');
        }

        if ($schema->hasTable('sponsoring_pack')) {
            $this->addSql('DROP TABLE sponsoring_pack');
        }

        if ($schema->hasTable('favorite')) {
            $this->addSql('DROP TABLE favorite');
        }
    }
}
