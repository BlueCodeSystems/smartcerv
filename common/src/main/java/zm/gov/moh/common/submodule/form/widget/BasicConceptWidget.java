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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.submodule.form.model.Condition;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;

public class BasicConceptWidget extends LinearLayoutCompat {

    String mLabel;
    String mHint;
    AppCompatEditText mEditText;
    RadioGroup radioGroup;
    RadioGroup checkBoxGroup;
    AppCompatTextView mTextView;
    long mConceptId;
    int mWeight = 0;
    int mTextSize;
    Context mContext;
    String mDataType;
    ObsValue<Object> mObsValue;
    Bundle bundle;
    Repository repository;
    List<Logic> logic;
    Form form;
    Object mValue;
    String mStyle;
    String mUuid;
    LinkedHashSet<Long> answerConcepts;
    Map<String, Long> conceptNameIdMap;
    AtomicBoolean canSetValue;
    ArrayList<Long> selectedConcepts = new ArrayList<>();
    boolean isCodedAnswersRetrieved = false;
    List<ConceptAnswerName> mConceptAnswerNames;
    final String STYLE_CHECK = "check";
    final String STYLE_RADIO = "radio";
    BaseWidget datePicker;

    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        if(answerConcepts == null)
            answerConcepts = new LinkedHashSet<>();

        long id = (long) compoundButton.getId();

        if (!(checked) && answerConcepts.contains(id))
            answerConcepts.remove(id);
        else
            answerConcepts.add(id);

        setObsValue(answerConcepts);
    }

    public void onSelectedValue(long i) {

        if(answerConcepts == null)
            answerConcepts = new LinkedHashSet<>();

        if (!answerConcepts.isEmpty())
            answerConcepts.clear();

        answerConcepts.add(i);
        setObsValue(answerConcepts);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i, i1, i2, i3);
    }

    public void setObsValue(Object obsValue) {

        if (canSetValue.get()) {

            if(mObsValue == null) {
                mObsValue = new ObsValue<>();
                mObsValue.setConceptDataType(mDataType);
                mObsValue.setConceptId(mConceptId);
                bundle.putSerializable((String) this.getTag(), mObsValue);
            }

            mValue = obsValue;
            mObsValue.setValue(obsValue);

            if (logic != null)
                for (Logic logic : logic)
                    if (logic.getAction().getType().equals("skipLogic")) {

                        Condition condition = logic.getCondition();
                        String dataType = logic.getCondition().getDataType();
                        if (dataType != null && logic.getCondition().getDataType().equals("date")) {

                            if (condition.getExpression().getLessThan() != null) {
                                String dob = bundle.getString((String) logic.getCondition().getValue());

                                if (dob != null) {
                                    LocalDate ld = LocalDate.parse(dob);
                                    int clientAge = LocalDateTime.now().getYear() - ld.getYear();
                                    int conditionAge = Integer.valueOf(condition.getExpression().getLessThan());

                                    if (clientAge < conditionAge) {

                                        Toast.makeText(mContext, "Patient should be older than 40", Toast.LENGTH_LONG).show();
                                        Set<String> tags = new HashSet<>();
                                        WidgetUtils.extractTagsRecursive(form.getRootView(), tags, logic.getAction().getMetadata().getTags());
                                        form.getFormContext().getVisibleWidgetTags().addAll(tags);

                                    }
                                }

                            }
                            // Section where skip logic is implemented
                        } else {

                            if ((mValue != null) && (((Set<Long>) mValue).contains(Math.round((Double) logic.getCondition().getValue())))) {
                                WidgetUtils.applyOnViewGroupChildren(form.getRootView(), v -> v.setVisibility(VISIBLE), logic.getAction().getMetadata().getTags().toArray());
                                form.getFormContext().getVisibleWidgetTags().removeAll(logic.getAction().getMetadata().getTags());
                            } else {

                                Set<String> tags = new HashSet<>();
                                WidgetUtils.extractTagsRecursive(form.getRootView(), tags, logic.getAction().getMetadata().getTags());
                                form.getFormContext().getVisibleWidgetTags().addAll(tags);
                            }
                        }
                    }
            render();
        }
    }

    public BasicConceptWidget setTag(String tag) {
        super.setTag(tag);

        return this;
    }

    public void render() {

        WidgetUtils.applyOnViewGroupChildren(form.getRootView(),
                v -> {
                    v.setVisibility(GONE);
                    if (v instanceof BasicConceptWidget)
                        ((BasicConceptWidget) v).reset();
                },
                form.getFormContext().getVisibleWidgetTags().toArray());
    }

    public BasicConceptWidget build() {

        canSetValue = new AtomicBoolean();
        canSetValue.set(true);

        if (logic != null)
            for (Logic logic : logic)
                if (logic.getAction().getType().equals("skipLogic"))
                    form.getFormContext().getVisibleWidgetTags().addAll(logic.getAction().getMetadata().getTags());

        WidgetUtils.setLayoutParams(this, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                .setOrientation(WidgetUtils.HORIZONTAL);

        //Create and initialize widgets

        mTextView = WidgetUtils.setLayoutParams(new AppCompatTextView(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        mTextView.setText(mLabel);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);

        mEditText = WidgetUtils.setLayoutParams(new AppCompatEditText(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);

        //Auto capitalize first letter of every word
        mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_CLASS_TEXT);

        //Set Cursor to Leftmost
        mEditText.setSelection(0);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
        mEditText.setHint(mHint);

        //Return view according to concept data type
        switch (mDataType) {

            case ConceptDataType.TEXT:
                View view = WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText);

                if (mStyle != null) {
                    if (mStyle.equals("TextBoxOne")) {
                        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                        ShapeDrawable border = new ShapeDrawable(new RectShape());
                        border.getPaint().setStyle(Paint.Style.STROKE);
                        border.getPaint().setColor(Color.BLACK);
                        mEditText.setBackground(border);
                        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
                        mEditText.setGravity(Gravity.LEFT);
                        mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);
                        WidgetUtils.setLayoutParams(mEditText, 800, WidgetUtils.WRAP_CONTENT, mWeight);
                        mEditText.setSingleLine(false);
                        mEditText.setMinLines(5);
                        this.addView(view);
                    } else if (mStyle.equals("TextBoxTwo")) {
                        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                        ShapeDrawable border = new ShapeDrawable(new RectShape());
                        border.getPaint().setStyle(Paint.Style.STROKE);
                        border.getPaint().setColor(Color.BLACK);
                        mEditText.setBackground(border);
                        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::onTextValueChangeListener));
                        mEditText.setGravity(Gravity.CENTER);
                        mEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT);
                        WidgetUtils.setLayoutParams(mEditText, 300, WidgetUtils.WRAP_CONTENT, mWeight);
                        addView(mEditText);
                    }
                } else {
                    addView(view);
                }
                break;

            case ConceptDataType.NUMERIC:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                view = WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView, mEditText);
                this.addView(view);
                break;


            case ConceptDataType.DATE:
                 datePicker =  new DatePickerWidget.Builder(mContext)
                                        .setOnValueChangeListener(this::onTextValueChangeListener)
                        .setHint(mHint).build();
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.HORIZONTAL, mTextView,datePicker));
                break;


            case ConceptDataType.BOOLEAN:

                HashMap<String, Long> conceptNameIdMap = new HashMap<>();
                if (mStyle.equals(STYLE_RADIO)) {

                    conceptNameIdMap.put("Yes", 1L);
                    conceptNameIdMap.put("No", 2L);
                    radioGroup = WidgetUtils.createRadioButtons(mContext, conceptNameIdMap, this::onSelectedValue, RadioGroup.HORIZONTAL, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 0);
                    this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, radioGroup));
                } else if (mStyle.equals(STYLE_CHECK)) {

                    conceptNameIdMap.put(mLabel, 1L);
                    checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, conceptNameIdMap, this::onCheckedChanged, RadioGroup.HORIZONTAL, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 0);
                    this.addView(checkBoxGroup);
                }
                break;

            case ConceptDataType.CODED:

                repository.getDatabase()
                        .conceptAnswerNameDao()
                        .getByConceptId(mConceptId)
                        .observe((AppCompatActivity) mContext, this::onConceptIdAnswersRetrieved);
                break;
        }

        render();
        return this;
    }

    public void onConceptIdAnswersRetrieved(List<ConceptAnswerName> conceptAnswerNames) {

        if(isCodedAnswersRetrieved){
            WidgetUtils.removeViewGroupChildren(this);
        }
        else
            isCodedAnswersRetrieved = true;

        mConceptAnswerNames = conceptAnswerNames;

        conceptNameIdMap = new LinkedHashMap<>();

        for (ConceptAnswerName conceptAnswerName : conceptAnswerNames)
            conceptNameIdMap.put(conceptAnswerName.getName(), conceptAnswerName.getAnswerConcept());

        int orientation = (conceptNameIdMap.size() > 2) ? WidgetUtils.VERTICAL : WidgetUtils.HORIZONTAL;

        switch (mStyle) {

            case STYLE_CHECK:
                checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, conceptNameIdMap, this::onCheckedChanged, orientation, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, checkBoxGroup));

                if(!selectedConcepts.isEmpty())
                    for (Long conceptId: selectedConcepts){
                        CheckBox button = checkBoxGroup.findViewWithTag(conceptId.intValue());

                        if(button != null)
                            button.setChecked(true);
                    }
                break;

            case STYLE_RADIO:
                RadioGroup radioGroup = WidgetUtils.createRadioButtons(mContext, conceptNameIdMap, this::onSelectedValue, orientation, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, radioGroup));

                if(!selectedConcepts.isEmpty())
                for (Long conceptId: selectedConcepts){
                    RadioButton button = radioGroup.findViewWithTag(conceptId.intValue());
                    button.setChecked(true);
                }
                break;

            case "dropdown":
                AppCompatSpinner spinner = WidgetUtils.createSpinner(mContext, conceptNameIdMap, this::onSelectedValue, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight);
                if (mLabel != null)
                    this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL, mTextView, spinner));
                else
                    this.addView(spinner);

                break;

        }

        render();
    }

    public BasicConceptWidget(Context context) {
        super(context);

        this.mContext = context;
    }

    public BasicConceptWidget setBundle(Bundle bundle) {

        this.bundle = bundle;
        return this;
    }

    public BasicConceptWidget setLabel(String text) {

        mLabel = text;
        return this;
    }

    public BasicConceptWidget setTextSize(int size) {

        mTextSize = size;
        return this;
    }

    public BasicConceptWidget setConceptId(long id) {

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


    //Add listener for a text change event
    public void onTextValueChangeListener(CharSequence value) {

        if(value != null && !value.equals(""))
            setObsValue(value);
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

    //getter for UUid
    public String getUuid() {
        return mUuid;
    }

    //setter for Uuid
    public BasicConceptWidget setUuid(String mUuid) {
        this.mUuid = mUuid;
        return this;
    }

    public BasicConceptWidget setStyle(String style) {

        mStyle = style;
        return this;
    }

    public void reset() {

        canSetValue.compareAndSet(true, false);
        if (mStyle != null)
            switch (mStyle) {

                case STYLE_CHECK:
                    WidgetUtils.applyOnViewGroupChildren(this, view -> {
                        if (view instanceof CheckBox)
                            ((CheckBox) view).setChecked(false);
                    });
                    break;

                case STYLE_RADIO:
                    WidgetUtils.applyOnViewGroupChildren(this, view -> {
                        if (view instanceof RadioButton)
                            ((RadioButton) view).setChecked(false);
                    });
                    break;
            }

        canSetValue.compareAndSet(false, true);
    }


    // Retrieves previously entered value and displays it in widget
    public void onLastObsRetrieved(ObsEntity... obs) {
    // Method retrieves entered observations and displays the values in widgets
        int firstIndex = 0;

        switch (mDataType) {

            // Numeric values retained
            case ConceptDataType.NUMERIC:
                String valuenum = String.valueOf(obs[firstIndex].getValueNumeric());

                //Numeric values are returned as Double, thus need to trim string to remove trailing decimal point
                String sub = valuenum.substring(valuenum.length() - 2);

                if (sub.equals(".0"))
                    mEditText.setText(valuenum.substring(0, valuenum.length() - 2));
                else
                    mEditText.setText(valuenum);
                break;

            //Retrieving Text Values
            case ConceptDataType.TEXT:
                String valuetxt = String.valueOf(obs[firstIndex].getValueText());
                mEditText.setText(valuetxt);
                break;

            case ConceptDataType.BOOLEAN:
                Long conceptId = obs[firstIndex].getValueCoded();

                if(mStyle.equals(STYLE_RADIO)){

                    if (conceptId == 1) {
                        RadioButton button = (RadioButton) radioGroup.getChildAt(0);
                        button.setChecked(true);
                    } else if (conceptId == 2) {
                        RadioButton button = (RadioButton) radioGroup.getChildAt(1);
                        button.setChecked(true);
                    }
                }else if(mStyle.equals(STYLE_CHECK)){
                   CheckBox checkBox = (CheckBox) checkBoxGroup.getChildAt(0);
                   if(checkBox != null && conceptId == 1)
                       checkBox.setChecked(true);
                }
                break;

            case ConceptDataType.CODED:

                for(ObsEntity codedObs :obs)
                 selectedConcepts.add(codedObs.getValueCoded());

                 if(isCodedAnswersRetrieved)
                     onConceptIdAnswersRetrieved(mConceptAnswerNames);
                break;

            case ConceptDataType.DATE:

                String date = obs[firstIndex].getValueDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                ((DatePickerWidget) datePicker).setValue(date);


                break;


        }
    }
}