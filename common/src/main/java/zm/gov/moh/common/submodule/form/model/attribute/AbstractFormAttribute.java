package zm.gov.moh.common.submodule.form.model.attribute;

public abstract class AbstractFormAttribute implements FormAttribute {

    private String type;
    private String submitLabel;
    private long encounterId;
    private String panelType;
    private String formType;

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
       return this.type;
    }

    public void setSubmitLabel(String submitLabel) {
        this.submitLabel = submitLabel;
    }

    public String getSubmitLabel() {
        return submitLabel;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public long getEncounterId() {
        return encounterId;
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

    @Override
    public String getFormType() {
        return formType;
    }
}