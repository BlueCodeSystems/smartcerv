package zm.gov.moh.common.submodule.form.model;

public class ObsValue<T> {

    private long conceptId;
    private T value;

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
}
