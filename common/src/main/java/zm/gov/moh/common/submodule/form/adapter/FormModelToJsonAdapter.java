package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import zm.gov.moh.common.submodule.form.model.EditTextModel;
import zm.gov.moh.common.submodule.form.model.WidgetModel;
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

                return editText;

               default: return null;
        }
    }



    @ToJson
    WidgetModelJson toJson(WidgetModel widgetModel) {

        WidgetModelJson json = new WidgetModelJson();

         if(widgetModel instanceof EditTextModel){
             EditTextModel basicFormEditText = (EditTextModel) widgetModel;

             json.setWidgetType(basicFormEditText.getWidgetType());
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