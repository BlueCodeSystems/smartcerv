package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import zm.gov.moh.core.model.ConceptDataType;

import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.utils.Utils;

@SuppressWarnings("deprecation")
public class BasicConceptWidget extends LinearLayoutCompat {

    String mLabel;
    String mHint;
    AppCompatEditText mEditText;
    AppCompatTextView mTextView;
    AppCompatActivity mTextBox;
    long mConceptId;
    int mWeight = 0;
    int mTextSize;
    Context mContext;
    String mDataType;
    ObsValue<Object> mObsValue;
    final String DATE_PICKER_LABEL = "Select Date";
    Bundle bundle;
    Repository repository;
    List<Logic> logic;
    Form form;
    Object mValue;
    String mStyle;
    LinkedHashSet<Long> answerConcepts;
    Map<String,Long> conceptNameIdMap;
    AtomicBoolean canSetValue;


    public void onDateValueChangeListener(DatePicker view, int year, int monthOfYear, int dayOfMonth){

        // set day of month , month and year value in the edit text
        String date = (year+"-" + ((monthOfYear + 1 < 10)? "0"+(monthOfYear + 1 ):(monthOfYear + 1 ))+"-"+((dayOfMonth < 10)? "0"+dayOfMonth:dayOfMonth));
        mObsValue.setValue(date);
        mEditText.setText(date);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        long id = (long)compoundButton.getId();

        if(!(checked) && answerConcepts.contains(id))
            answerConcepts.remove(id);
        else
            answerConcepts.add(id);

        setObsValue(answerConcepts);
    }

    public void onSelectedValue(long i){

        if(!answerConcepts.isEmpty())
            answerConcepts.clear();

        answerConcepts.add(i);
        setObsValue(answerConcepts);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i,i1,i2,i3);
    }

    public void setObsValue(Object obsValue) {

        if(canSetValue.get()) {
            mValue = obsValue;
            mObsValue.setValue(obsValue);

            if (logic != null)
                for (Logic logic : logic)
                    if (logic.getAction().getType().equals("skipLogic"))
                        if ((mValue != null) && (((Set<Long>) mValue).contains(Math.round((Double) logic.getCondition().getValue())))) {
                            WidgetUtils.applyOnViewGroupChildren(form.getRootView(), v -> v.setVisibility(VISIBLE), logic.getAction().getMetadata().getTags().toArray());
                            form.getFormContext().getVisibleWidgetTags().removeAll(logic.getAction().getMetadata().getTags());
                        } else {

                            Set<String> tags = new HashSet<>();
                            WidgetUtils.extractTagsRecursive(form.getRootView(), tags, logic.getAction().getMetadata().getTags());
                            form.getFormContext().getVisibleWidgetTags().addAll(tags);
                        }
            render();
        }
    }

    public BasicConceptWidget setTag(String tag) {
        super.setTag(tag);

        return this;
    }

    public void render(){

        WidgetUtils.applyOnViewGroupChildren(form.getRootView(),
                        v ->{
                                v.setVisibility(GONE);
                                if(v instanceof BasicConceptWidget)
                                    ((BasicConceptWidget)v).reset();
                        },
                        form.getFormContext().getVisibleWidgetTags().toArray());
    }

    public BasicConceptWidget build() {

        mObsValue = new ObsValue<>();
        mObsValue.setConceptDataType(mDataType);
        answerConcepts = new LinkedHashSet<>();
        mObsValue.setConceptId(mConceptId);
        bundle.putSerializable((String) this.getTag(), mObsValue);
        canSetValue = new AtomicBoolean();
        canSetValue.set(true);

        if (logic != null)
            for (Logic logic : logic)
                if (logic.getAction().getType().equals("skipLogic"))
                    form.getFormContext().getVisibleWidgetTags().addAll(logic.getAction().getMetadata().getTags());

        WidgetUtils.setLayoutParams(this, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                .setOrientation(WidgetUtils.HORIZONTAL);

        //Create and intialize widgets

        mTextView = WidgetUtils.setLayoutParams(new AppCompatTextView(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        mTextView.setText(mLabel);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);

        mEditText = WidgetUtils.setLayoutParams(new AppCompatEditText(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
        mEditText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        mEditText.setHint(mHint);


        //Return view according to concept data type
        switch (mDataType) {

            case ConceptDataType.TEXT:
                View view = WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText);
                if (mStyle.equals("TextBoxOne")) {
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                    ShapeDrawable border = new ShapeDrawable(new RectShape());
                    border.getPaint().setStyle(Paint.Style.STROKE);
                    border.getPaint().setColor(Color.BLACK);
                    mEditText.setBackground(border);
                    mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
                    mEditText.setGravity(Gravity.LEFT);
                    WidgetUtils.setLayoutParams(mEditText, 800, 200, mWeight);
                    this.addView(view);
                } else if (mStyle.equals("TextBoxTwo")) {
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                    ShapeDrawable border = new ShapeDrawable(new RectShape());
                    border.getPaint().setStyle(Paint.Style.STROKE);
                    border.getPaint().setColor(Color.BLACK);
                    mEditText.setBackground(border);
                    mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
                    mEditText.setGravity(Gravity.CENTER);
                    WidgetUtils.setLayoutParams(mEditText, 300, 70, mWeight);
                    addView(mEditText);
                }


                break;

            case ConceptDataType.NUMERIC:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                view = WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText);
                this.addView(view);
                break;


            case ConceptDataType.DATE:
                mEditText.setHint(DATE_PICKER_LABEL);
                Utils.dateDialog(mContext, mEditText, this::onDateValueChangeListener);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText));
                break;



            case ConceptDataType.BOOLEAN:

                HashMap<String, Long>  conceptNameIdMap = new HashMap<>();
                if(mStyle.equals("radio")) {

                     conceptNameIdMap.put("Yes", 1L);
                     conceptNameIdMap.put("No", 2L);
                    RadioGroup radioGroup = WidgetUtils.createRadioButtons(mContext, conceptNameIdMap, this::onSelectedValue, RadioGroup.HORIZONTAL, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 0);
                    this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, radioGroup));
                }else if(mStyle.equals("check")){

                     conceptNameIdMap.put(mLabel, 1L);
                    RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, conceptNameIdMap,this::onCheckedChanged, RadioGroup.HORIZONTAL,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,0);
                    this.addView(checkBoxGroup);
                    mObsValue.setValue(answerConcepts);
                }
                break;

            case  ConceptDataType.CODED:

                repository.getDatabase()
                        .conceptAnswerNameDao()
                        .getByConceptId(mConceptId)
                        .observe((AppCompatActivity)mContext,this::onConceptIdAnswersRetrieved);
                break;
        }

        render();
        return this;
    }

    public void onConceptIdAnswersRetrieved(List<ConceptAnswerName> conceptAnswerNames){

        conceptNameIdMap = new LinkedHashMap<>();
        answerConcepts = new LinkedHashSet<>();
        for(ConceptAnswerName conceptAnswerName: conceptAnswerNames)
             conceptNameIdMap.put(conceptAnswerName.getName(), conceptAnswerName.getAnswerConcept());

        int orientation = ( conceptNameIdMap.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;

        switch (mStyle){

            case "check":
                RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, conceptNameIdMap,this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,mWeight);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL,mTextView , checkBoxGroup));
                mObsValue.setValue(answerConcepts);
                break;

            case "radio":
                RadioGroup radioGroup = WidgetUtils.createRadioButtons(mContext, conceptNameIdMap,this::onSelectedValue, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,mWeight);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL,mTextView, radioGroup));
                break;

            case "dropdown":
                AppCompatSpinner spinner = WidgetUtils.createSpinner(mContext, conceptNameIdMap,this::onSelectedValue,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,mWeight);
                if(mLabel != null)
                    this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL,mTextView, spinner));
                else
                    this.addView(spinner);


                break;
        }

        render();
    }
    public BasicConceptWidget(Context context){
        super(context);

        this.mContext = context;
    }

    public BasicConceptWidget setBundle(Bundle bundle){

        this.bundle = bundle;
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

    public BasicConceptWidget setForm(Form form) {

        this.form = form;
        return this;
    }

    public BasicConceptWidget setLogic(List<Logic> logic) {

        this.logic = logic;
        return this;
    }

    public BasicConceptWidget setWeight(int mWeight) {
        this.mWeight = mWeight;
        return this;
    }

    public int getWeight() {
        return mWeight;
    }

    public List<Logic> getLogic() {
        return logic;
    }

    public BasicConceptWidget setStyle(String style) {

        mStyle = style;
        return this;
    }

    public void reset(){

        canSetValue.compareAndSet(true,false);
        if(mStyle != null)
            switch (mStyle){

                case "check":
                    WidgetUtils.applyOnViewGroupChildren(this, view -> {
                        if(view instanceof CheckBox)
                            ((CheckBox)view).setChecked(false);
                    });
                    break;

                case "radio":
                    WidgetUtils.applyOnViewGroupChildren(this, view -> {
                        if(view instanceof RadioButton)
                            ((RadioButton)view).setChecked(false);
                    });
                    break;
            }

         canSetValue.compareAndSet(false,true);
    }
}
