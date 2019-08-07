package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.util.TypedValue;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;

public class ProviderLabelWidget extends LinearLayoutCompat {

    private AppCompatTextView label;
    private AppCompatTextView value;
    private Bundle bundle;

    public ProviderLabelWidget(Context context, Repository repository, Bundle bundle){
        super(context);

        this.bundle = bundle;
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
                .getString(Key.AUTHORIZED_USER_UUID,"Not Found");

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

        String displayName = providerUser.getGivenName()+" "+providerUser.getFamilyName();
        setTextValue(displayName);
        this.bundle.putLong((String)getTag(), providerUser.getProviderId());
    }

    public void setFormData(Bundle bundle){
        this.bundle = bundle;
    }
}
