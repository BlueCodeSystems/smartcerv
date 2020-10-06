package zm.gov.moh.core.mocks;

import android.content.Context;
import android.database.SQLException;

import java.sql.PreparedStatement;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import zm.gov.moh.core.model.UserMock;
import zm.gov.moh.core.repository.database.Converter;
import zm.gov.moh.core.repository.database.Migrations;
import zm.gov.moh.core.repository.database.dao.domain.VisitDaoMock;
import zm.gov.moh.core.repository.database.entity.custom.Identifier;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.EncounterProvider;
import zm.gov.moh.core.repository.database.entity.domain.EncounterRole;
import zm.gov.moh.core.repository.database.entity.domain.EncounterType;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.User;
import zm.gov.moh.core.repository.database.entity.domain.VisitAttribute;
import zm.gov.moh.core.repository.database.entity.domain.VisitAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitType;
import zm.gov.moh.core.repository.database.entity.fts.ClientNameFts;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;

@androidx.room.Database(
        entities = {

                Person.class,
                PersonAddress.class,
                PersonAttributeEntity.class,
                PersonAttributeType.class,
                PersonName.class,
                PatientEntity.class,
                PatientIdentifierEntity.class,
                PatientIdentifierType.class,
                Location.class,
                LocationTag.class,
                LocationTagMap.class,
                LocationAttribute.class,
                LocationAttributeType.class,
                Provider.class,
                ProviderAttribute.class,
                ProviderAttributeType.class,
                User.class,
                UserMock.class,
                ObsEntity.class,
                EncounterEntity.class,
                EncounterProvider.class,
                EncounterRole.class,
                EncounterType.class,
                VisitEntity.class,
                VisitType.class,
                VisitAttribute.class,
                VisitAttributeType.class,
                ClientNameFts.class,
                ConceptName.class,
                ConceptAnswer.class,
                Concept.class,
                EntityMetadata.class,
                Drug.class,
                Identifier.class,
                PersonIdentifier.class

        }, version = 5, exportSchema = false)
@TypeConverters(Converter.class)
public class DatabaseMock extends RoomDatabase {

    private static final String DATABASE_NAME = "mcare";

    private static volatile DatabaseMock dbInstance;


    public VisitDaoMock visitDaoMock(String query) {
        return visitDaoMock(("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId"));
    }


    //database getter
    public static DatabaseMock getDatabase(final Context context) {

        if (dbInstance == null)
            synchronized (DatabaseMock.class) {
                if (dbInstance == null)
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), DatabaseMock.class, DATABASE_NAME)
                            .addMigrations(Migrations.MIGRATION_2_3)
                            .addMigrations(Migrations.MIGRATION_3_4)
                            .addMigrations(Migrations.MIGRATION_4_5)
                            .build();
            }

        return dbInstance;
    }

    public boolean insert() {
        try {
            String query = "(SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId";
            PreparedStatement stmt = (PreparedStatement) dbInstance.visitDaoMock(query);
            stmt.setString(1, "Test");
            int result = stmt.executeUpdate();

            if (result == 1) return true;
            return false;

        } catch (SQLException | java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public <T> Object insert(T eq) {
        return insert();
    }
}