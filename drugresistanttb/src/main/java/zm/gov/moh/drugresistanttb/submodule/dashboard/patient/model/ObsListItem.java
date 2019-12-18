package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model;

public class ObsListItem {

    long id;
    long conceptId;
    String conceptName;
    String obsValue;

    public ObsListItem(){}

    public ObsListItem(long id,String conceptName,String obsValue){

        this.id = id;
        this.conceptName = conceptName;
        this.obsValue = obsValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getObsValue() {
        return obsValue;
    }

    public void setObsValue(String obsValue) {
        this.obsValue = obsValue;
    }

    public long getConceptId() {
        return conceptId;
    }

    public void setConceptId(long conceptId) {
        this.conceptId = conceptId;
    }
}
