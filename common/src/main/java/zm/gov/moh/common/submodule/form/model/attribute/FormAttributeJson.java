package zm.gov.moh.common.submodule.form.model.attribute;

public class FormAttributeJson {

    private String type;
    private long encounterId;
    private String submitLabel;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public void setSubmitLabel(String submitLabel) {
        this.submitLabel = submitLabel;
    }

    public String getSubmitLabel() {
        return submitLabel;
    }
}
