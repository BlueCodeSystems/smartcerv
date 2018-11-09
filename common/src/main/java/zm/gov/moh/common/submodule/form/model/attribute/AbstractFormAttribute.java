package zm.gov.moh.common.submodule.form.model.attribute;

public abstract class AbstractFormAttribute implements FormAttribute {

    private String type;
    private String submitLabel;

    public void setFormType(String type){
        this.type = type;
    }

    public String getFormType(){
       return this.type;
    }

    public void setSubmitLabel(String submitLabel) {
        this.submitLabel = submitLabel;
    }

    public String getSubmitLabel() {
        return submitLabel;
    }
}