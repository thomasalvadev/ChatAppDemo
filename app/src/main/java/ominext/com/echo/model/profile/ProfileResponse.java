package ominext.com.echo.model.profile;

import com.google.gson.annotations.SerializedName;

import ominext.com.echo.model.ResponseDetail;

/**
 * Created by LuongHH on 11/22/2016.
 */

public class ProfileResponse {

    @SerializedName("error")
    private int error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Profile data;

    @SerializedName("detail")
    private ResponseDetail detail;

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Profile getData() {
        return data;
    }

    public ResponseDetail getDetail() {
        return detail;
    }
}
