package zm.gov.moh.common.submodule.form.widget;

import android.view.View;

import zm.gov.moh.common.submodule.form.model.FormData;

public interface BaseWidget {

    void setTag(String tag);

    void setFormData(FormData<String,String> formData);

    View getView();
}
