package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.util.Consumer;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.core.utils.Utils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WidgetUtils {

    public static final int  MATCH_PARENT = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
    public static final int  WRAP_CONTENT = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
    public static final int  VERTICAL = LinearLayoutCompat.VERTICAL;
    public static final int  HORIZONTAL = LinearLayoutCompat.HORIZONTAL;

    public static View createLinearLayout(Context context, int orientation,View... views){

        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(context);
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);



        linearLayoutCompat.setLayoutParams(layoutParams);
        linearLayoutCompat.setOrientation(orientation);
        linearLayoutCompat.setGravity(Gravity.CENTER_VERTICAL);
        linearLayoutCompat.setPadding(0,0,Utils.dpToPx(context,20),0);

        for(View view: views)
            linearLayoutCompat.addView(view);

        return linearLayoutCompat;
    }

    public static AppCompatTextView createTextView(Context context, String text, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;
        AppCompatTextView tv = new AppCompatTextView(context);
        tv.setText(text);
        tv.setLayoutParams(layoutParams);

        return tv;
    }

    public static RadioGroup createRadioButtons(Context context, Map<String,Long> labelValueMap, Consumer<Integer> onSelectionChange, int orientation, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;

        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(orientation);
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setOnCheckedChangeListener((RadioGroup radioGroup1, int i)-> {
                onSelectionChange.accept(radioGroup1.getCheckedRadioButtonId());
            });

        for (Map.Entry<String,Long> hash:labelValueMap.entrySet()) {

            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(hash.getKey());
            radioButton.setId(hash.getValue().intValue());
            radioButton.setPadding(0,0,Utils.dpToPx(context,20),0);
            radioGroup.addView(radioButton);
        }

        return radioGroup;
    }

    public static RadioGroup createCheckBoxes(Context context, Map<String,Long> labelValueMap, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, int orientation, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.setMargins(0,0,0,0);
        layoutParams.weight = weight;

        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(orientation);
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setGravity(Gravity.CENTER_VERTICAL);

        for (Map.Entry<String,Long> hash:labelValueMap.entrySet()) {

            CheckBox radioButton = new CheckBox(context);
            radioButton.setText(hash.getKey());
            radioButton.setId(hash.getValue().intValue());
            radioButton.setPadding(0,0,Utils.dpToPx(context,20),0);
            radioButton.setGravity(Gravity.CENTER_VERTICAL);
            radioButton.setOnCheckedChangeListener(onCheckedChangeListener);
            radioGroup.addView(radioButton);
        }

        return radioGroup;
    }

    public static AppCompatSpinner createSpinner(Context context, Map<String,Long> labelValueMap, Consumer<Long> onItemSelected, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;

        List<String> options = new LinkedList<>(labelValueMap.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,options);

        AppCompatSpinner spinner = new AppCompatSpinner(context);
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(layoutParams);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelected.accept(labelValueMap.get(options.get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return spinner;
    }

    public static AppCompatEditText createEditText(Context context, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;
        AppCompatEditText editText = new AppCompatEditText(context);
        editText.setLayoutParams(layoutParams);

        return editText;
    }

    public static <T extends View> T setLayoutParams(T view, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);

        return view;
    }


    public static <T extends View> T setLayoutParams(T view, int width, int height){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        view.setLayoutParams(layoutParams);

        return view;
    }

    public static TextWatcher createTextWatcher(Consumer<CharSequence> callback){

       return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               callback.accept(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public static void enableView(View view, boolean enable){

        if((view instanceof LinearLayoutCompat) || (view instanceof  RadioGroup) || (view instanceof ViewGroup)){
            ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableView(child, enable);
            }
        }else
            view.setEnabled(enable);
    }

    public static void applyOnViewGroupChildren(ViewGroup rootView, Consumer<View> callback, Object... tags){

        for(Object tag : tags)
            if(rootView.findViewWithTag(tag) != null)
                callback.accept(rootView.findViewWithTag(tag));
    }

    public static void applyOnViewGroupChildren(View view, Consumer<View> callback){

            if((view instanceof LinearLayoutCompat) || (view instanceof  RadioGroup) || (view instanceof ViewGroup)){

                ViewGroup viewGroup = (ViewGroup) view;
                callback.accept(view);

                for(int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    applyOnViewGroupChildren(child, callback);
                }
            }
            else{
                callback.accept(view);
            }
    }



    public static void extractTagsRecursive(ViewGroup rootView,Set<String> result, Set<String> search) {

        for(Object tag : search)
            if(rootView.findViewWithTag(tag) != null && (rootView.findViewWithTag(tag) instanceof BasicConceptWidget)) {

                BasicConceptWidget widget = rootView.findViewWithTag(tag);

                if(widget.logic != null)
                    for (Logic logic : ((BasicConceptWidget) rootView.findViewWithTag(tag)).logic) {

                        if(logic.getAction().getType().equals("skipLogic")){

                            result.addAll(search);
                            extractTagsRecursive(rootView,result,logic.getAction().getMetadata().getTags());
                        }
                    }
                else {
                        result.add((String) widget.getTag());
                        result.addAll(search);
                }
            }
    }
}
