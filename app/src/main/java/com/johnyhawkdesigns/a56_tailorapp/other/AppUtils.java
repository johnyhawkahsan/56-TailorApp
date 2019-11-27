package com.johnyhawkdesigns.a56_tailorapp.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {

    // Method to get current time from Calender
    public static Date getCurrentDateTime(){
        Date currentDate =  Calendar.getInstance().getTime();
        return currentDate;
    }


    // Method to convert Date to Text
    public static String getFormattedDateString(Date date) {
        try {
            SimpleDateFormat spf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            String dateString = spf.format(date);

            Date newDate = spf.parse(dateString);
            spf= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            return spf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get Date with time set to 00:00:00
     * https://stackoverflow.com/questions/5050170/how-do-i-get-a-date-without-time-in-java
     * @param dateWithTime date in Date format along with time
     * @return Date formatted date without time
     */
    //
    public static Date getDateWithoutTime(Date dateWithTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(dateWithTime));
        return dateWithoutTime;
    }


    /**
     * Toast message outline
     * @param context the context of the Activity
     * @param message the message that you need to display
     */
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    // Hide soft keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Input Bitmap along with quality and output database compatible BitmapByteArray
     * @param bitmap Bitmap image that need to converted to bitmapByteArray
     * @param quality the amount of quality to compress JPEG file i.e; 100
     * @return byte[] return a compressed byte array after converting Bitmap image
     */
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    /**
     * Input database readable bitmapByteArray and output a Bitmap image
     * @param bitmapByteArray bitmap byte array
     * @return bitmap return bitmap by converting byte array
     */
    public static Bitmap getBitmapFromByteArray(byte[] bitmapByteArray){
        //Bitmap bitmap = BitmapFactory.decodeFile(uri); // if we want to decode uri into Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.length); // if we want to decode byteArray into Bitmap

        return bitmap;
    }


}
