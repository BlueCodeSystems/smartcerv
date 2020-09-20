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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.threeten.bp.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import androidx.lifecycle.LiveData;
import zm.gov.moh.core.model.Encounter;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VisitDaoTest {

    // Class to be tested
    private VisitDaoTester visitDaoTester;

    // Dependencies (will be mocked)
    private EntityMetadata entityMetadata;
    private Logger logger;

    @Before
    public void setup() {
        visitDaoTester = new VisitDaoTester();

        entityMetadata = mock(EntityMetadata.class);
        visitDaoTester.setEntityMetaData(entityMetadata);

        logger = mock(Logger.class);
        visitDaoTester.setLogger(logger);
    }

    @Test
    public void visitSession() {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Visit visit = invocation.getArgument(0);
                visit.setId(123L);
                return null;
            }
        }).when(entityMetadata).persist(any(Visit.class));

        visitDaoTester.saveVisit("Initial VIA", "Post-Delayed Cryotherapy");

        verify(logger).info(String.valueOf(123L));
    }

    @Test(expected = IllegalArgumentException.class)

    public void missingInformation() {
        visitDaoTester.saveVisit("Initial VIA", null);
    }

    private class VisitDaoTester {
        public void setEntityMetaData(EntityMetadata entityMetadata) {
        }

        public void setLogger(Logger logger) {
        }

        public void saveVisit(String initial_via, String s) {
        }
    }
}

