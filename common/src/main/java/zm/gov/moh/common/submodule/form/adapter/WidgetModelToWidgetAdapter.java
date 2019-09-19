package zm.gov.moh.common.submodule.form.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.widgetModel.BasicConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.BasicDrugWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CameraButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CameraConnectButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CervicalCancerIDEditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DialogButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DefaultCameraButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictFacilityPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FacilityLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.GenderPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ImageViewButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.NumericEditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderNumberModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ReadonlyTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.TextBoxModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.TextBoxTwoModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.widget.BaseWidget;
import zm.gov.moh.common.submodule.form.widget.BasicConceptWidget;
import zm.gov.moh.common.submodule.form.widget.BasicDrugWidget;
import zm.gov.moh.common.submodule.form.widget.CameraConnectButtonWidget;
import zm.gov.moh.common.submodule.form.widget.CervicalCancerIDEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.DatePickerWidget;
import zm.gov.moh.common.submodule.form.widget.DistrictFacilityPickerWidget;
import zm.gov.moh.common.submodule.form.widget.DistrictLabelWidget;
import zm.gov.moh.common.submodule.form.widget.DistrictPickerWidget;
import zm.gov.moh.common.submodule.form.widget.EditTextWidget;
import zm.gov.moh.common.submodule.form.widget.FacilityLabelWidget;
import zm.gov.moh.common.submodule.form.widget.FormCameraButtonWidget;
import zm.gov.moh.common.submodule.form.widget.FormDatePickerWidget;
import zm.gov.moh.common.submodule.form.widget.FormDefaultCameraButtonWidget;
import zm.gov.moh.common.submodule.form.widget.FormDialogWidget;
import zm.gov.moh.common.submodule.form.widget.FormImageViewButtonWidget;
import zm.gov.moh.common.submodule.form.widget.GenderPickerWidget;
import zm.gov.moh.common.submodule.form.widget.NumericEditTextWidget;
import zm.gov.moh.common.submodule.form.widget.ProviderLabelWidget;
import zm.gov.moh.common.submodule.form.widget.ProviderNumberWidget;
import zm.gov.moh.common.submodule.form.widget.ReadonlyTextWidget;
import zm.gov.moh.common.submodule.form.widget.TextBoxWidget;
import zm.gov.moh.common.submodule.form.widget.TextBoxWidgetTwo;
import zm.gov.moh.common.submodule.form.widget.TextViewWidget;
import zm.gov.moh.common.submodule.form.widget.WidgetUtils;
import zm.gov.moh.core.repository.api.Repository;

public class WidgetModelToWidgetAdapter {

    Context context;
    Repository repository;
    Bundle bundle;
    Form form;

    public WidgetModelToWidgetAdapter(Context context, Repository repository, Bundle bundle, Form form){

        this.context = context;
        this.repository = repository;
        this.bundle = bundle;
        this.form = form;

    }

    public View getWidget(WidgetModel widgetModel) {


        form.getFormContext().getTags().add(widgetModel.getTag());
        if (widgetModel instanceof EditTextModel) {

            EditTextModel model = (EditTextModel) widgetModel;

            BaseWidget widget = new EditTextWidget.Builder(this.context)
                    .setDataType(((EditTextModel) widgetModel).getDataType())
                    .setHint(model.getHint())
                    .setLabel(model.getLabel())
                    .setTextSize(18)
                    .setBundle(this.bundle)
                    .setRegex(model.getRegex())
                    .setErrorMessage(model.getErrorMessage())
                    .setBundle(this.bundle)
                    .setRequired(model.getRequired())
                    .setWeight(1)
                    .setTag(model.getTag())
                    .build();

            return widget;
        } else if (widgetModel instanceof ReadonlyTextModel) {

            ReadonlyTextModel model = (ReadonlyTextModel) widgetModel;

            BaseWidget widget = new ReadonlyTextWidget.Builder(this.context)
                    .setBundle(this.bundle)
                    .setHint(model.getHint())
                    .setLabel(model.getLabel())
                    .setTextSize(18)
                    .setWeight(1)
                    .setTag(model.getTag())
                    .build();

            return widget;
        } else if (widgetModel instanceof TextBoxModel) {

            TextBoxModel model = (TextBoxModel) widgetModel;

            BaseWidget widget = new TextBoxWidget.Builder(this.context)
                    .setBundle(this.bundle)
                    .setHint(model.getHint())
                    .setLabel(model.getLabel())
                    .setTextSize(18)
                    .setWeight(1)
                    .setTag(model.getTag())
                    .build();

            return widget;
        } else if (widgetModel instanceof TextBoxTwoModel) {

            TextBoxTwoModel model = (TextBoxTwoModel) widgetModel;

            BaseWidget widget = new TextBoxWidgetTwo.Builder(this.context)
                    .setBundle(this.bundle)
                    .setHint(model.getHint())
                    .setLabel(model.getLabel())
                    .setTextSize(18)
                    .setWeight(1)
                    .setTag(model.getTag())
                    .build();

            return widget;
        } else if (widgetModel instanceof DatePickerButtonModel) {

            DatePickerButtonModel model = (DatePickerButtonModel) widgetModel;

            FormDatePickerWidget widget = new FormDatePickerWidget(this.context, this.bundle);
            widget.setTag(model.getTag());
            widget.setText(model.getText());
            return widget;
        } else if (widgetModel instanceof CameraButtonModel) {

            CameraButtonModel model = (CameraButtonModel) widgetModel;

            BaseWidget widget = new FormCameraButtonWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setBundle(bundle)
                    .build();
            return widget;
        } else if (widgetModel instanceof DefaultCameraButtonModel) {

            DefaultCameraButtonModel model = (DefaultCameraButtonModel) widgetModel;

            BaseWidget widget = new FormDefaultCameraButtonWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setBundle(bundle)
                    .build();
            return widget;
        } else if (widgetModel instanceof DialogButtonModel) {

            DialogButtonModel model = (DialogButtonModel) widgetModel;

            BaseWidget widget = new FormDialogWidget.Builder(this.context)
                    //.setLabel(model.getLabel())
                    .setBundle(bundle)
                    .build();
            return widget;
        }
        else if (widgetModel instanceof CameraConnectButtonModel) {

            CameraConnectButtonModel model = (CameraConnectButtonModel) widgetModel;
            BaseWidget widget = new CameraConnectButtonWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setUuid(model.getUuid())
                    .setRepository(repository)
                    .setBundle(bundle)
                    .setTag(model.getTag())
                    .build();
            return widget;

        }

        else if(widgetModel instanceof ImageViewButtonModel) {

            ImageViewButtonModel model = (ImageViewButtonModel) widgetModel;

            BaseWidget widget = new FormImageViewButtonWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setUuid(model.getUuid())
                    .setRepository(repository)
                    .setBundle(bundle)
                    .setTag(model.getTag())
                    .build();
            return widget;
        }
        else if(widgetModel instanceof WidgetGroupRowModel){

            WidgetGroupRowModel model = (WidgetGroupRowModel)widgetModel;

            int index = 0;
            View[] widgets = new View[model.getChildren().size()];

            for(WidgetModel widgetModel1 : model.getChildren())
                widgets[index++] = getWidget(widgetModel1);

            View widgetGroup = WidgetUtils.createLinearLayout(context,WidgetUtils.HORIZONTAL,widgets);
            widgetGroup.setTag(model.getTag());

            return widgetGroup;
        }
        else if(widgetModel instanceof FormLabelModel){

            FormLabelModel model = (FormLabelModel) widgetModel;

            BaseWidget widget = new TextViewWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setTextSize(model.getTextSize())
                    .setWeight(model.getWeight())
                    .build();

            return widget;
        }
        else if(widgetModel instanceof DistrictFacilityPickerModel){

            DistrictFacilityPickerModel model = (DistrictFacilityPickerModel) widgetModel;

            DistrictFacilityPickerWidget widget = new DistrictFacilityPickerWidget(this.context, model.getDistrictText(), model.getFacilityText(),this.repository);
            return widget;
        }
        else if(widgetModel instanceof ProviderLabelModel){

            ProviderLabelModel model = (ProviderLabelModel) widgetModel;

            ProviderLabelWidget widget = new ProviderLabelWidget(this.context,this.repository, bundle);
            widget.setLabelText(model.getLabel());
            widget.setLabelTextSize(model.getTextSize());
            widget.setValueTextSize(model.getTextSize());
            widget.setTag(model.getTag());
            return widget;
        }
        else if (widgetModel instanceof ProviderNumberModel) {
            ProviderNumberModel model = (ProviderNumberModel) widgetModel;

            ProviderNumberWidget widget = new ProviderNumberWidget(this.context, this.repository, this.bundle)
                    .setLabel(model.getLabel())
                    .setTextSize(model.getTextSize())
                    .build();

            return widget;
        }
        else if(widgetModel instanceof DistrictLabelModel){

            DistrictLabelModel model = (DistrictLabelModel) widgetModel;

            DistrictLabelWidget widget = new DistrictLabelWidget(this.context,this.repository, bundle);
            widget.setLabelText(model.getLabel());
            widget.setLabelTextSize(model.getTextSize());
            widget.setValueTextSize(model.getTextSize());
            widget.setTag(model.getTag());
            return widget;
        }
        else if(widgetModel instanceof FacilityLabelModel){

            FacilityLabelModel model = (FacilityLabelModel) widgetModel;

            FacilityLabelWidget widget = new FacilityLabelWidget(this.context,this.repository, bundle);
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
            widget.setBundle(bundle);

            return widget;
        }
        else if(widgetModel instanceof BasicConceptWidgetModel){

            BasicConceptWidgetModel model = (BasicConceptWidgetModel) widgetModel;

            return new BasicConceptWidget(context)
                    .setStyle(model.getStyle())
                    .setTag(model.getTag())
                    .setBundle(bundle)
                    .setForm(form)
                    .setConceptId(model.getConceptId())
                    .setDataType(model.getDataType())
                    .setRepository(repository)
                    .setLabel(model.getLabel())
                    .setHint(model.getHint())
                    .setTextSize(model.getTextSize())
                    .setLogic(model.getLogic())
                    .setFutureDate(model.getFutureDate())
                    //pass Uuid to basic widget
                    .setUuid(model.getUuid())
                    .setWeight(model.getWeight())
                    .build();
        }
        else if(widgetModel instanceof BasicDrugWidgetModel) {
            BasicDrugWidgetModel model = (BasicDrugWidgetModel) widgetModel;

            BaseWidget widget = new BasicDrugWidget.Builder(this.context)
                    .setUuid(model.getUuid())
                    .setRepository(repository)
                    .setBundle(bundle)
                    .setTag(model.getTag())
                    .build();

            return widget;
        }
        else if(widgetModel instanceof GenderPickerModel){

            GenderPickerModel model = (GenderPickerModel) widgetModel;

            BaseWidget widget = new GenderPickerWidget.Builder(this.context)
                    .setFemaleLabel("Female")
                    .setMaleLabel("Male")
                    .setBundle(this.bundle)
                    .setErrorMessage(model.getErrorMessage())
                    .setTag(model.getTag())
                    .setWeight(1)
                    .build();

            return widget;
        }
        else if(widgetModel instanceof DistrictPickerModel){

            DistrictPickerModel model = (DistrictPickerModel) widgetModel;
            BaseWidget widget = new DistrictPickerWidget.Builder(this.context)
                    .setDistrictLabel("District")
                    .setProvinceLabel("Province")
                    .setRepository(repository)
                    .setBundle(this.bundle)
                    .setTag(widgetModel.getTag())
                    .setWeight(1)
                    .build();

            return widget;
        }
        else if(widgetModel instanceof DatePickerModel){

            DatePickerModel model = (DatePickerModel)widgetModel;
            BaseWidget widget = new DatePickerWidget.Builder(this.context)
                    .setLabel(model.getLabel())
                    .setHint(((DatePickerModel) widgetModel).getHint())
                    .setBundle(this.bundle)
                    .setRequired(model.getRequired())
                    .setErrorMessage(model.getErrorMessage())
                    .setWeight(1)
                    .setFutureDate(((DatePickerModel) widgetModel).getFutureDate())
                    .setTag(model.getTag())
                    .build();

            return widget;
        } else if(widgetModel instanceof NumericEditTextModel) {

            NumericEditTextModel model = (NumericEditTextModel) widgetModel;

            BaseWidget widget = new NumericEditTextWidget.Builder(this.context)
                    .setDataType(model.getDataType())
                    .setHint(model.getHint())
                    .setLabel(model.getLabel())
                    .setTextSize(18)
                    .setBundle(this.bundle)
                    .setWeight(1)
                    .setTag(model.getTag())
                    .build();

            return widget;
        }

        return null;
    }
}
