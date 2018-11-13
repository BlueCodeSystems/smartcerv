package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import zm.gov.moh.common.submodule.form.model.widget.DatePickerButtonModel;
import zm.gov.moh.common.submodule.form.model.widget.EditTextModel;
import zm.gov.moh.common.submodule.form.model.widget.FormLabelModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetGroupRowModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetModel;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;
import zm.gov.moh.common.submodule.form.model.attribute.BasicFormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttribute;
import zm.gov.moh.common.submodule.form.model.attribute.FormAttributeJson;

public class FormModelToJsonAdapter {

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
                formLabelModel.setText(widgetModelJson.getText());
                formLabelModel.setTextSize(widgetModelJson.getTextSize());

                return formLabelModel;

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
             json.setText(formLabelModel.getText());
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