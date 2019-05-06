package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.widgetModel.IRecyclerViewClickListener;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.DoubleSummaryStatistics;

public abstract class FormPhotoAlbumWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {

    private Bundle bundle;
    public DoubleSummaryStatistics onClick;
    private int ORIGINAL_REQUEST_CODE = 3;
    private Object view;
    private Intent intent;
    IRecyclerViewClickListener clickListener;


    public FormPhotoAlbumWidget(Context context) {
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
    protected void onCreate(Bundle savedInstanceState) {

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





    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progBar);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());

        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }


    public static class Builder extends TextViewWidget.Builder {

        protected String mLabel;
        protected Bundle mBundle;

        public Builder(Context context) {
            super(context);
        }

        public FormPhotoAlbumWidget.Builder setLabel(String label) {

            mLabel = label;
            return this;
        }

        public FormPhotoAlbumWidget.Builder setBundle(Bundle bundle) {

            mBundle = bundle;
            return this;
        }

        @Override
        public BaseWidget build() {

            FormPhotoAlbumWidget widget = new FormPhotoAlbumWidget(mContext) {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v, getAdapterPosition());

                }

                private int getAdapterPosition() {
                    return getAdapterPosition();
                }
            };

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