package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import java.util.HashMap;

import zm.gov.moh.core.utils.Utils;

public class FormDatePickerWidget extends AppCompatButton {

    private HashMap<String, Object> formData;

    public FormDatePickerWidget(Context context, HashMap<String, Object> formData){
        super(context);

        this.setAllCaps(false);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        Utils.dateDialog(context,this, (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {

            // set day of month , month and year value in the edit text
            String dob = ((dayOfMonth < 10)? "0"+dayOfMonth:dayOfMonth) + "-" + ((monthOfYear + 1 < 10)? "0"+(monthOfYear + 1 ):(monthOfYear + 1 ))  + "-" + year;
            this.setText(dob);

            formData.put((String)getTag(), dob);
        });
    }
}
