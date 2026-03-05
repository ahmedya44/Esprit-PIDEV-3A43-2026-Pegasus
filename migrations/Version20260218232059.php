<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20260218232059 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Create forum posts, comments, and messenger tables in a platform-agnostic way';
    }

    public function up(Schema $schema): void
    {
        if (!$schema->hasTable('forum_post')) {
            $postTable = $schema->createTable('forum_post');
            $postTable->addColumn('id', 'integer', ['autoincrement' => true]);
            $postTable->addColumn('title', 'string', ['length' => 180]);
            $postTable->addColumn('content', 'text');
            $postTable->addColumn('author_name', 'string', ['length' => 120]);
            $postTable->addColumn('author_email', 'string', ['length' => 150]);
            $postTable->addColumn('status', 'string', ['length' => 20]);
            $postTable->addColumn('created_at', 'datetime_immutable');
            $postTable->addColumn('updated_at', 'datetime_immutable', ['notnull' => false]);
            $postTable->setPrimaryKey(['id']);
            $postTable->addIndex(['status'], 'IDX_996BCC5A7B00651C');
            $postTable->addIndex(['created_at'], 'IDX_996BCC5A8B8E8428');
        }

        if (!$schema->hasTable('forum_commentaire')) {
            $commentTable = $schema->createTable('forum_commentaire');
            $commentTable->addColumn('id', 'integer', ['autoincrement' => true]);
            $commentTable->addColumn('content', 'text');
            $commentTable->addColumn('author_name', 'string', ['length' => 120]);
            $commentTable->addColumn('author_email', 'string', ['length' => 150]);
            $commentTable->addColumn('created_at', 'datetime_immutable');
            $commentTable->addColumn('updated_at', 'datetime_immutable', ['notnull' => false]);
            $commentTable->addColumn('post_id', 'integer');
            $commentTable->setPrimaryKey(['id']);
            $commentTable->addIndex(['post_id'], 'IDX_61C4EB1E4B89032C');
            $commentTable->addIndex(['created_at'], 'IDX_61C4EB1E8B8E8428');
        }

        $commentTable = $schema->getTable('forum_commentaire');
        if (!$commentTable->hasForeignKey('FK_61C4EB1E4B89032C')) {
            $commentTable->addForeignKeyConstraint('forum_post', ['post_id'], ['id'], ['onDelete' => 'CASCADE'], 'FK_61C4EB1E4B89032C');
        }

        if (!$schema->hasTable('messenger_messages')) {
            $messagesTable = $schema->createTable('messenger_messages');
            $messagesTable->addColumn('id', 'bigint', ['autoincrement' => true]);
            $messagesTable->addColumn('body', 'text');
            $messagesTable->addColumn('headers', 'text');
            $messagesTable->addColumn('queue_name', 'string', ['length' => 190]);
            $messagesTable->addColumn('created_at', 'datetime_immutable');
            $messagesTable->addColumn('available_at', 'datetime_immutable');
            $messagesTable->addColumn('delivered_at', 'datetime_immutable', ['notnull' => false]);
            $messagesTable->setPrimaryKey(['id']);
            $messagesTable->addIndex(['queue_name', 'available_at', 'delivered_at'], 'IDX_75EA56E0FB7336F0E3BD61CE16BA31DBBF396750');
        }
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('forum_commentaire')) {
            $schema->dropTable('forum_commentaire');
        }

        if ($schema->hasTable('forum_post')) {
            $schema->dropTable('forum_post');
        }

        if ($schema->hasTable('messenger_messages')) {
            $schema->dropTable('messenger_messages');
        }
    }
}
