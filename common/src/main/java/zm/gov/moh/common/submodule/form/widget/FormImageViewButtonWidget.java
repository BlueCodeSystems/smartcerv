package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import zm.gov.moh.common.submodule.form.util.MediaStorageUtil;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.MediaStoreFileLoader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class FormImageViewButtonWidget extends ConceptWidget<ObsValue<String>> implements View.OnClickListener  {

    public DoubleSummaryStatistics onClick;
    private long key;
    private Double value;
    private ArrayList<String> tags;
    protected String mLabel;
    protected AppCompatImageView imageView;
    protected String pictureFolder = "EDI";

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
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        mBundle.putString(Key.VIEW_TAG, (String)getTag());
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((AppCompatActivity)mContext).startActivityForResult(intent,5);
    }

    public void onUriRetrieved(Uri uri){

        //File file = new File(uri.);
        String path = getRealPathFromURI(mContext,uri );

        ObsValue<String> obsValue = new ObsValue<>(mConceptId,ConceptDataType.TEXT, uri.toString());


        mBundle.putSerializable( (String)getTag(),obsValue);
        imageView.setImageURI(uri);

        /*try {
            InputStream in = mContext.getContentResolver().openInputStream(uri);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri)
            File pictures = MediaStorageUtil.getPrivateAlbumStorageDir(mContext,pictureFolder);
        }catch (Exception e){

        }*/

    }

    public void saveToStorage(Uri uri){

    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndex(proj[0]);
            String path = cursor.getString(column_index);
            return path;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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