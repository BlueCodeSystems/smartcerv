package zm.gov.moh.common.submodule.form.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import java.util.HashMap;

import zm.gov.moh.common.submodule.form.model.widget.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widget.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widget.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetModel;
import zm.gov.moh.common.submodule.form.widget.FormDatePickerWidget;
import zm.gov.moh.common.submodule.form.widget.FormEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.FormLabelWidget;

public class FormModelWidgetAdapter {

    public static View getWidget(Context context, WidgetModel widgetModel, HashMap<String, Object> formData){


        if(widgetModel instanceof EditTextModel){

            EditTextModel model = (EditTextModel) widgetModel;

            FormEditTextWidget widget = new FormEditTextWidget(context, model.getWeight());
            widget.setHint(model.getHint());
            widget.setTag(model.getTag());
            widget.setFormData(formData);
            widget.setText(model.getText());
           return widget;
        }
        else if(widgetModel instanceof DatePickerButtonModel){

            DatePickerButtonModel model = (DatePickerButtonModel) widgetModel;

            FormDatePickerWidget widget = new FormDatePickerWidget(context,formData);
            widget.setTag(model.getTag());
            widget.setText(model.getText());
            return widget;
        }
        else if(widgetModel instanceof WidgetGroupRowModel){

            WidgetGroupRowModel model = (WidgetGroupRowModel)widgetModel;
            LinearLayoutCompat linearLayoutCompat = createLinerLayout(context);

            for(WidgetModel widgetModel1 : model.getChildren())
                linearLayoutCompat.addView(getWidget(context,widgetModel1,formData));

            return linearLayoutCompat;
        }
        else if(widgetModel instanceof FormLabelModel){

            FormLabelModel model = (FormLabelModel) widgetModel;

            FormLabelWidget widget = new FormLabelWidget(context,model.getWeight());
            widget.setText(model.getText());
            widget.setTextSize(TypedValue.COMPLEX_UNIT_SP, model.getTextSize());
            return widget;
        }

        return null;
    }

    private static LinearLayoutCompat createLinerLayout(Context context){

        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(context);

        LinearLayoutCompat.LayoutParams  layoutParams = new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        linearLayoutCompat.setLayoutParams(layoutParams);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.HORIZONTAL);
        linearLayoutCompat.setGravity(Gravity.CENTER_VERTICAL);

        return linearLayoutCompat;
    }
}
