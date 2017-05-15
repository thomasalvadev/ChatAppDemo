package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by P.Tuan on 11/8/2016.
 */

public class Message implements Serializable {
    @SerializedName("error")
    public int error;
    @SerializedName("message")
    public String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
