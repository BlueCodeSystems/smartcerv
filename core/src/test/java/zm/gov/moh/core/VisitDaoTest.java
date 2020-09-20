package zm.gov.moh.core;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VisitDaoTest {


    @Mock
    private long[] id;
    private long offset;
    private Long[] longEntitiesWithoutIDs;


    @InjectMocks
    private Database db;

    @Mock
    private VisitDao visitDao;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTest() {
        zm.gov.moh.core.repository.database.dao.domain.VisitDao mockVisitDAO = visitDao;
        List allVisits = mockVisitDAO.getAll();
        assertNotNull(allVisits);
        assertEquals(2, allVisits.size());
    }

    @Test
    public void getUnpushedVisitEntityId() {
    }

    @Test
        public void test() {
        Mockito.verify(visitDao).findEntityNotWithId(1L);
        }

        @Test
    public void findEntityNotWithIDTest() {
        when(visitDao.findEntityNotWithId(offset, id)).thenReturn(longEntitiesWithoutIDs);
        visitDao.findEntityNotWithId(offset, id);
        System.out.print("EntityWithoutID was tested successfully");
    }

}

