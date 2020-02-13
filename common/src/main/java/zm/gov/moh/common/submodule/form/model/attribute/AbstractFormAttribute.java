package zm.gov.moh.common.submodule.form.model.attribute;

import java.util.List;

import zm.gov.moh.common.submodule.form.model.Logic;

public abstract class AbstractFormAttribute implements FormAttribute {

    private String type;
    private String name;
    private String submitLabel;
    private long encounterId;
    private String panelType;
    private String formType;
    private List<Logic> logic;

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
    public void setLogic(List<Logic> logic) {
        this.logic = logic;
    }

    @Override
    public List<Logic> getLogic() {
        return logic;
    }

    @Override
    public String getFormType() {
        return formType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}