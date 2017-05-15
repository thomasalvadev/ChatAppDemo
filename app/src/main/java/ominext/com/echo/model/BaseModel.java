package ominext.com.echo.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vinh on 10/31/2016.
 */

public abstract class BaseModel {

    public JSONObject toJSON() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
