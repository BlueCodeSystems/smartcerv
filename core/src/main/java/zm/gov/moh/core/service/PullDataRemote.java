package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemote extends RemoteService {

    public PullDataRemote(){
        super(ServiceManager.Service.PULL_ENTITY_REMOTE);
    }

    List<Long> localPatientIds;

    final String MIN_DATETIME = "1970-01-01T00:00:00";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    protected void executeAsync() {

        onTaskStarted();

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_SYNC_DATE,null);

        if(lastSyncDate == null){
            lastSyncDate = MIN_DATETIME;
        }

        //Patient identifier
        ConcurrencyUtils.consumeBlocking(
                patientIdentifierEntities -> {

                    List<String> localIdentifiers = db.patientIdentifierDao().getLocal();


                    localPatientIds = Observable.fromArray(patientIdentifierEntities).filter(
                            patientIdentifierEntity -> localIdentifiers.contains(patientIdentifierEntity.getIdentifier()))
                            .map(patientIdentifierEntity -> patientIdentifierEntity.getPatientId())
                            .toList()
                            .blockingGet();

                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifierEntities);
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accessToken), //producer
                TIMEOUT);



        //Patients
        ConcurrencyUtils.consumeBlocking(


                patient -> {
                    List<Long> indexIds = db.patientDao().getIds();
                    List<PatientEntity> patientEntities = Observable.fromArray(patient)
                            .filter((patientEntity -> (!localPatientIds.contains(patientEntity.getPatientId()) && !indexIds.contains(patientEntity.getPatientId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().patientDao().insert(patientEntities);
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accessToken), //producer
                TIMEOUT);

        //Person Names
        ConcurrencyUtils.consumeBlocking(
                personNames -> {

                    List<Long> indexIds = db.personNameDao().getIds();

                    List<PersonName> personNameList = Observable.fromArray(personNames)
                            .filter((personName -> (!localPatientIds.contains(personName.getPersonId()) && !indexIds.contains(personName.getPersonId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().personNameDao().insert(personNameList);
                },//consumer
                this::onError,
                repository.getRestApi().getPersonNames(accessToken), //producer
                TIMEOUT);

        //Person Address
        ConcurrencyUtils.consumeBlocking(
                personAddresses -> {

                    List<Long> indexIds = db.personAddressDao().getIds();
                    List<PersonAddress> personAddressList = Observable.fromArray(personAddresses)
                            .filter((personAddress ->(!localPatientIds.contains(personAddress.getPersonId()) && !indexIds.contains(personAddress.getPersonId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().personAddressDao().insert(personAddressList);
                }
                , //consumer
                this::onError,
                repository.getRestApi().getPersonAddresses(accessToken), //producer
                TIMEOUT);

        //Person
        ConcurrencyUtils.consumeBlocking(
                persons -> {
                    List<Long> indexIds = db.personDao().getIds();
                    List<Person> personList = Observable.fromArray(persons)
                            .map(person -> {

                                db.personIdentifierDao().insert(new PersonIdentifier(person));
                                return person;
                            })
                            .filter((person -> (!localPatientIds.contains(person.getPersonId()) && !indexIds.contains(person.getPersonId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().personDao().insert(personList);
                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken), //producer
                TIMEOUT);



        if(lastSyncDate.equals(MIN_DATETIME)) {

            //Observations
            ConcurrencyUtils.consumeAsync(
                    obs -> {
                        repository.getDatabase().obsDao().insert(obs);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getObs(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();

            //Visit
            ConcurrencyUtils.consumeAsync(
                    visits -> {
                        repository.getDatabase().visitDao().insert(visits);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getVisit(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();

            //Encounter
            ConcurrencyUtils.consumeAsync(
                    encounterEntities -> {
                        repository.getDatabase().encounterDao().insert(encounterEntities);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getEncounters(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();
        }

        onTaskCompleted();
    }

    @Override
    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted) {
            notifyCompleted();
            repository.getDefaultSharePrefrences().edit().putString(Key.LAST_SYNC_DATE,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
            mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.VISIT);
                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                        .putExtras(mBundle)
                        .start();
        }
    }


    public void persistPatientIdentifier(PatientIdentifierEntity... patientIdentifiers){


    }
}