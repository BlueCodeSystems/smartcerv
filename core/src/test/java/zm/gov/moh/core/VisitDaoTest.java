package zm.gov.moh.core;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.threeten.bp.LocalDateTime;
import org.w3c.dom.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.custom.IdentifierDao;
import zm.gov.moh.core.repository.database.dao.derived.ClientDao;
import zm.gov.moh.core.repository.database.dao.derived.ConceptAnswerNameDao;
import zm.gov.moh.core.repository.database.dao.derived.FacilityDistrictCodeDao;
import zm.gov.moh.core.repository.database.dao.derived.GenericDao;
import zm.gov.moh.core.repository.database.dao.derived.MetricsDao;
import zm.gov.moh.core.repository.database.dao.derived.PersonIdentifierDao;
import zm.gov.moh.core.repository.database.dao.derived.ProviderUserDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptAnswerDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptNameDao;
import zm.gov.moh.core.repository.database.dao.domain.DrugDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterDao_Impl;
import zm.gov.moh.core.repository.database.dao.domain.EncounterProviderDao;
import zm.gov.moh.core.repository.database.dao.domain.EncounterTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagMapDao;
import zm.gov.moh.core.repository.database.dao.domain.ObsDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderDao;
import zm.gov.moh.core.repository.database.dao.domain.UserDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao_Impl;
import zm.gov.moh.core.repository.database.dao.domain.VisitTypeDao;
import zm.gov.moh.core.repository.database.dao.fts.ClientFtsDao;
import zm.gov.moh.core.repository.database.dao.system.EntityMetadataDao;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.BaseWorker;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.InjectorUtils;
import zm.gov.moh.core.utils.BaseAndroidViewModel;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import android.app.Application;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import javax.inject.Inject;

public class VisitDaoTest extends BaseWorker {
    @Mock
    Database db;
    @Mock
    private VisitDao visitDao;
    @Mock
    private EncounterDao encounterDao;
    @InjectMocks
    private Visit visit;
    @Mock
    private List<VisitEntity> visitEntities;
    @Mock
    private List<Long> ids;
    @Mock
    private Repository mRepository;
    private VisitDao VisitDao;
    @Mock
    private Object VisitEntity;

    public VisitDaoTest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Database database = Mockito.mock(Database.class, Mockito.CALLS_REAL_METHODS);
     //   db = Room.inMemoryDatabaseBuilder(this, Database.class).build();

        VisitDao = db.visitDao();
        long pushedEntityID[] = new long[4];
        EncounterEntity[] encounterEntities = new EncounterEntity[4];
        for (int i = 0; i < 4; i++) {
            short code = (short) ((4L + i));
            long entityid = (long) ((1000L + i));
            int entityTypeId = (int) ((10L + i));
            EncounterEntity encounterEntity = new EncounterEntity();
            encounterEntity.setEncounterId(1000L + i);
            encounterEntity.setEncounterType((int) (10L + i));
            encounterEntity.setEncounterId(1L + i);
            encounterEntity.setLocationId(1L + i);
            encounterEntity.setPatientId(12 + i);
            encounterEntity.setVisitId(12L + i);
            encounterEntities[i] = encounterEntity;
        }
        database.encounterDao().insert(encounterEntities);


    }


    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void writeVisitAndReadInList() throws Exception {
        visit.setPatient("Ju");
        visitDao.insert();
    }

    @Test
    public void getMaxId() {
        db.visitDao().getIds();
       /* when(visitDao.getMaxId()).thenReturn(1000L);
        visitDao.getMaxId();
        assertEquals((Long) 1000L, visitDao.getMaxId());
        System.out.print("getMaxId was tested successfully");

        */
    }

    @Test
    public void getIds() {

        when(visitDao.getIds()).thenReturn(ids);
        visitDao.getMaxId();
        assertEquals(ids, visitDao.getIds());
        System.out.print("getIds was tested successfully");
    }

    @Test
    public void getById() {
        when(visitDao.getById(new Long[100])).thenReturn(visitEntities);
        visitDao.getById(new Long[100]);
    }

    @Test
    public void findEntityNotWithIdTest() {
         long unpushedVisitEntityID [] = new long[4];
         VisitEntity visitEntitys [] = new VisitEntity[4];
         for(int i =0; i<4; i++) {
             VisitEntity visitEntity = new VisitEntity();
             visitEntity.setVisitId(1000L+i);
             visitEntity.setPatientId(10L+i);
             visitEntity.setVisitTypeId(1L+i);
             visitEntity.setIndicationConceptId(4L+i);
             visitEntity.setLocationId(5L+i);
             visitEntity.setCreator(7L+i);
             visitEntitys[i] =visitEntity;
         }

         visitDao.insert(visitEntitys);

        for(int i = 0; i< unpushedVisitEntityID.length; i++)
        {
            unpushedVisitEntityID[i] = 10L + i;
        }

        //visit
        Long[] unpushedVisitIds = new Long[1000];

        for(int i = 0; i< unpushedVisitIds.length; i++)
        {
            unpushedVisitIds[i] = 10L + i;
        }

        //Create a collection of 1000 IDs which are longs
        //and pass them into the method
      //  when(visitDao.findEntityNotWithId(offset, unpushedVisitEntityID)).thenReturn(unpushedVisitIds);
       //visitDao.findEntityNotWithId(offset, unpushedVisitEntityID);
    }

    @Test
        public <T> void testAddPatientVisit_returnsNewPatientWithId() {
        EntityMetadata[] entityMetadata = new EntityMetadata [1000];
        final OngoingStubbing<T> tOngoingStubbing;
        tOngoingStubbing = (OngoingStubbing<T>) when(db.entityMetadataDao().insert1(entityMetadata)).thenAnswer((Answer<Visit>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Visit visit = (Visit) arguments[0];
                visit.setId(1);
                return visit;
            }
            return (Visit) visitEntities;
        });
        entityMetadata = new EntityMetadata[1000];
            assertThat(db.entityMetadataDao().insert1(entityMetadata), is(notNullValue()));
        }
}




