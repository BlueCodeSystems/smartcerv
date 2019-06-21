package zm.gov.moh.core.service;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.util.Function;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Encounter;
import zm.gov.moh.core.model.Obs;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PushDataRemote extends RemoteService {

    public PushDataRemote(){
        super(ServiceManager.Service.PUSH_ENTITY_REMOTE);
    }

    @Override
    protected void executeAsync() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        for (EntityType entitiyId:EntityType.values())
            ConcurrencyUtils.consumeAsync(this::pushInfoRemote, this::onError, entitiyId, batchVersion);

    }

   protected void pushInfoRemote(EntityType entityType, long batchVersion){

        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(entityType.getId(), Status.PUSHED.getCode());
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;


        switch (entityType){

            case PATIENT:

                onTaskStarted();
                Patient[] patients;
                int index = 0;
                Long[] unpushedPatientEntityId = db.patientDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatientEntityId.length != 0) {

                    patients = new Patient[unpushedPatientEntityId.length];

                    for(Long patientEntityId : unpushedPatientEntityId){

                        Patient patient = createPatient(patientEntityId);
                        if(patient != null)
                            patients[index++] = patient;
                    }

                    restApi.putPatients(accessToken, batchVersion, patients)
                            .subscribe(onComplete(unpushedPatientEntityId, entityType.getId()), this::onError);

                    onTaskCompleted();
                }else
                    onTaskCompleted();
                break;

            case VISIT:

                onTaskStarted();
                Visit[] visits;
                int visitIndex = 0;
                Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedEntityId);
                int cool;
                if(unpushedVisitEntityId.length > 0) {

                        Visit[] patientVisits = createVisits(unpushedVisitEntityId);
                        if (patientVisits != null && patientVisits.length != 0)
                            cool = patientVisits.length;


                    restApi.putVisit(accessToken, batchVersion, patientVisits)
                            .subscribe(onComplete(unpushedVisitEntityId, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
        }
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){


        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
            onTaskCompleted();
        };
    }

    public Patient createPatient(long patientId){

       Person person = db.personDao().findById(patientId);
       PersonName personName = db.personNameDao().findPersonNameById(patientId);
       PersonAddress personAddress = db.personAddressDao().findByPersonId(patientId);
       List<PatientIdentifier> patientIdentifiers = db.patientIdentifierDao().findAllByPatientId(patientId);
       List<PersonAttribute> personAttributes = db.personAttributeDao().findByPersonId(patientId);

       return new Patient.Builder()
               .setPerson(person)
               .setPersonName(personName)
               .setPersonAddress(personAddress)
               .setAttributes(personAttributes)
               .setIdentifiers(patientIdentifiers)
               .build();
    }

    public Visit[] createVisits(Long ...visitEntityId){

        List<VisitEntity> visitEntities = db.visitDao().getById(visitEntityId);

        if(visitEntities.size() > 0) {

            int visitIndex = 0;
            Visit[] visits = new Visit[visitEntities.size()];

            List<Long> visitIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                    .toList().blockingGet();

            List<EncounterEntity> encounterEntities = db.encounterDao().getByVisitId(visitIds);

            List<Long> encounterIds = Observable.fromIterable(encounterEntities)
                    .map(encounterEntity -> encounterEntity.getEncounterId())
                    .toList()
                    .blockingGet();

            List<ObsEntity> obsEntities = db.obsDao().getObsByEncounterId(encounterIds);



            for (VisitEntity visitEntity : visitEntities) {

                Visit.Builder visit = normalizeVisit(visitEntity);

                List<EncounterEntity> visitEncounter = Observable.fromIterable(encounterEntities)
                        .filter( encounterEntity -> (encounterEntity.getVisitId().longValue() == visitEntity.getVisitId().longValue())).toList().blockingGet();


                Encounter[] encounters = new Encounter[visitEncounter.size()];
                int index = 0;

                for (EncounterEntity encounterEntity : visitEncounter) {

                    if(encounterEntity == null)
                        continue;
                    Encounter encounter = normalizeEncounter(encounterEntity);


                    List<ObsEntity> encounterObs = Observable.fromIterable(obsEntities)
                            .filter(obsEntity -> (obsEntity.getEncounterId() == encounterEntity.getEncounterId()))
                            .toList()
                            .blockingGet();

                     Obs[] obs = Observable.fromIterable(encounterObs)
                            .map((this::normalizeObs))
                            .toList()
                            .blockingGet()
                            .toArray(new Obs[encounterObs.size()]);


                    encounter.setObs(obs);

                    encounters[index++] = encounter;
                }

                visits[visitIndex++] = visit.setEncounters(encounters).build();

            }

            return visits;
        }
        return null;
    }

    public Obs normalizeObs(ObsEntity obsEntity){

        Obs obs = new Obs();
        String concept = db.conceptDao().getConceptUuidById(obsEntity.getConceptId());
        String person = db.personDao().findByPatientId(obsEntity.getPersonId());


        obs.setConcept(concept);
        obs.setObsDatetime(obsEntity.getObsDateTime());
        obs.setPerson(person);

        if(obsEntity.getValueCoded() != null )
            obs.setValue(db.conceptDao().getConceptUuidById(obsEntity.getValueCoded()));
        else if (obsEntity.getValueDateTime() != null )
            obs.setValue(obsEntity.getValueDateTime().toString());
        else if (obsEntity.getValueNumeric() != null)
            obs.setValue(obsEntity.getValueNumeric().toString());
        else if (obsEntity.getValueText() != null)
            obs.setValue(obsEntity.getValueText());

        return obs;
    }

    public Visit.Builder normalizeVisit(VisitEntity visitEntity){

        Visit.Builder visit = new  Visit.Builder();
        String visitType = db.visitTypeDao().getUuidVisitTypeById(visitEntity.getVisitTypeId());
        String patient = db.personDao().findByPatientId(visitEntity.getPatientId());
        String location = db.locationDao().getUuidById(visitEntity.getLocationId());

        visit.setVisitType(visitType)
                .setPatient(patient)
                .setLocation(location)
                .setStartDatetime(visitEntity.getDateStarted())
                .setStopDatetime(visitEntity.getDateStopped());

        return visit;
    }

    public Encounter normalizeEncounter(EncounterEntity encounterEntity){

        Encounter encounter = new Encounter();

        String location = db.locationDao().getUuidById(encounterEntity.getLocationId());
        String encounterType = db.encounterTypeDao().getUuidById(encounterEntity.getEncounterType());

        encounter.setLocation(location);
        encounter.setEncounterType(encounterType);
        encounter.setEncounterDatetime(encounterEntity.getEncounterDatetime());

        return encounter;
    }
}
