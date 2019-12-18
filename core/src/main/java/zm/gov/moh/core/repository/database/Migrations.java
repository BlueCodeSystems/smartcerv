package zm.gov.moh.core.repository.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {

    public static Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE entity_metadata ADD COLUMN last_modified TEXT");
        }
    };

    public static Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            //Drop person_identifier table because had no data initially
            database.execSQL("DROP TABLE person_identifier");

            //Recreate person_identifier table with new schema
            database.execSQL("CREATE TABLE `person_identifier` (`identifier` TEXT NOT NULL,`remote_id` INTEGER, `local_id` INTEGER, `remote_uuid` TEXT, PRIMARY KEY(`identifier`))");

            //insert local person identifiers
            database.execSQL("INSERT INTO person_identifier(identifier,local_id) SELECT patient_identifier.identifier,person.person_id AS local_id FROM person JOIN patient_identifier ON person.person_id = patient_identifier.patient_id WHERE person.uuid IS NULL AND identifier_type=3");
        }
    };

    public static Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("CREATE TABLE `person_attribute_type_temp` (`person_attribute_type_id` INTEGER NOT NULL, `name` TEXT, `description` TEXT, `format` TEXT, `foreign_key` INTEGER, `searchable` INTEGER NOT NULL, `creator` INTEGER NOT NULL, `date_created` TEXT, `changed_by` INTEGER, `date_changed` TEXT, `retired` INTEGER NOT NULL, `retired_by` INTEGER, `date_retired` TEXT, `retire_reason` TEXT, `edit_privilege` TEXT, `sort_weight` REAL NOT NULL, `uuid` TEXT, PRIMARY KEY(`person_attribute_type_id`) )");
                database.execSQL("INSERT INTO person_attribute_type_temp SELECT * FROM person_attribute_type");
                database.execSQL("DROP TABLE `person_attribute_type`");
                database.execSQL("CREATE TABLE `person_attribute_type` (`person_attribute_type_id` INTEGER NOT NULL, `name` TEXT, `description` TEXT, `format` TEXT, `foreign_key` INTEGER, `searchable` INTEGER NOT NULL, `creator` INTEGER NOT NULL, `date_created` TEXT, `changed_by` INTEGER, `date_changed` TEXT, `retired` INTEGER NOT NULL, `retired_by` INTEGER, `date_retired` TEXT, `retire_reason` TEXT, `edit_privilege` TEXT, `sort_weight` REAL NOT NULL, `uuid` TEXT, PRIMARY KEY(`person_attribute_type_id`) )");
                database.execSQL("INSERT INTO person_attribute_type SELECT * FROM person_attribute_type_temp");
                database.execSQL("DROP TABLE `person_attribute_type_temp`");

                database.execSQL("CREATE TABLE `person_attribute_temp` (`person_attribute_id` INTEGER NOT NULL, `person_id` INTEGER NOT NULL, `value` TEXT, `person_attribute_type_id` INTEGER NOT NULL, `creator` INTEGER NOT NULL, `date_created` TEXT, `changed_by` INTEGER, `date_changed` TEXT, `voided` INTEGER NOT NULL, `voided_by` INTEGER, `date_voided` TEXT, `void_reason` TEXT, `uuid` TEXT, PRIMARY KEY(`person_attribute_id`))");
                database.execSQL("INSERT INTO person_attribute_temp SELECT * FROM person_attribute");
                database.execSQL("DROP TABLE `person_attribute`");
                database.execSQL("CREATE TABLE `person_attribute` (`person_attribute_id` INTEGER NOT NULL, `person_id` INTEGER NOT NULL, `value` TEXT, `person_attribute_type_id` INTEGER NOT NULL, `creator` INTEGER NOT NULL, `date_created` TEXT, `changed_by` INTEGER, `date_changed` TEXT, `voided` INTEGER NOT NULL, `voided_by` INTEGER, `date_voided` TEXT, `void_reason` TEXT, `uuid` TEXT, PRIMARY KEY(`person_attribute_id`))");
                database.execSQL("INSERT INTO person_attribute SELECT * FROM person_attribute_temp");
                database.execSQL("DROP TABLE `person_attribute_temp`");
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    };
}