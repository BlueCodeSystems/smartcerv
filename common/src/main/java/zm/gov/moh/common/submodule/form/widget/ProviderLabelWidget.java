package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;

import java.util.HashMap;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;

public class ProviderLabelWidget extends LinearLayoutCompat {

    private AppCompatTextView label;
    private AppCompatTextView value;
    private HashMap<String,Object> formaData;

    public ProviderLabelWidget(Context context, Repository repository, HashMap<String, Object> formaData){
        super(context);

        this.formaData = formaData;
        LinearLayoutCompat.LayoutParams textViewlayoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        LinearLayoutCompat.LayoutParams linearlayoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        label = new AppCompatTextView(context);
        value = new AppCompatTextView(context);
        label.setLayoutParams(textViewlayoutParams);
        value.setLayoutParams(textViewlayoutParams);
        value.setTextColor(Color.BLACK);
        this.setLayoutParams(linearlayoutParams);
        this.setOrientation(LinearLayoutCompat.HORIZONTAL);
        this.addView(label);
        this.addView(value);

        String userUuid = repository.getDefaultSharePrefrences()
                .getString(context.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key),"Not Found");

        repository.getDatabase().providerUserDao().getAllByUserUuid(userUuid).observe((AppCompatActivity)context,this::setProvider);
    }

    public void setLabelText(String text) {
        this.label.setText(text);
    }

    private void setTextValue(String value) {

        this.value.setText("  "+value);
    }

    public void setLabelTextSize(int size){

        label.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    public void setValueTextSize(int size){
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    private void setProvider(ProviderUser providerUser) {

        String displayName = providerUser.given_name+" "+providerUser.family_name;
        setTextValue(displayName);
        this.formaData.put((String)getTag(), providerUser.provider_id);
    }

    public void setFormData(HashMap<String,Object> formData){
        this.formaData = formData;
    }
}
