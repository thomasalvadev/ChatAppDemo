package ominext.com.echo.model.profile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LuongHH on 11/22/2016.
 */

public class Profile {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("comment")
    private String comment;

    @SerializedName("email")
    private String email;

    @SerializedName("phonenumber")
    private String phoneNumber;

    @SerializedName("friend_status")
    private String friendStatus;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComment() {
        return comment;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFriendStatus() {
        return friendStatus;
    }
}
