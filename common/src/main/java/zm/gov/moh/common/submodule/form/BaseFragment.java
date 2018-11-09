package zm.gov.moh.common.submodule.form;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.Serializable;
import java.util.Map;
import zm.gov.moh.core.utils.Utils;

public class BaseFragment extends Fragment implements Serializable {

    Map<View, String[]> validations;
    protected static final String JSON_FORM_KEY = "JSON_FORM_KEY";

    public void setDatePickerView(Context context, Button view, Consumer<String> onDateChangeListener) {

        Utils.dateDialog(context, view, (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {

            // set day of month , month and year value in the edit text
            String dob = ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1 < 10) ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + year;
            onDateChangeListener.accept(dob);

            if (view.getError() != null)
                view.setError(null);
        });
    }
}
