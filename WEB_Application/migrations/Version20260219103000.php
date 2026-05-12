<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20260219103000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Create translation table for symfonycasts/object-translation-bundle';
    }

    public function up(Schema $schema): void
    {
        if ($schema->hasTable('translation')) {
            return;
        }

        $table = $schema->createTable('translation');
        $table->addColumn('id', 'integer', ['autoincrement' => true]);
        $table->addColumn('object_type', 'string', ['length' => 255]);
        $table->addColumn('object_id', 'string', ['length' => 255]);
        $table->addColumn('locale', 'string', ['length' => 255]);
        $table->addColumn('field', 'string', ['length' => 255]);
        $table->addColumn('value', 'text');
        $table->setPrimaryKey(['id']);
        $table->addIndex(['object_type', 'object_id'], 'idx_translation_object');
        $table->addUniqueIndex(['object_type', 'object_id', 'locale', 'field'], 'uniq_object_translation');
    }

    public function down(Schema $schema): void
    {
        if ($schema->hasTable('translation')) {
            $schema->dropTable('translation');
        }
    }
}
