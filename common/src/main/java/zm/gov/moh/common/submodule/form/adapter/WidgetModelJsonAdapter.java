package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import zm.gov.moh.common.submodule.form.model.ConceptDataType;
import zm.gov.moh.common.submodule.form.model.widgetModel.AbstractCodedConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.AbstractConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.BasicConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CervicalCancerIDEditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CodedConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictFacilityPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DropDownWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FacilityLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;
import zm.gov.moh.common.submodule.form.model.attribute.BasicFormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttributeJson;
import zm.gov.moh.common.submodule.form.widget.BasicConceptWidget;
import zm.gov.moh.common.submodule.form.widget.CodedConceptWidget;

public class WidgetModelJsonAdapter {

    @FromJson
    WidgetModel fromJson(WidgetModelJson widgetModelJson) {

        switch (widgetModelJson.getWidgetType()) {

            case "EditText":

                final EditTextModel editText = new EditTextModel();

                editText.setWidgetType(widgetModelJson.getWidgetType());
                editText.setTag(widgetModelJson.getTag());
                editText.setHint(widgetModelJson.getHint());
                editText.setText(widgetModelJson.getText());
                editText.setWeight(widgetModelJson.getWeight());

                return editText;

            case "DatePickerButton":

                final DatePickerButtonModel datePickerButtonModel = new DatePickerButtonModel();

                datePickerButtonModel.setWidgetType(widgetModelJson.getWidgetType());
                datePickerButtonModel.setTag(widgetModelJson.getTag());
                datePickerButtonModel.setText(widgetModelJson.getText());

                return datePickerButtonModel;

            case "WidgetGroupRow":

                final WidgetGroupRowModel widgetGroupRowModel = new WidgetGroupRowModel();

                widgetGroupRowModel.setWidgetType(widgetModelJson.getWidgetType());
                widgetGroupRowModel.addChildren(widgetModelJson.getWidgets());


                return widgetGroupRowModel;

            case "TextView":

                final FormLabelModel formLabelModel = new FormLabelModel();

                formLabelModel.setWidgetType(widgetModelJson.getWidgetType());
                formLabelModel.setTag(widgetModelJson.getTag());
                formLabelModel.setLabel(widgetModelJson.getLabel());
               // formLabelModel.setText(widgetModelJson.getText());
                formLabelModel.setTextSize(widgetModelJson.getTextSize());

                return formLabelModel;
            /*
            case "DropDownView":
                final DropDownWidgetModel dropDownViewModel = new DropDownWidgetModel();
                dropDownViewModel.setWidgetType();

                return dropDownViewModel;
            */

            case "DistrictFacilityPicker":

                final DistrictFacilityPickerModel model = new DistrictFacilityPickerModel();

                model.setWidgetType(widgetModelJson.getWidgetType());
                model.setTag(widgetModelJson.getTag());
                model.setFacilityText(widgetModelJson.getFacilityText());
                model.setDistrictText(widgetModelJson.getDistrictText());

                return model;

            case "ProviderLabel":

                final ProviderLabelModel providerLabelModel = new ProviderLabelModel();

                providerLabelModel.setWidgetType(widgetModelJson.getWidgetType());
                providerLabelModel.setTag(widgetModelJson.getTag());
                providerLabelModel.setLabel(widgetModelJson.getLabel());
                providerLabelModel.setTextSize(widgetModelJson.getTextSize());

                return providerLabelModel;

            case "FacilityLabel":

                final FacilityLabelModel facilityLabelModel = new FacilityLabelModel();

                facilityLabelModel.setWidgetType(widgetModelJson.getWidgetType());
                facilityLabelModel.setTag(widgetModelJson.getTag());
                facilityLabelModel.setLabel(widgetModelJson.getLabel());
                facilityLabelModel.setTextSize(widgetModelJson.getTextSize());

                return facilityLabelModel;

            case "DistrictLabel":

                final DistrictLabelModel districtLabelModel = new DistrictLabelModel();

                districtLabelModel.setWidgetType(widgetModelJson.getWidgetType());
                districtLabelModel.setTag(widgetModelJson.getTag());
                districtLabelModel.setLabel(widgetModelJson.getLabel());
                districtLabelModel.setTextSize(widgetModelJson.getTextSize());

                return districtLabelModel;

            case "Concept":

                if(widgetModelJson.getDataType().equals(ConceptDataType.CODED)){

                    CodedConceptWidgetModel codedConceptWidgetModel = new CodedConceptWidgetModel();
                    codedConceptWidgetModel.setConceptId(widgetModelJson.getConceptId());
                    codedConceptWidgetModel.setStyle(widgetModelJson.getStyle());
                    codedConceptWidgetModel.setDataType(widgetModelJson.getDataType());
                    codedConceptWidgetModel.setLabel(widgetModelJson.getLabel());
                    codedConceptWidgetModel.setTextSize(widgetModelJson.getTextSize());
                    codedConceptWidgetModel.setLogic(widgetModelJson.getLogic());
                    codedConceptWidgetModel.setTag(widgetModelJson.getTag());


                    return codedConceptWidgetModel;
                }


                BasicConceptWidgetModel basicConceptWidgetModel = new BasicConceptWidgetModel();

                basicConceptWidgetModel.setConceptId(widgetModelJson.getConceptId());
                basicConceptWidgetModel.setDataType(widgetModelJson.getDataType());
                basicConceptWidgetModel.setWidgetType(widgetModelJson.getWidgetType());
                basicConceptWidgetModel.setTag(widgetModelJson.getTag());
                basicConceptWidgetModel.setLabel(widgetModelJson.getLabel());
                basicConceptWidgetModel.setTextSize(widgetModelJson.getTextSize());
                basicConceptWidgetModel.setHint(widgetModelJson.getHint());



                return basicConceptWidgetModel;

            case "CCPIZEditText":

                final CervicalCancerIDEditTextModel cervicalCancerIDEditTextModel = new CervicalCancerIDEditTextModel();

                cervicalCancerIDEditTextModel.setWidgetType(widgetModelJson.getWidgetType());
                cervicalCancerIDEditTextModel.setTag(widgetModelJson.getTag());
                cervicalCancerIDEditTextModel.setWeight(widgetModelJson.getWeight());
                cervicalCancerIDEditTextModel.setTag(widgetModelJson.getTag());

                return cervicalCancerIDEditTextModel;
               default: return null;
        }
    }


    @ToJson
    WidgetModelJson toJson(WidgetModel widgetModel) {

        WidgetModelJson json = new WidgetModelJson();

         if(widgetModel instanceof EditTextModel){

             EditTextModel basicFormEditText = (EditTextModel) widgetModel;

             json.setWidgetType(basicFormEditText.getWidgetType());
             json.setHint(basicFormEditText.getHint());
             json.setTag(basicFormEditText.getTag());
             json.setText(basicFormEditText.getText());
         }
        else if(widgetModel instanceof DatePickerButtonModel){

             DatePickerButtonModel datePickerButtonModel = (DatePickerButtonModel) widgetModel;

            json.setWidgetType(datePickerButtonModel.getWidgetType());
            json.setTag(datePickerButtonModel.getTag());
        }
         else if(widgetModel instanceof FormLabelModel){

             FormLabelModel formLabelModel = (FormLabelModel) widgetModel;

             json.setWidgetType(formLabelModel.getWidgetType());
             json.setTag(formLabelModel.getTag());
             json.setText(formLabelModel.getLabel());
         }

        return json;
    }

    //form attritube converters
    @FromJson
    FormAttribute fromJson(FormAttributeJson formAttributeJson) {

        switch (formAttributeJson.getType()) {

            case "Basic":

                final BasicFormAttribute attribute = new BasicFormAttribute();

                attribute.setFormType(formAttributeJson.getType());
                attribute.setSubmitLabel(formAttributeJson.getSubmitLabel());

                return attribute;

            default: return null;
        }
    }

    @ToJson
    FormAttributeJson toJson(FormAttribute formAttribute) {

        FormAttributeJson json = new FormAttributeJson();

        if(formAttribute instanceof BasicFormAttribute){
            BasicFormAttribute basicFormEditText = (BasicFormAttribute) formAttribute;

            json.setType(basicFormEditText.getFormType());
        }

        return json;
    }
}