package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;

public class ARTNumericEditTextWidget extends EditTextWidget {

    protected static final int TOTAL_ART_LENGTH = 16;

    protected String mDataType;

    public ARTNumericEditTextWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {
        super.onCreateView();

        if(mDataType != null) {
            mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

            InputFilter[] inputFilters = mEditText.getFilters();
            InputFilter[] newInputFilters = new InputFilter[inputFilters.length + 1];
            System.arraycopy(inputFilters, 0, newInputFilters, 0, inputFilters.length);
            newInputFilters[newInputFilters.length - 1] = new InputFilter.LengthFilter(TOTAL_ART_LENGTH);   // Set total length

            mEditText.setFilters(newInputFilters);
        }
    }
}
