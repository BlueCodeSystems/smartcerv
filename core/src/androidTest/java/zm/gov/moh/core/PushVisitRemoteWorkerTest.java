package zm.gov.moh.core;

import android.content.Context;


import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.room.Room;


import org.junit.Before;
import org.junit.Test;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import  androidx.test.platform.app.*;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.TestDatabase;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.system.EntityMetadataDao;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PushVisitRemoteWorkerTest {
    private VisitDao visitDao;
    private EntityMetadataDao entitymetadataDao;
    TestDatabase database;
    Context appContext;
    private Long[] getUnpushedVisitEntityId;

   @Before
    public void setUp() throws Exception {

        appContext = InstrumentationRegistry.getInstrumentation().getContext();
        AndroidThreeTen.init(appContext);
        database=Room.inMemoryDatabaseBuilder(appContext,TestDatabase.class).build();
        visitDao = database.visitDao();
        entitymetadataDao = database.entityMetadataDao();
    }
    @Test
    public void testDao() {
        List<Long> ids = new ArrayList<>();
        VisitEntity[] visitEntities = new VisitEntity[1];
        VisitEntity visitEntity = new VisitEntity(4L, 5L, 9L, 10L, 11L, LocalDateTime.now());
        visitEntities[0] = visitEntity;
        visitDao.insert(visitEntities);
        ids = visitDao.getIds();
        Long[] expected = new Long[1];
        Long[] actual = new Long[1];
        List<Long> returnedData = visitDao.getIds();
        expected[0] = visitEntity.getVisitId();
        for (int i = 0; i < returnedData.size(); i++) {
            actual[i] = returnedData.get(i);
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    public void findEntityNotWithIdTestFail()
    {
        long offset = 5L;
        VisitEntity[] visitEntitiesArray = new VisitEntity[10001];
        for (int i = 0; i < visitEntitiesArray.length; i++) {
            visitEntitiesArray[i] = new VisitEntity(offset + i, 5L + offset, 9L + offset, 10L + offset, 11L + offset, LocalDateTime.now());
        }
        long[] idsNotIN = new long[10001];
        for (int i = 0; i < idsNotIN.length; i++) {
            idsNotIN[i] = 4000 + i;
        }
        Long[] expectedIds = new Long[10001];
        for (int i = 0; i < visitEntitiesArray.length; i++) {
            expectedIds[i] = visitEntitiesArray[i].getVisitId();
        }
        visitDao.insert(visitEntitiesArray);
        assertArrayEquals(expectedIds, visitDao.findEntityNotWithId(offset, idsNotIN));
    }

    @Test
    public void findEntityNotWithIdTestPassed() {
        long offset = 5L;
        int entityTypeId = 1000;
        short remoteStatus = 1000;
        EntityMetadata[] entityMetadataArray = new EntityMetadata[10001];
        for (int i = 0; i < entityMetadataArray.length; i++) {
            entityMetadataArray[i] = new EntityMetadata(offset + i, entityTypeId, remoteStatus, LocalDateTime.now());
        }

        VisitEntity[] visitEntitiesArray = new VisitEntity[10001];
        for (int i = 0; i < visitEntitiesArray.length; i++) {
            visitEntitiesArray[i] = new VisitEntity(offset + i, 5L + offset, 9L + offset, 10L + offset, 11L + offset, LocalDateTime.now());
        }
        long[] idsNotIN = new long[10001];
        for (int i = 0; i < idsNotIN.length; i++) {
            idsNotIN[i] = 4000 + i;
        }
        Long[] expectedIds = new Long[10001];
        for (int i = 0; i < visitEntitiesArray.length; i++) {
            expectedIds[i] = visitEntitiesArray[i].getVisitId();
        }
        visitDao.insert(visitEntitiesArray);
        entitymetadataDao.insert(entityMetadataArray);
        assertArrayEquals(expectedIds, visitDao.findEntityNotWithId2(offset, entityTypeId, remoteStatus));
    }

    @Test
    public Long[] getUnpushedVisitEntityId(long[] UnpushedVisitEntityId) {
        long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        int entityTypeId = 1000;
        short remoteStatus = 1 ;
        //Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId2(offset, UnpushedVisitEntityId);
        Database db =Room.inMemoryDatabaseBuilder(appContext,Database.class).build();
        Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId2(offset, entityTypeId, remoteStatus);
        return unpushedVisitEntityId;
    }
}
