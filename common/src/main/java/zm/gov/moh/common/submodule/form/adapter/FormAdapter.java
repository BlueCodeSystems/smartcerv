package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import zm.gov.moh.common.submodule.form.model.FormModel;

public class FormAdapter{

    public static JsonAdapter<FormModel>  getAdapter(){

        Moshi moshi = new Moshi.Builder().add(new WidgetModelJsonAdapter()).build();
        JsonAdapter<FormModel> jsonAdapter = moshi.adapter(FormModel.class);
        return jsonAdapter;
    }

    public static JsonAdapter<FormModel>  getFormComponentAdapter(){

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<FormModel> jsonAdapter = moshi.adapter(FormModel.class);
        return jsonAdapter;
    }
}
