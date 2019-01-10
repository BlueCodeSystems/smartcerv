package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;
import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

public class GreyoutViewGroup extends FrameLayout {

    public GreyoutViewGroup(Context context){
        super(context);
    }

    public GreyoutViewGroup(Context context, View view){
        this(context);
        FrameLayout greyOut = new FrameLayout(context);
        greyOut.setBackgroundColor(context.getResources().getColor(R.color.grey_out));
        greyOut.setClickable(true);
        greyOut.setElevation(Utils.dpToPx(context,4));

        WidgetUtils.setLayoutParams(this,WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT);
        WidgetUtils.setLayoutParams(greyOut,WidgetUtils.MATCH_PARENT, WidgetUtils.MATCH_PARENT);
        this.addView(greyOut);
        this.addView(view);
        this.setEnabled(false);
    }
}
