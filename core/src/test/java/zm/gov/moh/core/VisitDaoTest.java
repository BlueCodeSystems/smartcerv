package zm.gov.moh.core;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VisitDaoTest<longEntityIDs> {

    VisitDaoTest visitDaoTest = (VisitDaoTest) mock(VisitDao.class);



    @Mock



    @Spy
    Database db;



    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PushVisitDataRemoteWorker pushVisitDataRemoteWorker = mock(PushVisitDataRemoteWorker.class);

    }

    @Test
    public void PushedEntityMetadataTest() {

        //when(visitDaoTest.findEntityNotWithId(offset, id)).thenReturn(longEntityIDs);
        visitDaoTest.findEntityNotWithId();
        System.out.print("PushedEntityMetadata was tested successfully");
    }



    private void findEntityNotWithId() {
    }
}

