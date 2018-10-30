package zm.gov.moh.core.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import okhttp3.Credentials;

public class Utils {


    public static String credentialsToBase64(CharSequence username, CharSequence password) {

       return Credentials.basic(username.toString(), password.toString());
    }

    public static boolean checkInternetConnectivity(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public static void showSnackBar(Context context, String message,int color, int duration){

        Activity activity = (Activity) context;
        Snackbar.make(activity.findViewById(android.R.id.content) , message, duration).setActionTextColor(color).show();
    }

    public static ProgressDialog showProgressDialog(Context context, String message){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message );
        progressDialog.setCancelable(false);

        return progressDialog;
    }

    public static AlertDialog.Builder showModelDialog(Context context, String title , String message){

       return new AlertDialog.Builder(context)
               .setTitle(title)
                .setMessage(message)
               .setPositiveButton("OK",null);
    }

    public static void dateDialog(Context context,View view, DatePickerDialog.OnDateSetListener onDateSetListener){

        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR); // current year
        int month = c.get(Calendar.MONTH); // current month
        int day = c.get(Calendar.DAY_OF_MONTH); // current day
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener , year, month, day);

        // perform click event on edit text
        view.setOnClickListener(view1 -> datePickerDialog.show());
    }
}
