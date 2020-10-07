package zm.gov.moh.core;

import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import  androidx.test.platform.app.*;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.api.RepositoryImp;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.TestDatabase;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    private VisitDao visitDao;
    TestDatabase database;
    Context appContext;
   /* @Test
    public void useAppContext() {
        // Context of the app under test.
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("zm.gov.moh.core.test", appContext.getPackageName());
    }

    */
    @Test
    public void testDao()
    {
       List<Long> ids =new ArrayList<>();
       /*
        ids= database.visitDao().getIds();
        int x;
        x =ids.size();
        */
        VisitEntity[] visitEntities =new VisitEntity[1];
        VisitEntity visitEntity =new VisitEntity(4L, 5L,9L, 10L, 11L, LocalDateTime.now());
        visitEntities[0] =visitEntity;
        visitDao.insert(visitEntities);
        ids = visitDao.getIds();
        Long[] expected =new Long[1];
        Long[] actual =new Long[1];
        List<Long> returnedData =visitDao.getIds();
        expected[0]=visitEntity.getVisitId();
        for(int i=0; i<returnedData.size();i++)
        {
            actual[i] =returnedData.get(i);
        }

        assertArrayEquals(expected,actual);


    }

    @Before
    public void setUp() throws Exception {

        appContext = InstrumentationRegistry.getInstrumentation().getContext();
        AndroidThreeTen.init(appContext);
        //database =Database.getDatabase(appContext);
        database=Room.inMemoryDatabaseBuilder(appContext,TestDatabase.class).build();
        visitDao = database.visitDao();
    }
}
