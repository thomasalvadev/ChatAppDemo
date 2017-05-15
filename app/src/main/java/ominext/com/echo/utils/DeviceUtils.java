package ominext.com.echo.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Vinh on 10/28/2016.
 */

public class DeviceUtils {

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
