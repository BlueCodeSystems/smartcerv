package zm.gov.moh.common.submodule.form.model.attribute;

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

    String getType();
}