package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LuongHH on 12/15/2016.
 */

public class Info {
    @SerializedName("sender")
    public Sender sender;

    @SerializedName("receiver")
    public MessageInfo.Receiver receiver;

    @SerializedName("id")
    public Integer id;

    @SerializedName("message_key")
    public String messageKey;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("data")
    public Data data;

    @SerializedName("room_id")
    public String roomId;

    @SerializedName("status")
    public int status;

    public class Sender {
        @SerializedName("id")
        public Integer id;

        @SerializedName("name")
        public String name;

        @SerializedName("nickname")
        public String nickname;

        @SerializedName("avatar")
        public String avatar;
    }

    public class Data {
        @SerializedName("type")
        public Integer type;

        @SerializedName("content")
        public String content;

        @SerializedName("thumbnail")
        public String thumbnail;
    }
}
