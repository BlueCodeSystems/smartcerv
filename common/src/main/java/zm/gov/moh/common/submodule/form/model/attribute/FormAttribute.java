package zm.gov.moh.common.submodule.form.model.attribute;

import java.io.Serializable;
import java.util.List;

import zm.gov.moh.common.submodule.form.model.Logic;

public interface FormAttribute extends Serializable {

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

    void setName(String name);

    String getName();
}