package zm.gov.moh.core.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Locale;

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

    public static String getStringFromInputStream(InputStream stream) throws IOException {

        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

    public static int dpToPx(Context context,int dp){

       return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,context.getResources().getDisplayMetrics());
    }

    public static int ptToPx(Context context,int dp){

        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp,context.getResources().getDisplayMetrics());
    }

    public static String stringReverse(String string){

        if(string == null)
            return null;

        String reversed = "";
        for (char character : string.toCharArray())
            reversed = character + reversed;

        return reversed;
    }




}
