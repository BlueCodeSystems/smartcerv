package zm.gov.moh.core.service;

import android.content.Intent;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.custom.Identifier;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PersistDemographics extends PersistService {

    public PersistDemographics(){
        super(ServiceManager.Service.PERSIST_DEMOGRAPHICS);
    }

    @Override
    protected void executeAsync() {

        long personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);
        final long patientIdentifierId = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);
        final long  personNameId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personNameDao()::getMaxId);
        final long  personAddressId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personAddressDao()::getMaxId);
        final String givenName = mBundle.getString(Key.PERSON_GIVEN_NAME);
        final String familyName = mBundle.getString(Key.PERSON_FAMILY_NAME);
        final String dob = mBundle.getString(Key.PERSON_DOB);
        final String gender = mBundle.getString(Key.PERSON_GENDER);
        final String address = mBundle.getString(Key.PERSON_ADDRESS);
        final Long districtId = mBundle.getLong(Key.PERSON_DISTRICT_LOCATION_ID);
        final Long provinceId = mBundle.getLong(Key.PERSON_PROVINCE_LOCATION_ID);
        final long locationId = mBundle.getLong(Key.LOCATION_ID);







        if (givenName != null && familyName != null && address != null && districtId != null && provinceId != null && dob != null) {

            LocalDateTime dateOfBirth = LocalDateTime.parse(dob + MID_DAY_TIME, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            LocalDateTime now = LocalDateTime.now();

            String districtName = getRepository().getDatabase().locationDao().getNameById(districtId);
            String provinceName = getRepository().getDatabase().locationDao().getNameById(provinceId);
            Identifier identifier = db.identifierDao().getIdentifierNotAssigned();

            //Edit client block
            if(mBundle.getLong(Key.PERSON_ID,0) != 0){

                personId = mBundle.getLong(Key.PERSON_ID);

                PersonName personName = db.personNameDao().findByPersonId(personId);
                PersonAddress personAddress = db.personAddressDao().findByPersonId(personId);
                Person person = db.personDao().findById(personId);

                if(personName != null){
                    personName.setFamilyName(familyName);
                    personName.setGivenName(givenName);
                    //check if patient has middle name
                    personName.setMiddleName(mBundle.getString(Key.MIDDLE_NAME));

                    personName.setDateChanged(now);
                    db.personNameDao().insert(personName);
                }

                if(personAddress != null){
                    personAddress.setAddress1(address);
                    personAddress.setCityVillage(districtName);
                    personAddress.setStateProvince(provinceName);
                    personAddress.setDateChanged(now);
                    db.personAddressDao().insert(personAddress);
                }

                if(person != null){
                    person.setBirthDate(dateOfBirth);
                    person.setDateChanged(now);
                    db.personDao().insert(person);
                }

                EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(personId);

                if(entityMetadata == null)
                    entityMetadata = new EntityMetadata(personId, EntityType.PATIENT.getId());
                entityMetadata.setLastModified(LocalDateTime.now());
                entityMetadata.setRemoteStatusCode(RemoteWorker.Status.NOT_PUSHED.getCode());

                db.entityMetadataDao().insert(entityMetadata);

                return;
            }

            if(identifier == null){

                mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.INSUFFICIENT_IDENTIFIERS_FAILED_REGISTRATION));
                return;
            }


            //Create database entity instances
            PatientIdentifierEntity patientId = new PatientIdentifierEntity(patientIdentifierId, personId, identifier.getIdentifier(), 3, NOT_PREFERRED, locationId, now);
            PersonName personName = new PersonName(personNameId,personId, givenName, familyName, PREFERRED, now);
            Person person = new Person(personId, dateOfBirth, gender,now);
            PersonAddress personAddress = new PersonAddress(personAddressId,personId, address, districtName, provinceName, PREFERRED, now);
            PatientEntity patient = new PatientEntity(personId, now);
            PersonIdentifier personIdentifier = new PersonIdentifier(identifier.getIdentifier(),personId);

            identifier.markAsAssigned();
            db.identifierDao().insert(identifier);

            //Persist database entity instances asynchronously into the database
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, patientId);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personIdentifierDao()::insert,this::onError, personIdentifier);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personNameDao()::insert, this::onError, personName);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personDao()::insert, this::onError, person);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);
        }
    }
}
