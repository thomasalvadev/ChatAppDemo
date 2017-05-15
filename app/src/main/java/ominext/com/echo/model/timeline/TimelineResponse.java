package ominext.com.echo.model.timeline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import ominext.com.echo.model.ResponseDetail;

/**
 * Created by dieunv on 5/28/2016.
 */
public class TimelineResponse implements Serializable, Cloneable {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private TimelineResponse data;

    @SerializedName("list_posts")
    @Expose
    private ArrayList<Post> posts;

    @SerializedName("detail")
    @Expose
    private ResponseDetail detail;

    public Integer getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Post> getListPost() {
        return posts;
    }

    public TimelineResponse getData() {
        return data;
    }

    public ResponseDetail getDetail() {
        return detail;
    }
}
