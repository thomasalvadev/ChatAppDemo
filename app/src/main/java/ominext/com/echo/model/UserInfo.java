package ominext.com.echo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GamePC on 10/28/2016.
 */

public class UserInfo extends RealmObject implements Serializable {

    @Ignore
    @SerializedName("id")
    @Expose
    private Integer id;
    @PrimaryKey
    @SerializedName("name")
    @Expose
    private String name;
    @Ignore
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @Ignore
    @SerializedName("email")
    @Expose
    private String email;
    @Ignore
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Ignore
    @SerializedName("status")
    @Expose
    private String status;
    @Ignore
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
