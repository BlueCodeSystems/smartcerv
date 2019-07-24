package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.DrugObsValue;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;
import zm.gov.moh.core.utils.Utils;

public class BasicDrugWidget extends RepositoryWidget<String> {

    protected String mUuid;
    protected AppCompatSpinner frequencySpinner;
    protected AppCompatSpinner durationSpinner;
    protected  String mTag;

    protected TableLayout tableLayout;
    protected TableRow tableRow;
    protected TableRow.LayoutParams rowLayoutParams;
    protected TableLayout.LayoutParams layoutParams;

    protected Map<String, Long> frequencyIdMap = new HashMap<>();
    protected Map<String, Long> durationIdMap = new HashMap<>();
    protected Map<String, Long> checkboxNameIdMap = new HashMap<>();
    Long answerConcept;
    Long answerFrequencyConcept;
    Long answerDurationConcept;

    AtomicBoolean canSetValue;
    //Long drugConceptId, mFrequency, mDuration;
    //DrugObsValue mDrugObsValue;
    ObsValue<ArrayList<Long>> mObsValue; // temporarily use to Array contain value of frequency and Duration
    List<Logic> logic;
    Form form;

    private BasicDrugWidget(Context context) {
        super(context);
        mContext = context;
    }

    public String getUuid() {
        return mUuid;
    }

    public BasicDrugWidget setUuid(String uuid) {
        this.mUuid = uuid;
        return this;
    }

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        mTag = (String)tag;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        return null;
    }

    public void onCreateView(){

        layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        tableLayout = new TableLayout(mContext);
        tableLayout.setLayoutParams(layoutParams);

        rowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);

        //frequency list
        mRepository.getDatabase()
                .conceptAnswerNameDao()
                .getByConceptId(165225L)
                .observe((AppCompatActivity)mContext, this::onFrequencyAnswersNamesReceived);

        addView(tableLayout);
    }

    public void onFrequencyAnswersNamesReceived(List<ConceptAnswerName> frequencyAnswerNames) {

        for(ConceptAnswerName conceptAnswerName: frequencyAnswerNames)
            frequencyIdMap.put(conceptAnswerName.getName(), conceptAnswerName.getAnswerConcept());

        //duration lists
        mRepository.getDatabase()
                .conceptAnswerNameDao()
                .getByConceptId(165224L)
                .observe((AppCompatActivity)mContext, this::onDurationAnswersNamesReceived);
    }

    public void onDurationAnswersNamesReceived(List<ConceptAnswerName> durationAnswerNames) {

        for(ConceptAnswerName conceptAnswerName: durationAnswerNames)
            durationIdMap.put(conceptAnswerName.getName(), conceptAnswerName.getAnswerConcept());

        mRepository.getDatabase()
                .drugDao()
                .getDrugNameByUuid(mUuid)
                .observe((AppCompatActivity)mContext, this::onDrugReceived);
    }

    public void onDrugReceived(Drug drug) {

        tableLayout.removeAllViews();
        String strength = "";
        if(drug.strength != null)
            strength = drug.strength;
        checkboxNameIdMap.put(drug.name + " " + strength, drug.concept_id);

        int orientation = (checkboxNameIdMap.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;

        RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, checkboxNameIdMap,
            this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,1);

        tableRow = new TableRow(mContext);
        tableRow.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));

        frequencySpinner = WidgetUtils.createSpinner(mContext, frequencyIdMap, this::onSelectedFrequencyValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        durationSpinner = WidgetUtils.createSpinner(mContext, durationIdMap, this::onSelectedDurationValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        checkBoxGroup.setLayoutParams(rowLayoutParams);
        frequencySpinner.setLayoutParams(rowLayoutParams);
        durationSpinner.setLayoutParams(rowLayoutParams);

        tableRow.addView(checkBoxGroup);

        tableLayout.addView(tableRow);
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        long id = (long)compoundButton.getId();

        answerConcept = id;

        if( (frequencySpinner.getParent() != null) && (frequencySpinner.getParent() != null) ) {
            tableRow.removeView(frequencySpinner);
            frequencySpinner.setVisibility(GONE);
            tableRow.removeView(durationSpinner);
            durationSpinner.setVisibility(GONE);
        } else {
            tableRow.addView(frequencySpinner);
            frequencySpinner.setVisibility(VISIBLE);
            tableRow.addView(durationSpinner);
            durationSpinner.setVisibility(VISIBLE);
        }

        //setDrugObsValue(answerConcept, answerFrequencyConcept, answerDurationConcept);
        setObsValue();
    }

    /*public void setDrugObsValue(Long obsValue, Long obsFrequencyValue, Long obsDurationValue) {

        if( (frequencySpinner.getParent() != null) && (frequencySpinner.getParent() != null) ) {
            tableRow.removeView(frequencySpinner);
            frequencySpinner.setVisibility(GONE);
            tableRow.removeView(durationSpinner);
            durationSpinner.setVisibility(GONE);
        } else {
            tableRow.addView(frequencySpinner);
            frequencySpinner.setVisibility(VISIBLE);
            tableRow.addView(durationSpinner);
            durationSpinner.setVisibility(VISIBLE);
        }

        if(canSetValue.get()) {
            drugConceptId = obsValue;
            mFrequency = obsFrequencyValue;
            mDuration = obsDurationValue;

            mDrugObsValue.setValue(obsValue);
            mDrugObsValue.setFrequency(obsFrequencyValue);
            mDrugObsValue.setDuration(obsDurationValue);

            mBundle.putSerializable((String)this.getTag(), mDrugObsValue);
        }
    }*/

    public void setObsValue() {


            mObsValue.setConceptId(answerConcept);
            mObsValue.getValue().clear();
            mObsValue.getValue().add(answerDurationConcept);
            mObsValue.getValue().add(answerFrequencyConcept);

            mBundle.putSerializable(mTag, mObsValue);

    }

    private void onSelectedFrequencyValue(Long value) {
        answerFrequencyConcept = value;
        setObsValue();
    }

    private void onSelectedDurationValue(Long value) {
        answerDurationConcept = value;
        setObsValue();
    }

    public static class Builder extends RepositoryWidget.Builder {


        protected String mUuid;

        public Builder(Context context) {
            super(context);
        }


        public String getUuid() {
            return mUuid;
        }

        public Builder setUuid(String uuid) {
            this.mUuid = uuid;
            return this;
        }

        public BasicDrugWidget build() {
            BasicDrugWidget widget = new BasicDrugWidget(mContext);
            //widget.mDrugObsValue = new DrugObsValue();
            widget.mObsValue = new ObsValue<>();
            widget.mObsValue.setValue(new ArrayList<>());
            widget.mObsValue.setConceptDataType(ConceptDataType.CODED);
            widget.canSetValue = new AtomicBoolean();
            widget.canSetValue.set(true);

            if(mUuid != null)
                widget.setUuid(mUuid);
            if(mRepository != null)
                widget.setRepository(mRepository);
            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mTag != null)
                widget.setTag(mTag);

            widget.onCreateView();

            return widget;
        }
    }

}
