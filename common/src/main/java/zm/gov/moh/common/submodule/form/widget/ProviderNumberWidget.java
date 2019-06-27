package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;

public class ProviderNumberWidget extends LinearLayoutCompat {

    private String label;
    private String number;  // the phone number, equivalent of value on ProviderLabelWidget
    private Integer textSize;
    private Context context;

    private AppCompatTextView labelView;
    private AppCompatTextView phoneNumberView;

    public ProviderNumberWidget(Context context, Repository repository, Bundle bundle) {
        super(context);

        this.context = context;

        // Set the layout for the label - value
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat
            .LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        Long providerId = bundle.getLong(Key.PROVIDER_ID);

        repository.getDatabase().providerAttributeDao().getAttributeByType(OpenmrsConfig.PROVIDER_ATTRIBUTE_TYPE_PHONE, providerId).observe((AppCompatActivity)context,this::setNumberOfProvider);

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;

        phoneNumberView.setText(" " + number);
    }

    private void setNumberOfProvider(ProviderAttribute providerAttribute) {

        if(providerAttribute != null)
            setNumber(providerAttribute.getValueReference());
        else
            setNumber(" N/A");
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


        WidgetUtils.setLayoutParams(this, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);

        this.addView(labelView);
        this.addView(phoneNumberView);

        return this;
    }

}
