package zm.gov.moh.common.submodule.form.model.attribute;

public class FormAttributeJson {

    private String type;
    private long encounterId;
    private String submitLabel;
    private String formType;
    private String panelType;


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

    public String getPanelType() {
        return panelType;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormType() {
        return formType;
    }
}
