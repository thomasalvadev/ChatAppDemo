package ominext.com.echo.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ominext.com.echo.model.ResponseDetail;
import ominext.com.echo.model.UserInfo;

/**
 * Created by dieunv on 5/28/2016.
 */
public class LoginResponse implements Serializable, Cloneable {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("message")
    @Expose
    private String message;



    @SerializedName("data")
    @Expose
    private UserInfo user;

    @SerializedName("detail")
    @Expose
    private ResponseDetail detail;

    public Integer getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public ResponseDetail getDetail() {
        return detail;
    }
}
