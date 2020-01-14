package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;

public class BasicDrugWidget extends RepositoryWidget<String> implements Retainable {

    protected String mDrugUuid;
    protected String mUuid;
    protected AppCompatSpinner frequencySpinner;
    protected AppCompatSpinner durationSpinner;
    protected  String mTag;

    protected TableLayout tableLayout;
    protected TableRow tableRow;
    protected TableRow.LayoutParams rowLayoutParams;
    protected TableLayout.LayoutParams layoutParams;

    //protected Map<String, Long> frequencyIdMap = new HashMap<>();
    protected Map<String, Long> frequencyIdMap = new LinkedHashMap<>();
    //protected Map<String, Long> durationIdMap = new HashMap<>();
    Map<String, Long> durationIdMap = new LinkedHashMap<>();
    protected Map<String, Long> checkboxNameIdMap = new HashMap<>();

    ArrayList<ObsEntity> selectedObservations = new ArrayList<>();
    RadioGroup checkBoxGroup;

    Long answerConcept;
    Long answerFrequencyConcept;
    Long answerDurationConcept;

    @Override
    public boolean isValid() {
        return true;
    }

    AtomicBoolean canSetValue;
    ObsValue<Set<Long>> mObsValue; // temporarily use to Array contain value of frequency and Duration
    List<Logic> logic;
    Form form;

    public BasicDrugWidget(Context context) {
        super(context);
        mContext = context;
    }

    public String getUuid()
    {
        return mUuid;
    }

    public BasicDrugWidget setUuid(String uuid) {
        this.mUuid = uuid;
        return this;
    }

    public String getDrugUuid() {
        return mDrugUuid;
    }

    public BasicDrugWidget setDrugUuid(String drugUuid) {
        this.mDrugUuid = drugUuid;
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
                .getDrugNameByUuid(mDrugUuid)
                .observe((AppCompatActivity)mContext, this::onDrugReceived);
    }

    public void onDrugReceived(Drug drug) {

        tableLayout.removeAllViews();
        String strength = "";
        if(drug.strength != null)
            strength = drug.strength;
        checkboxNameIdMap.put(drug.name + " " + strength, drug.concept_id);

        int orientation = (checkboxNameIdMap.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;

        checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, checkboxNameIdMap,
            this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,1);

        tableRow = new TableRow(mContext);
        tableRow.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));

        frequencySpinner = WidgetUtils.createSpinner(mContext, frequencyIdMap, this::onSelectedFrequencyValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        durationSpinner = WidgetUtils.createSpinner(mContext, durationIdMap, this::onSelectedDurationValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        checkBoxGroup.setLayoutParams(rowLayoutParams);
        checkBoxGroup.setTag(drug.name);
        frequencySpinner.setLayoutParams(rowLayoutParams);
        durationSpinner.setLayoutParams(rowLayoutParams);

        tableRow.addView(checkBoxGroup);

        tableLayout.addView(tableRow);
        accessRetrievedObservations(selectedObservations, drug.concept_id);
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

    public void setObsValue() {


            mObsValue.setConceptId(answerConcept);
            mObsValue.setUuid(mDrugUuid);
            mObsValue.getValue().clear();
            mObsValue.getValue().add(answerDurationConcept);
            mObsValue.getValue().add(answerFrequencyConcept);

            mBundle.putSerializable(mTag, mObsValue);

    }

    void onSelectedFrequencyValue(Long value) {
        answerFrequencyConcept = value;
        setObsValue();
    }

    void onSelectedDurationValue(Long value) {
        answerDurationConcept = value;
        setObsValue();
    }

    @Override
    public void onLastObsRetrieved(ObsEntity... obs) {

        if (obs.length > 0) {
            if (mObsValue.getConceptDataType().equals(ConceptDataType.CODED)) {
                for (ObsEntity codedDrugObs : obs)
                    selectedObservations.add(codedDrugObs);
            }
        }
    }

    /**
     * Uses returned observations objects to return previously checked and spinner values
     * @param observations List of obs Entities
     * @param drugConceptId Drug concept in current observable iteration
     */
    public void accessRetrievedObservations(List<ObsEntity> observations, Long drugConceptId) {

        int position = 0;
        CheckBox checkBox = (CheckBox)checkBoxGroup.getChildAt(0); // Retrieve checkbox
        List<Long> observationConcepts = new ArrayList<>();

        Map<Integer, Long> frequencyPosition = new LinkedHashMap<>();
        Map<Integer, Long> durationPosition = new LinkedHashMap<>();

        Set<Map.Entry<String, Long>> collectionView = frequencyIdMap.entrySet();
        for (Map.Entry<String, Long> group : collectionView) {
            frequencyPosition.put(position, group.getValue());  // Map integers to conceptId's
            position++;
        }

        position = 0;   // Reset position for duration map
        Set<Map.Entry<String, Long>> collectionView2 = durationIdMap.entrySet();
        for (Map.Entry<String, Long> group : collectionView2) {
            durationPosition.put(position, group.getValue());   // Map integers to conceptId's
            position++;
        }

        // Iterate key value pairs using entrySet()
        Set<Map.Entry<Integer, Long>> freqMapView = frequencyPosition.entrySet();
        Set<Map.Entry<Integer, Long>> duraMapView = durationPosition.entrySet();


        for (ObsEntity obs: observations) {
            observationConcepts.add(obs.getConceptId());    // Add's drug concept

            for (Map.Entry<Integer, Long> group : freqMapView) {
                if (frequencyIdMap.containsValue(obs.getValueCoded())) {
                    answerFrequencyConcept = obs.getValueCoded();   // Set answer frequency if in Frequency Map
                    if (answerFrequencyConcept.equals(group.getValue()))
                        frequencySpinner.setSelection(group.getKey());  // Get integer if concepts are equal
                }

            }

            for (Map.Entry<Integer, Long> group : duraMapView) {
                if (durationIdMap.containsValue(obs.getValueCoded())) {
                    answerDurationConcept = obs.getValueCoded(); // Set answer frequency if in Frequency Map
                    if (answerDurationConcept.equals(group.getValue()))
                        durationSpinner.setSelection(group.getKey());   // Get integer if concepts are equal
                }
            }

        }

        if(observationConcepts.contains(drugConceptId))
            checkBox.setChecked(true);  // Check the CheckBox

    }

    public static class Builder extends RepositoryWidget.Builder {


        protected String mDrugUuid;
        protected String mUuid;

        public Builder(Context context) {
            super(context);
        }


        public String getDrugUuid() {
            return mDrugUuid;
        }

        public Builder setDrugUuid(String uuid) {
            this.mDrugUuid = uuid;
            return this;
        }

        public String getUuid() {
            return mUuid;
        }

        public Builder setUuid(String mUuid) {
            this.mUuid = mUuid;
            return this;
        }

        public BasicDrugWidget build() {
            BasicDrugWidget widget = new BasicDrugWidget(mContext);
            //widget.mDrugObsValue = new DrugObsValue();
            widget.mObsValue = new ObsValue<>();
            widget.mObsValue.setValue(new HashSet<>());
            widget.mObsValue.setConceptDataType(ConceptDataType.CODED);
            widget.canSetValue = new AtomicBoolean();
            widget.canSetValue.set(true);

            if(mDrugUuid != null)
                widget.setDrugUuid(mDrugUuid);
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
