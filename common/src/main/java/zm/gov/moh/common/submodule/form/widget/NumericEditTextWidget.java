package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;

public class NumericEditTextWidget extends EditTextWidget {

    protected static final int TOTAL_NRC_LENGTH = 11;    // 9 digits plus 2 slashes

    protected String mDataType;

    public NumericEditTextWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {
        super.onCreateView();

        if(mDataType != null && mDataType.equals("Numeric")) {
            mEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER);

            InputFilter[] inputFilters = mEditText.getFilters();
            InputFilter[] newInputFilters = new InputFilter[inputFilters.length + 1];
            System.arraycopy(inputFilters, 0, newInputFilters, 0, inputFilters.length);
            newInputFilters[newInputFilters.length - 1] = new InputFilter.LengthFilter(TOTAL_NRC_LENGTH);

            mEditText.setFilters(newInputFilters);

        } else {
            mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            int previousLength = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = mEditText.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int currentLength = editable.length();
                if ((previousLength < currentLength) && (editable.length() == 6 || editable.length() == 9)) {
                    editable.append("/");
                }
            }
        });

        removeView(mEditText);
        addView(mEditText);
    }

    public String getDataType() {
        return mDataType;
    }

    public void setDataType(String mDataType) {
        this.mDataType = mDataType;
    }

    public void setValue(CharSequence value) {

    }

    public static class Builder extends EditTextWidget.Builder {

        protected String mDataType;

        public Builder(Context context) {
            super(context);
        }

        public String getDataType() {
            return mDataType;
        }

        public Builder setDataType(String dataType) {
            this.mDataType = dataType;
            return this;
        }

        @Override
        public BaseWidget build() {

            NumericEditTextWidget widget = new NumericEditTextWidget(mContext);

            if (mHint != null)
                widget.setHint(mHint);
            if (mBundle != null)
                widget.setBundle(mBundle);
            if (mLabel != null)
                widget.setLabel(mLabel);
            if (mTag != null)
                widget.setTag(mTag);
            if(mDataType != null)
                widget.setDataType(mDataType);
            if (mValueChangeListener != null)
                widget.setOnValueChangeListener(mValueChangeListener);

            widget.setTextSize(mTextSize);

            widget.onCreateView();

            return widget;
        }
    }
}
