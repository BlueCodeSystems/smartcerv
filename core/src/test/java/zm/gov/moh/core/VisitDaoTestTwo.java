package zm.gov.moh.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;

import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class VisitDaoTestTwo {
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


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Database database = Mockito.mock(Database.class, Mockito.CALLS_REAL_METHODS);

        //   db = Room.inMemoryDatabaseBuilder(this, Database.class).build();

        VisitDao = db.visitDao();
        long pushedEntityID[] = new long[4];
        EncounterEntity[] encounterEntities = new EncounterEntity[4];
        for (int i = 0; i < 4; i++) {
            short code = (short) ((4L + i));
            long entityid = (long) ((1000L + i));
            int entityTypeId = (int) ((10L + i));
            EncounterEntity encounterEntity = new EncounterEntity();
            encounterEntity.setEncounterId(1000L + i);
            encounterEntity.setEncounterType((int) (10L + i));
            encounterEntity.setEncounterId(1L + i);
            encounterEntity.setLocationId(1L + i);
            encounterEntity.setPatientId(12 + i);
            encounterEntity.setVisitId(12L + i);
            encounterEntities[i] = encounterEntity;
        }
        database.encounterDao().insert(encounterEntities);
    }


    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void writeVisitAndReadInList() throws Exception {
        visit.setPatient("Ju");
        visitDao.insert();
    }

    @Test
    public void getMaxId() {
        db.visitDao().getIds();
       /* when(visitDao.getMaxId()).thenReturn(1000L);
        visitDao.getMaxId();
        assertEquals((Long) 1000L, visitDao.getMaxId());
        System.out.print("getMaxId was tested successfully");

        */
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
            //VisitEntity visitEntity = new VisitEntity(100L, 2L, 120L, 50L, 3L);
            //visitEntity.setVisitId(1000L+i);
           // visitEntity.setPatientId(10L+i);
          //  visitEntity.setVisitTypeId(1L+i);
          //  visitEntity.setIndicationConceptId(4L+i);
         //   visitEntity.setLocationId(5L+i);
          //  visitEntity.setCreator(7L+i);
          //  visitEntitys[i] =visitEntity;
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
}




