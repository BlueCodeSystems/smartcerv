package zm.gov.moh.core.model;

import java.io.Serializable;

public class ObsValue<T> implements Serializable {

    private long conceptId;
    private T value;
    private String conceptDataType;

    public ObsValue(){}

    public ObsValue(long conceptId, String conceptDataType, T value){

        this.conceptId = conceptId;
        this.conceptDataType = conceptDataType;
        this.value = value;
    }

    public void setConceptId(long conceptId) {
        this.conceptId = conceptId;
    }

    public long getConceptId() {
        return conceptId;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setConceptDataType(String conceptDataType) {
        this.conceptDataType = conceptDataType;
    }

    public String getConceptDataType() {
        return conceptDataType;
    }
}