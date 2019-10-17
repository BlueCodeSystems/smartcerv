package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;

public class BasicOtherDrugWidget extends BasicDrugWidget {

    protected AppCompatEditText otherText;

    public BasicOtherDrugWidget(Context context) {
        super(context);
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
        otherText = WidgetUtils.setLayoutParams(new AppCompatEditText(mContext), WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        otherText.setHint("Prescription Drug");
        otherText.addTextChangedListener(WidgetUtils.createTextWatcher(this::setOtherTextValue));

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

    private void setOtherTextValue(CharSequence charSequence) {
        ObsValue<String> textObs = new ObsValue<>();
        textObs.setValue(charSequence.toString());
        textObs.setConceptId(165197L);
        textObs.setUuid("48cbf1c5-bbdb-44f4-96b7-61812c67bebe");
        textObs.setConceptDataType(ConceptDataType.TEXT);

        mBundle.putSerializable("prescription_text", textObs);
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        long id = (long)compoundButton.getId();

        answerConcept = id;

        if( (frequencySpinner.getParent() != null) && (frequencySpinner.getParent() != null) ) {
            if (answerConcept == 165407L) {     // other selection
                tableRow.removeView(otherText);
                otherText.setVisibility(GONE);
            }

            tableRow.removeView(frequencySpinner);
            frequencySpinner.setVisibility(GONE);
            tableRow.removeView(durationSpinner);
            durationSpinner.setVisibility(GONE);
        } else {
            if (answerConcept == 165407L) {     // other selection
                tableRow.addView(otherText);
                otherText.setVisibility(VISIBLE);
                otherText.setLayoutParams(rowLayoutParams);
            }

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
        mObsValue.getValue().clear();
        mObsValue.getValue().add(answerDurationConcept);
        mObsValue.getValue().add(answerFrequencyConcept);

        mBundle.putSerializable(mTag, mObsValue);

    }

    public static class Builder extends BasicDrugWidget.Builder {

        public Builder(Context context) {
            super(context);
        }

        public BasicOtherDrugWidget build() {

            BasicOtherDrugWidget widget = new BasicOtherDrugWidget(mContext);
            widget.mObsValue = new ObsValue<>();
            widget.mObsValue.setValue(new HashSet<>());
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
