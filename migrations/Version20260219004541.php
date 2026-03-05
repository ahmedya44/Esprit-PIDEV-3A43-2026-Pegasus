<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260219004541 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE course (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, thumbnail_url VARCHAR(255) DEFAULT NULL, status VARCHAR(30) NOT NULL, created_at DATETIME NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE course_section (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, order_index INT NOT NULL, course_id INT NOT NULL, INDEX IDX_25B07F03591CC992 (course_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE course_video (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, video_url VARCHAR(255) NOT NULL, duration_sec INT NOT NULL, order_index INT NOT NULL, is_preview TINYINT(1) NOT NULL, section_id INT NOT NULL, INDEX IDX_956CDDC4D823E37A (section_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE quiz (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, time_limit_min INT DEFAULT NULL, passing_score INT NOT NULL, attempt_limit INT DEFAULT NULL, course_id INT NOT NULL, INDEX IDX_A412FA92591CC992 (course_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE quiz_choice (id INT AUTO_INCREMENT NOT NULL, label VARCHAR(255) NOT NULL, is_correct TINYINT(1) NOT NULL, question_id INT NOT NULL, INDEX IDX_2CEFAACB1E27F6BF (question_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE quiz_question (id INT AUTO_INCREMENT NOT NULL, question_text LONGTEXT NOT NULL, points INT NOT NULL, order_index INT NOT NULL, quiz_id INT NOT NULL, INDEX IDX_6033B00B853CD175 (quiz_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0E3BD61CE16BA31DBBF396750 (queue_name, available_at, delivered_at, id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4');
        $this->addSql('ALTER TABLE course_section ADD CONSTRAINT FK_25B07F03591CC992 FOREIGN KEY (course_id) REFERENCES course (id)');
        $this->addSql('ALTER TABLE course_video ADD CONSTRAINT FK_956CDDC4D823E37A FOREIGN KEY (section_id) REFERENCES course_section (id)');
        $this->addSql('ALTER TABLE quiz ADD CONSTRAINT FK_A412FA92591CC992 FOREIGN KEY (course_id) REFERENCES course (id)');
        $this->addSql('ALTER TABLE quiz_choice ADD CONSTRAINT FK_2CEFAACB1E27F6BF FOREIGN KEY (question_id) REFERENCES quiz_question (id)');
        $this->addSql('ALTER TABLE quiz_question ADD CONSTRAINT FK_6033B00B853CD175 FOREIGN KEY (quiz_id) REFERENCES quiz (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE course_section DROP FOREIGN KEY FK_25B07F03591CC992');
        $this->addSql('ALTER TABLE course_video DROP FOREIGN KEY FK_956CDDC4D823E37A');
        $this->addSql('ALTER TABLE quiz DROP FOREIGN KEY FK_A412FA92591CC992');
        $this->addSql('ALTER TABLE quiz_choice DROP FOREIGN KEY FK_2CEFAACB1E27F6BF');
        $this->addSql('ALTER TABLE quiz_question DROP FOREIGN KEY FK_6033B00B853CD175');
        $this->addSql('DROP TABLE course');
        $this->addSql('DROP TABLE course_section');
        $this->addSql('DROP TABLE course_video');
        $this->addSql('DROP TABLE quiz');
        $this->addSql('DROP TABLE quiz_choice');
        $this->addSql('DROP TABLE quiz_question');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
