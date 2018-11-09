package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RelativeLayout;

import zm.gov.moh.common.submodule.form.model.FormData;

public class FormEditTextWidget extends android.support.v7.widget.AppCompatEditText{

    FormData<String,Object> formData;


    public FormEditTextWidget(Context context){
        super(context);

        this.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formData.put((String) getTag(),charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setFormData(FormData<String, Object> formData) {
        this.formData = formData;
    }
}
