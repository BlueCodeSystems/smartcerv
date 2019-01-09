package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import zm.gov.moh.common.submodule.form.model.Action;
import zm.gov.moh.common.submodule.form.model.ConceptDataType;

import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.common.submodule.form.model.ObsValue;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.utils.Utils;

public class BasicConceptWidget extends LinearLayoutCompat {

    String mLabel;
    String mHint;
    AppCompatEditText mEditText;
    AppCompatTextView mTextView;
    long mConceptId;
    int weight;
    int mTextSize;
    Context mContext;
    String mDataType;
    ObsValue<Object> mObsValue;
    final String DATE_PICKER_LABEL = "Select Date";
    HashMap<String, Object> formData;
    Repository repository;
    List<Logic> logic;
    View rootView;
    Object mValue;


    public BasicConceptWidget(Context context){
        super(context);

        this.mContext = context;
    }

    public BasicConceptWidget setFormData(HashMap<String,Object> formData){

        this.formData = formData;
        return this;
    }

    public BasicConceptWidget setLabel(String text) {

        mLabel = text;
        return this;
    }

    public BasicConceptWidget setTextSize(int size){

        mTextSize = size;
        return this;
    }

    public BasicConceptWidget setConceptId(long id){

        this.mConceptId = id;
        return this;
    }

    public BasicConceptWidget setRepository(Repository repository) {

        this.repository = repository;
        return this;
    }

    public BasicConceptWidget setHint(String hint) {

        mHint = hint;
        return this;
    }

    public BasicConceptWidget setDataType(String dataType) {

        mDataType = dataType;
        return this;
    }

    public void onTextValueChangeListener(CharSequence value){

        mObsValue.setValue(value);
    }

    public BasicConceptWidget setRootView(View rootView) {

        this.rootView = rootView;
        return this;
    }

    public BasicConceptWidget setLogic(List<Logic> logic) {

        this.logic = logic;
        return this;
    }

    public List<Logic> getLogic() {
        return logic;
    }

    public void onDateValueChangeListener(DatePicker view, int year, int monthOfYear, int dayOfMonth){

        // set day of month , month and year value in the edit text
        String date = (year+"-" + ((monthOfYear + 1 < 10)? "0"+(monthOfYear + 1 ):(monthOfYear + 1 ))+"-"+((dayOfMonth < 10)? "0"+dayOfMonth:dayOfMonth));
        mObsValue.setValue(date);
        mEditText.setText(date);
    }

    public void onSelectedValue(int i){
       setObsValue(i);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i,i1,i2,i3);
    }

    public void setObsValue(Object obsValue) {

        mValue = obsValue;
        mObsValue.setValue(obsValue);
    }

    public void performAction(Action action){

        View root = this.getRootView();

        switch (action.getType()){

            case "skipLogic":
                for(String tag : action.getMetadata().getTags())
                    root.findViewWithTag(tag).setVisibility(View.VISIBLE);
        }
    }

    public BasicConceptWidget setTag(String tag) {
        super.setTag(tag);

        return this;
    }


    public BasicConceptWidget build(){

        mObsValue = new ObsValue<>();
        mObsValue.setConceptId(mConceptId);
        formData.put((String)this.getTag(),mObsValue);

        WidgetUtils.setLayoutParams(this,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
                .setOrientation(WidgetUtils.HORIZONTAL);

        //Create and intialize widgets
        mTextView = WidgetUtils.setLayoutParams(new AppCompatTextView(mContext),WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        mTextView.setText(mLabel);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize);

        mEditText = WidgetUtils.setLayoutParams(new AppCompatEditText(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
        mEditText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        mEditText.setHint(mHint);


        //Return view according to concept data type
        switch (mDataType) {

            case ConceptDataType.TEXT:
                //this.addView(WidgetUtils.createLinearLayout(context, WidgetUtils.HORIZONTAL, this.label, mEditText));
                View view = WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText);
                this.addView(view);
                //WidgetUtils.enableView(view, false);
                //WidgetUtils.enableView(view, true);
                break;

            case ConceptDataType.DATE:
                mEditText.setHint(DATE_PICKER_LABEL);
                Utils.dateDialog(mContext, mEditText, this::onDateValueChangeListener);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText));
                break;

            case ConceptDataType.BOOLEAN:

                HashMap<String, Integer> radioLabelValues = new HashMap<>();
                radioLabelValues.put("Yes", 1);
                radioLabelValues.put("No", 2);
                RadioGroup radioGroup = WidgetUtils.createRadioButtons(mContext, radioLabelValues, this::onSelectedValue, RadioGroup.HORIZONTAL, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 0);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, radioGroup));
                break;
        }

        return this;
    }
}
