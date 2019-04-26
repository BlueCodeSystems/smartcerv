package zm.gov.moh.core.service;

import android.os.Bundle;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public class DemographicsPersist extends PersistService {

    public DemographicsPersist(){
        super("Demographics Persist");
    }

    @Override
    public void persistAsync(Bundle bundle) {

        final long personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);
        final long patientIdentifierId = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);
        final String givenName = bundle.getString(Key.PERSON_GIVEN_NAME);
        final String familyName = bundle.getString(Key.PERSON_FAMILY_NAME);
        final String dob = bundle.getString(Key.PERSON_DOB);
        final String gender = bundle.getString(Key.PERSON_GENDER);
        final String address = bundle.getString(Key.PERSON_ADDRESS);
        final String district = bundle.getString(Key.PERSON_DISTRICT_LOCATION_ID);
        final String province = bundle.getString(Key.PERSON_PROVINCE_LOCATION_ID);
        final long locationId = bundle.getLong(Key.LOCATION_ID);


        if (givenName != null && familyName != null && gender != null && address != null && district != null && province != null && dob != null) {


            LocalDateTime dateOfBirth = LocalDateTime.parse(dob, DateTimeFormatter.ISO_ZONED_DATE_TIME);

            //Create database entity instances
            PatientIdentifier patientId = new PatientIdentifier(patientIdentifierId, personId, String.valueOf(personId).substring(14), 3, PREFERRED, locationId, LocalDateTime.now());
            PersonName personName = new PersonName(personId, givenName, familyName, PREFERRED);
            Person person = new Person(personId, dateOfBirth, gender);
            PersonAddress personAddress = new PersonAddress(personId, address, district, province, PREFERRED);
            Patient patient = new Patient(personId, LocalDateTime.now());

            //Persist database entity instances asynchronously into the database
            getRepository().consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, patientId);
            getRepository().consumeAsync(getRepository().getDatabase().personNameDao()::insert, this::onError, personName);
            getRepository().consumeAsync(getRepository().getDatabase().personDao()::insert, this::onError, person);
            getRepository().consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);
            getRepository().consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Exception e = new Exception(throwable);
    }

}
