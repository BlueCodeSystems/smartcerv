package zm.gov.moh.common.submodule.form.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

public class DatePickerWidget extends EditTextWidget {

    AppCompatImageButton button;
    protected String mFutureDate;

    public DatePickerWidget(Context context){
        super(context);

    }


    public String getFutureDate() {
        return mFutureDate;
    }

    public void setFutureDate(String futureDate) {
        mFutureDate = futureDate;
    }


    @Override
    public void onCreateView() {
        super.onCreateView();
        button = new AppCompatImageButton(mContext);
        super.setGravity(Gravity.CENTER_HORIZONTAL);
        mEditText.setEnabled(false);
        mEditText.setTextColor(Color.BLACK);




        button.setBackgroundResource(R.drawable.calendar);

        WidgetUtils.setLayoutParams(button,Utils.dpToPx(mContext,35), Utils.dpToPx(mContext,35));
        ((LinearLayoutCompat.LayoutParams)button.getLayoutParams()).setMarginEnd(Utils.dpToPx(mContext,25));


             DatePickerDialog datePickerDialog = Utils.dateDialog(mContext,button,(DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {


            // set day of month , month and year value in the edit text

            String dob = (year+"-" + ((monthOfYear + 1 < 10)? "0"+(monthOfYear + 1 ):(monthOfYear + 1 ))+"-"+((dayOfMonth < 10)? "0"+dayOfMonth:dayOfMonth));

            this.setValue(dob);

            return ;
             }

        );

       if (mFutureDate != null && mFutureDate.matches("False")) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        else if (mFutureDate != null && mFutureDate.matches("True")){
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }

        this.setGravity(Gravity.CENTER_VERTICAL);
        addView(button);
    }

    public void clear(){
       // mEditText.setText("");
        getEditTextView().setText("");
        mValueChangeListener.accept("");
    }

   public void setLongClick(OnLongClickListener clickListener){
        button .setOnLongClickListener(clickListener);
    }


    @Override
    public boolean isValid() {

        if(mRequired != null && mRequired)
            if(mValue == null){
                mEditText.setError(mErrorMessage);

                return false;
            }


        return true;
    }

    @Override
    public void setValue(CharSequence value) {

        String date = value.toString();

        try {

            LocalDate localDate = LocalDate.parse(date);
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern(mHint));
            if(mBundle != null) {
                mBundle.putString((String) getTag(), date);
                mValue = date;
                mEditText.setError(null);
            }
            getEditTextView().setText(formattedDate);
            mValueChangeListener.accept(date);
        }catch (Exception e){

        }
    }

    @Override
    public String getValue() {
        return null;
    }

    public static class Builder extends EditTextWidget.Builder{

        public Builder(Context context){
            super(context);
        }

        @Override
        public BaseWidget build() {

            DatePickerWidget widget = new DatePickerWidget(mContext);

            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mTag != null)
                widget.setTag(mTag);
            if(mHint != null)
                widget.setHint(mHint);
            if(mLabel != null)
                widget.setLabel(mLabel);
            if(mFutureDate !=null)
                widget.setFutureDate(mFutureDate);
            if(mValueChangeListener != null)
                widget.setOnValueChangeListener(mValueChangeListener);
            if(mErrorMessage != null)
                widget.setErrorMessage(mErrorMessage);
            if(mRequired != null)
                widget.setRequired(mRequired);

            widget.onCreateView();

            return  widget;
        }
    }
}
