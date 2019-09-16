package zm.gov.moh.core.model;

import java.util.Date;

public class LineChartVisitItem {
   public int Count_patient_id;
   public String dateStarted;
    public long valueCoded;

    public int getPatient_id() {
        return Count_patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.Count_patient_id= patient_id;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public long getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(long valueCoded) {
        this.valueCoded = valueCoded;
    }

    @Override
    public String toString() {
        return "LineChartVisitItem{" +
                "Count_patient_id=" + Count_patient_id +
                ", dateStarted='" + dateStarted + '\'' +
                ", valueCoded='" + valueCoded + '\'' +
                '}';
    }
}
