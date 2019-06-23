package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUserNumber;

public class ProviderNumberWidget extends LinearLayoutCompat {

    private String label;
    private String number;  // the phone number, equivalent of value on ProviderLabelWidget
    private Integer textSize;
    private Context context;
    private Bundle bundle;
    private Integer age;

    private AppCompatTextView labelView;
    private AppCompatTextView phoneNumberView;

    public ProviderNumberWidget(Context context, Repository repository, Bundle bundle) {
        super(context);

        this.bundle = bundle;
        this.context = context;

        // Set the layout for the label - value
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat
            .LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        Long personId = bundle.getLong(Key.PERSON_ID);
        String userUuid = repository.getDefaultSharePrefrences()
                .getString(context.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key),"Not Found");

        repository.getDatabase().providerUserNumberDao().getAllNumbersByUser(userUuid).observe((AppCompatActivity)context,this::setNumberOfProvider);

    }

    public String getNumber() {
        return number;
    }

    public ProviderNumberWidget setNumber(String number) {
        this.number = number;
        return this;
    }

    private void setNumberOfProvider(ProviderUserNumber providerUserNumber) {
        number = providerUserNumber.getPhoneNumber();
    }

    public String getLabel() {
        return label;
    }

    public ProviderNumberWidget setLabel(String label) {
        this.label = label;
        return this;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public ProviderNumberWidget setTextSize(Integer textSize) {
        this.textSize = textSize;
        return this;
    }

    public ProviderNumberWidget build() {

        labelView = new AppCompatTextView(context);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        labelView.setText(label);

        phoneNumberView = new AppCompatTextView(context);
        phoneNumberView.setTextColor(Color.BLACK);
        phoneNumberView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        phoneNumberView.setText(" " + number);

        WidgetUtils.setLayoutParams(this, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);

        this.addView(labelView);
        this.addView(phoneNumberView);

        return this;
    }

}
