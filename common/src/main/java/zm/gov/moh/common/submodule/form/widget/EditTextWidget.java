package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import zm.gov.moh.common.R;

public class EditTextWidget extends TextViewWidget implements Submittable<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected String mRegex;
    protected String mErrorMessage;
    protected Bundle mBundle;
    protected AppCompatEditText mEditText;
    private Context context;
    protected Consumer<CharSequence> mValueChangeListener;

    public EditTextWidget(Context context){
        super(context);
    }

    @Override
    public String getValue() {
        return mValue;
    }

    public void setHint(String hint){
        mHint = hint;
    }

    @Override
    public void setValue(CharSequence value) {

        mBundle.putString((String) this.getTag(),value.toString());

        if(mValueChangeListener != null)
            mValueChangeListener.accept(value);
    }

    public void setOnValueChangeListener(Consumer<CharSequence> valueChangeListener){
        mValueChangeListener = valueChangeListener;
    }

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public void setRegex(String mRegex) {
        this.mRegex = mRegex;
    }

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @Override
    public void onCreateView() {

        super.onCreateView();
        mEditText = new AppCompatEditText(mContext);
        mEditText.setHint(mHint);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::setValue));
        mEditText.setGravity(Gravity.START);
        WidgetUtils.setLayoutParams(mEditText,WidgetUtils.MATCH_PARENT,WidgetUtils.WRAP_CONTENT, mWeight);
        addView(mEditText);

        //auto populate
        if(mBundle != null) {
            String value = mBundle.getString((String) getTag());
            if (value != null)
                mEditText.setText(value);
        }
    }

    @Override
    public void addViewToViewGroup() {

    }

    public boolean isValid(){

        if(mValue != null && mValue.matches(mRegex))
            return true;
        else
            mEditText.setError("Crap!");

        return false;
    }


    AppCompatEditText getEditTextView(){
        return mEditText;
    }

    public static class Builder extends TextViewWidget.Builder{

        protected String mHint;
        protected Bundle mBundle;
        protected String mRegex;
        protected String mErrorMessage;
        protected Consumer<CharSequence> mValueChangeListener;

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

        public Builder setRegex(String mRegex){

            mRegex = mRegex;
            return this;
        }

        public Builder setErrorMessage(String errorMessage){

            mErrorMessage = errorMessage;
            return this;
        }

        public Builder setOnValueChangeListener(Consumer<CharSequence> valueChangeListener){
            mValueChangeListener = valueChangeListener;
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
            if(mTag != null)
                widget.setTag(mTag);
            if(mValueChangeListener != null)
                widget.setOnValueChangeListener(mValueChangeListener);
            if(mRegex != null)
                widget.setRegex(mRegex);
            if(mErrorMessage != null)
                widget.setErrorMessage(mErrorMessage);
            if(mLabel != null)

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return  widget;
        }
    }
}
