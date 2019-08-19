package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;

public class TextBoxWidgetTwo extends TextViewWidget implements Submittable<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected Bundle mBundle;
    protected AppCompatEditText mTextBox;
    private Context context;

    public TextBoxWidgetTwo(Context context){
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
        mTextBox.setGravity(Gravity.START);
        //mTextBox.setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        //mTextBox.setGravity(Gravity.CENTER_HORIZONTAL);
        WidgetUtils.setLayoutParams(mTextBox,300,WidgetUtils.WRAP_CONTENT, mWeight);
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

    @Override
    public boolean isValid() {
        return true;
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
            TextBoxWidgetTwo widget = new TextBoxWidgetTwo(mContext);

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
