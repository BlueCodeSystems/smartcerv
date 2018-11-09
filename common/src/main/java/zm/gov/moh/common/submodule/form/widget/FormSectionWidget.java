package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

public class FormSectionWidget extends LinearLayoutCompat {

    private Context context;
    public FormSectionWidget(Context context){
        super(context);
        this.context = context;
        {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0,Utils.dpToPx(context,5),0,Utils.dpToPx(context,5));
            setLayoutParams(layoutParams);
            setPadding(Utils.dpToPx(context,5),Utils.dpToPx(context,5),Utils.dpToPx(context,5),Utils.dpToPx(context,5));
            setOrientation(LinearLayoutCompat.VERTICAL);
            setBackgroundResource(zm.gov.moh.core.R.drawable.round_border);
        }
    }

    public void setHeading(String heading){

        TextView editText = new TextView(context);
        editText.setText(heading);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        editText.setTextColor(context.getResources().getColor(R.color.primary));
        this.addView(editText);
    }
}
