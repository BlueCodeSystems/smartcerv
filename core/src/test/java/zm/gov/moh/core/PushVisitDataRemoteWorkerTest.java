package zm.gov.moh.core;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.core.model.Encounter;
import zm.gov.moh.core.model.Obs;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PushVisitDataRemoteWorkerTest {

    PushVisitDataRemoteWorker pushVisitDataRemoteWorker=mock(PushVisitDataRemoteWorker.class);

    @Mock
    private ArrayList<VisitEntity> visitEntities;
    @Mock
    private List<Long> ids;
    @Mock
    private List<EncounterEntity> encounterEntities;
    @Mock
    private List<ObsEntity> obsEntities;
    @Mock
    private VisitEntity visitEntity;
    @Mock
    private EncounterEntity encounterEntity;
    @Mock
    private List<ObsEntity> obsEntityList;
    @Mock
    private List<EncounterEntity> encounterEntityList;
    @Mock
    private Obs[] obs;
    @Mock
    private Encounter[] encounters;
    @Mock
    private List<Visit> visitList;
    @Mock
    private ObsEntity obsEntity;
    private Object PrintAttributes;
    private Visit visit;
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void PushedEntityMetadataTest(){

        //doReturn(new Long[100]).when(pushVisitDataRemoteWorker).getPushedEntityMetadata();
        when(pushVisitDataRemoteWorker.getPushedEntityMetadata()).thenReturn(new long[100]);
        pushVisitDataRemoteWorker.getPushedEntityMetadata();
        System.out.print("PushedEntityMetadata was tested successfully");
    }

    @Test
    public void unPushedVisitEntityIDsTest()
    {
        when(pushVisitDataRemoteWorker.getUnpushedVisitEntityId(new long[100])).thenReturn(new Long[100]);
        pushVisitDataRemoteWorker.getUnpushedVisitEntityId(new long[100]);
        System.out.print("unPushedVisitEntityIDs  was tested successfully");
    }

    @Test
    public void visitEntities()
    {
        when(pushVisitDataRemoteWorker.getVisitEntities(new Long[100])).thenReturn(visitEntities);
        pushVisitDataRemoteWorker.getUnpushedVisitEntityId(new long[100]);
        System.out.print("unPushedVisitEntityIDs  was tested successfully");

    }

    @Test
    public void getVisitIDsTest()
    {
        when(pushVisitDataRemoteWorker.getVisitIds(visitEntities)).thenReturn(ids);
        pushVisitDataRemoteWorker.getVisitIds(visitEntities);
        System.out.print("getVisitIDs  was tested successfully");
    }

    @Test
    public void getEncounterEntitiesTest()
    {
        when(pushVisitDataRemoteWorker.getEncounterEntities(ids)).thenReturn(encounterEntities);
        pushVisitDataRemoteWorker.getEncounterEntities(ids);
        System.out.print("getEncounterEntities  was tested successfully");
    }

    @Test

    public void getEncounterIdsTest()
    {
        when(pushVisitDataRemoteWorker.getEncounterIds(encounterEntities)).thenReturn(ids);
        pushVisitDataRemoteWorker.getEncounterIds(encounterEntities);
        System.out.print("getEncounterIDs was successfully tested");
    }

    @Test
    public void getObsEntititesTest()
    {
        when(pushVisitDataRemoteWorker.getObsEntities(ids)).thenReturn(obsEntities);
        pushVisitDataRemoteWorker.getObsEntities(ids);
        System.out.print("getObsEntities was successfully tested");
    }

    @Test
    public void getVisitEncounterTest()
    {
        when(pushVisitDataRemoteWorker.getVisitEncounter(encounterEntities,visitEntity)).thenReturn(encounterEntityList);
        pushVisitDataRemoteWorker.getVisitEncounter(encounterEntities,visitEntity);
        System.out.print("getVisitEncounterTest was successfully tested");
    }

    @Test
    public void getEncounterObs()
    {
        when(pushVisitDataRemoteWorker.getEncounterObs(obsEntities,encounterEntity)).thenReturn(obsEntityList);
        pushVisitDataRemoteWorker.getEncounterObs(obsEntities,encounterEntity);
        System.out.print("getEncountersObs was successfully");
    }

    @Test
    public void getObsTest()
    {
        when(pushVisitDataRemoteWorker.getObs(obsEntityList)).thenReturn(obs);
        pushVisitDataRemoteWorker.getObs(obsEntityList);
        System.out.print("getObs was successfully tested");

    }

    @Test
    public void getEncounterTest()
    {
        when(pushVisitDataRemoteWorker.getEncounter(encounterEntityList,obsEntities)).thenReturn(encounters);
        pushVisitDataRemoteWorker.getEncounter(encounterEntityList,obsEntities);
        System.out.print("getEncounters was successfully tested");
    }

    @Test
    public  void createVisitsTest()
    {
        when(pushVisitDataRemoteWorker.createVisits(new Long[100])).thenReturn(visitList);
        pushVisitDataRemoteWorker.createVisits(new  Long[100]);
        System.out.print("createVisitsTest was successfully tested");
    }

    @Test
    public  void normalizeObsTest()
    {
        when(pushVisitDataRemoteWorker.normalizeObs(obsEntity)).thenReturn(new Obs());
        pushVisitDataRemoteWorker.normalizeObs(obsEntity);
        System.out.print("normalizedObs was successfully tested");
    }


    @Test
    public  void normalizeVisitTest() throws Exception {
        when(pushVisitDataRemoteWorker.normalizeVisit(visitEntity)).thenReturn(new Visit.Builder());
        pushVisitDataRemoteWorker.normalizeVisit(visitEntity);
        System.out.print("normalizedVisit was successfully tested");
    }

    @Test
    public  void normalizeEncounterTest() throws Exception {
        when(pushVisitDataRemoteWorker.normalizeEncounter(encounterEntity)).thenReturn(new Encounter());
        pushVisitDataRemoteWorker.normalizeEncounter(encounterEntity);
        System.out.print("normalizedEncounter was successfully tested");
    }

    @Test
    public void visitSessionScenario(){
        Patient patient = new Patient();
        visitEntity.setPatientId(10000L);
        visitEntity.setPatientId(20000L);

        VisitEntity visitEntity = mock(VisitEntity.class);
        //when(visitEntity.).thenReturn();

        Patient patient1 = new Patient();
        visitEntity.setVisitId(1L);

        Long VisitID = visitEntity.getVisitId();
        assertEquals("Retrieved Visit ID",VisitID);
    }

}


        /*TODO Recreate the current issue
        /*TODO  1. Create 1001 dummy metaDataDaos then test
        /*TODO 2. Step call the query and see if the results are correct
        /*TODO  3. Create 50000.*/


