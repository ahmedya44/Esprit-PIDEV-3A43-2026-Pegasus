<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260305011818 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        $this->addSql("ALTER TABLE `admin` CHANGE birth_date birth_date DATE DEFAULT NULL COMMENT '(DC2Type:date_immutable)'");
        $this->addSql("ALTER TABLE user CHANGE roles roles JSON NOT NULL, CHANGE phone phone VARCHAR(30) DEFAULT NULL, CHANGE avatar_url avatar_url VARCHAR(255) DEFAULT NULL, CHANGE created_at created_at DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)', CHANGE reset_token reset_token VARCHAR(64) DEFAULT NULL, CHANGE reset_token_expires_at reset_token_expires_at DATETIME DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)', CHANGE email_verification_token email_verification_token VARCHAR(64) DEFAULT NULL, CHANGE email_verification_token_expires_at email_verification_token_expires_at DATETIME DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'");
        $this->addSql("ALTER TABLE artiste CHANGE styles styles VARCHAR(255) DEFAULT NULL, CHANGE facebook facebook VARCHAR(255) DEFAULT NULL, CHANGE instagram instagram VARCHAR(255) DEFAULT NULL, CHANGE portfolio_url portfolio_url VARCHAR(255) DEFAULT NULL, CHANGE birth_date birth_date DATE DEFAULT NULL COMMENT '(DC2Type:date_immutable)'");
        $this->addSql("ALTER TABLE course ADD artist_id INT NOT NULL, CHANGE thumbnail_url thumbnail_url VARCHAR(255) DEFAULT NULL, CHANGE created_at created_at DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)'");
        $this->addSql('ALTER TABLE course ADD CONSTRAINT FK_169E6FB9B7970CF8 FOREIGN KEY (artist_id) REFERENCES artiste (id)');
        $this->addSql('CREATE INDEX IDX_169E6FB9B7970CF8 ON course (artist_id)');
        $this->addSql("ALTER TABLE normal_user CHANGE birth_date birth_date DATE DEFAULT NULL COMMENT '(DC2Type:date_immutable)'");
        $this->addSql('ALTER TABLE sponsor CHANGE website website VARCHAR(255) DEFAULT NULL, CHANGE address address VARCHAR(255) DEFAULT NULL');
        $this->addSql("ALTER TABLE messenger_messages CHANGE created_at created_at DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)', CHANGE available_at available_at DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)', CHANGE delivered_at delivered_at DATETIME DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'");
    }

    public function down(Schema $schema): void
    {
        $this->addSql('ALTER TABLE course DROP FOREIGN KEY FK_169E6FB9B7970CF8');
        $this->addSql('DROP INDEX IDX_169E6FB9B7970CF8 ON course');
        $this->addSql('ALTER TABLE course DROP artist_id, CHANGE thumbnail_url thumbnail_url VARCHAR(255) DEFAULT NULL, CHANGE created_at created_at DATETIME NOT NULL');
        $this->addSql('ALTER TABLE `admin` CHANGE birth_date birth_date DATE DEFAULT NULL');
        $this->addSql('ALTER TABLE user CHANGE roles roles JSON NOT NULL, CHANGE phone phone VARCHAR(30) DEFAULT NULL, CHANGE avatar_url avatar_url VARCHAR(255) DEFAULT NULL, CHANGE created_at created_at DATETIME NOT NULL, CHANGE reset_token reset_token VARCHAR(64) DEFAULT NULL, CHANGE reset_token_expires_at reset_token_expires_at DATETIME DEFAULT NULL, CHANGE email_verification_token email_verification_token VARCHAR(64) DEFAULT NULL, CHANGE email_verification_token_expires_at email_verification_token_expires_at DATETIME DEFAULT NULL');
        $this->addSql('ALTER TABLE artiste CHANGE styles styles VARCHAR(255) DEFAULT NULL, CHANGE facebook facebook VARCHAR(255) DEFAULT NULL, CHANGE instagram instagram VARCHAR(255) DEFAULT NULL, CHANGE portfolio_url portfolio_url VARCHAR(255) DEFAULT NULL, CHANGE birth_date birth_date DATE DEFAULT NULL');
        $this->addSql('ALTER TABLE normal_user CHANGE birth_date birth_date DATE DEFAULT NULL');
        $this->addSql('ALTER TABLE sponsor CHANGE website website VARCHAR(255) DEFAULT NULL, CHANGE address address VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE messenger_messages CHANGE created_at created_at DATETIME NOT NULL, CHANGE available_at available_at DATETIME NOT NULL, CHANGE delivered_at delivered_at DATETIME DEFAULT NULL');
    }
}
