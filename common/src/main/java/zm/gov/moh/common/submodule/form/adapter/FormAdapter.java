package zm.gov.moh.common.submodule.form.adapter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import zm.gov.moh.common.submodule.form.model.Form;

public class FormAdapter{

    public static JsonAdapter<Form>  getAdapter(){

        Moshi moshi = new Moshi.Builder().add(new WidgetModelJsonAdapter()).build();
        JsonAdapter<Form> jsonAdapter = moshi.adapter(Form.class);
        return jsonAdapter;
    }

    public static JsonAdapter<Form>  getFormComponentAdapter(){

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Form> jsonAdapter = moshi.adapter(Form.class);
        return jsonAdapter;
    }
}
