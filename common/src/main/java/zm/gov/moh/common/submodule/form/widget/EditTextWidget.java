package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import zm.gov.moh.common.R;

public class EditTextWidget extends SubmittableWidget<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected String mLabel;
    protected int mTextSize;
    protected AppCompatEditText mEditText;
    protected TextView mTextView;
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

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public void setOnValueChangeListener(Consumer<CharSequence> valueChangeListener){
        mValueChangeListener = valueChangeListener;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @Override
    public void onCreateView() {

        if(mLabel != null) {

            mTextView = new AppCompatTextView(mContext);
            mTextView.setText(mLabel);
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            WidgetUtils.setLayoutParams(mTextView, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight)
                    .setGravity(Gravity.CENTER_VERTICAL);

            addView(mTextView);
        }
        mEditText = new AppCompatEditText(mContext);
        mEditText.setHint(mHint);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::setValue));
        mEditText.setGravity(Gravity.START);
        WidgetUtils.setLayoutParams(mEditText,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT, mWeight);
        addView(mEditText);



        //auto populate
        if(mBundle != null) {
            String value = mBundle.getString((String) getTag());
            if (value != null)
                mEditText.setText(value);
        }
    }

    public boolean isValid(){

        if(mRequired != null && mRequired) {
            mValue = mBundle.getString((String) getTag());

            if (mValue != null && mValue.matches(mRegex))
                return true;
            else {
                mEditText.setError(mErrorMessage);
                return false;
            }
        }
        else
            return true;
    }


    AppCompatEditText getEditTextView(){
        return mEditText;
    }

    public static class Builder extends SubmittableWidget.Builder{

        protected String mHint;
        protected int mTextSize;
        protected String mLabel;
        protected Consumer<CharSequence> mValueChangeListener;

        public Builder(Context context){
            super(context);
        }

        public Builder setHint(String hint){

            mHint = hint;
            return this;
        }

        public Builder setLabel(String label){
            mLabel = label;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.mTextSize = textSize;
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
            if(mRequired != null)
                widget.setRequired(mRequired);

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return  widget;
        }
    }
}
