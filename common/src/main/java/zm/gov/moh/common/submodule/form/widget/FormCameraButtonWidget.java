package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.DatagramPacket;
import java.util.DoubleSummaryStatistics;

public class FormCameraButtonWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {
    
    private Bundle bundle;
    public DoubleSummaryStatistics onClick;



    public FormCameraButtonWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {
        AppCompatButton button = new AppCompatButton(this.mContext);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        this.addView(button);
    }

    @Override
    public void addViewToViewGroup() {

    }

    @Override
    public boolean isValid() {
        return true;
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

        Intent cameraIntent = mContext.getPackageManager().getLaunchIntentForPackage("jp.co.canon.ic.cameraconnect");
        if(cameraIntent == null) {
            Toast.makeText(mContext, "Canon Camera connect not installed", Toast.LENGTH_LONG).show();
            return;
        }
        ((AppCompatActivity)mContext).startActivity(cameraIntent);  
    }

    public static class Builder extends TextViewWidget.Builder {

        protected String mLabel;
        protected Bundle mBundle;

        public Builder(Context context) {
            super(context);
        }

        public FormCameraButtonWidget.Builder setLabel(String label) {

            mLabel = label;
            return this;
        }

        public FormCameraButtonWidget.Builder setBundle(Bundle bundle) {

            mBundle = bundle;
            return this;
        }

        @Override
        public BaseWidget build() {

            FormCameraButtonWidget widget = new FormCameraButtonWidget(mContext);

            if (mLabel != null)
                widget.setLabel(mLabel);
            if (mBundle != null)
                widget.setBundle(mBundle);

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return widget;
        }
    }

}
