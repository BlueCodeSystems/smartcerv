package zm.gov.moh.common.submodule.form.model.attribute;

import java.util.List;

import zm.gov.moh.common.submodule.form.model.Logic;

public class FormAttributeJson {

    private String type;
    private long encounterId;
    private String submitLabel;
    private String formType;
    private String panelType;
    private List<Logic> logic;


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

    //Added constructor for logic attribute: setter
    public void setLogic(List<Logic> logic) {
        this.logic = logic;
    }

    //getter for logic
    public List<Logic> getLogic() {
        return logic;
    }
}
