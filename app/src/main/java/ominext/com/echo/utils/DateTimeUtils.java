package ominext.com.echo.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Vinh on 11/10/2016.
 */

public class DateTimeUtils {

    public static final String SERVER_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

    public static String formatDateTimeFriendList(Context context, String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(SERVER_DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));

        try {
            return formatDateTimeFriendList(context, dateFormat.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateTimeFriendList(Context context, Date date) {
        Locale current = context.getResources().getConfiguration().locale;
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            if (now.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                return formatTime(date, current);
            }
            return formatShortDateTime(date, current);
        }
        return formatLongDateTime(date, current);
    }

    public static String formatTime(Date date, Locale current) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", current);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String formatLongDateTime(Date date, Locale current) {
        DateFormat dateFormat = new SimpleDateFormat(SERVER_DATE_TIME_FORMAT, current);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String formatShortDateTime(Date date, Locale current) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm", current);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String formateDateFromstring(Context context, String inputFormat, String outputFormat, String inputDate) {

        if (inputDate == null) {
            return "";
        }
        Date parsed;
        String outputDate = "";
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        // df_input.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        df_input.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        Locale current = context.getResources().getConfiguration().locale;
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, current);
        df_output.setTimeZone(tz);
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;

    }

    public static String getTimeDateCurrent(Context context) {
        Calendar c = Calendar.getInstance();
        Locale current = context.getResources().getConfiguration().locale;
        SimpleDateFormat df_output = new SimpleDateFormat("dd-MM-yyyy HH:mm", current);
        df_output.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        String formattedDate = df_output.format(c.getTime());
        return formattedDate;
    }

    public static long timestamp(String time) {
        if (TextUtils.isEmpty(time)) return 0;
        try {
            String tim = time.replace("T", " ");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            TimeZone tz = TimeZone.getDefault();
            sdf.setTimeZone(tz);
            Date date = sdf.parse(tim);
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertToStandardFormat(String strDate, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(strDate);
            dateFormat = new SimpleDateFormat(SERVER_DATE_TIME_FORMAT);
            return  dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String convertToStandardFormat(String strDate) {
        return convertToStandardFormat(strDate, "dd-MM-yyyy HH:mm");
    }
}
