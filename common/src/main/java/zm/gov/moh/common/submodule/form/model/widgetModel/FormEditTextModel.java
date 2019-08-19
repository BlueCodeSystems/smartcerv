package zm.gov.moh.common.submodule.form.model.widgetModel;

import java.io.Serializable;

public interface FormEditTextModel extends Serializable {

    void setHint(String hint);
    void setText(String text);
    String getHint();
    String getText();
}