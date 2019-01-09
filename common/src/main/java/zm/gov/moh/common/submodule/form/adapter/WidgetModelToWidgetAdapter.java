package zm.gov.moh.common.submodule.form.adapter;

import android.content.Context;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import java.util.HashMap;

import zm.gov.moh.common.submodule.form.model.ConceptDataType;
import zm.gov.moh.common.submodule.form.model.widgetModel.BasicConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CervicalCancerIDEditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CodedConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictFacilityPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FacilityLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.widget.BasicConceptWidget;
import zm.gov.moh.common.submodule.form.widget.CervicalCancerIDEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.CodedConceptWidget;
import zm.gov.moh.common.submodule.form.widget.DistrictFacilityPickerWidget;
import zm.gov.moh.common.submodule.form.widget.DistrictLabelWidget;
import zm.gov.moh.common.submodule.form.widget.FacilityLabelWidget;
import zm.gov.moh.common.submodule.form.widget.FormDatePickerWidget;
import zm.gov.moh.common.submodule.form.widget.FormEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.FormLabelWidget;
import zm.gov.moh.common.submodule.form.widget.ProviderLabelWidget;
import zm.gov.moh.common.submodule.form.widget.WidgetUtils;
import zm.gov.moh.core.repository.api.Repository;

public class WidgetModelToWidgetAdapter {

    Context context;
    Repository repository;
    HashMap<String, Object> formData;
    View rootView;

    public WidgetModelToWidgetAdapter(Context context, Repository repository, HashMap<String, Object> formData, View rootView){

        this.context = context;
        this.repository = repository;
        this.formData = formData;
        this.rootView = rootView;

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

            int index = 0;
            View[] widgets = new View[model.getChildren().size()];

            for(WidgetModel widgetModel1 : model.getChildren())
                widgets[index++] = getWidget(widgetModel1);

            return WidgetUtils.createLinearLayout(context,WidgetUtils.HORIZONTAL,widgets);
        }
        else if(widgetModel instanceof FormLabelModel){

            FormLabelModel model = (FormLabelModel) widgetModel;

            FormLabelWidget widget = new FormLabelWidget(this.context,model.getWeight());
            widget.setText(model.getLabel());
            widget.setTextSize(TypedValue.COMPLEX_UNIT_SP, model.getTextSize());
            return widget;
        }
        else if(widgetModel instanceof DistrictFacilityPickerModel){

            DistrictFacilityPickerModel model = (DistrictFacilityPickerModel) widgetModel;

            DistrictFacilityPickerWidget widget = new DistrictFacilityPickerWidget(this.context, model.getDistrictText(), model.getFacilityText(),this.repository);
            return widget;
        }
        else if(widgetModel instanceof ProviderLabelModel){

            ProviderLabelModel model = (ProviderLabelModel) widgetModel;

            ProviderLabelWidget widget = new ProviderLabelWidget(this.context,this.repository,formData);
            widget.setLabelText(model.getLabel());
            widget.setLabelTextSize(model.getTextSize());
            widget.setValueTextSize(model.getTextSize());
            widget.setTag(model.getTag());
            return widget;
        }
        else if(widgetModel instanceof DistrictLabelModel){

            DistrictLabelModel model = (DistrictLabelModel) widgetModel;

            DistrictLabelWidget widget = new DistrictLabelWidget(this.context,this.repository,formData);
            widget.setLabelText(model.getLabel());
            widget.setLabelTextSize(model.getTextSize());
            widget.setValueTextSize(model.getTextSize());
            widget.setTag(model.getTag());
            return widget;
        }
        else if(widgetModel instanceof FacilityLabelModel){

            FacilityLabelModel model = (FacilityLabelModel) widgetModel;

            FacilityLabelWidget widget = new FacilityLabelWidget(this.context,this.repository,formData);
            widget.setLabelText(model.getLabel());
            widget.setLabelTextSize(model.getTextSize());
            widget.setValueTextSize(model.getTextSize());
            widget.setTag(model.getTag());
            return widget;
        }
        else if(widgetModel instanceof CervicalCancerIDEditTextModel){

            CervicalCancerIDEditTextModel model = (CervicalCancerIDEditTextModel) widgetModel;
            CervicalCancerIDEditTextWidget widget = new CervicalCancerIDEditTextWidget(context,model.getWeight(),repository);
            widget.setTag(model.getTag());
            widget.setFormData(formData);

            return widget;
        }
        else if(widgetModel instanceof BasicConceptWidgetModel){

            BasicConceptWidgetModel model = (BasicConceptWidgetModel) widgetModel;

            return new BasicConceptWidget(context)
                    .setTag(model.getTag())
                    .setFormData(formData)
                    .setConceptId(model.getConceptId())
                    .setDataType(model.getDataType())
                    .setRepository(repository)
                    .setRootView(rootView)
                    .setLabel(model.getLabel())
                    .setHint(model.getHint())
                    .setTextSize(model.getTextSize())
                    .setLogic(model.getLogic())
                    .build();
        }
        else if(widgetModel instanceof CodedConceptWidgetModel){

            CodedConceptWidgetModel model = (CodedConceptWidgetModel) widgetModel;

            return new CodedConceptWidget(context)
                    .setStyle(model.getStyle())
                    .setTag(model.getTag())
                    .setFormData(formData)
                    .setRootView(rootView)
                    .setConceptId(model.getConceptId())
                    .setDataType(model.getDataType())
                    .setRepository(repository)
                    .setLabel(model.getLabel())
                    .setHint(model.getHint())
                    .setTextSize(model.getTextSize())
                    .setLogic(model.getLogic())
                    .build();
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
