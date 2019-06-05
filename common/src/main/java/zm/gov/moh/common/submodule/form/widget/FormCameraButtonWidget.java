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

import java.io.File;
import java.net.DatagramPacket;
import java.util.DoubleSummaryStatistics;

public class FormCameraButtonWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {


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


    public FormCameraButtonWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {
        AppCompatButton button = new AppCompatButton(this.mContext);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        this.addView(button);
        //{super.onCreateView();
            //AppCompatButton button = new AppCompatButton(this.mContext)
            //mageView = new AppCompatImageView(mContext);
            //WidgetUtils.setLayoutParams(cam,100,100);
            //button.setOnClickListener(this);
            //button.setText(this.mLabel);
            //this.addView(button);
            //this.addView(imageView);}
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

    /*@Override
    public String getValue() {
        return null;
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                //".jpg",         /* suffix */
                //storageDir      /* directory */
        //);

        // Save a file: path for use with ACTION_VIEW intents
        /*currentPhotoPath = image.getAbsolutePath();
        return image;
    }*/

    //private File getExternalFilesDir(String directoryPictures) {
        //return image;
    //}

    @Override
    public void onClick(View v) {
        Intent cameraIntent = mContext.getPackageManager().getLaunchIntentForPackage("jp.co.canon.ic.cameraconnect");
        ((AppCompatActivity)mContext).startActivity(cameraIntent);  
    }

    public PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }



        /*
        {
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/storage/emulated/0/DCIM/Camera/")));
        }
        {
            i.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        ((AppCompatActivity)mContext).startActivityForResult(i, mRequestCode);
        

            }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }



    private void sendBroadcast(Intent mediaScanIntent) {
    }

    protected void startActivityForResult(int requestCode, int resultCode, Intent data) {
                this.requestCode = requestCode;
                this.resultCode = resultCode;
                intent = data;
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }*/

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
