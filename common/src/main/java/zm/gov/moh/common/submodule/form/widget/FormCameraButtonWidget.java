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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.DoubleSummaryStatistics;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FormCameraButtonWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {


    private static final int REQUEST_IMAGE_CAPTURE = 6;
    private static final Object ACTION_IMAGE_CAPTURE = 6;
    private Bundle bundle;
    public DoubleSummaryStatistics onClick;
    private Intent intent;
    private int mRequestCode;


    public FormCameraButtonWidget(Context context) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        {
            /* i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp"))); */
        }  {
            i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        ((AppCompatActivity)mContext).startActivityForResult(i, mRequestCode);
    }

    private void startActivityForResult(Intent i, int mRequestCode) {
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

            widget.addViewToViewGroup();

            return widget;
        }
    }

}
