package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.util.Consumer;
import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;


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
        linearLayoutCompat.setGravity(Gravity.START);

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

    public static RadioGroup createRadioButtons(Context context, HashMap<String,Integer> labelValueMap, Consumer<Integer> onSelectionChange, int orientation, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;

        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(orientation);
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setOnCheckedChangeListener((RadioGroup radioGroup1, int i)-> {
                onSelectionChange.accept(radioGroup1.getCheckedRadioButtonId());
            });

        for (Map.Entry<String,Integer> hash:labelValueMap.entrySet()) {

            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(hash.getKey());
            radioButton.setId(hash.getValue());
            radioButton.setPadding(0,0,Utils.dpToPx(context,20),0);
            radioGroup.addView(radioButton);
        }

        return radioGroup;
    }

    public static RadioGroup createCheckBoxes(Context context, HashMap<String,Integer> labelValueMap, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, int orientation, int width, int height, int weight){

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(width,height);
        layoutParams.weight = weight;

        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(orientation);
        radioGroup.setLayoutParams(layoutParams);

        for (Map.Entry<String,Integer> hash:labelValueMap.entrySet()) {

            CheckBox radioButton = new CheckBox(context);
            radioButton.setText(hash.getKey());
            radioButton.setId(hash.getValue());
            radioButton.setPadding(0,0,Utils.dpToPx(context,20),0);
            radioButton.setOnCheckedChangeListener(onCheckedChangeListener);
            radioGroup.addView(radioButton);
        }

        return radioGroup;
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

    public static void setVisibilityOnViewWithTag(View rootView,final int VISIBILITY,Object... tags){

        for(Object tag : tags)
            rootView.findViewWithTag(tag).setVisibility(VISIBILITY);
    }

    public static void setVisibilityOnViewWithTag(View rootView,final boolean ENABLE,Object... tags){

        for(Object tag : tags)
            enableView(rootView.findViewWithTag(tag),ENABLE);
    }


}
