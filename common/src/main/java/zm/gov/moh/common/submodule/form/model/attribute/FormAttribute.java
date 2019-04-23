package zm.gov.moh.common.submodule.form.model.attribute;

public interface FormAttribute {

    void setImageViewLabel(String imageViewLabel);
    String getImageViewLabel();

    void setFormType(String formType);
    String getFormType();

    void setSubmitLabel(String submitLabel);

    String getSubmitLabel();

    void setEncounterId(long encounterId);

    long getEncounterId();

}