package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

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
