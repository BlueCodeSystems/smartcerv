package zm.gov.moh.common;

public class OpenmrsConfig {

    //Vitals
    public static final String CONCEPT_UUID_RESPIRATORY_RATE = "5242AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_SYSTOLIC_BLOOD_PRESSURE = "5085AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_DIASTOLIC_BLOOD_PRESSURE = "5086AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_PULSE = "5087AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_TEMPERATURE = "5088AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_WEIGHT = "5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_HEIGHT = "5090AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String CONCEPT_UUID_BLOOD_OXYGEN_SATURATION = "5092AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    //Encounter types
    public static final String ENCOUNTER_TYPE_UUID_VITALS = "67a71486-1a54-468f-ac3e-7091a9a79584";

    //Visit types
    public static final String VISIT_TYPE_UUID_FACILITY_VISIT = "7b0f5697-27e3-40c4-8bae-f4049abfb4ed";

    public static final long VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM = 11;
    public static final long VISIT_TYPE_ID_MDR_BACTERIAL_EXAM = 3;
    public static final long VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT = 4;

    //Location tags
    public static final String LOCATION_TAG_UUID_DISTRICT = "332f8bdb-7a2d-4063-9c76-cbb4a969fb8d";
    public static final String LOCATION_TAG_UUID_PROVINCE ="4bd0baf0-40eb-4209-bc6b-1080f3b34b41";

    //Person Attribute types
    public static final String PERSON_ATTRIBUTE_TYPE = "0dc3daad-1ff2-4e6e-934f-e675a092a3ed";
    public static final String PERSON_ATTRIBUTE_TYPE_NRC="ef5935b5-b96f-4d00-8429-46c0c3267c3a";
    public static final String PERSON_ATTRIBUTE_TYPE_PHONE="c7e0a063-c20a-4e83-b461-0db6d79d2388";

    // Provider Attributes
    public static final String PROVIDER_ATTRIBUTE_TYPE_PHONE = "0ec123b7-2826-41d6-a21e-b019350f78d7";

    public static final String PROVIDER_ATTRIBUTE_TYPE_HOME_FACILITY = "c34fac13-9c48-4f29-beb1-04c8d0a86754";

    public static final String CCPIZ_IDENTIFIER_TYPE = "852a0bd1-d283-48b1-b72e-e7af0b4b6ce7";
    public static final String MDRPIZ_IDENTIFIER_TYPE = "c8e464ab-11be-4b90-8b28-226dacab2b2d";
}
