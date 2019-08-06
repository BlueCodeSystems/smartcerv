package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.util.Consumer;

public class EditTextWidget extends TextViewWidget implements Submittable<CharSequence> {


    protected String mValue;
    protected String mHint;
    protected Bundle mBundle;
    protected AppCompatEditText mEditText;
    private Context context;
    protected Consumer<CharSequence> mValueChangeListener;

    public EditTextWidget(Context context) {
        super(context);
    }

    @Override
    public String getValue() {
        return mValue;
    }

    public void setHint(String hint) {
        mHint = hint;
    }

    @Override
    public void setValue(CharSequence value) {

        mBundle.putString((String) this.getTag(), value.toString());

        if (mValueChangeListener != null)
            mValueChangeListener.accept(value);
    }

    public void setOnValueChangeListener(Consumer<CharSequence> valueChangeListener) {
        mValueChangeListener = valueChangeListener;
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

        mEditText.setGravity(Gravity.TOP);
        //auto capitalize first word in sentence
        mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);
        WidgetUtils.setLayoutParams(mEditText, WidgetUtils.MATCH_PARENT, WidgetUtils.WRAP_CONTENT, mWeight);

        mEditText.setGravity(Gravity.START);
        WidgetUtils.setLayoutParams(mEditText,WidgetUtils.MATCH_PARENT,WidgetUtils.WRAP_CONTENT, mWeight);

        addView(mEditText);

        //auto populate
        if (mBundle != null) {
            String value = mBundle.getString((String) getTag());
            if (value != null)
                mEditText.setText(value);
        }
    }

    @Override
    public void addViewToViewGroup() {

    }


    AppCompatEditText getEditTextView() {
        return mEditText;
    }

    public static class Builder extends TextViewWidget.Builder {

        protected String mHint;
        protected Bundle mBundle;
        protected Consumer<CharSequence> mValueChangeListener;

        public Builder(Context context) {
            super(context);
        }

        public Builder setHint(String hint) {

            mHint = hint;
            return this;
        }

        public Builder setBundle(Bundle bundle) {

            mBundle = bundle;
            return this;
        }

        public Builder setOnValueChangeListener(Consumer<CharSequence> valueChangeListener) {
            mValueChangeListener = valueChangeListener;
            return this;
        }

        @Override
        public BaseWidget build() {

            // super.build();
            EditTextWidget widget = new EditTextWidget(mContext);

            if (mHint != null)
                widget.setHint(mHint);
            if (mBundle != null)
                widget.setBundle(mBundle);
            if (mLabel != null)
                widget.setLabel(mLabel);
            if (mTag != null)
                widget.setTag(mTag);
            if (mValueChangeListener != null)
                widget.setOnValueChangeListener(mValueChangeListener);

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return widget;
        }
    }
}