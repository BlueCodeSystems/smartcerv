package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.model.DrugObsValue;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;

public class BasicDrugWidget extends RepositoryWidget<String> {

    protected String mUuid;
    protected AppCompatSpinner frequencySpinner;
    protected AppCompatSpinner durationSpinner;

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
    Long drugConceptId, mFrequency, mDuration;
    DrugObsValue mDrugObsValue;
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
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        return null;
    }

    public void onCreateView(){

        //WidgetUtils.setLayoutParams(this,WidgetUtils.MATCH_PARENT,WidgetUtils.WRAP_CONTENT);
        layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT);

        tableLayout = new TableLayout(mContext);
        tableLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));
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
            frequencyIdMap.put(conceptAnswerName.name, conceptAnswerName.answer_concept);

        //duration lists
        mRepository.getDatabase()
            .conceptAnswerNameDao()
            .getByConceptId(165224L)
            .observe((AppCompatActivity)mContext, this::onDurationAnswersNamesReceived);
    }

    public void onDurationAnswersNamesReceived(List<ConceptAnswerName> durationAnswerNames) {

        for(ConceptAnswerName conceptAnswerName: durationAnswerNames)
            durationIdMap.put(conceptAnswerName.name, conceptAnswerName.answer_concept);

        mRepository.getDatabase()
                .drugDao()
                .getDrugNameByUuid(mUuid)
                .observe((AppCompatActivity)mContext, this::onDrugReceived);
    }

    public void onDrugReceived(Drug drug) {

        String strength = "";
        if(drug.strength != null)
            strength = drug.strength;
        checkboxNameIdMap.put(drug.name + " " + strength, drug.concept_id);

        int orientation = (checkboxNameIdMap.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;

        RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, checkboxNameIdMap,this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,1);
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
        tableRow.addView(frequencySpinner);
        tableRow.addView(durationSpinner);

        tableLayout.addView(tableRow);
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        long id = (long)compoundButton.getId();

            answerConcept = id;

        setDrugObsValue(answerConcept, answerFrequencyConcept, answerDurationConcept);
    }

    public void setDrugObsValue(Long obsValue, Long obsFrequencyValue, Long obsDurationValue) {

        if(canSetValue.get()) {
            drugConceptId = obsValue;
            mFrequency = obsFrequencyValue;
            mDuration = obsDurationValue;
            mDrugObsValue.setValue(obsValue);
            mDrugObsValue.setFrequency(obsFrequencyValue);
            mDrugObsValue.setDuration(obsDurationValue);

            mBundle.putSerializable((String)this.getTag(),mDrugObsValue);
            /*for (Logic logic : logic) {
                if (logic.getAction().getType().equals("skipLogic"))
                    if ((drugConceptId != null) && (((Set<Long>) drugConceptId).contains(Math.round((Double) logic.getCondition().getValue())))) {
                        WidgetUtils.applyOnViewGroupChildren(form.getRootView(), v -> v.setVisibility(VISIBLE), logic.getAction().getMetadata().getTags().toArray());
                        form.getFormContext().getVisibleWidgetTags().removeAll(logic.getAction().getMetadata().getTags());
                    } else {

                        Set<String> tags = new HashSet<>();
                        WidgetUtils.extractTagsRecursive(form.getRootView(), tags, logic.getAction().getMetadata().getTags());
                        form.getFormContext().getVisibleWidgetTags().addAll(tags);
                    }
            }*/
            //render();
        }
    }

    private void onSelectedFrequencyValue(Long value) {
        answerFrequencyConcept = value;
    }

    private void onSelectedDurationValue(Long value) {
        answerDurationConcept = value;
    }

    /*public void render(){

        WidgetUtils.applyOnViewGroupChildren(form.getRootView(),
                v ->{
                    v.setVisibility(GONE);
                    if(v instanceof BasicDrugWidget)
                        ((BasicDrugWidget)v).reset();
                },
                form.getFormContext().getVisibleWidgetTags().toArray());
    }*/

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
            widget.canSetValue = new AtomicBoolean();
            widget.canSetValue.set(true);

            if(mUuid != null)
                widget.setUuid(mUuid);
            if(mRepository != null)
                widget.setRepository(mRepository);

            widget.onCreateView();

            return  widget;
        }
    }

    public void reset(){

        /*canSetValue.compareAndSet(true,false);
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

        canSetValue.compareAndSet(false,true);*/
    }
}
