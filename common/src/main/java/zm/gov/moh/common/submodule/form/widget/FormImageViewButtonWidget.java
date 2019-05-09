package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class FormImageViewButtonWidget extends ConceptWidget<ObsValue<String>> implements View.OnClickListener  {

    public DoubleSummaryStatistics onClick;
    private long key;
    private Double value;
    private ArrayList<String> tags;
    protected String mLabel;

    //Function<String,Long> encounterTypeUuidToId = getRepository().getDatabase().encounterTypeDao()::getIdByUuid;

    public FormImageViewButtonWidget(Context context) {
        super(context);
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        AppCompatButton button = new AppCompatButton(this.mContext);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        this.addView(button);
    }

    @Override
    public void setValue(ObsValue<String> value) {

    }

    @Override
    public ObsValue<String> getValue() {
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        mBundle.putString(Key.VIEW_TAG, (String)getTag());
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((AppCompatActivity)mContext).startActivityForResult(intent,5);
    }

    public void onUriRetrieved(Uri uri){

        ObsValue<String> obsValue = new ObsValue<>(mConceptId,ConceptDataType.TEXT,uri.toString());

        String tagg = (String)getTag();
        mBundle.putSerializable(tagg,obsValue);
    }

    public static class Builder extends ConceptWidget.Builder{

        protected String mLabel;

        public Builder(Context context){
            super(context);
        }

        public FormImageViewButtonWidget.Builder setLabel(String label){

            mLabel = label;
            return this;
        }

        public FormImageViewButtonWidget.Builder setBundle(Bundle bundle){

            mBundle = bundle;
            return this;
        }



        @Override
        public BaseWidget build() {

            FormImageViewButtonWidget widget = new FormImageViewButtonWidget(mContext);

            if(mLabel != null)
                widget.setLabel(mLabel);
            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mRepository != null)
                widget.setRepository(mRepository);
            if(mUuid != null)
                widget.setUuid(mUuid);
            if(mTag != null)
                widget.setTag(mTag);


            //widget.setTextSize(mTextSize);

            widget.onCreateView();

            return  widget;
        }
    }

}