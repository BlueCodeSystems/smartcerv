package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import zm.gov.moh.common.submodule.form.model.widgetModel.BasicConceptWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.BasicDrugWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CervicalCancerIDEditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DatePickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DefaultCameraButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictFacilityPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.DistrictPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FacilityLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.GenderPickerModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ImageViewButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.CameraButtonModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.PhotoAlbumButtonWidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderLabelModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ProviderNumberModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.ReadonlyTextModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.TextBoxModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.TextBoxTwoModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;
import zm.gov.moh.common.submodule.form.model.attribute.BasicFormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttributeJson;

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
                editText.setLabel(widgetModelJson.getLabel());


                return editText;

            case "ReadonlyText":

                final ReadonlyTextModel readonlyText = new ReadonlyTextModel();

                readonlyText.setWidgetType(widgetModelJson.getWidgetType());
                readonlyText.setTag(widgetModelJson.getTag());
                readonlyText.setHint(widgetModelJson.getHint());
                readonlyText.setText(widgetModelJson.getText());
                readonlyText.setWeight(widgetModelJson.getWeight());
                readonlyText.setLabel(widgetModelJson.getLabel());

                return readonlyText;

            case "TextBoxOne":

                final TextBoxModel textBoxone = new TextBoxModel();

                textBoxone.setWidgetType(widgetModelJson.getWidgetType());
                textBoxone.setTag(widgetModelJson.getTag());
                textBoxone.setHint(widgetModelJson.getHint());
                textBoxone.setText(widgetModelJson.getText());
                textBoxone.setWeight(widgetModelJson.getWeight());
                textBoxone.setLabel(widgetModelJson.getLabel());

                return textBoxone;

            case "TextBoxTwo":

                final TextBoxTwoModel textBoxtwo = new TextBoxTwoModel();

                textBoxtwo.setWidgetType(widgetModelJson.getWidgetType());
                textBoxtwo.setTag(widgetModelJson.getTag());
                textBoxtwo.setHint(widgetModelJson.getHint());
                textBoxtwo.setText(widgetModelJson.getText());
                textBoxtwo.setWeight(widgetModelJson.getWeight());
                textBoxtwo.setLabel(widgetModelJson.getLabel());

                return textBoxtwo;

            case "DatePickerButton":

                final DatePickerButtonModel datePickerButtonModel = new DatePickerButtonModel();

                datePickerButtonModel.setWidgetType(widgetModelJson.getWidgetType());
                datePickerButtonModel.setTag(widgetModelJson.getTag());
                datePickerButtonModel.setText(widgetModelJson.getText());

                return datePickerButtonModel;

            case "ImageViewButton":

                final ImageViewButtonModel imageViewButtonModel = new ImageViewButtonModel();

                imageViewButtonModel.setWidgetType(widgetModelJson.getWidgetType());
                imageViewButtonModel.setTag(widgetModelJson.getTag());
                imageViewButtonModel.setLabel(widgetModelJson.getLabel());
                imageViewButtonModel.setUuid(widgetModelJson.getUuid());


                return imageViewButtonModel;

            case "PhotoAlbumButton":

                final PhotoAlbumButtonWidgetModel photoAlbumButtonWidgetModel = new PhotoAlbumButtonWidgetModel();

                photoAlbumButtonWidgetModel.setWidgetType(widgetModelJson.getTag());
                photoAlbumButtonWidgetModel.setLabel(widgetModelJson.getLabel());

                return photoAlbumButtonWidgetModel;

            case "CameraButton":

                final CameraButtonModel CameraButtonModel = new CameraButtonModel();

                CameraButtonModel.setWidgetType(widgetModelJson.getTag());
                CameraButtonModel.setLabel(widgetModelJson.getLabel());

                return CameraButtonModel;

            case "DefaultCameraButton":

                final DefaultCameraButtonModel DefaultCameraButtonModel = new DefaultCameraButtonModel();

                DefaultCameraButtonModel.setWidgetType(widgetModelJson.getTag());
                DefaultCameraButtonModel.setLabel(widgetModelJson.getLabel());

                return DefaultCameraButtonModel;

            case "WidgetGroupRow":

                final WidgetGroupRowModel widgetGroupRowModel = new WidgetGroupRowModel();

                widgetGroupRowModel.setWidgetType(widgetModelJson.getWidgetType());
                widgetGroupRowModel.setTag(widgetModelJson.getTag());
                widgetGroupRowModel.addChildren(widgetModelJson.getWidgets());


                return widgetGroupRowModel;

            case "TextView":

                final FormLabelModel formLabelModel = new FormLabelModel();

                formLabelModel.setWidgetType(widgetModelJson.getWidgetType());
                formLabelModel.setTag(widgetModelJson.getTag());
                formLabelModel.setLabel(widgetModelJson.getLabel());
                formLabelModel.setTextSize(widgetModelJson.getTextSize());

                return formLabelModel;

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
            case "ProviderNumber":

                final ProviderNumberModel providerNumberModel = new ProviderNumberModel();

                providerNumberModel.setWidgetType(widgetModelJson.getWidgetType());
                providerNumberModel.setTag(widgetModelJson.getTag());
                providerNumberModel.setLabel(widgetModelJson.getLabel());
                providerNumberModel.setTextSize(widgetModelJson.getTextSize());

                return providerNumberModel;

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

                final BasicConceptWidgetModel basicConceptWidgetModel = new BasicConceptWidgetModel();

                basicConceptWidgetModel.setConceptId(widgetModelJson.getConceptId());
                basicConceptWidgetModel.setDataType(widgetModelJson.getDataType());
                basicConceptWidgetModel.setWidgetType(widgetModelJson.getWidgetType());
                basicConceptWidgetModel.setTag(widgetModelJson.getTag());
                basicConceptWidgetModel.setLabel(widgetModelJson.getLabel());
                basicConceptWidgetModel.setLogic(widgetModelJson.getLogic());
                basicConceptWidgetModel.setTextSize(widgetModelJson.getTextSize());
                basicConceptWidgetModel.setHint(widgetModelJson.getHint());
                basicConceptWidgetModel.setStyle(widgetModelJson.getStyle());
                basicConceptWidgetModel.setWeight(widgetModelJson.getWeight());
                //Added to put uuid in bundle
                basicConceptWidgetModel.setUuid(widgetModelJson.getUuid());

                return basicConceptWidgetModel;

            case "CCPIZEditText":

                final CervicalCancerIDEditTextModel cervicalCancerIDEditTextModel = new CervicalCancerIDEditTextModel();

                cervicalCancerIDEditTextModel.setWidgetType(widgetModelJson.getWidgetType());
                cervicalCancerIDEditTextModel.setTag(widgetModelJson.getTag());
                cervicalCancerIDEditTextModel.setWeight(widgetModelJson.getWeight());
                cervicalCancerIDEditTextModel.setTag(widgetModelJson.getTag());

                return cervicalCancerIDEditTextModel;

            case "GenderPicker":

                final GenderPickerModel genderPickerModel = new GenderPickerModel();

                genderPickerModel.setWidgetType(widgetModelJson.getWidgetType());
                genderPickerModel.setTag(widgetModelJson.getTag());
                genderPickerModel.setWeight(widgetModelJson.getWeight());
                genderPickerModel.setStyle(widgetModelJson.getStyle());

                return genderPickerModel;

            case "DistrictPicker":

                final DistrictPickerModel districtPickerModel = new DistrictPickerModel();

                districtPickerModel.setWidgetType(widgetModelJson.getWidgetType());
                districtPickerModel.setTag(widgetModelJson.getTag());
                districtPickerModel.setWeight(widgetModelJson.getWeight());
                districtPickerModel.setLabel(widgetModelJson.getLabel());

                return districtPickerModel;

            case "ConceptDrug":
                final BasicDrugWidgetModel basicDrugWidgetModel = new BasicDrugWidgetModel();

                basicDrugWidgetModel.setUuid(widgetModelJson.getUuid());

                return basicDrugWidgetModel;

            case "DatePicker":

                final DatePickerModel datePickerModel = new DatePickerModel();

                datePickerModel.setWidgetType(widgetModelJson.getWidgetType());
                datePickerModel.setTag(widgetModelJson.getTag());
                datePickerModel.setLogic(widgetModelJson.getLogic());
                datePickerModel.setWeight(widgetModelJson.getWeight());
                datePickerModel.setHint(widgetModelJson.getHint());
                datePickerModel.setLabel(widgetModelJson.getLabel());

                return datePickerModel;

            default:
                return null;
        }
    }


    @ToJson
    WidgetModelJson toJson(WidgetModel widgetModel) {

        WidgetModelJson json = new WidgetModelJson();

        if (widgetModel instanceof EditTextModel) {

            EditTextModel basicFormEditText = (EditTextModel) widgetModel;

            json.setWidgetType(basicFormEditText.getWidgetType());
            json.setHint(basicFormEditText.getHint());
            json.setTag(basicFormEditText.getTag());
            json.setText(basicFormEditText.getText());

        } else if (widgetModel instanceof TextBoxModel) {

            TextBoxModel basicFormTextBox = (TextBoxModel) widgetModel;

            json.setWidgetType(basicFormTextBox.getWidgetType());
            json.setHint(basicFormTextBox.getHint());
            json.setTag(basicFormTextBox.getTag());
            json.setText(basicFormTextBox.getText());

        } else if (widgetModel instanceof DatePickerButtonModel) {

            DatePickerButtonModel datePickerButtonModel = (DatePickerButtonModel) widgetModel;

            json.setWidgetType(datePickerButtonModel.getWidgetType());
            json.setTag(datePickerButtonModel.getTag());
        } else if (widgetModel instanceof FormLabelModel) {

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

        switch (formAttributeJson.getPanelType()) {

            case "form":

                final BasicFormAttribute basicFormAttribute = new BasicFormAttribute();

                basicFormAttribute.setPanelType(formAttributeJson.getPanelType());
                basicFormAttribute.setFormType(formAttributeJson.getFormType());
                basicFormAttribute.setType(formAttributeJson.getType());
                basicFormAttribute.setEncounterId(formAttributeJson.getEncounterId());
                basicFormAttribute.setSubmitLabel(formAttributeJson.getSubmitLabel());
                //Added new form attribute called logic
                basicFormAttribute.setLogic(formAttributeJson.getLogic());

                return basicFormAttribute;

            case "Encounter":

                final BasicFormAttribute encounterFormAttribute = new BasicFormAttribute();

                encounterFormAttribute.setType(formAttributeJson.getType());
                encounterFormAttribute.setEncounterId(formAttributeJson.getEncounterId());
                encounterFormAttribute.setSubmitLabel(formAttributeJson.getSubmitLabel());

                return encounterFormAttribute;

            default:
                return null;
        }
    }

    @ToJson
    FormAttributeJson toJson(FormAttribute formAttribute) {

        FormAttributeJson json = new FormAttributeJson();

        if (formAttribute instanceof BasicFormAttribute) {
            BasicFormAttribute basicFormEditText = (BasicFormAttribute) formAttribute;

            json.setType(basicFormEditText.getType());
        }

        return json;
    }
}
