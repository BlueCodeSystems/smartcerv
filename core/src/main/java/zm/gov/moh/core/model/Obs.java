package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

public class Obs {

    private String concept;
    private LocalDateTime obsDatetime;
    private String person;
    private String value;

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public LocalDateTime getObsDatetime() {
        return obsDatetime;
    }

    public void setObsDatetime(LocalDateTime obsDatetime) {
        this.obsDatetime = obsDatetime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPerson() {
        return person;
    }
}
