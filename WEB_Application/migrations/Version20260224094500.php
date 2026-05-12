<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260224094500 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Create forum_post_rating table for 1..5 ratings on posts';
    }

    public function up(Schema $schema): void
    {
        if ($schema->hasTable('forum_post_rating')) {
            return;
        }

        $table = $schema->createTable('forum_post_rating');
        $table->addColumn('id', 'integer', ['autoincrement' => true]);
        $table->addColumn('post_id', 'integer');
        $table->addColumn('value', 'smallint');
        $table->addColumn('rater_email', 'string', ['length' => 150]);
        $table->addColumn('created_at', 'datetime_immutable');
        $table->addColumn('updated_at', 'datetime_immutable', ['notnull' => false]);
        $table->setPrimaryKey(['id']);
        $table->addIndex(['post_id'], 'idx_forum_post_rating_post');
        $table->addUniqueIndex(['post_id', 'rater_email'], 'uniq_post_rater_email');
        $table->addForeignKeyConstraint('forum_post', ['post_id'], ['id'], ['onDelete' => 'CASCADE'], 'fk_forum_post_rating_post');
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_post_rating')) {
            $schema->dropTable('forum_post_rating');
        }
    }
}

