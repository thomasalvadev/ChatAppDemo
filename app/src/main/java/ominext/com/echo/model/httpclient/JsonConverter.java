package ominext.com.echo.model.httpclient;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.model.timeline.TimelineResponse;

/**
 * Created by LuongHH on 10/28/2016.
 */

public class JsonConverter {

    private static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).serializeNulls()
            .create();

    static LoginResponse getLoginResponse(String data){
        return gson.fromJson(data, LoginResponse.class);
    }

    static TimelineResponse getTimelineResponse(String data){
        return gson.fromJson(data, TimelineResponse.class);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
