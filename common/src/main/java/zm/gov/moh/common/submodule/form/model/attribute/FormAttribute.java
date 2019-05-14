package zm.gov.moh.common.submodule.form.model.attribute;

import java.util.List;

import zm.gov.moh.common.submodule.form.model.Logic;

public interface FormAttribute {

    void setType(String formType);

    String getFormType();

    void setSubmitLabel(String submitLabel);

    String getSubmitLabel();

    void setEncounterId(long encounterId);

    long getEncounterId();

    String getPanelType();

    void setPanelType(String panelType);

    void setFormType(String formType);
//Getter and setter imlpemented in Form attribute interface class
    void setLogic(List<Logic> logic);
    List<Logic> getLogic();

    String getType();
}