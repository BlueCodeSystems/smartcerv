package zm.gov.moh.common.submodule.form.widget;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.text.SimpleDateFormat;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.widgetModel.AbstractWidgetModel;
import zm.gov.moh.core.utils.Utils;

public class TimePickerWidget extends EditTextWidget {
    protected AppCompatImageButton timeButton;
    protected int hourOfDay;
    protected int minutes;
    protected TimePickerDialog mTimePickerDialog;

        // super class reference
    public TimePickerWidget(Context context) {
        super(context);
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    @Override
    public  void  onCreateView(){
        super.onCreateView();
        timeButton.getContext();
        super.setGravity(Gravity.CENTER_HORIZONTAL);
        mEditText.setEnabled(false);
        mEditText.setTextColor(Color.BLACK);


        timeButton.setBackgroundResource(R.drawable.timer);
        WidgetUtils.setLayoutParams(timeButton, Utils.dpToPx(mContext,35), Utils.dpToPx(mContext,35));
        ((LinearLayoutCompat.LayoutParams)timeButton.getLayoutParams()).setMarginEnd(Utils.dpToPx(mContext,25));


        TimePickerDialog timePickerDialog = Utils.

    }


}
