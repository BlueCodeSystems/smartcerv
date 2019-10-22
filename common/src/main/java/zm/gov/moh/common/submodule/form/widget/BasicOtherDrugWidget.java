package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.util.Consumer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;

public class BasicOtherDrugWidget extends BasicDrugWidget {

    protected AppCompatEditText otherText;
    ObsValue<String> mObsValue;
    String frequencyName;
    String durationName;
    String otherDrug;

    public BasicOtherDrugWidget(Context context) {
        super(context);
    }

    public boolean isValid() {
        /*if (otherDrug == null || otherDrug.equals("")) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_other_prescription), Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
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
        otherDrug = charSequence.toString();
        obsNotify();
        setObsValue();
    }

    private void obsNotify() {
        mObsValue.setValue(otherDrug + "-" + frequencyName + "-" + durationName);
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        long id = (long)compoundButton.getId();

        answerConcept = id;

        if( (frequencySpinner.getParent() != null) && (frequencySpinner.getParent() != null) ) {
            if (answerConcept == 165197L) {     // other selection
                tableRow.removeView(otherText);
                otherText.setVisibility(GONE);
            }

            tableRow.removeView(frequencySpinner);
            frequencySpinner.setVisibility(GONE);
            tableRow.removeView(durationSpinner);
            durationSpinner.setVisibility(GONE);
        } else {
            if (answerConcept == 165197L) {     // other selection
                tableRow.addView(otherText);
                otherText.setVisibility(VISIBLE);
                otherText.setLayoutParams(rowLayoutParams);
            }

            tableRow.addView(frequencySpinner);
            frequencySpinner.setVisibility(VISIBLE);
            tableRow.addView(durationSpinner);
            durationSpinner.setVisibility(VISIBLE);
        }

        setObsValue();
    }

    // set the rest of the ObsValue instance
    public void setObsValue() {

        mObsValue.setConceptId(answerConcept);
        mObsValue.setUuid(mUuid);

        mBundle.putSerializable(mTag, mObsValue);
    }

    void onSelectedFrequencyValue(Long value) {
        answerFrequencyConcept = value;

        for (Map.Entry<String,Long> entry :frequencyIdMap.entrySet()){

            if(entry.getValue() == value)
                frequencyName = entry.getKey();

        }
        obsNotify();
        setObsValue();
    }

    void onSelectedDurationValue(Long value) {
        answerDurationConcept = value;

        for (Map.Entry<String,Long> entry :durationIdMap.entrySet()){

            if(entry.getValue() == value)
                durationName = entry.getKey();

        }
        obsNotify();
        setObsValue();
    }

    public static class Builder extends BasicDrugWidget.Builder {

        public Builder(Context context) {
            super(context);
        }

        public BasicOtherDrugWidget build() {

            BasicOtherDrugWidget widget = new BasicOtherDrugWidget(mContext);
            widget.mObsValue = new ObsValue<>();
            widget.mObsValue.setConceptDataType(ConceptDataType.TEXT);
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
