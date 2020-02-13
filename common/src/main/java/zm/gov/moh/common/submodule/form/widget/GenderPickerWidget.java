package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenderPickerWidget extends SubmittableWidget<String> {

    protected String mMaleLabel;
    protected String mFemaleLabel;
    protected final int BUTTON_ID_MALE = 1;
    protected final int BUTTON_ID_FEMALE = 2;
    protected Map<String,Long> genderData;
    protected RadioGroup radioGroup;
    private RadioButton errorTooltipAnchor;


   public GenderPickerWidget(Context context){
       super(context);
   }

    @Override
    public void setValue(String value) {
        mBundle.putString((String) getTag(),value);
    }

    @Override
    public String getValue() {
        return null;
    }

    public void onButtonSelected(int buttonId){

       final String genderValue = (buttonId == BUTTON_ID_MALE)? "M":"F";
       setValue(genderValue);
       if(errorTooltipAnchor != null)
           errorTooltipAnchor.setError(null);
    }

    public void setFemaleLabel(String mFemaleLabel) {
        this.mFemaleLabel = mFemaleLabel;
    }

    public void setMaleLabel(String mMaleLabel) {
        this.mMaleLabel = mMaleLabel;
    }

    @Override
    public boolean isValid() {

        if(mBundle.getString((String) getTag()) == null){

            errorTooltipAnchor = (RadioButton) radioGroup.getChildAt(1);
            errorTooltipAnchor.setError(mErrorMessage);
            return false;
        }
        return true;
    }

    @Override
    public void onCreateView() {

        genderData = new LinkedHashMap<>();
        genderData.put(mMaleLabel, Long.valueOf(BUTTON_ID_MALE));
        genderData.put(mFemaleLabel, Long.valueOf(BUTTON_ID_FEMALE));
        radioGroup = WidgetUtils.createRadioButtons(mContext, genderData,this::onButtonSelected,WidgetUtils.HORIZONTAL ,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,mWeight);
        this.addView(radioGroup);
    }

    public static class Builder extends SubmittableWidget.Builder{

        protected String mMaleLabel;
        protected String mFemaleLabel;

        public Builder(Context context){
            super(context);
        }

        public GenderPickerWidget.Builder setFemaleLabel(String label){

            mFemaleLabel = label;
            return this;
        }

        public GenderPickerWidget.Builder setMaleLabel(String label){

            mMaleLabel = label;
            return this;
        }

        @Override
        public BaseWidget build() {

            GenderPickerWidget widget = new GenderPickerWidget(mContext);

            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mMaleLabel != null)
                widget.setMaleLabel(mMaleLabel);
            if(mFemaleLabel != null)
                widget.setFemaleLabel(mFemaleLabel);
            if(mTag != null)
                widget.setTag(mTag);
            if(mErrorMessage != null)
                widget.setErrorMessage(mErrorMessage);

            widget.onCreateView();

            return  widget;
        }
    }
}
