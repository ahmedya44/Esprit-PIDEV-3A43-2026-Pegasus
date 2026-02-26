<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260226071906 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql(<<<'SQL'
            CREATE TABLE categorie (
              id INT AUTO_INCREMENT NOT NULL,
              nom VARCHAR(255) NOT NULL,
              description LONGTEXT DEFAULT NULL,
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE commande (
              id INT AUTO_INCREMENT NOT NULL,
              date_commande DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)',
              statut VARCHAR(50) NOT NULL,
              total DOUBLE PRECISION NOT NULL,
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE favorite (
              id INT AUTO_INCREMENT NOT NULL,
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE ligne_commande (
              id INT AUTO_INCREMENT NOT NULL,
              commande_id INT DEFAULT NULL,
              produit_id INT DEFAULT NULL,
              quantite INT NOT NULL,
              prix_unitaire DOUBLE PRECISION NOT NULL,
              INDEX IDX_3170B74B82EA2E54 (commande_id),
              INDEX IDX_3170B74BF347EFB (produit_id),
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE ligne_panier (
              id INT AUTO_INCREMENT NOT NULL,
              panier_id INT DEFAULT NULL,
              produit_id INT DEFAULT NULL,
              quantite INT NOT NULL,
              prix_unitaire DOUBLE PRECISION NOT NULL,
              INDEX IDX_21691B4F77D927C (panier_id),
              INDEX IDX_21691B4F347EFB (produit_id),
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE panier (
              id INT AUTO_INCREMENT NOT NULL,
              date_creation DATETIME NOT NULL COMMENT '(DC2Type:datetime_immutable)',
              total DOUBLE PRECISION NOT NULL,
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              ligne_commande
            ADD
              CONSTRAINT FK_3170B74B82EA2E54 FOREIGN KEY (commande_id) REFERENCES commande (id)
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              ligne_commande
            ADD
              CONSTRAINT FK_3170B74BF347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              ligne_panier
            ADD
              CONSTRAINT FK_21691B4F77D927C FOREIGN KEY (panier_id) REFERENCES panier (id)
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              ligne_panier
            ADD
              CONSTRAINT FK_21691B4F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_commentaire_translation
            DROP
              FOREIGN KEY fk_forum_comment_translation_comment
        SQL);
        $this->addSql('ALTER TABLE forum_post_translation DROP FOREIGN KEY fk_forum_post_translation_post');
        $this->addSql('DROP TABLE forum_commentaire_translation');
        $this->addSql('DROP TABLE forum_post_translation');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              art_view
            ADD
              CONSTRAINT FK_F7DDDD118C25E51A FOREIGN KEY (art_id) REFERENCES art (id)
        SQL);
        $this->addSql('ALTER TABLE forum_commentaire DROP FOREIGN KEY FK_F37E3FAF7E3C61F9');
        $this->addSql('DROP INDEX idx_f37e3faf7e3c61f9 ON forum_commentaire');
        $this->addSql('CREATE INDEX IDX_61C4EB1E7E3C61F9 ON forum_commentaire (owner_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_commentaire
            ADD
              CONSTRAINT FK_F37E3FAF7E3C61F9 FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE
            SET
              NULL
        SQL);
        $this->addSql('ALTER TABLE forum_post DROP FOREIGN KEY FK_D2B19BE67E3C61F9');
        $this->addSql('DROP INDEX idx_d2b19be67e3c61f9 ON forum_post');
        $this->addSql('CREATE INDEX IDX_996BCC5A7E3C61F9 ON forum_post (owner_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post
            ADD
              CONSTRAINT FK_D2B19BE67E3C61F9 FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE
            SET
              NULL
        SQL);
        $this->addSql('ALTER TABLE forum_post_allowed_viewer DROP FOREIGN KEY FK_8A6B18A24B89032C');
        $this->addSql('ALTER TABLE forum_post_allowed_viewer DROP FOREIGN KEY FK_8A6B18A2A76ED395');
        $this->addSql('DROP INDEX idx_8a6b18a24b89032c ON forum_post_allowed_viewer');
        $this->addSql('CREATE INDEX IDX_4E0BC74C4B89032C ON forum_post_allowed_viewer (post_id)');
        $this->addSql('DROP INDEX idx_8a6b18a2a76ed395 ON forum_post_allowed_viewer');
        $this->addSql('CREATE INDEX IDX_4E0BC74CA76ED395 ON forum_post_allowed_viewer (user_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_allowed_viewer
            ADD
              CONSTRAINT FK_8A6B18A24B89032C FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_allowed_viewer
            ADD
              CONSTRAINT FK_8A6B18A2A76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE forum_post_rating DROP FOREIGN KEY fk_forum_post_rating_post');
        $this->addSql('DROP INDEX idx_forum_post_rating_post ON forum_post_rating');
        $this->addSql('CREATE INDEX IDX_EE9F41E54B89032C ON forum_post_rating (post_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_rating
            ADD
              CONSTRAINT fk_forum_post_rating_post FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE passkey_credential DROP FOREIGN KEY FK_2EAA5890A76ED395');
        $this->addSql('DROP INDEX idx_2eaa5890a76ed395 ON passkey_credential');
        $this->addSql('CREATE INDEX IDX_DFD64A45A76ED395 ON passkey_credential (user_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              passkey_credential
            ADD
              CONSTRAINT FK_2EAA5890A76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE produit ADD categorie_id INT DEFAULT NULL');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              produit
            ADD
              CONSTRAINT FK_29A5EC27BCF5E72D FOREIGN KEY (categorie_id) REFERENCES categorie (id)
        SQL);
        $this->addSql('CREATE INDEX IDX_29A5EC27BCF5E72D ON produit (categorie_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              translation
            CHANGE
              object_type object_type VARCHAR(64) NOT NULL,
            CHANGE
              object_id object_id VARCHAR(64) NOT NULL,
            CHANGE
              locale locale VARCHAR(10) NOT NULL,
            CHANGE
              field field VARCHAR(64) NOT NULL
        SQL);
        $this->addSql('DROP INDEX idx_translation_object ON translation');
        $this->addSql('CREATE INDEX IDX_B469456F11CB6B3A232D562B ON translation (object_type, object_id)');
        $this->addSql('DROP INDEX uniq_8d93d6491c0c4fcf ON user');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D649D7C8DC19 ON user (reset_token)');
        $this->addSql('DROP INDEX uniq_8d93d649d7b5a66b ON user');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D649C4995C67 ON user (email_verification_token)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC27BCF5E72D');
        $this->addSql(<<<'SQL'
            CREATE TABLE forum_commentaire_translation (
              id INT AUTO_INCREMENT NOT NULL,
              commentaire_id INT NOT NULL,
              locale VARCHAR(10) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`,
              content LONGTEXT CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`,
              INDEX idx_forum_comment_translation_comment (commentaire_id),
              UNIQUE INDEX uniq_forum_comment_translation_locale_comment (locale, commentaire_id),
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = ''
        SQL);
        $this->addSql(<<<'SQL'
            CREATE TABLE forum_post_translation (
              id INT AUTO_INCREMENT NOT NULL,
              post_id INT NOT NULL,
              locale VARCHAR(10) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`,
              title VARCHAR(180) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`,
              content LONGTEXT CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`,
              INDEX idx_forum_post_translation_post (post_id),
              UNIQUE INDEX uniq_forum_post_translation_locale_post (locale, post_id),
              PRIMARY KEY(id)
            ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = ''
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_commentaire_translation
            ADD
              CONSTRAINT fk_forum_comment_translation_comment FOREIGN KEY (commentaire_id) REFERENCES forum_commentaire (id) ON DELETE CASCADE
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_translation
            ADD
              CONSTRAINT fk_forum_post_translation_post FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE ligne_commande DROP FOREIGN KEY FK_3170B74B82EA2E54');
        $this->addSql('ALTER TABLE ligne_commande DROP FOREIGN KEY FK_3170B74BF347EFB');
        $this->addSql('ALTER TABLE ligne_panier DROP FOREIGN KEY FK_21691B4F77D927C');
        $this->addSql('ALTER TABLE ligne_panier DROP FOREIGN KEY FK_21691B4F347EFB');
        $this->addSql('DROP TABLE categorie');
        $this->addSql('DROP TABLE commande');
        $this->addSql('DROP TABLE favorite');
        $this->addSql('DROP TABLE ligne_commande');
        $this->addSql('DROP TABLE ligne_panier');
        $this->addSql('DROP TABLE panier');
        $this->addSql('ALTER TABLE art_view DROP FOREIGN KEY FK_F7DDDD118C25E51A');
        $this->addSql('ALTER TABLE forum_commentaire DROP FOREIGN KEY FK_61C4EB1E7E3C61F9');
        $this->addSql('DROP INDEX idx_61c4eb1e7e3c61f9 ON forum_commentaire');
        $this->addSql('CREATE INDEX IDX_F37E3FAF7E3C61F9 ON forum_commentaire (owner_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_commentaire
            ADD
              CONSTRAINT FK_61C4EB1E7E3C61F9 FOREIGN KEY (owner_id) REFERENCES `user` (id) ON DELETE
            SET
              NULL
        SQL);
        $this->addSql('ALTER TABLE forum_post DROP FOREIGN KEY FK_996BCC5A7E3C61F9');
        $this->addSql('DROP INDEX idx_996bcc5a7e3c61f9 ON forum_post');
        $this->addSql('CREATE INDEX IDX_D2B19BE67E3C61F9 ON forum_post (owner_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post
            ADD
              CONSTRAINT FK_996BCC5A7E3C61F9 FOREIGN KEY (owner_id) REFERENCES `user` (id) ON DELETE
            SET
              NULL
        SQL);
        $this->addSql('ALTER TABLE forum_post_allowed_viewer DROP FOREIGN KEY FK_4E0BC74C4B89032C');
        $this->addSql('ALTER TABLE forum_post_allowed_viewer DROP FOREIGN KEY FK_4E0BC74CA76ED395');
        $this->addSql('DROP INDEX idx_4e0bc74c4b89032c ON forum_post_allowed_viewer');
        $this->addSql('CREATE INDEX IDX_8A6B18A24B89032C ON forum_post_allowed_viewer (post_id)');
        $this->addSql('DROP INDEX idx_4e0bc74ca76ed395 ON forum_post_allowed_viewer');
        $this->addSql('CREATE INDEX IDX_8A6B18A2A76ED395 ON forum_post_allowed_viewer (user_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_allowed_viewer
            ADD
              CONSTRAINT FK_4E0BC74C4B89032C FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE
        SQL);
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_allowed_viewer
            ADD
              CONSTRAINT FK_4E0BC74CA76ED395 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE forum_post_rating DROP FOREIGN KEY FK_EE9F41E54B89032C');
        $this->addSql('DROP INDEX idx_ee9f41e54b89032c ON forum_post_rating');
        $this->addSql('CREATE INDEX idx_forum_post_rating_post ON forum_post_rating (post_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              forum_post_rating
            ADD
              CONSTRAINT FK_EE9F41E54B89032C FOREIGN KEY (post_id) REFERENCES forum_post (id) ON DELETE CASCADE
        SQL);
        $this->addSql('ALTER TABLE passkey_credential DROP FOREIGN KEY FK_DFD64A45A76ED395');
        $this->addSql('DROP INDEX idx_dfd64a45a76ed395 ON passkey_credential');
        $this->addSql('CREATE INDEX IDX_2EAA5890A76ED395 ON passkey_credential (user_id)');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              passkey_credential
            ADD
              CONSTRAINT FK_DFD64A45A76ED395 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE
        SQL);
        $this->addSql('DROP INDEX IDX_29A5EC27BCF5E72D ON produit');
        $this->addSql('ALTER TABLE produit DROP categorie_id');
        $this->addSql(<<<'SQL'
            ALTER TABLE
              translation
            CHANGE
              object_type object_type VARCHAR(255) NOT NULL,
            CHANGE
              object_id object_id VARCHAR(255) NOT NULL,
            CHANGE
              locale locale VARCHAR(255) NOT NULL,
            CHANGE
              field field VARCHAR(255) NOT NULL
        SQL);
        $this->addSql('DROP INDEX idx_b469456f11cb6b3a232d562b ON translation');
        $this->addSql('CREATE INDEX idx_translation_object ON translation (object_type, object_id)');
        $this->addSql('DROP INDEX uniq_8d93d649d7c8dc19 ON `user`');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D6491C0C4FCF ON `user` (reset_token)');
        $this->addSql('DROP INDEX uniq_8d93d649c4995c67 ON `user`');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D649D7B5A66B ON `user` (email_verification_token)');
    }
}
