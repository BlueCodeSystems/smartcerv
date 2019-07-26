package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

public class Visit {

    private String patient;
    private String visitType;
    private LocalDateTime startDatetime;
    private LocalDateTime stopDatetime;
    private Encounter[] encounters;
    private String location;

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getStopDatetime() {
        return stopDatetime;
    }

    public void setStopDatetime(LocalDateTime stopDatetime) {
        this.stopDatetime = stopDatetime;
    }

    public Encounter[] getEncounters() {
        return encounters;
    }

    public void setEncounters(Encounter[] encounters) {
        this.encounters = encounters;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public static class Builder{

        Encounter[] encounters;
        LocalDateTime startDatetime;
        LocalDateTime stopDatetime;
        String patient;
        private String visitType;
        String location;

        public Builder setStartDatetime(LocalDateTime startDatetime) {
            this.startDatetime = startDatetime;
            return this;
        }

        public Builder setStopDatetime(LocalDateTime stopDatetime) {
            this.stopDatetime = stopDatetime;
            return this;
        }

        public Builder setEncounters(Encounter[] encounters) {
            this.encounters = encounters;
            return this;
        }

        public Builder setPatient(String patient) {
            this.patient = patient;
            return this;
        }

        public Builder setVisitType(String visitType) {
            this.visitType = visitType;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Visit build(){
            Visit visit = new Visit();

            visit.setStartDatetime(startDatetime);
            visit.setStopDatetime(stopDatetime);
            visit.setEncounters(encounters);
            visit.setVisitType(visitType);
            visit.setPatient(patient);
            visit.setLocation(location);

            return visit;
        }

    }
}