package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zm.gov.moh.core.repository.database.dao.derived.ConceptAnswerNameDao;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;

public class BasicDrugWidget extends RepositoryWidget<String> {

    protected String mValue;
    protected String mUuid;
    protected Drug drugObject;
    protected CheckBox mCheckbox;
    protected AppCompatTextView mTextView;
    protected AppCompatSpinner frequencySpinner;
    protected AppCompatSpinner durationSpinner;
    Set<Long> answerConcepts;
    protected Map<String, Long> frequencyIdMap = new HashMap<>();;
    protected Map<String, Long> durationIdMap = new HashMap<>();;
    protected Map<String, Long> conceptNameIdMap = new HashMap<>();
    protected Set<Long> answerConceptIds;


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

        //frequency list
        mRepository.getDatabase()
            .conceptAnswerNameDao()
            .getByConceptId(165225L)
            .observe((AppCompatActivity)mContext, this::onFrequencyAnswersNamesReceived);




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

        mRepository.getDatabase().drugDao().getDrugNameByUuid(mUuid)
                .observe((AppCompatActivity)mContext, this::onDrugReceived);
    }

    public void onDrugReceived(Drug drug) {

        int orientation = (conceptNameIdMap.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;
        mCheckbox = new CheckBox(mContext);
        RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext, conceptNameIdMap,this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,1);

        frequencySpinner = WidgetUtils.createSpinner(mContext, frequencyIdMap, this::onSelectedValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        durationSpinner = WidgetUtils.createSpinner(mContext, durationIdMap, this::onSelectedValue,
            WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, 1);

        LinearLayoutCompat.LayoutParams layoutParams =  new LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

        //mTextView.setLayoutParams(layoutParams);
        frequencySpinner.setLayoutParams(layoutParams);
        durationSpinner.setLayoutParams(layoutParams);

        mTextView = new AppCompatTextView(mContext);
        mTextView.setText(drug.name);// retrieve name using uuid here
        mTextView.setTextSize(18);

        addView(mCheckbox);
        addView(mTextView);
        addView(frequencySpinner);
        addView(durationSpinner);

    }

    private void onSelectedValue(Long value) {
        /*if(!answerConcepts.isEmpty())
            answerConcepts.clear();

        answerConcepts.add(value);*/
        //setObsValue(answerConcepts);
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

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

            if(mUuid != null)
                widget.setUuid(mUuid);
            if(mRepository != null)
                widget.setRepository(mRepository);

            widget.onCreateView();

            return  widget;
        }
    }
}
