package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import zm.gov.moh.common.submodule.form.utils.MediaStorageUtil;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.database.entity.domain.Obs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.UUID;

public class FormImageViewButtonWidget extends ConceptWidget<ObsValue<String>> implements View.OnClickListener, Retainable  {

    private static final int RESULT_LOAD_IMAGE1 = 200;
    public DoubleSummaryStatistics onClick;
    private long key;
    private Double value;
    private ArrayList<String> tags;
    protected String mLabel;
    protected AppCompatImageView imageView;
    protected final String pictureFolder = "EDI";
    private SharedPreferences.Editor params;
    private File dir;
    private Object Gallery;
    FormEditTextWidget uploadImageName, downloadImageName;
    private AppCompatEditText mEditText;
    private Intent imageIntent;
    private String imagename;

    //Function<String,Long> encounterTypeUuidToId = getRepository().getDatabase().encounterTypeDao()::getIdByUuid;

    public FormImageViewButtonWidget(Context context) {
        super(context);
    }



    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public String getUuid() {
        return mUuid;
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        AppCompatButton button = new AppCompatButton(this.mContext);
        imageView = new AppCompatImageView(mContext);
        WidgetUtils.setLayoutParams(imageView,1200,600);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        this.addView(button);
        this.addView(imageView);
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
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mBundle.putString(Key.VIEW_TAG, (String)getTag());
        //Toast.makeText(FormImageViewButtonWidget.this, "You clicked on ImageView", Toast.LENGTH_LONG).show();
        ((AppCompatActivity)mContext).startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE1);


    }

    public void onUriRetrieved(Uri uri){


        String sampleName = UUID.randomUUID().toString();
        ObsValue<String> obsValue = new ObsValue<>(mConceptId,ConceptDataType.TEXT, sampleName);

        mBundle.putSerializable( (String)getTag(), obsValue);
        imageView.setImageURI(uri);

        try {

            File ediSamples = MediaStorageUtil.getPrivateAlbumStorageDir(mContext, MediaStorageUtil.EDI_DIRECTORY);
            mContext.getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(ediSamples.getAbsolutePath()+"/"+sampleName+".png"));
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        }
        catch (Exception e){
            Exception ex = e;
        }
    }

    public void onLastObsRetrieved(Obs obs) {

        try {
            String sample = obs.getValueText();

            File image = MediaStorageUtil.getPrivateAlbumStorageDir(mContext, MediaStorageUtil.EDI_DIRECTORY);
            Glide.with(mContext)
                    .asBitmap()
                    .load(image.getCanonicalPath()+"/"+sample+".png")
                    .into(imageView);
        }catch (Exception e){

        }

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