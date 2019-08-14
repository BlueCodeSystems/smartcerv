package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
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
    protected String dataType;

    public EditTextWidget(Context context) {
        super(context);
    }


    public void setDataType(String DataType) {
        this.dataType = DataType;
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
        //auto populate
        if (mBundle != null) {
            String value = mBundle.getString((String) getTag());
            if (value != null)
                mEditText.setText(value);
        }

        if(dataType != null && dataType.equals("Numeric")) {
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else if(dataType != null && dataType.equals("Text")) {
            //auto capitalize first word in sentence
            mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);

            InputFilter filtertxt = (source, start, end, dest, dstart, dend) -> {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            };

            mEditText.setFilters(new InputFilter[]{filtertxt});

        }
        /** if(dataType != null && dataType.equals("Numeric"))
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        else if(dataType != null && dataType.equals("Text"))
            //auto capitalize first word in sentence
            mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);

           /** String valuetxt = mEditText.getText().toString();
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher( mEditText.getText().toString());
            boolean bs = ms.matches();
            if (bs == true) {
                mEditText.setText(valuetxt);
            } else
                mEditText.setError("Enter Letters only");
            */

        mEditText.setGravity(Gravity.TOP);


        WidgetUtils.setLayoutParams(mEditText, WidgetUtils.MATCH_PARENT, WidgetUtils.WRAP_CONTENT, mWeight);

        mEditText.setGravity(Gravity.START);
        WidgetUtils.setLayoutParams(mEditText,WidgetUtils.MATCH_PARENT,WidgetUtils.WRAP_CONTENT, mWeight);

        addView(mEditText);
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
        protected String mDataType;

        public Builder setDataType(String dataType) {
            mDataType = dataType;
            return this;
        }


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
            if (mDataType !=null)
                widget.setDataType(mDataType);
            if (mValueChangeListener != null)
                widget.setOnValueChangeListener(mValueChangeListener);

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return widget;
        }
    }
}