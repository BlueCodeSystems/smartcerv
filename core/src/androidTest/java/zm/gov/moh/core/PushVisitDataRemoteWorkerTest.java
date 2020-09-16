package zm.gov.moh.core;

import android.content.Context;
import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.AndroidJUnitRunner;
import androidx.work.ListenableWorker;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.system.EntityMetadataDao;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.TIMEOUT;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PushVisitDataRemoteWorkerTest {

    @Mock
    VisitDao visitDaoMock;

    @Mock
    EntityMetadataDao entityMetadataDaoMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void queryPushedMetaDataTest(){

        // create mock
        PushVisitDataRemoteWorker test  = mock(PushVisitDataRemoteWorker.class);
        long[] checkResult = test.getPushedEntityMetadata();
        assertTrue(checkResult);
        verify(test.getPushedEntityMetadata());
        /*TODO define return value for method getUniqueId()
       /*TODO when(test.getPushedEntityMetadata()).thenReturn(long[] 43);
         /* TODO use mock in test....
       /* TODO assertEquals(test.getPushedEntityMetadata(), 43);*/

        /*TODO Recreate the current issue
        /*TODO  1. Create 1001 dummy metaDataDaos then test
        /*TODO 2. Step call the query and see if the results are correct
        /*TODO  3. Create 50000.*/
    }

    private void assertTrue(long[] check) {
    }

   }