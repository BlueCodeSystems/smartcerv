package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;

public class FormLabelWidget extends AppCompatTextView {

    public FormLabelWidget(Context context, int weight){
        super(context);

        LinearLayoutCompat.LayoutParams layoutParams =  new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = weight;

        this.setLayoutParams(layoutParams);
    }
}
