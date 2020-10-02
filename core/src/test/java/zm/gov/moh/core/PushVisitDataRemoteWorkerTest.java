/*package zm.gov.moh.core;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import zm.gov.moh.core.mocks.DatabaseMock;
import zm.gov.moh.core.repository.database.dao.domain.VisitDaoMock;
import zm.gov.moh.core.mocks.VisitEntityMock;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
*/
/*public class PushVisitDataRemoteWorkerTest {

    private PushVisitDataRemoteWorker pushVisitDataRemoteWorker = mock(PushVisitDataRemoteWorker.class);

    private final ThreadLocal<DatabaseMock> db = new ThreadLocal<DatabaseMock>();

    private VisitDaoMock visitDaoMock  = new VisitDaoMock() {
        @Override
        public LocalDateTime getMaxDatetime(long locationId) {
            return null;
        }

        @Override
        public Long getMaxId() {
            return null;
        }

        @Override
        public List<Long> getIds() {
            return null;
        }

        @Override
        public LiveData<List<VisitEntity>> getByPatientIdLive(long id) {
            return null;
        }

        @Override
        public Long[] getByDatetime(String dateTime, long locationId) {
            return new Long[0];
        }

        @Override
        public VisitEntity[] getByPatientId(long id) {
            return new VisitEntity[0];
        }

        @Override
        public void updatePatientId(long id, long patientId, LocalDateTime dateTime) {

        }

        @Override
        public VisitEntity getByDate(LocalDateTime dateTime, long offset) {
            return null;
        }

        @Override
        public VisitEntity getAll(LocalDateTime dateTime) {
            return null;
        }

        @Override
        public List<VisitEntity> getAll() {
            return null;
        }

        @Override
        public VisitEntity getByDate(long offset) {
            return null;
        }

        @Override
        public void deleteById(long[] visitId, long offset) {

        }

        @Override
        public List<VisitEntity> getById(Long[] id) {
            return null;
        }

        @Override
        public LocalDateTime getDateStartTimeByVisitId(long id) {
            return null;
        }

        @Override
        public VisitEntity getById(Long id) {
            return null;
        }

        @Override
        public LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes) {
            return null;
        }

        @Override
        public void insert(VisitEntity... visits) {

        }

        @Override
        public void insert1(EntityMetadata... visits) {

        }

        @Override
        public void insert2(Long[] id) {

        }

        @Override
        public void updateVisit(VisitEntity visit) {

        }

        @Override
        public void updateVisit(EntityMetadata entityMetadata) {

        }

        @Override
        public Long[] findEntityNotWithId(long offsetId, long... id) {
            return new Long[0];
        }

        @Override
        public Long[] findEntityNotWithId2(long offsetId, int entityTypeId, short remoteStatus) {
            return new Long[0];
        }

        @Override
        public void replaceLocalPatientId(long localPatientId, long remotePatientId) {

        }

        @Override
        public void abortVisitEncounter(long visitId, long locationId) {

        }

        @Override
        public void abortVisit(long visitId, long locationId) {

        }

        @Override
        public void abortVisitObs(long visitId, long locationId) {

        }
    };


    private Long[] entityIds;

    private int entityTypeId;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    public void test() {
        MockitoAnnotations.initMocks(this);
        //PushVisitDataRemoteWorkerMock pushVisitDataRemoteWorkerMock = (PushVisitDataRemoteWorkerMock) PushVisitDataRemoteWorkerMock.getUnpushedVisitEntityId();
        //pushVisitDataRemoteWorkerMock.createVisits(1000L);
        Mockito.verify(visitDaoMock).findEntityNotWithId(1000L);
        EncounterEntity encounterEntity = new EncounterEntity();
        for (Long aLong : db.get().visitDaoMock().findEntityNotWithId(1000L)) {
                for (Long entityId : entityIds) {
                    EntityMetadata entityMetadata;
                    entityMetadata = new EntityMetadata(entityTypeId, RemoteWorker.Status.PUSHED.getCode());
                    db.get().entityMetadataDao().insert(entityMetadata);

                }
            };
        }

      /*  @Test
        public void getUnpushedVisitEntityId() {
        //PushVisitDataRemoteWorkerMock pushVisitDataRemoteWorkerMock = new PushVisitDataRemoteWorkerMock((Context) visitDaoMock);
        //PushVisitDataRemoteWorkerMock pushVisitDataRemoteWorkerMock;
           // pushVisitDataRemoteWorkerMock = (PushVisitDataRemoteWorkerMock) PushVisitDataRemoteWorkerMock.getUnpushedVisitEntityId();
            when(visitDaoMock.findEntityNotWithId(1000L)).thenReturn(createTestEntity());
        //VisitEntityMock actual = (VisitEntityMock) pushVisitDataRemoteWorkerMock.createVisits(1000L);
       // Assert.assertEquals("Visit ID", actual.getVisitId());
        //Assert.assertEquals("Visit Type ID", actual.getVisitTypeId());
        Mockito.verify(visitDaoMock).findEntityNotWithId(1000L);
    }

    private Long[] createTestEntity() {
        VisitEntityMock visitEntityMock = new VisitEntityMock();
        visitEntityMock.setVisitId(1L);
        visitEntityMock.setVisitTypeId(2L);
        return createTestEntity();
    }*/
/*


        /*TODO Recreate the current issue
        /*TODO  1. Create 1001 dummy metaDataDaos then test
        /*TODO 2. Step call the query and see if the results are correct
        /*TODO  3. Create 50000.*/


