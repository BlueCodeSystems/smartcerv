package zm.gov.moh.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.system.EntityMetadataDao;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class PushVisitDataRemoteWorkerTest {

    @Mock
    VisitDao visitDaoMock;

    @Mock
    EntityMetadataDao entityMetadataDaoMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    public PushVisitDataRemoteWorkerTest(EntityMetadataDao entityMetadataDaoMock) {
    }

    /*@Test
    public long[] queryPushedMetaDataTester(String s){
        PushVisitDataRemoteWorkerTest t  = new PushVisitDataRemoteWorkerTest(entityMetadataDaoMock);
        long[] check = t.queryPushedMetaDataTester("* from visit");
        assertTrue(check);
        verify(entityMetadataDaoMock).findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());

        return check;
    }*/

    @Test
    public void queryPushedMetaDataTest() {

        // create mock
        PushVisitDataRemoteWorker test = mock(PushVisitDataRemoteWorker.class);
        long[] checkResult = test.getPushedEntityMetadata();
        assertTrue(checkResult);
        verify(test.getPushedEntityMetadata());
    }
    private void assertTrue(long[] check) {
    }

}
        /*TODO define return value for method getUniqueId()
       /*TODO when(test.getPushedEntityMetadata()).thenReturn(long[] 43);
         /* TODO use mock in test....
       /* TODO assertEquals(test.getPushedEntityMetadata(), 43);*/

        /*TODO Recreate the current issue
        /*TODO  1. Create 1001 dummy metaDataDaos then test
        /*TODO 2. Step call the query and see if the results are correct
        /*TODO  3. Create 50000.*/


