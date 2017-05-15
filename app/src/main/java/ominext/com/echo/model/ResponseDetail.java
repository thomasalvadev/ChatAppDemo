package ominext.com.echo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinh on 10/31/2016.
 */

public class ResponseDetail {

    @SerializedName("field")
    private String field;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
