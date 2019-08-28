package zm.gov.moh.core.repository.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import zm.gov.moh.core.repository.database.dao.custom.IdentifierDao;
import zm.gov.moh.core.repository.database.dao.derived.ClientDao;
import zm.gov.moh.core.repository.database.dao.derived.ConceptAnswerNameDao;
import zm.gov.moh.core.repository.database.dao.derived.FacilityDistrictCodeDao;
import zm.gov.moh.core.repository.database.dao.derived.GenericDao;
import zm.gov.moh.core.repository.database.dao.derived.MetricsDao;
import zm.gov.moh.core.repository.database.dao.derived.PersonIdentifierDao;
import zm.gov.moh.core.repository.database.dao.derived.ProviderUserDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptAnswerDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptNameDao;
import zm.gov.moh.core.repository.database.dao.domain.DrugDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterProviderDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagMapDao;
import zm.gov.moh.core.repository.database.dao.domain.ObsDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderDao;
import zm.gov.moh.core.repository.database.dao.domain.UserDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitTypeDao;
import zm.gov.moh.core.repository.database.dao.fts.ClientFtsDao;
import zm.gov.moh.core.repository.database.dao.system.EntityMetadataDao;
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
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitAttribute;
import zm.gov.moh.core.repository.database.entity.domain.VisitAttributeType;
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

        }, version = 3)
@TypeConverters(Converter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "mcare";

    private static volatile Database dbInstance;

    //Domain
    public abstract PersonDao personDao();
    public abstract PersonAddressDao personAddressDao();
    public abstract PersonAttributeDao personAttributeDao();
    public abstract PersonAttributeTypeDao personAttributeTypeDao();
    public abstract PersonNameDao personNameDao();
    public abstract LocationDao locationDao();
    public abstract LocationAttributeDao locationAttributeDao();
    public abstract LocationTagDao locationTagDao();
    public abstract LocationTagMapDao locationTagMapDao();
    public abstract LocationAttributeTypeDao locationAttributeTypeDao();
    public abstract ProviderUserDao providerUserDao();
    public abstract UserDao userDao();
    public abstract ProviderDao providerDao();
    public abstract ProviderAttributeDao providerAttributeDao();
    public abstract ProviderAttributeTypeDao providerAttributeTypeDao();
    public abstract PatientDao patientDao();
    public abstract PatientIdentifierDao patientIdentifierDao();
    public abstract PatientIdentifierTypeDao patientIdentifierTypeDao();
    public abstract ObsDao obsDao();
    public abstract EncounterDao encounterDao();
    public abstract EncounterProviderDao encounterProviderDao();
    public abstract EncounterTypeDao encounterTypeDao();
    public abstract VisitDao visitDao();
    public abstract VisitTypeDao visitTypeDao();
    public abstract MetricsDao metricsDao();
    public abstract ClientFtsDao clientFtsDao();
    public abstract ConceptAnswerDao conceptAnswerDao();
    public abstract ConceptNameDao conceptNameDao();
    public abstract ConceptDao conceptDao();
    public abstract ConceptAnswerNameDao conceptAnswerNameDao();
    public abstract DrugDao drugDao();
    public abstract IdentifierDao identifierDao();

    //Derived
    public abstract ClientDao clientDao();
    public abstract FacilityDistrictCodeDao facilityDistrictCodeDao();
    public abstract GenericDao genericDao();
    public abstract PersonIdentifierDao personIdentifierDao();

    //System
    public abstract EntityMetadataDao entityMetadataDao();

    //database getter
    public static Database getDatabase(final Context context){

        if (dbInstance == null)
              synchronized (Database.class){
                  if (dbInstance == null)
                     dbInstance = Room.databaseBuilder(context.getApplicationContext(),Database.class, DATABASE_NAME)
                             .addMigrations(Migrations.MIGRATION_2_3)
                             .build();
              }

         return dbInstance;
    }
}