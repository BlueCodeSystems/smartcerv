package zm.gov.moh.core.model;

import java.io.Serializable;

public class DrugObsValue implements Serializable {

    private Long value;
    private String uuid;
    private Long frequency;
    private Long duration;

    public DrugObsValue() {

    }

    public DrugObsValue(Long value, String uuid){
        this.value = value;
        this.uuid = uuid;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
