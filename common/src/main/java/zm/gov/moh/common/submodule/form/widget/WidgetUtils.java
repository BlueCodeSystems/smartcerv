package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

public class WidgetUtils {

    public static View createLinearLayout(Context context, int orientation,View... views){

        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(context);
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        linearLayoutCompat.setLayoutParams(layoutParams);
        linearLayoutCompat.setOrientation(orientation);

        for(View view: views)
            linearLayoutCompat.addView(view);

        return linearLayoutCompat;
    }
}
