package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dieunv on 12/1/2016.
 */

public class MessageSocketInfo implements Serializable {
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("text_message")
    private String text_message;
    @SerializedName("message_key")
    private String message_key;

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public void setMessage_key(String message_key) {
        this.message_key = message_key;
    }
}
