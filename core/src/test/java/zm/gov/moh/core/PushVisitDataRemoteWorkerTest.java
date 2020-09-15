package zm.gov.moh.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.ListenableWorker;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

import static com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.TIMEOUT;
import static org.junit.Assert.*;

public class PushVisitDataRemoteWorkerTest {

    @Test
    public void doWork() {
        {
            assertEquals(4, 2 + 2);

        }
    }


    @Test
    public void onError() {
    }

    @Test
    public void updateMetadata() {
    }

    @Test
    public void onTaskCompleted() {
    }

    @Test
    public void awaitResult() {
    }

    @Test
    public void testDoWork() {
    }

    @Test
    public void setRepository() {
    }

    @Test
    public void getRepository() {
    }

    @Test
    public void testOnError() {
    }

    @Test
    public void startWork() {
    }

    @Test
    public void getApplicationContext() {
    }

    @Test
    public void getId() {
    }

    @Test
    public void getInputData() {
    }

    @Test
    public void getTags() {
    }

    @Test
    public void getTriggeredContentUris() {
    }

    @Test
    public void getTriggeredContentAuthorities() {
    }

    @Test
    public void getNetwork() {
    }

    @Test
    public void getRunAttemptCount() {
    }

    @Test
    public void isStopped() {
    }

    @Test
    public void stop() {
    }

    @Test
    public void onStopped() {
    }

    @Test
    public void isUsed() {
    }

    @Test
    public void setUsed() {
    }

    @Test
    public void getBackgroundExecutor() {
    }

    @Test
    public void getTaskExecutor() {
    }

    @Test
    public void getWorkerFactory() {
    }

    @Test
    public void testDoWork1() {
    }

    @Test
    public void testOnTaskCompleted() {
    }

    @Test
    public void onComplete() {
    }

    @Test
    public void createVisits() {
    }

    @Test
    public void normalizeObs() {
    }

    @Test
    public void normalizeVisit() {
    }

    @Test
    public void normalizeEncounter() {
    }
}