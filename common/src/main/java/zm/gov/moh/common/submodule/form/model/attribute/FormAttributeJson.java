package zm.gov.moh.common.submodule.form.model.attribute;

public class FormAttributeJson {

    private String type;
    private String encounterType;
    private String submitLabel;


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setEncouterType(String encouterType) {
        this.encounterType = encouterType;
    }

    public String getEncouterType() {
        return encounterType;
    }

    public void setSubmitLabel(String submitLabel) {
        this.submitLabel = submitLabel;
    }

    public String getSubmitLabel() {
        return submitLabel;
    }
}
