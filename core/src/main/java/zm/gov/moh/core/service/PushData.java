package zm.gov.moh.core.service;

import android.content.Intent;

import com.squareup.moshi.Moshi;

import androidx.annotation.Nullable;
import io.reactivex.Maybe;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class PushData extends SyncService {

    Moshi moshi;
    public PushData(){
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);

        //personName

    }

    @Override
    protected void executeAsync() {
        moshi = new Moshi.Builder().build();

        for (EntityType entitiyId:EntityType.values())
            pushEntityRemote(entitiyId);

    }

    protected void pushEntityRemote(EntityType entityType){

        long[] syncedEntityId = db.entityMetadataDao().findEntityIdByTypeSyncStatus(entityType.getId(), SYNCED);
        Response[] responses = null;
        final int RETRY_ATTEMPTS = 5;

        switch (entityType){

            case PERSON_NAME:
                PersonName[] unsyncedPersonName = db.personNameDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedPersonName.length != 0)
                    responses = restApi.putPersonNames(accesstoken, unsyncedPersonName).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case PERSON:
                Person[] unsyncedPerson = db.personDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedPerson.length != 0)
                    responses = restApi.putPersons(accesstoken, unsyncedPerson).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case PERSON_ADDRESS:
                PersonAddress[] unsyncedPersonAddress = db.personAddressDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedPersonAddress.length != 0)
                    responses = restApi.putPersonAddresses(accesstoken, unsyncedPersonAddress).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case PATIENT:
                Patient[] unsyncedPatient = db.patientDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedPatient.length != 0)
                    responses = restApi.putPatients(accesstoken, unsyncedPatient).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case PATIENT_IDENTIFIER:
                PatientIdentifier[] unsyncedPatietIdentifier = db.patientIdentifierDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedPatietIdentifier.length != 0)
                    responses = restApi.putPatientIdentifiers(accesstoken, unsyncedPatietIdentifier).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case OBS:
                Obs[] unsyncedObs = db.obsDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedObs.length != 0)
                    responses = restApi.putObs(accesstoken, unsyncedObs).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case ENCOUNTER:
                Encounter[] unsyncedEncounter = db.encounterDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedEncounter.length != 0)
                    responses = restApi.putEncounter(accesstoken, unsyncedEncounter).retry(RETRY_ATTEMPTS).blockingGet();
                break;

            case VISIT:
                Visit[] unsyncedVisit = db.visitDao().findEntityNotWithId(syncedEntityId);
                if(unsyncedVisit.length != 0)
                    responses = restApi.putVisit(accesstoken, unsyncedVisit).retry(RETRY_ATTEMPTS).blockingGet();
                break;

        }
    }

    @FunctionalInterface
    interface BiConsumer<T1,T2>{

        Maybe<Response[]> get(T1 param1, T2 param2);
    }
}