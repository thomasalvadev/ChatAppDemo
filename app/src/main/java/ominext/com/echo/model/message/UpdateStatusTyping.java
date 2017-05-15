package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dieunv on 12/1/2016.
 */

public class UpdateStatusTyping implements Serializable {
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("status")
    private int status;
    public void setStatus(int status) {
        this.status = status;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }


}
