package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import zm.gov.moh.common.R;

public class TextBoxWidget extends TextViewWidget implements Submittable<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected Bundle mBundle;
    protected AppCompatEditText mTextBox;
    private Context context;

    public TextBoxWidget(Context context){
        super(context);
    }

    @Override
    public String getValue() {
        return mValue;
    }

    @Override
    public void setValue(CharSequence value) {

        mBundle.putString((String) this.getTag(),value.toString());
    }

    public void setHint(String hint){
        mHint = hint;
    }

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @Override
    public void onCreateView() {

        super.onCreateView();
        mTextBox = new AppCompatEditText(mContext);
        mTextBox.setHint(mHint);
        mTextBox.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(500) });
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);
        mTextBox.setBackground(border);
        mTextBox.addTextChangedListener(WidgetUtils.createTextWatcher(this::setValue));
        mTextBox.setGravity(Gravity.TOP);
        //mTextBox.setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        //mTextBox.setGravity(Gravity.CENTER_HORIZONTAL);
        WidgetUtils.setLayoutParams(mTextBox,800,200, mWeight);
            //.setGravity(Gravity.CENTER_VERTICAL);
        addView(mTextBox);




        //auto populate
        String value = mBundle.getString((String) getTag());
        if(value != null)
            mTextBox.setText(value);


    }



    @Override
    public void addViewToViewGroup() {

    }


    AppCompatEditText getTextBoxView(){
        return mTextBox;
    }

    public static class Builder extends TextViewWidget.Builder{

        protected String mHint;
        protected Bundle mBundle;

        public Builder(Context context){
            super(context);
        }

        public Builder setHint(String hint){

            mHint = hint;
            return this;
        }

        public Builder setBundle(Bundle bundle){

            mBundle = bundle;
            return this;
        }

        @Override
        public BaseWidget build() {

            // super.build();
            TextBoxWidget widget = new TextBoxWidget(mContext);

            if(mHint != null)
                widget.setHint(mHint);
            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mLabel != null)
                widget.setLabel(mLabel);
            if(mTag != null)
                widget.setTag(mTag);
            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return  widget;
        }
    }
}
