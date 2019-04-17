package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;

public class EditTextWidget extends TextViewWidget implements Submittable<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected Bundle mBundle;
    protected AppCompatEditText mEditText;
    private EditTextWidget(Context context){
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
    public void addViewToViewGroup() {

        super.addViewToViewGroup();
        mEditText = new AppCompatEditText(mContext);
        mEditText.setHint(mHint);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::setValue));
        WidgetUtils.setLayoutParams(mEditText,WidgetUtils.MATCH_PARENT,WidgetUtils.WRAP_CONTENT, mWeight);
        addView(mEditText);
    }

    AppCompatEditText getEditTextView(){
        return mEditText;
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

            EditTextWidget widget = new EditTextWidget(mContext);

            if(mHint != null)
                widget.setHint(mHint);
            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mLabel != null)
                widget.setLabel(mLabel);
            widget.setTextSize(mTextSize);

            widget.addViewToViewGroup();

            return  widget;
        }
    }
}
