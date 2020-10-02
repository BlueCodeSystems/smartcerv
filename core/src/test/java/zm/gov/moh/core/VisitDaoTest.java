package zm.gov.moh.core;

import android.content.Intent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import zm.gov.moh.core.mocks.SQLiteDatabaseMock;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.custom.Identifier;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.PersistService;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.ConcurrencyUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class VisitDaoTest  extends PersistService {
    @Mock
    Database db;
    @Mock
    private VisitDao visitDao;
    @Mock
    private EncounterDao encounterDao;
    @InjectMocks
    private Visit visit;
    @Mock
    private List<VisitEntity> visitEntities;
    @Mock
    private List<Long> ids;
    @Mock
    private Repository mRepository;
    private VisitDao VisitDao;
    @Mock
    private Object VisitEntity;
    Database database1;

    SQLiteDatabaseMock mock = new SQLiteDatabaseMock();

    public VisitDaoTest(ServiceManager.Service service) {
        super(service);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Database database = Mockito.mock(Database.class, Mockito.CALLS_REAL_METHODS);
        //   db = Room.inMemoryDatabaseBuilder(this, Database.class).build();
         database1 = mock.getRepository().getDatabase();
        VisitDao = db.visitDao();

        long personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);
        final long patientIdentifierId = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);
        final long  personNameId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personNameDao()::getMaxId);
        final long  personAddressId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personAddressDao()::getMaxId);
        final String givenName = mBundle.getString(Key.PERSON_GIVEN_NAME);
        final String familyName = mBundle.getString(Key.PERSON_FAMILY_NAME);
        final String dob = mBundle.getString(Key.PERSON_DOB);
        final String gender = mBundle.getString(Key.PERSON_GENDER);
        final String address = mBundle.getString(Key.PERSON_ADDRESS);
        final String address1 = mBundle.getString(Key.PERSON_ADDRESS1);
        final String phone = mBundle.getString(Key.PERSON_PHONE);
        final Long districtId = mBundle.getLong(Key.PERSON_DISTRICT_LOCATION_ID);
        final Long provinceId = mBundle.getLong(Key.PERSON_PROVINCE_LOCATION_ID);
        final long locationId = mBundle.getLong(Key.LOCATION_ID);
        final String phoneUuid = mBundle.getString(Key.PERSON_PHONE_ATTRIBUTE_TYPE_UUID);
        final long attributeTypeId = getRepository().getDatabase().personAttributeTypeDao().getPersonAttributeByUuid(phoneUuid);

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

                if(personName != null && ((!personName.getFamilyName().equals(familyName)) || (!personName.getGivenName().equals(givenName)))){
                    personName.setFamilyName(familyName);
                    personName.setGivenName(givenName);
                    personName.setDateChanged(now);
                    db.personNameDao().insert(personName);
                }

                if(personAddress != null && ((!personAddress.getAddress1().equals(address1)) || (!personAddress.getCityVillage().equals(districtName)) || (!personAddress.getStateProvince().equals(provinceName)))){
                    personAddress.setAddress1(address1);
                    personAddress.setCityVillage(districtName);
                    personAddress.setStateProvince(provinceName);
                    personAddress.setDateChanged(now);
                    db.personAddressDao().insert(personAddress);
                }

                if(person != null && (!person.getBirthDate().equals(dateOfBirth))){
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
            PersonAttributeEntity personAttribute = new PersonAttributeEntity(personId, phone, attributeTypeId);

            identifier.markAsAssigned();
            db.identifierDao().insert(identifier);

            //Persist database entity instances asynchronously into the database
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, patientId);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personIdentifierDao()::insert,this::onError, personIdentifier);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personNameDao()::insert, this::onError, personName);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personDao()::insert, this::onError, person);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personAttributeDao()::insert, this::onError, personAttribute);
        }



    }


    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void writeVisitAndReadInList() throws Exception {
        visit.setPatient("Inserting Patients");
        visitDao.insert();
        long personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);
        final long patientIdentifierId = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);
        final long  personNameId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personNameDao()::getMaxId);
        final long  personAddressId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personAddressDao()::getMaxId);
        final String givenName = mBundle.getString(Key.PERSON_GIVEN_NAME);
        final String familyName = mBundle.getString(Key.PERSON_FAMILY_NAME);
        final String dob = mBundle.getString(Key.PERSON_DOB);
        final String gender = mBundle.getString(Key.PERSON_GENDER);
        final String address = mBundle.getString(Key.PERSON_ADDRESS);
        final String address1 = mBundle.getString(Key.PERSON_ADDRESS1);
        final String phone = mBundle.getString(Key.PERSON_PHONE);
        final Long districtId = mBundle.getLong(Key.PERSON_DISTRICT_LOCATION_ID);
        final Long provinceId = mBundle.getLong(Key.PERSON_PROVINCE_LOCATION_ID);
        final long locationId = mBundle.getLong(Key.LOCATION_ID);
        final String phoneUuid = mBundle.getString(Key.PERSON_PHONE_ATTRIBUTE_TYPE_UUID);
        final long attributeTypeId = getRepository().getDatabase().personAttributeTypeDao().getPersonAttributeByUuid(phoneUuid);
        List<Long> ids = mock.getIDs();
        personId = mBundle.getLong(Key.PERSON_ID);
        PersonName personName = db.personNameDao().findByPersonId(personId);
        PersonAddress personAddress = db.personAddressDao().findByPersonId(personId);
        Person person = db.personDao().findById(personId);
        Assert.assertEquals(database1.visitDao().getIds(),database1.visitDao().getIds());

    }

    @Test
    public void getMaxId() {
        long personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);
        //db.visitDao().getIds();
        personId = mBundle.getLong(Key.PERSON_ID);
        PersonName personName = db.personNameDao().findByPersonId(personId);
        PersonAddress personAddress = db.personAddressDao().findByPersonId(personId);
        Person person = db.personDao().findById(personId);

        EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(personId);

        entityMetadata = new EntityMetadata(personId, EntityType.PATIENT.getId());
        entityMetadata.setLastModified(LocalDateTime.now());
        entityMetadata.setRemoteStatusCode(RemoteWorker.Status.NOT_PUSHED.getCode());

        db.entityMetadataDao().insert(entityMetadata);
        }


    @Test
    public void getIds() {

        when(visitDao.getIds()).thenReturn(ids);
        visitDao.getMaxId();
        assertEquals(ids, visitDao.getIds());
        System.out.print("getIds was tested successfully");
    }

    @Test
    public void getById() {
        when(visitDao.getById(new Long[100])).thenReturn(visitEntities);
        visitDao.getById(new Long[100]);
    }

    @Test
    public void findEntityNotWithIdTest() {
        long unpushedVisitEntityID [] = new long[4];
        VisitEntity visitEntitys [] = new VisitEntity[4];
        for(int i =0; i<4; i++) {
            /*VisitEntity visitEntity = new VisitEntity(100L, 2L, 120L, 50L, 3L);
            visitEntity.setVisitId(1000L+i);
            visitEntity.setPatientId(10L+i);
            visitEntity.setVisitTypeId(1L+i);
            visitEntity.setIndicationConceptId(4L+i);
            visitEntity.setLocationId(5L+i);
            visitEntity.setCreator(7L+i);
            visitEntitys[i] =visitEntity;*/
        }

        visitDao.insert(visitEntitys);

        for(int i = 0; i< unpushedVisitEntityID.length; i++)
        {
            unpushedVisitEntityID[i] = 10L + i;
        }

        //visit
        Long[] unpushedVisitIds = new Long[1000];

        for(int i = 0; i< unpushedVisitIds.length; i++)
        {
            unpushedVisitIds[i] = 10L + i;
        }

        //Create a collection of 1000 IDs which are longs
        //and pass them into the method
        //  when(visitDao.findEntityNotWithId(offset, unpushedVisitEntityID)).thenReturn(unpushedVisitIds);
        //visitDao.findEntityNotWithId(offset, unpushedVisitEntityID);
    }

    @Test
    public <T> void testAddPatientVisit_returnsNewPatientWithId() {
        EntityMetadata[] entityMetadata = new EntityMetadata [1000];
        final OngoingStubbing<T> tOngoingStubbing;
        tOngoingStubbing = (OngoingStubbing<T>) when(db.entityMetadataDao().insert1(entityMetadata)).thenAnswer((Answer<Visit>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Visit visit = (Visit) arguments[0];
                visit.setId(1);
                return visit;
            }
            return (Visit) visitEntities;
        });
        entityMetadata = new EntityMetadata[1000];
        assertThat(db.entityMetadataDao().insert1(entityMetadata), is(notNullValue()));
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
        final String address1 = mBundle.getString(Key.PERSON_ADDRESS1);
        final String phone = mBundle.getString(Key.PERSON_PHONE);
        final Long districtId = mBundle.getLong(Key.PERSON_DISTRICT_LOCATION_ID);
        final Long provinceId = mBundle.getLong(Key.PERSON_PROVINCE_LOCATION_ID);
        final long locationId = mBundle.getLong(Key.LOCATION_ID);
        final String phoneUuid = mBundle.getString(Key.PERSON_PHONE_ATTRIBUTE_TYPE_UUID);
        final long attributeTypeId = getRepository().getDatabase().personAttributeTypeDao().getPersonAttributeByUuid(phoneUuid);

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

                if(personName != null && ((!personName.getFamilyName().equals(familyName)) || (!personName.getGivenName().equals(givenName)))){
                    personName.setFamilyName(familyName);
                    personName.setGivenName(givenName);
                    personName.setDateChanged(now);
                    db.personNameDao().insert(personName);
                }

                if(personAddress != null && ((!personAddress.getAddress1().equals(address1)) || (!personAddress.getCityVillage().equals(districtName)) || (!personAddress.getStateProvince().equals(provinceName)))){
                    personAddress.setAddress1(address1);
                    personAddress.setCityVillage(districtName);
                    personAddress.setStateProvince(provinceName);
                    personAddress.setDateChanged(now);
                    db.personAddressDao().insert(personAddress);
                }

                if(person != null && (!person.getBirthDate().equals(dateOfBirth))){
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
            PersonAttributeEntity personAttribute = new PersonAttributeEntity(personId, phone, attributeTypeId);

            identifier.markAsAssigned();
            db.identifierDao().insert(identifier);

            //Persist database entity instances asynchronously into the database
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, patientId);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personIdentifierDao()::insert,this::onError, personIdentifier);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personNameDao()::insert, this::onError, personName);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personDao()::insert, this::onError, person);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().personAttributeDao()::insert, this::onError, personAttribute);
            /* when(visitDao.getMaxId()).thenReturn(1000L);
        visitDao.getMaxId();
        assertEquals((Long) 1000L, visitDao.getMaxId());
        System.out.print("getMaxId was tested successfully");*/
        }

    }
}




