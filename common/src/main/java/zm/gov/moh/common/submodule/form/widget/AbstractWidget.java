package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import java.util.HashMap;

import androidx.appcompat.widget.LinearLayoutCompat;

public abstract class AbstractWidget extends LinearLayoutCompat {

    protected HashMap<String,Object> formData;

    public AbstractWidget(Context context){
        super(context);
    }

    public void setFormData(HashMap<String, Object> formData) {
        this.formData = formData;
    }

    public HashMap<String, Object> getFormData() {
        return formData;
    }

    public static class Builder{

        Builder(Context context){

        }
    }

    public void setValue(Object value){

    }
}
