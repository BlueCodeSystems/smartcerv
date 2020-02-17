package zm.gov.moh.drugresistanttb;

public class OpenmrsConfig {

    //Visit Types

    public static final long VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM = 11;
    public static final long VISIT_TYPE_ID_MDR_BACTERIAL_EXAM = 3;
    public static final long VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT = 4;

    public static final String VISIT_TYPE_UUID_MDR_MONTHLY_REVIEW_FORM = "5049444d-1c09-43b2-b910-46805c63f762";
    public static final String VISIT_TYPE_UUID_MDR_BACTERIAL_EXAM = "217fd086-8dc6-4ac8-8593-67cd9e86c9f8";
    public static final String VISIT_TYPE_UUID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT = "9a00d8bf-28eb-4d85-85ed-832edc47b378";

    //Concepts
    public static final String CONCEPT_UUID_SMEAR_MICROSCOPY_RESULTS = "e434f969-5f50-477f-9d31-0e2b1149ca9e";
    public static final String CONCEPT_UUID_CULTURE_RESULTS = "2939ff13-6ae2-4448-85a9-108954440328";

    public static final String CONCEPT_UUID_PRESUMPTIVE_TB = "1bbf922b-f3e0-4d07-8b1a-ea8a92f0fe04";
    public static final String CONCEPT_UUID_RIFAMPICIN_RESISTANT = "74b3e6f9-3a1d-4348-890e-bb4f158aa900";
    public static final String CONCEPT_UUID_TESTS_PERFORMED = "f03e1d75-1acb-4c5d-8397-5942e5ef495c";


    public static final String CONCEPT_UUID_DRUG_RESISTANCE_TYPE = "59d74c6f-1f45-462c-9bb2-b31a91fff258";
    public static final String CONCEPT_UUID_SITE_OF_DISEASE = "c24a0a24-cf6a-4293-b205-0d7cbdb918ae";


    //Patient Identifier Type
    public static final String IDENTIFIER_TYPE_MDRPIZ_UUID = "c8e464ab-11be-4b90-8b28-226dacab2b2d";
}
