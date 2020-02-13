package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.net.DatagramPacket;
import java.util.DoubleSummaryStatistics;

public class FormDefaultCameraButtonWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {


    private static final int REQUEST_IMAGE_CAPTURE = 6;
    private static final Object ACTION_IMAGE_CAPTURE = 6;
    private static final int SELECT_FILE1 = 7;
    private Bundle bundle;
    public DoubleSummaryStatistics onClick;
    private Intent intent;
    private int mRequestCode;
    private String selectedPath1;
    private int SELECT_FILE2 = 8;
    private String selectedPath2;
    private DatagramPacket data;
    private WidgetModelJson tv;
    private ImageView imageView;
    private int requestCode;
    private int resultCode;
    private File image;


    public FormDefaultCameraButtonWidget(Context context) {
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
    public boolean isValid() {
        return true;
    }

    @Override
    public void addViewToViewGroup() {

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
        Intent i = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        {
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp")));
        }
        {
            i.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        ((AppCompatActivity)mContext).startActivityForResult(i, mRequestCode);

    }



public static class Builder extends TextViewWidget.Builder {

    protected String mLabel;
    protected Bundle mBundle;

    public Builder(Context context) {
        super(context);
    }

    public FormDefaultCameraButtonWidget.Builder setLabel(String label) {

        mLabel = label;
        return this;
    }

    public FormDefaultCameraButtonWidget.Builder setBundle(Bundle bundle) {

        mBundle = bundle;
        return this;
    }

    @Override
    public BaseWidget build() {

        FormDefaultCameraButtonWidget widget = new FormDefaultCameraButtonWidget(mContext);

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
