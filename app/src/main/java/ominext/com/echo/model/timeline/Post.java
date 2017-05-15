package ominext.com.echo.model.timeline;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ominext.com.echo.model.UserInfo;

/**
 * Created by LuongHH on 11/1/2016.
 */

public class Post extends BaseObservable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("owner")
    @Expose
    private UserInfo user;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("total_like")
    @Expose
    private Integer totalLike;
    @SerializedName("total_comment")
    @Expose
    private Integer totalComment;
    @SerializedName("files")
    @Expose
    private ArrayList<File> files = new ArrayList<File>();
    @SerializedName("liked")
    @Expose
    private Integer liked;
    @SerializedName("is_new_post")
    @Expose
    private Integer isNewPost;
    @SerializedName("is_mark_reported")
    @Expose
    private Integer isMarkReported;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserInfo getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public Integer getLiked() {
        return liked;
    }

    public Integer getIsNewPost() {
        return isNewPost;
    }

    public Integer getIsMarkReported() {
        return isMarkReported;
    }
}
