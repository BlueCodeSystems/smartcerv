package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model;

import org.threeten.bp.LocalDateTime;

public class VisitListItem {

    long id;
    String visitType;
    LocalDateTime dateCreated;
    LocalDateTime dateTimeStart;
    LocalDateTime dateTimeStop;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeStop() {
        return dateTimeStop;
    }

    public void setDateTimeStop(LocalDateTime dateTimeStop) {
        this.dateTimeStop = dateTimeStop;
    }
}