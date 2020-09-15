package zm.gov.moh.core;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
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
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.TIMEOUT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PushVisitDataRemoteWorkerTest {

    /*public PushVisitDataRemoteWorkerTest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }*/

    public void queryPushedMetaDataTest(){
        //Recreate the current issue
        // 1. Create 1001 dummy metaDataDaos then test
        // 2. Step call the query and see if the results are correct
        // 3. Create 50000.


    }

    public void queryPushedMetaDataTestWithSomeUnpushed(){
        //Recreate the current issue
        // 1. Create 1001 dummy metaDataDaos then test
        // 2. Step call the query and see if the results are correct
        // 3. Create 50000
    }

    public void  getUnpushedVisitEntityIDsTest(){
        //Generate a long[] containing 50000 ids that SHOULD not be found
    }



    public void createVisitsTest(){
        //create dummy visits
        //pass the ids of those dummy visits to createvisits and see if it returns the same visits
    }

    @Test
    public void doWork() {


    }



    @Test
    public void onError() {
    }

    @Test
    public void updateMetadata() {
    }

    @Test
    public void onTaskCompleted() {
        //repository.getDefaultSharePrefrences().edit().putString(Key.LAST_DATA_SYNC_DATETIME,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
        //mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SYNC_COMPLETE));
    }





    /*@Test
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
    }*/

   /* @Test
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
    }*/

    @Test
    public Executor getBackgroundExecutor() {
        return null;
    }

    @Test
    public TaskExecutor getTaskExecutor() {
        return null;
    }

    @Test
    public WorkerFactory getWorkerFactory() {
        return null;
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
    public List<Visit> createVisits(Long[] unpushedVisitEntityId) {
        int visitIndex = 0;
        List<Visit> visits = new ArrayList<>();
        return visits;
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