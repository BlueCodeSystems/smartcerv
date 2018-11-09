package zm.gov.moh.common.submodule.form.adapter;

import android.content.Context;
import android.view.View;

import zm.gov.moh.common.submodule.form.model.EditTextModel;
import zm.gov.moh.common.submodule.form.model.FormData;
import zm.gov.moh.common.submodule.form.model.WidgetModel;
import zm.gov.moh.common.submodule.form.widget.FormEditTextWidget;

public class FormModelWidgetAdapter {

    public static View getWidget(Context context, WidgetModel widgetModel, FormData<String, Object> formData){

        if(widgetModel instanceof EditTextModel){

            EditTextModel model = (EditTextModel) widgetModel;

            FormEditTextWidget widget = new FormEditTextWidget(context);
            widget.setHint(model.getHint());
            widget.setTag(model.getTag());
            widget.setFormData(formData);
            widget.setText(model.getText());
            return widget;
        }

        return null;
    }
}
