package ominext.com.echo.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * @author quanpv
 * Utils
 */
public class Utils {
    /* private static final int SECOND_MILLIS = 1000;
     private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
     private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
     private static final int DAY_MILLIS = 24 * HOUR_MILLIS;*/
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEk_MILLIS = 7 * DAY_MILLIS;
    private static final int MONTH_MILLIS = 30 * DAY_MILLIS;
    private static final int YEAR_MILLIS = 365 * DAY_MILLIS;
    private static final String[] perforated_letters = {"ガ、", "ギ、", "グ、", "ゲ、", "ゴ、", "ザ、", "ジ、", "ズ、", "ゼ、", "ゾ、", "ダ、", "ヂ、", "ヅ、", "デ、", "ド、", "バ、", "ビ、", "ブ、", "ベ、", "ボ、", "ギャ、", "ギュ、", "ギョ、", "ジャ、", "ジュ、", "ジョ、", "ビャ、", "ビュ、", "ビョ", "パ、", "ピ、", "プ、", "ペ、", "ポ、", "ピャ、", "ピュ、", "ピョ"};

    static boolean isCheck = false;


    private enum DAYLEFT {

        DAY1(DAY_MILLIS), DAY2(2 * DAY_MILLIS), DAY3(3 * DAY_MILLIS), DAY4(4 * DAY_MILLIS), DAY5(5 * DAY_MILLIS), DAY6(6 * DAY_MILLIS), DAY7(7 * DAY_MILLIS);
        private int value;

        DAYLEFT(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    // set animation click button
    public static void setclickColor(final View view) {
        isCheck = false;
        view.setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            isCheck = true;
                            setLightingColorFilter1(view);
                            if (isCheck) {
                                try {
                                    new CountDownTimer(500, 500) {
                                        @Override
                                        public void onTick(
                                                long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            isCheck = false;
                                            setLightingColorFilter2(view);
                                        }
                                    }.start();
                                } catch (Exception e) {
                                }
                            }
                        }
                    });

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            setLightingColorFilter2(view);
                        }
                    });
                }
                return false;
            }
        });
    }

    public static String getLanguage() {
        String lang = Locale.getDefault().getLanguage();
        if (lang.equalsIgnoreCase(Constants.JA) || lang.equalsIgnoreCase(Constants.EN)) {
            return lang;
        } else
            return Constants.JA;
    }

    public static String getFilename(String filepath) {
        if (filepath == null)
            return null;

        final String[] filepathParts = filepath.split("/");

        return filepathParts[filepathParts.length - 1];
    }

    public static boolean checkFile(String url)
    {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), fileName);
        return file.exists();
    }
    public static boolean checkFilePictures(String url)
    {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
        return file.exists();
    }
    public static boolean checkFileEcho(String url)
    {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        final File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + "echo",
                fileName );
        return file.exists();
    }
    public static void animation(final RelativeLayout rltBottomView)
    {
        rltBottomView.animate()
                .translationY(0)
                .setDuration(3000)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rltBottomView.setVisibility(View.GONE);
                    }
                });
    }
    public static void setupUI(View view, final Activity context) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(context);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, context);
            }
        }
    }
    public static void setupUI(View view, final RelativeLayout relativeLayout) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof RelativeLayout)) {
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (relativeLayout.getVisibility()== View.GONE)
                    {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, relativeLayout);
            }
        }
    }

    public static void galleryAddPic(Activity activity, String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }
    public static void rotateImage(final String file, final Context context) throws IOException {

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(file, opts);
        File imageFile = new File(file);
        int rotationAngle = getCameraPhotoOrientation(context, Uri.fromFile(imageFile), imageFile.toString());

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        FileOutputStream fos=new FileOutputStream(file);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }
    public static Bitmap rotateBitmap(String src) {
        Bitmap bitmap = BitmapFactory.decodeFile(src);
        try {
            ExifInterface exif = new ExifInterface(src);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                case ExifInterface.ORIENTATION_UNDEFINED:
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, final String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotate = 0;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getTime()
    {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("ddMMyyyyHHmmss");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        String localTime = date.format(currentLocalTime);
        return localTime;
    }
    public static String getTimeAgo(long time) {
        /*if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }*/

        long now = System.currentTimeMillis();
       /* if (time > now || time <= 0) {
            return "";
        }*/

        // TODO: localize
        final long diff = Math.abs((now - time) / SECOND_MILLIS);
        /*if (diff < MINUTE_MILLIS) {
            return "just now";// vừa gửi
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";// phút trước
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minute ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "a hours ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";// giờ trước
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday " + getDate(time, "dd/MM/yyyy HH:mm");//Hôm qua
        } else {
            return getDate(time, "dd/MM/yyyy hh:mm");
        }*/

        if (diff < MINUTE_MILLIS) {
            if (diff == 0) {
                return 1 + " 秒前";// giay truoc
            } else {
                return diff + " 秒前";// giay truoc
            }

        } else if (diff < HOUR_MILLIS) {
            if (diff / MINUTE_MILLIS == 0) {
                return 1 + " 分前";// phút trước
            } else {
                return diff / MINUTE_MILLIS + " 分前";// phút trước
            }
        } else if (diff < DAY_MILLIS) {
            return diff / HOUR_MILLIS + " 時間前"; //gio truoc
        } else if (diff < WEEk_MILLIS) {
            return diff / DAY_MILLIS + "日前"; // ngay truoc
        } else if (diff < MONTH_MILLIS) {
            return diff / WEEk_MILLIS + " 週間前";// tuan truoc
        } else if (diff < YEAR_MILLIS) {
            return diff / MONTH_MILLIS + " ヶ月前";// thang truoc
        } else {
            return diff / YEAR_MILLIS + " 年前";// nam truoc;
        }

    }

    public static String getTimeDayAgoSort(long time) {
        long now = System.currentTimeMillis();
        // TODO: localize
        final long diff = Math.abs((now - time) / SECOND_MILLIS);
        long day = diff / DAY_MILLIS;
        if (day == 0)
            return 1 + "日";
        else
            return diff / DAY_MILLIS + "日"; // ngay truoc
    }

    public static String getTimeSystemSort(long time) {

        long now = System.currentTimeMillis();
        // TODO: localize
        final long diff = Math.abs((now - time) / SECOND_MILLIS);

        if (diff / WEEk_MILLIS == 0) {
            return 1 + "週間";
        } else if (diff / WEEk_MILLIS == 4) {
            return 1 + "ヶ月";// thang truoc
        } else if (diff < MONTH_MILLIS) {
            long tuan = diff / WEEk_MILLIS;
            long week = diff / WEEk_MILLIS + 1;
            if (week == 4) return 1 + "ヶ月";// thang truoc
            else
                return week + " 週間";// tuan truoc
        } else if (diff / MONTH_MILLIS == 0) {
            return 1 + "ヶ月";// thang truoc
        } else if (diff < YEAR_MILLIS) {
            long month = diff / MONTH_MILLIS + 1;
            return month + "ヶ月";// thang truoc
        } else if (diff / YEAR_MILLIS == 0) {
            return 1 + "年間";// nam truoc;
        } else {
            return diff / YEAR_MILLIS + "年間";// nam truoc;
        }

    }

    public static String getTimeRemain(long time) {
        long now = System.currentTimeMillis();
        /*if (time > now || time <= 0) {
            return "";
        }*/

        final long diff = Math.abs((now - time) / SECOND_MILLIS);

        if (diff < DAYLEFT.DAY1.getValue()) {
            return "7";
        } else if (diff < DAYLEFT.DAY2.getValue()) {
            return "6";
        } else if (diff < DAYLEFT.DAY3.getValue()) {
            return "5";
        } else if (diff < DAYLEFT.DAY4.getValue()) {
            return "4";
        } else if (diff < DAYLEFT.DAY5.getValue()) {
            return "3";
        } else if (diff < DAYLEFT.DAY6.getValue()) {
            return "2";
        } else if (diff < DAYLEFT.DAY7.getValue()) {
            return "1";
        } else {
            return "0";
        }

    }

    public static String getAge(long time) {

        long now = System.currentTimeMillis();
        /*if (time > now || time <= 0) {
            return "";
        }*/

        // TODO: localize
        final long diff = Math.abs((now - time) / SECOND_MILLIS);

        return diff / YEAR_MILLIS + " 才";// tuoi;

    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    // set color click button enable
    public static void setLightingColorFilter1(View iv) {
        int mul = Color.argb(255, 90, 109, 148);
        int add = Color.argb(255, 63, 69, 53);
        LightingColorFilter lightingColorFilter = new LightingColorFilter(mul,
                add);
        iv.getBackground().setColorFilter(lightingColorFilter);
    }

    // set color click button unenable
    public static void setLightingColorFilter2(View iv) {
        int mul = Color.argb(255, 255, 255, 255);
        int add = Color.argb(0, 0, 0, 0);
        LightingColorFilter lightingColorFilter = new LightingColorFilter(mul,
                add);
        iv.getBackground().setColorFilter(lightingColorFilter);
    }

    // check email
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static long convertDateToTimeStamp(String inputFormat, String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        Date date = null;
        try {
            date = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime();
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        if (inputDate == null) {
            return "";
        }
        Date parsed;
        String outputDate = "";
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        df_input.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.JAPANESE);
        df_output.setTimeZone(tz);

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

        }
//        Log.i("TIME ZONE", outputDate);
//        Log.i("TIME ZONE", "TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezon id :: " + tz.getID());
//        Log.i("TIME STAMP", convertDateToTimeStamp(inputFormat, outputDate));
        return outputDate;

    }

    public static void startAudioFile(Activity activity, int type) {
        String fileName = null;

        switch (type) {
            case Constants.TYPE_NEW_MESSAGE:
                fileName = "message";
                break;
            case Constants.TYPE_TYPING:
                fileName = "typing";
                break;
        }
        try {
            int resID = activity.getResources().getIdentifier(fileName, "raw", activity.getPackageName());
            Uri path = Uri.parse("android.resource://" + activity.getPackageName() + fileName);
            //final MediaPlayer mpintro = MediaPlayer.create(activity, resID);
            final Ringtone r = RingtoneManager.getRingtone(activity, path);
            r.play();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (r.isPlaying()) {
                        r.stop();
                    }
                }
            }, 1000);
        } catch (Exception e) {
        }


    }

    public static String convertDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        if (inputDate == null) {
            return "";
        }
        Date parsed;
        String outputDate = "";
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        df_input.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.JAPANESE);
        df_output.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

        }
//        Log.i("TIME ZONE", outputDate);
//        Log.i("TIME ZONE", "TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezon id :: " + tz.getID());
//        Log.i("TIME STAMP", convertDateToTimeStamp(inputFormat, outputDate));
        return outputDate;

    }

    public static String formatDateFromstring(String outputFormat, Date inputDate) {

        if (inputDate == null) {
            return "";
        }
        String outputDate;

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.JAPANESE);
        df_output.setTimeZone(TimeZone.getDefault());
        outputDate = df_output.format(inputDate);
        if (outputDate == null) {
            return "";
        } else
            return outputDate;

    }

    public static Date getDateFromString(String inputFormat, String inputDate) {
        Date parsed = null;
        if (inputDate == null) {
            return parsed;
        }
        try {
            SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
            parsed = df_input.parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsed;
    }

    private static final String[] days =
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static String getDateTimeLanguage(String year, String month, String date, String day) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
//            int i = calendar.get(Calendar.DAY_OF_WEEK);
//            result = Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日" + "(" + days[i - 1] + ")";
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            String reportDate = dateFormat.format(calendar.getTime());
//            reportDate = reportDate.replace("" + calendar.get(Calendar.YEAR), "").replace("年", "");
//            Log.i("getDateTimeLanguage", reportDate);
            return reportDate + "(" + day + ")";
//                    reportDate.replace(calendar.get(Calendar.YEAR) + "", "");
            /*String weekDay;
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.JAPANESE);

            Calendar calendar = Calendar.getInstance();
            weekDay = dayFormat.format(calendar.getTime());
            return weekDay.toString();*/
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getDateTimeLanguageBirthday(String year, String month, String date, String day) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            String reportDate = dateFormat.format(calendar.getTime());
            return reportDate;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getDateTimeLanguageLong(String year, String month, String date) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            String reportDate = dateFormat.format(calendar.getTime());
            return reportDate;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getDateTimeLanguageLong(Date date) {
        String result = "";
        try {
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            result = dateFormat.format(date);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getDateTimeLanguageCurrent() {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
//            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            String reportDate = dateFormat.format(calendar.getTime());
            return reportDate;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getDateTimeLanguageShort(String year, String month, String date, String day) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPANESE);
            String reportDate = dateFormat.format(calendar.getTime());
            reportDate = reportDate.replace("" + calendar.get(Calendar.YEAR), "").replace("年", "");
            return year+"/"+month + "/" + day ;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getTimeDateCurrent() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
       df.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        //df.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        String formattedDate = df.format(c.getTime());
        Log.i("time_current", formattedDate);//TimeZone.getTimeZone("GMT+00:00")
        return formattedDate;
    }

    /*public static String getFomatTimeJapane() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.JAPANESE);
        String reportDate = dateFormat.format(today);
        Log.i("", "===========>reportDate= " + reportDate.replace(calendar.get(Calendar.YEAR) + "", ""));
    }*/

  /*  public static List<CityInfo> getlistCities(Context context) {
        String json = loadJSONFromAsset(context, "cities.json");
        if (json != null) {
            List<CityInfo> listCities = new ArrayList<>();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> list = HttpUtil.getInstance().initGson().fromJson(json, type);
            for (String item : list) {
                CityInfo info = new CityInfo();
                info.setTitle(item);
                info.setSelected(false);
                listCities.add(info);
            }
            return listCities;
        }
        return new ArrayList<CityInfo>();
    }*/

    public static String loadJSONFromAsset(Context context, String namefile) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(namefile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getError(String code) {
        String error = "";
        switch (code) {
            case "01":
                error = "Error when login";
                break;
            case "02":
                error = "Error when login - User is not existing in DB";
                break;
            case "03":
                error = "Error when verify user by birthday";
                break;
            case "04":
                error = "Failed when verify user info(birthday incorrect)";
                break;
            case "05":
                error = "Error when register account/user";
                break;
            case "06":
                error = "Error when update user info";
                break;
            case "07":
                error = "Error when knock - out of room";
                break;
            default:
                error = "Unknown error";
                break;
        }
        return error;

      /*  10:Error when get chat -rooms by user_id
        11:Error when get notifications info
        12:Error when get users in rooms
        13:Error when get Doctors by user_id
        14:Error when get Doctors in rooms

        20:Unable to query from DynamoDB(table messages)
        21:Unable to add item into DynamoDB (table messages)
        22:Error when update 'last_message' and 'unread_messages'
        23:Error when get user_id FROM chat_room_user
        24:Error when locate searched msg
        25:Error when update msg
        26:Error when delete msg


        30:Error when send file:Invalid file type !
                31:Error when create thumbnail
        32:Error when upload file (opening file)
        33:Error when upload file (writing file)

        40:Error when add new room
        41:User is existing in current room
        42:Error when add user into room
        43:Can 't check user is existing in current room or not


        50:Eror when update number_of_read_msgs

        60:pushNotification:
        Error when get 'token' from DB
        61:pushNotification:
        Error when push notification
        62:register_notification_key:
        Error when check token existing
        63:register_notification_key:
        Error when insert new key

        70:Error when update QR code
        71:Error when get QR code(empty)
        72:Error when get QR code
        73:Error when get doctor by patientInvitationCode (empty)
                74:Error when get doctor by patientInvitationCode
        75:Error when get user by patientInvitationCode (empty)
                76:Error when get user by patientInvitationCode
        77:Error - QR code is used*/
    }

    public static final void hideSoftKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText())
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static final void showSoftKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static final void showSoftKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void focusTextSearch(TextView textView, String content, String txtSearch) {
        try {
            String _txtSearch = Utils.unAccent(txtSearch).toLowerCase();
            String _content = Utils.unAccent(content).toLowerCase();
            SpannableString ss = new SpannableString(content);
        /*ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };*/

            for (int i = -1; (i = _content.indexOf(_txtSearch, i + 1)) != -1; ) {
//            ss.setSpan(clickableSpan, i, i + txtSearch.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.BLACK), i, i + txtSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new StyleSpan(Typeface.BOLD), i, i + txtSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setTextColor(Color.BLACK);
            textView.setText(ss);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setHighlightColor(Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d").toLowerCase();
//                pattern.matcher(temp).replaceAll("");
    }

    public static boolean openApp(Activity activity, String packageName) {
        PackageManager manager = activity.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new PackageManager.NameNotFoundException();
            }
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            i.setType("*/*");
//            context.startActivity(i);
            activity.startActivityForResult(Intent.createChooser(i, "Upload file from..."), 400);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText() && activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }

    public static boolean checkInputKeyBoardFullWidthKatakana(String input) {
        boolean check = false;
        input = input.replaceAll("[―ー－‐・　 ]", "");
        input = input.replaceAll("[ガ、ギ、グ、ゲ、ゴ、ザ、ジ、ズ、ゼ、ゾ、ダ、ヂ、ヅ、デ、ド、バ、ビ、ブ、ベ、ボ、パ、ピ、プ、ペ、ポ、ギャ、ギュ、ギョ、ビャ、ビュ、ビョ、ピャ、ピュ、ピョ、ジャ、ジュ、ジョ、ヂャ、ヂュ、ヂョ、ヷ、ヴ、ヺ、ヴァ、ヴィ、ヴェ、ヴォ、ヴャ、ディ、ドゥ、デュ、ジェ]", "");
        if (input.isEmpty())
            return true;
        //  Pattern p = Pattern.compile("[ァ-ン]*");
        Pattern p = Pattern.compile("[\u30a0-\u30ff]*");
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Matcher m = p.matcher(Character.toString(c));
            check = m.matches();
            Log.i("KeyBoardKatakana", "============>check:" + check);
            if (!check)
                break;
        }
        return check;
    }

    public static boolean isContainerCharacterSpecialAndNumber(String input) {
        boolean check = false;
        Pattern p = Pattern.compile("[０-９0-9\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\^\\_\\`\\~]+$");
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            Matcher m = p.matcher(Character.toString(c));
            check = m.matches();
            Log.i("KeyBoardSpecial", "============>check:" + check);
            if (check)
                break;
        }

        return check;
    }

    public static boolean isContainerCharacterSpecial(String input) {
        boolean check = false;

        Pattern p = Pattern.compile("[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\^\\_\\`\\~]+$");
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Matcher m = p.matcher(Character.toString(c));
            check = m.matches();
            Log.i("KeyBoardSpecial", "============>check:" + check);
            if (check)
                break;
        }

        return check;
    }

    public static boolean isContainerCharacterNumber(String input) {
        boolean check = false;
        Pattern p = Pattern.compile("[０-９0-9]+$");
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Matcher m = p.matcher(Character.toString(c));
            check = m.matches();
            Log.i("KeyBoardSpecial", "============>check:" + check);
            if (check)
                break;
        }

        return check;
    }

    public static String getMimeType(String path) {
        String mimetype = null;
        path = path.replaceAll("[^a-zA-Z_0-9\\.\\-\\(\\)\\%]", "");
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        return mimetype;
    }

    public static String getMimeType(Context context, String path) {
      /*  String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;*/
        String type = null;
        File selected = new File(path);
        if (!selected.isDirectory()) {

            Uri selectedUri = Uri.fromFile(selected);
           /* String fileExtension
                    = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);*/
            ContentResolver cR = context.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getExtensionFromMimeType(cR.getType(selectedUri));
        }
        return type;
    }

    public static String getExtentionFile(String filePath) {
        String extension = "";
        if (filePath != null) {
            extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        }
        return extension;
    }

    public static String getTypeFile(String mimetype) {
        String extension = "";
        if (mimetype != null) {
            extension = mimetype.substring(0, mimetype.indexOf("/"));
        }
        return extension;
    }

    public static String getTypeFilePath(String filePath) {
        return getTypeFile(getMimeType(filePath));
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final void showToastNotify(Context context, String notify) {
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();

    }

   /* public static User checkUserInRoom(List<User> userInfos, String userId) {
        User _userInfo = null;
        for (User userInfo : userInfos) {
            if (userId.equalsIgnoreCase(userInfo.getUser_id())) {
                _userInfo = userInfo;
                return _userInfo;
            }
        }
        return _userInfo;
    }*/

    /*  public static boolean checkStatusUserCurrentInRoom(List<RoomInfo> list) {
          boolean check = false;
          int i = 0;
          for (RoomInfo roomInfo : list) {
              User info = Utils.checkUserInRoom(roomInfo.getUsers(), roomInfo.getUser_id_current());
              if (info != null && info.getStatus() == 0) {
                  i++;
              }
          }
          if (i > 0)
              check = true;
          return check;
      }*/

    public static String getName(String first, String last) {
        return String.format("%s %s", last.trim(), first.trim());
    }

    public static String getAvatar(String avatar, String customize_avatar) {
        String path;
     /*   if (!TextUtils.isEmpty(customize_avatar)) {
            path = customize_avatar;
        } else */
        if (!TextUtils.isEmpty(avatar)) {
            path = avatar;
        } else if (!TextUtils.isEmpty(customize_avatar)) {
            path = customize_avatar;
        } else {
            path = "";
        }
        return path;
    }

    public static String getNickName(String nick_name, String customize_nick_name) {
        String nickName;
        if (!TextUtils.isEmpty(customize_nick_name)) {
            nickName = customize_nick_name;
        } else if (!TextUtils.isEmpty(nick_name)) {
            nickName = nick_name;
        } else {
            nickName = "";
        }
        return nickName;
    }

    public static String getCreated(String create, String modified) {
        String path;
        if (!TextUtils.isEmpty(modified)) {
            path = modified;
        } else if (!TextUtils.isEmpty(create)) {
            path = create;
        } else {
            path = "";
        }
        return path;
    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    public static InputFilter EMOJI_DISABLE = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence src, int start,
                                   int end, Spanned dst, int dstart, int dend) {
            if (src.equals("")) { // for backspace
                return src;
            }
            if (src.toString().matches("[\\x00-\\x7F]+")) {
                return src;
            }
            return "";
        }
    };


    public static void setColorStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }
    }


   /* public static void showConfirmDialog(Context context, String _txt_title, String _txt_content, String _txt_cancel, String _txt_ok, int color, final OnActionPress onActionCancel, final OnActionPress onActionOk) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_confirm_action_nurse);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        *//*dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onActionCancel.onActionPress();
            }
        });*//*
        dialog.setCancelable(false);
        dialog.show();

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        txt_title.setText(_txt_title);
        TextView txt_content = (TextView) dialog.findViewById(R.id.txt_content);
        txt_content.setText(_txt_content);

        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        txt_cancel.setText(_txt_cancel);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        txt_ok.setText(_txt_ok);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionCancel.onActionPress();
                dialog.dismiss();
            }
        });

        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionOk.onActionPress();
                dialog.dismiss();
            }
        });

    }

    public static void showConfirmDialog(Context context, String _txt_title, String _txt_content, String _txt_cancel, String _txt_ok, final OnActionPress onActionCancel, final OnActionPress onActionOk) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_confirm_action);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        *//*dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onActionCancel.onActionPress();
            }
        });*//*
        dialog.setCancelable(false);
        dialog.show();

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        txt_title.setText(_txt_title);
        TextView txt_content = (TextView) dialog.findViewById(R.id.txt_content);
        txt_content.setText(_txt_content);

        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        txt_cancel.setText(_txt_cancel);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        txt_ok.setText(_txt_ok);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionCancel.onActionPress();
                dialog.dismiss();
            }
        });

        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionOk.onActionPress();
                dialog.dismiss();
            }
        });

    }

    public static void showDialogRetryMessage(Context context, int status, final OnActionPress onRetry, final OnActionPress onDelete) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_retry_message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        LinearLayout choose_retry_send = (LinearLayout) dialog.findViewById(R.id.choose_retry_send);
        LinearLayout choose_retry_trash = (LinearLayout) dialog.findViewById(R.id.choose_retry_trash);

        if (status != 0) {
            choose_retry_send.setVisibility(View.GONE);
        }
        choose_retry_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry.onActionPress();
                dialog.dismiss();
            }
        });


        choose_retry_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete.onActionPress();
                dialog.dismiss();
            }
        });
    }*/

    public static boolean checkLargeFileSize(String filePath, long limitFileSize) {
        boolean isLarge = false;
        File file = new File(filePath);
        Log.i("File size: ", "" + file.length());
        if (file.length() > limitFileSize) {
            isLarge = true;
        }
        return isLarge;
    }


    public static boolean checkOverDurationVideo(Context context, String path) {
        boolean isOverDuration = false;
        try {
            MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
            long duration = mp.getDuration() / 1000;// second
            Log.i("checkDurationVideo: ", "" + duration);
            if (duration > 60) {
                isOverDuration = true;
            }
            mp.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOverDuration;
    }

    /**
     * validate password
     *
     * @param context
     * @param password
     * @return
     */
    public static boolean checkPassword(Context context, String password) {
        int numOfSpecial = 0;
        int numOfLetters = 0;
        int numOfUpperLetters = 0;
        int numOfLowerLetters = 0;
        int numOfDigits = 0;
        byte[] bytes = password.getBytes();
        for (byte tempByte : bytes) {
            if (tempByte >= 33 && tempByte <= 47) {
                numOfSpecial++;
            }

            char tempChar = (char) tempByte;
            if (Character.isDigit(tempChar)) {
                numOfDigits++;
            }

            if (Character.isLetter(tempChar)) {
                numOfLetters++;
            }

            if (Character.isUpperCase(tempChar)) {
                numOfUpperLetters++;
            }

            if (Character.isLowerCase(tempChar)) {
                numOfLowerLetters++;
            }
        }
        if (numOfDigits == 0 || numOfLowerLetters == 0 || numOfUpperLetters == 0) {
            return false;
        }
        return true;
    }

    public static boolean checkDuplicateBirthday(String birthday1, String birthday2) {
        if (birthday1.equalsIgnoreCase(birthday2)) {
            return true;
        } else {
            return false;
        }
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
   /* public static String getGender(Context context, int gender) {
        if (gender == 0) {
            return context.getResources().getString(R.string.male);
        } else if (gender == 1) {
            return context.getResources().getString(R.string.female);
        } else if (gender == 2) {
            return context.getResources().getString(R.string.unknown);
        } else return "";
    }*/
}

