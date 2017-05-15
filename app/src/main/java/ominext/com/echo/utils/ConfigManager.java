package ominext.com.echo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.Set;

import ominext.com.echo.model.sharedpreferences.ObjectSerializer;
import ominext.com.echo.model.UserInfo;


/**
 * Created by dieunv on 4/27/2016.
 */
public class ConfigManager {
    Context activity;
    private SharedPreferences pref;
    private Editor editor;
    private boolean autoCommit = true;

    public ConfigManager(Context activity) {
        this.autoCommit = true;
        this.activity = activity;
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = pref.edit();
    }

    public ConfigManager(Activity activity, boolean autoCommit) {
        this.autoCommit = autoCommit;
        this.activity = activity;
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = pref.edit();
    }


    public void putString(String key, String value) {
        editor.putString(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void removeAll() {
        editor.remove("identity_id");
        editor.remove(Constants.KEY_GET_TOKEN_API);
        editor.remove(Constants.KEY_USER);
        editor.remove(Constants.KEY_CURRENT_USER);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public void commit() {
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public void putArrayListInteger(String key, Set<String> list) {
        editor.putStringSet(key, list);
        if (autoCommit) {
            commit();
        }
    }

    public Set<String> getArrayListInteger(String key, Set<String> defaultValue) {
        return pref.getStringSet(key, defaultValue);
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

   /* public void putArrayList(String key, ArrayList<UserInfoShared> userList) {
        try {
            editor.putString(key, ObjectSerializer.serialize(userList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public void putUserInfoShared(String key, UserInfoShared obj) {
        try {
            editor.putString(key, ObjectSerializer.serialize(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public UserInfoShared getUserInfoShared(String key) {
        UserInfoShared user = null;
        try {
            user = (UserInfoShared) ObjectSerializer.deserialize(pref.getString(key, ObjectSerializer.serialize(new UserInfoShared())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<UserInfoShared> getArrayListUser(String key) {
        ArrayList userList = new ArrayList();
        try {
            userList = (ArrayList) ObjectSerializer.deserialize(pref.getString(key, ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }*/

    public void putIsCodeLogin(String key, boolean isCodeLogin) {
        editor.putBoolean(key, isCodeLogin);
        editor.commit();
    }

    public boolean getIsCodeLogin(String key) {
        return pref.getBoolean(key, false);
    }

    public SharedPreferences getPref() {
        return pref = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void removeAllExceptSwitchAccount() {
        editor.remove("identity_id");
        editor.remove(Constants.KEY_GET_TOKEN_API);
        editor.remove(Constants.KEY_USER);
        editor.remove(Constants.KEY_CURRENT_USER);
        // editor.remove(Constants.KEY_GET_LIST_ACCOUNT);
        editor.commit();
    }

    public void putUserInfoShared(String key, UserInfo obj) {
        try {
            editor.putString(key, ObjectSerializer.serialize(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public UserInfo getUserInfoShared(String key) {
        UserInfo user = null;
        try {
            user = (UserInfo) ObjectSerializer.deserialize(pref.getString(key, ObjectSerializer.serialize(new UserInfo())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
