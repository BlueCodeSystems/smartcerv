package zm.gov.moh.common.submodule.form.widget;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.WidgetModelJson;
import zm.gov.moh.core.utils.Utils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.DoubleSummaryStatistics;

public class FormDialogWidget extends TextViewWidget implements Submittable <String>, View.OnClickListener {


    private Bundle bundle;
    public DoubleSummaryStatistics onClick;
    private WidgetModelJson alertDialog;


    public FormDialogWidget(Context context) {
        super(context);
    }


    @Override
    public void onCreateView() {
        AppCompatButton button = new AppCompatButton(this.mContext);
        button.setOnClickListener(this);
        button.setText(this.mLabel);
        button.setBackgroundResource(R.drawable.ic_baseline_live_help_24px);
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.popupview);
        WidgetUtils.setLayoutParams(button, Utils.dpToPx(mContext, 50), Utils.dpToPx(mContext, 50));
        ((LinearLayoutCompat.LayoutParams) button.getLayoutParams()).setMarginEnd(Utils.dpToPx(mContext, 20));
        this.addView(button);

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

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setTitle("BATCH UPLOAD HELP");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.test_list_item);

        arrayAdapter.add(   "STEP 1: Go to the SmartCerv image upload web platform via " +
                            "a web browser following the url provided : www.smartcerv.org/image_upload");

        arrayAdapter.add(   "STEP 2: Upon the site opening up, enter user credentials.");

        arrayAdapter.add(   "STEP 3: Click 'Add SmartCerv uploads' on the top right corner " +
                            "of the screen to add/upload a batch of photo/images.");

        arrayAdapter.add(   "STEP 4: A page named 'Add Smartcerv upload' will appear. Click “Choose File” ");

        arrayAdapter.add(   "STEP 5: Select the desired images in the popup box by navigating to the location " +
                             "of the images on the computer. To select multiple images, hold down control key on " +
                                "the keyboard while clicking the images. Click open");

        arrayAdapter.add(   "STEP 6: The number of files selected appears next to the 'Choose file' icon. " +
                            "Click the save button to save the images.");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(mContext);
                builderInner.setMessage(strName);
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();

            }
        });
        builderSingle.show();
        //builderSingle.getLayoutParams().height = 6000;
        //builderSingle.getLayoutParams().width = 3000;


    /*@Override
    public void onClick(View v) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("BATCH UPLOAD HELP");
        alertDialog.setMessage("STEP 1: Go to the SmartCerv image upload web platform via a web browser following the url provided : <a> www.smartcerv.org/image_upload </a>" +
                               "STEP 2: Upon the site opening up, enter user credentials.",
                               "STEP 3: Click \"Add SmartCerv uploads\" on the top right corner of the screen to add/upload a batch of photo/images.",);




        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.getWindow().setLayout(600, 3000);

    }*/
    }


    public static class Builder extends TextViewWidget.Builder {

        protected String mLabel;
        protected Bundle mBundle;

        public Builder(Context context) {
            super(context);
        }

        public FormDialogWidget.Builder setLabel(String label) {

            mLabel = label;
            return this;
        }

        public FormDialogWidget.Builder setBundle(Bundle bundle) {

            mBundle = bundle;
            return this;
        }

        @Override
        public BaseWidget build() {

            FormDialogWidget widget = new FormDialogWidget(mContext);

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
