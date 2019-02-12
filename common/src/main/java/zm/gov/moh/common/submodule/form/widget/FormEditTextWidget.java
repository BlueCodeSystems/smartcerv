package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import androidx.appcompat.widget.LinearLayoutCompat;
import zm.gov.moh.core.model.Key;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.HashMap;

public class FormEditTextWidget extends androidx.appcompat.widget.AppCompatEditText{

    Bundle bundle;


    public FormEditTextWidget(Context context,int weight){
        super(context);

        LinearLayoutCompat.LayoutParams layoutParams =  new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = weight;

        //this.setAllCaps(false);
        this.setLayoutParams(layoutParams);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tag = getTag().toString();
                if(charSequence != null && charSequence !="")
                    bundle.putString((String) getTag(),charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
        if(bundle.containsKey(getTag().toString()))
            setText(bundle.getString(getTag().toString()));
    }

}
