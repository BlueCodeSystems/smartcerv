package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;

public class CodedConceptWidget extends BasicConceptWidget {

    String mStyle;
    List<Long> answerConcepts;
    HashMap<String,Integer> radioLabelValues = new LinkedHashMap<>();


    public CodedConceptWidget(Context context){
        super(context);

    }

    public CodedConceptWidget build(){
        super.build();

        repository.getDatabase().conceptAnswerNameDao().getByConceptId(mConceptId).observe((AppCompatActivity)mContext,this::onConceptIdAnswersRetrieved);
        return this;
    }

    public CodedConceptWidget setStyle(String style) {

        mStyle = style;
        return this;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        long id = (long)compoundButton.getId();

        if(!(checked) && answerConcepts.contains(id))
            answerConcepts.remove(id);
        else
            answerConcepts.add(id);

    }

    public void onConceptIdAnswersRetrieved(List<ConceptAnswerName> conceptAnswerNames){

        for(ConceptAnswerName conceptAnswerName: conceptAnswerNames)
            radioLabelValues.put(conceptAnswerName.name,(int)conceptAnswerName.answer_concept);

        int orientation = (radioLabelValues.size() > 2)? WidgetUtils.VERTICAL: WidgetUtils.HORIZONTAL;

        switch (mStyle){

            case "check":
                RadioGroup checkBoxGroup = WidgetUtils.createCheckBoxes(mContext,radioLabelValues,this::onCheckedChanged, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,0);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL,mTextView , checkBoxGroup));
                answerConcepts = new ArrayList<>();
                mObsValue.setValue(answerConcepts);
                break;

            case "radio":
                RadioGroup radioGroup = WidgetUtils.createRadioButtons(mContext,radioLabelValues,this::onSelectedValue, orientation,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,0);
                this.addView(WidgetUtils.createLinearLayout(mContext, WidgetUtils.VERTICAL,mTextView, radioGroup));
                break;
        }

        render();
    }

    public void render() {

        if(logic != null)
            for(Logic logic: logic)
                if(logic.getAction().getType().equals("skipLogic") && mStyle.equals("radio"))
                    if((mValue != null) && ((int)Math.round((Double)logic.getCondition().getValue()) == (int)mValue ))
                        WidgetUtils.setVisibilityOnViewWithTag(rootView,true,logic.getAction().getMetadata().getTags().toArray());
                    else
                        WidgetUtils.setVisibilityOnViewWithTag(rootView,false,logic.getAction().getMetadata().getTags().toArray());
    }

    @Override
    public void setObsValue(Object obsValue) {
        super.setObsValue(obsValue);
        render();
    }
}
