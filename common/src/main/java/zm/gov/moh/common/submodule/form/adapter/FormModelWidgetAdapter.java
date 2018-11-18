package zm.gov.moh.common.submodule.form.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import java.util.HashMap;

import zm.gov.moh.common.submodule.form.model.widget.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widget.DistrictFacilityPickerModel;
import zm.gov.moh.common.submodule.form.model.widget.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widget.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetModel;
import zm.gov.moh.common.submodule.form.widget.DistrictFacilityPickerWidget;
import zm.gov.moh.common.submodule.form.widget.FormDatePickerWidget;
import zm.gov.moh.common.submodule.form.widget.FormEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.FormLabelWidget;
import zm.gov.moh.core.repository.api.Repository;

public class FormModelWidgetAdapter {

    Context context;
    Repository repository;
    HashMap<String, Object> formData;

    public FormModelWidgetAdapter(Context context,Repository repository, HashMap<String, Object> formData){

        this.context = context;
        this.repository = repository;
        this.formData = formData;

    }

    public View getWidget(WidgetModel widgetModel){


        if(widgetModel instanceof EditTextModel){

            EditTextModel model = (EditTextModel) widgetModel;

            FormEditTextWidget widget = new FormEditTextWidget(this.context, model.getWeight());
            widget.setHint(model.getHint());
            widget.setTag(model.getTag());
            widget.setFormData(this.formData);
            widget.setText(model.getText());
           return widget;
        }
        else if(widgetModel instanceof DatePickerButtonModel){

            DatePickerButtonModel model = (DatePickerButtonModel) widgetModel;

            FormDatePickerWidget widget = new FormDatePickerWidget(this.context, this.formData);
            widget.setTag(model.getTag());
            widget.setText(model.getText());
            return widget;
        }
        else if(widgetModel instanceof WidgetGroupRowModel){

            WidgetGroupRowModel model = (WidgetGroupRowModel)widgetModel;
            LinearLayoutCompat linearLayoutCompat = createLinerLayout(context);

            for(WidgetModel widgetModel1 : model.getChildren())
                linearLayoutCompat.addView(getWidget(widgetModel1));

            return linearLayoutCompat;
        }
        else if(widgetModel instanceof FormLabelModel){

            FormLabelModel model = (FormLabelModel) widgetModel;

            FormLabelWidget widget = new FormLabelWidget(this.context,model.getWeight());
            widget.setText(model.getText());
            widget.setTextSize(TypedValue.COMPLEX_UNIT_SP, model.getTextSize());
            return widget;
        }
        else if(widgetModel instanceof DistrictFacilityPickerModel){

            DistrictFacilityPickerModel model = (DistrictFacilityPickerModel) widgetModel;

            DistrictFacilityPickerWidget widget = new DistrictFacilityPickerWidget(this.context, model.getDistrictText(), model.getFacilityText(),this.repository);
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
