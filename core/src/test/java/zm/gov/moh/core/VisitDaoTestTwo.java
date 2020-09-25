package zm.gov.moh.core;

import android.content.Context;

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

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.BaseWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class VisitDaoTestTwo  {
    @Mock
    Database db;
    @Mock
    private VisitDao visitDao;
    @Mock
    private EncounterDao encounterDao;
    @Mock
    private EntityMetadata[] entityMetadata = new EntityMetadata[1000] ;
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
        //MockitoAnnotations.initMocks(this);
        EntityMetadata entityMetadata = Mockito.mock(EntityMetadata.class, Mockito.CALLS_REAL_METHODS);
        /*VisitDao = db.visitDao();
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
        db.encounterDao().insert(encounterEntities);*/
    }
    @After
    public void tearDown() throws Exception {
//        db.close();
    }
    @Test
        public <T> void testAddPatientVisit_returnsNewPatientWithId() {
        EntityMetadata[] entityMetadata = new EntityMetadata [1000];
        final OngoingStubbing<T> tOngoingStubbing = (OngoingStubbing<T>)
                when(db.entityMetadataDao().insert1(entityMetadata)).thenAnswer((Answer<Visit>) invocation -> {
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




