package zm.gov.moh.core;

import android.content.Context;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static org.mockito.Mockito.*;



public class VisitDaoTest {

    //VisitDao visitDao=mock(VisitDao.class);

    @Mock
    private long[] id;

    @Mock
    //private Long offset;
    long offset = Constant.LOCAL_ENTITY_ID_OFFSET;

    @Mock
    private Long[] longEntitiesWithoutIDs;

    @Mock
    private Database db;

    @Spy
    private VisitDao visitDao;
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        long[] pushedEntityIDs = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());
        long offset = Constant.LOCAL_ENTITY_ID_OFFSET;


    }



    @Test
    public void findEntityNotWithIDTest() {
       when(db.visitDao().findEntityNotWithId( Constant.LOCAL_ENTITY_ID_OFFSET, db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode()))).thenReturn(longEntitiesWithoutIDs);
        visitDao.findEntityNotWithId(Constant.LOCAL_ENTITY_ID_OFFSET, db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode()));
        when(visitDao.getMaxId()).thenReturn(offset);
        visitDao.getMaxId();
        System.out.print("EntityWithoutID was tested successfully");
    }
}

