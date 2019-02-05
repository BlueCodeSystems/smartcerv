package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestListDataEntry {

    public TestListDataEntry() {

    }

    public static HashMap<String, List<String>> addDummyData() {
        HashMap<String, List<String>> formDetailInListForm = new HashMap<String, List<String>>();

        List<String> reproductive_health_history = new ArrayList<>();
        reproductive_health_history.add("Parity - 1");
        reproductive_health_history.add("Last Menstrual Period - ija day inangu");
        reproductive_health_history.add("Previous Screening and Treatment - LEEP");

        List<String> hiv_status = new ArrayList<>();
        hiv_status.add("Unknown - Reason: new test patient");
        hiv_status.add("PICT Offered - Yes");
        hiv_status.add("Referred to ART clinic");

        List<String> physical_exam = new ArrayList<>();
        physical_exam.add("Pelvic exam done - Yes");
        physical_exam.add("Cervix - Normal");
        physical_exam.add("Clinical Diagnosis - Invasive Cancer");

        formDetailInListForm.put("REPRODUCTIVE HEALTH HISTORY", reproductive_health_history);
        formDetailInListForm.put("HIV STATUS", hiv_status);
        formDetailInListForm.put("PHYSICAL_EXAM", physical_exam);
        return formDetailInListForm;
    }
}
