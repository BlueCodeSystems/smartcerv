package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.util.Consumer;
import butterknife.OnClick;
import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.DoubleSummaryStatistics;

public class FormImageViewButtonWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener  {

    private Bundle bundle;
    public DoubleSummaryStatistics onClick;



    public FormImageViewButtonWidget(Context context) {
        super(context);
    }

    @Override
    public void addViewToViewGroup() {
        AppCompatButton button = new AppCompatButton(this.mContext);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        this.addView(button);
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;

    }

    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((AppCompatActivity)mContext).startActivityForResult(intent,5);
    }

    public static class Builder extends TextViewWidget.Builder{

        protected String mLabel;
        protected Bundle mBundle;

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

            widget.setTextSize(mTextSize);

            widget.addViewToViewGroup();

            return  widget;
        }
    }

}
