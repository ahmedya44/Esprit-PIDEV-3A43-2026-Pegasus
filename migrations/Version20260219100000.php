<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260219100000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Create translation tables for forum posts and comments';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post_translation')) {
            $table = $schema->createTable('forum_post_translation');
            $table->addColumn('id', 'integer', ['autoincrement' => true]);
            $table->addColumn('locale', 'string', ['length' => 10]);
            $table->addColumn('title', 'string', ['length' => 180, 'notnull' => false]);
            $table->addColumn('content', 'text', ['notnull' => false]);
            $table->addColumn('post_id', 'integer');
            $table->setPrimaryKey(['id']);
            $table->addUniqueIndex(['locale', 'post_id'], 'uniq_forum_post_translation_locale_post');
            $table->addIndex(['post_id'], 'idx_forum_post_translation_post');
            $table->addForeignKeyConstraint('forum_post', ['post_id'], ['id'], ['onDelete' => 'CASCADE'], 'fk_forum_post_translation_post');
        }

        if (!$schema->hasTable('forum_commentaire_translation')) {
            $table = $schema->createTable('forum_commentaire_translation');
            $table->addColumn('id', 'integer', ['autoincrement' => true]);
            $table->addColumn('locale', 'string', ['length' => 10]);
            $table->addColumn('content', 'text', ['notnull' => false]);
            $table->addColumn('commentaire_id', 'integer');
            $table->setPrimaryKey(['id']);
            $table->addUniqueIndex(['locale', 'commentaire_id'], 'uniq_forum_comment_translation_locale_comment');
            $table->addIndex(['commentaire_id'], 'idx_forum_comment_translation_comment');
            $table->addForeignKeyConstraint('forum_commentaire', ['commentaire_id'], ['id'], ['onDelete' => 'CASCADE'], 'fk_forum_comment_translation_comment');
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_commentaire_translation')) {
            $schema->dropTable('forum_commentaire_translation');
        }

        if ($schema->hasTable('forum_post_translation')) {
            $schema->dropTable('forum_post_translation');
        }
    }
}
