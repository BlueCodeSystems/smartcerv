package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model;

import java.util.LinkedList;

public class VisitEncounterItem {
    long id;
    String encounterType;
    LinkedList<ObsListItem> obsListItems;

    public VisitEncounterItem(){
        obsListItems = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public LinkedList<ObsListItem> getObsListItems() {
        return obsListItems;
    }

    public void setObsListItems(LinkedList<ObsListItem> obsListItems) {
        this.obsListItems = obsListItems;
    }
}
