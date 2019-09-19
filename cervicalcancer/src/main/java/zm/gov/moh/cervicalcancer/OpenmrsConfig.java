package zm.gov.moh.cervicalcancer;

public class OpenmrsConfig {

    //Visit Types VIA
    public static final long VISIT_TYPE_ID_INTIAL_VIA = 2;
    public static final long VISIT_TYPE_ID_DELAYED_CRYOTHERAPHY_THERMAL_COAGULATION = 3;
    public static final long VISIT_TYPE_ID_POST_TREATMENT_COMPILATION = 4;
    public static final long VISIT_TYPE_ID_ONE_YEAR_FOLLOW_UP = 5;
    public static final long VISIT_TYPE_ID_ROUTINE_SCREENING = 6;
    public static final long VISIT_TYPE_ID_REFERRAL_CRYOTHERAPHY_THERMAL_COAGULATION = 7;

    public static final String VISIT_TYPE_UUID_INITIAL_VIA = "2bd5d3e2-6cbf-4c3c-83d7-d759a1e23072";
    public static final String VISIT_TYPE_UUID_DELAYED_CRYOTHERAPHY_THERMAL_COAGULATION = "4cd28081-8879-492a-8e87-d456d4a22f75";
    public static final String VISIT_TYPE_UUID_POST_TREATMENT_COMPILATION = "8613e0a9-8ff5-460b-b9bf-866a420f5a48";
    public static final String VISIT_TYPE_UUID_ONE_YEAR_FOLLOW_UP = "6451a53d-14c8-4e6d-9961-d6d58b5e02d2";
    public static final String VISIT_TYPE_UUID_ROUTINE_SCREENING = "20cf57e0-1047-4700-91df-79f715f7958d";
    public static final String VISIT_TYPE_UUID_REFERRAL_CRYOTHERAPHY_THERMAL_COAGULATION = "f39746aa-3553-401c-97e1-81b67a845a8e";

    //Concepts
    public static final String CONCEPT_UUID_VIA_INSPECTION_DONE = "a1dc1c20-074d-4cba-86e6-42a41b14f9f1";
    public static final String CONCEPT_UUID_VIA_SCREENING_RESULT = "9cb76924-6d8c-4017-a0ed-27e9a5a1bc45";
    public static final String CONCEPT_UUID_VIA_NEGATIVE = "41955487-8429-420a-b3c8-03aea3fb411d";
    public static final String CONCEPT_UUID_VIA_POSITIVE = "6523eddf-8746-4051-a929-809440de274d";
    public static final String CONCEPT_UUID_SUSPECTED_CANCER = "e989fbe3-3ffa-4764-bd24-f612cc172d5f";
    public static final String CONCEPT_UUID_SUSPECTED_CANCER_REFFERAL = "927f44fc-de0f-46bd-b21c-d81068089bb1";
    public static final String CONCEPT_UUID_LARGE_LESION_REFFERAL = "d9ffc8ab-5d16-4338-af00-52e70c982906";
    public static final String CONCEPT_UUID_REASON_FOR_REFERRAL = "f3fc438b-fd59-4665-b584-6a567a3fb2e5";
    public static final String CONCEPT_UUID_VIA_TREATMENT_TYPE_DONE = "b0f3a292-3fe1-4333-bf01-3da55fe5003c";
    public static final String CONCEPT_UUID_THERMAL_COAGULATION_TODAY = "12b34703-0900-4c8d-a621-223ad4a84750";
    public static final String CONCEPT_UUID_CRYTHERAPY_PERFORMED_TODAY = "00fdce3a-7e10-415f-90e0-0739257ccda4";
    public static final String CONCEPT_UUID_CRYTHERAPY_DELAYED = "dfffbe57-d079-4279-8e2a-dbb4bc671f49";
    public static final String CONCEPT_UUID_EDI_IMAGE = "4da37d55-3754-4648-8549-35abf708dee9";
    public static final String CONCEPT_UUID_CAMERA_CONNECT = "2f8ce96d-2fd1-430f-9ba8-7d8ad42dae36";
    public static final String CONCEPT_UUID_THERMAL_COAGULATION_DELAYED = "177b78d1-8dca-415a-9fdb-e2d2a5aa2491";

    public static final String CONCEPT_UUID_HEALTH_FACILITY_REFERRED = "38905f09-b2fb-4c98-a530-a1e1e779f3db";
    public static final String CONCEPT_UUID_REFERRAL_REASON = "f3fc438b-fd59-4665-b584-6a567a3fb2e5";
    public static final String CONCEPT_UUID_VIA_TREATMENT_PERFORMED = "b0f3a292-3fe1-4333-bf01-3da55fe5003c";
    public static final String CONCEPT_UUID_HIV_STATUS = "7febbfd6-1c41-4afd-9add-12f9fe338e6f";
    public static final String CONCEPT_UUID_TREATMENT_PROVIDER = "e1364818-c8b6-4629-9356-561a658c9922";
    public static final String CONCEPT_UUID_SCREENING_PROVIDER = "f80a9ba8-1771-46b9-9179-c228baad03b2";

    //Encounter Type
    public static final String ENCOUNTER_TYPE_UUID_TEST_RESULT = "18c7295b-bd02-4766-8341-95337197606b";
    public static final String ENCOUNTER_TYPE_UUID_TREAMENT = "792cdf01-d7c6-435b-8224-40159f5baea2";

}