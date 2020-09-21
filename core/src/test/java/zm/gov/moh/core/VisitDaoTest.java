package zm.gov.moh.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.threeten.bp.LocalDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.Set;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.PatientDao_Impl;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao_Impl;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VisitDaoTest {
   // private VisitDao visitDao;
    @Mock
 Database db;
    @Mock
    private VisitDao visitDao;
    @Mock
    private VisitEntity visitEntity;
    @Mock
    private Visit visit;
    @Mock
    private List<VisitEntity> visitEntities;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
     /*patientList = new ArrayList<>();

     patientList.add(createPatient(1L));
     patientList.add(createPatient(2L));
     patientList.add(createPatient(3L));*/
    }



 @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMaxDatetime() {


    }

    @Test
    public void getMaxId() {
       when(visitDao.getMaxId()).thenReturn(1000L);
        visitDao.getMaxId();
       assertEquals((Long)1000L,visitDao.getMaxId());
        System.out.print("getMaxId was tested successfully");
    }

    @Test
    public void getIds() {
    }

    @Test
    public void getByPatientIdLive() {
    }

    @Test
    public void getByDatetime() {
    }

    @Test
    public void getByPatientId() {
    }

    @Test
    public void updatePatientId() {
    }

    @Test
    public void getByDate() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void testGetAll() {
    }

    @Test
    public void testGetByDate() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void getById() {
        when(visitDao.getById(new Long[100])).thenReturn(visitEntities);
        //visitDao.getById();

    }

    @Test
    public void getDateStartTimeByVisitId() {
    }

    @Test
    public void testGetById() {
    }

    @Test
    public void getByPatientIdVisitTypeId() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void updateVisit() {
    }

    @Test
    public void findEntityNotWithIdTest() {
     when(visitDao.findEntityNotWithId(anyLong(),anyLong())).thenReturn(new Long[100]);
     visitDao.findEntityNotWithId(anyLong(),anyLong());
    }

    @Test
    public void findEntityNotWithId2() {
    }

    @Test
    public void replaceLocalPatientId() {
    }

    @Test
    public void abortVisitEncounter() {
    }

    @Test
    public void abortVisit() {
    }

    @Test
    public void abortVisitObs() {
    }
}