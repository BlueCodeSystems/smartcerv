package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.HashMap;

public class FormEditTextWidget extends android.support.v7.widget.AppCompatEditText{

    HashMap<String,Object> formData;


    public FormEditTextWidget(Context context,int weight){
        super(context);

        LinearLayoutCompat.LayoutParams layoutParams =  new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = weight;

        this.setAllCaps(false);
        this.setLayoutParams(layoutParams);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formData.put((String) getTag(),charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setFormData(HashMap<String, Object> formData) {
        this.formData = formData;
    }
}
