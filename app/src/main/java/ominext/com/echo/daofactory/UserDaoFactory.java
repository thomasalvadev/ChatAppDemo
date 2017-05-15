package ominext.com.echo.daofactory;

import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ominext.com.echo.model.UserInfo;

/**
 * Created by LuongHH on 11/7/2016.
 */

public class UserDaoFactory {
    private String TAG = UserDaoFactory.class.getSimpleName();
    private Realm realm;
    private static UserDaoFactory messageDaoFactory;

    public UserDaoFactory() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
    }

    public static UserDaoFactory getInstaince() {
        if (messageDaoFactory == null) {
            messageDaoFactory = new UserDaoFactory();
        }
        return messageDaoFactory;
    }


    public void insert(UserInfo userInfo) {
        try {
            RealmResults<UserInfo> results = realm.where(UserInfo.class)
                    .equalTo("name", userInfo.getName())
                    .findAll();
            if (results.size() > 0) {
                realm.beginTransaction();
                realm.clear(UserInfo.class);
                realm.commitTransaction();

            }
            realm.beginTransaction();
            realm.copyToRealm(userInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            realm.commitTransaction();
        }
        Log.i(TAG, "=====================> insert: " + realm.where(UserInfo.class).count());

    }

    public void insert(UserInfo userInfo, boolean update) {
        try {
            RealmResults<UserInfo> result = realm.where(UserInfo.class)
                    .equalTo("name", userInfo.getName())
                    .findAll();
            if (result.size() == 0) {
                realm.beginTransaction();
                realm.copyToRealm(userInfo);
                realm.commitTransaction();
            }
            if (update == true) {

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }

    }

    public UserInfo getUserbyId(String name) {
        RealmResults<UserInfo> result = realm.where(UserInfo.class)
                .equalTo("name", name)
                .findAll();
        if (result.size() > 0) {
            UserInfo userInfo = result.first();
            return userInfo;
        } else {
            return null;
        }
    }

    public ArrayList<String> getListLoggedInUsernames() {
        RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();
        ArrayList<String> usernames = new ArrayList<>();
        if (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {
                usernames.add(results.get(i).getName());
            }
        }
        return usernames;
    }

    public String getLastLoggedInUsername() {
        RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();
        if (results.size() > 0) {
            return results.last().getName();
        }
        return null;
    }

    public void deleteAll() {
        realm.beginTransaction();
        realm.where(UserInfo.class).findAll().clear();
        realm.commitTransaction();
    }

}
