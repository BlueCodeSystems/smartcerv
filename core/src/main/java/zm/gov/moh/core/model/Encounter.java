package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

public class Encounter {

    private String encounterType;
    private LocalDateTime encounterDatetime;
    private Obs[] obs;
    private String provider;
    private String location;

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public LocalDateTime getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(LocalDateTime encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public Obs[] getObs() {
        return obs;
    }

    public void setObs(Obs[] obs) {
        this.obs = obs;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
