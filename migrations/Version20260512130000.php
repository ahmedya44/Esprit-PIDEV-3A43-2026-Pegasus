<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260512130000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add learning_progress table for DB-persisted course progress tracking';
    }

    public function up(Schema $schema): void
    {
        if ($schema->hasTable('learning_progress')) {
            return;
        }

        $this->addSql('CREATE TABLE learning_progress (
            id INT AUTO_INCREMENT NOT NULL,
            user_id INT NOT NULL,
            course_id INT NOT NULL,
            status VARCHAR(20) NOT NULL DEFAULT \'in-progress\',
            progress_percent INT NOT NULL DEFAULT 0,
            completed_video_ids JSON NOT NULL,
            started_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\',
            completed_at DATETIME DEFAULT NULL COMMENT \'(DC2Type:datetime_immutable)\',
            UNIQUE INDEX UNIQ_learning_progress (user_id, course_id),
            INDEX IDX_lp_course (course_id),
            PRIMARY KEY(id)
        ) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');

        $this->addSql('ALTER TABLE learning_progress
            ADD CONSTRAINT FK_lp_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE learning_progress
            ADD CONSTRAINT FK_lp_course FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('learning_progress')) {
            $this->addSql('DROP TABLE learning_progress');
        }
    }
}
