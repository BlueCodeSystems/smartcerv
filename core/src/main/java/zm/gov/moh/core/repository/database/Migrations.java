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
}