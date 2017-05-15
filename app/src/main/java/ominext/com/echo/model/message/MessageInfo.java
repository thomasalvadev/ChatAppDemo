package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ominext.com.echo.model.UserInfo;

/**
 * Created by dieunv on 11/4/2016.
 */

public class MessageInfo implements Serializable {

    public boolean isStatusSend;

    @SerializedName("sender")
    private UserInfo sender;

    @SerializedName("id")
    private String id;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("number_seen")
    private int number_seen;

    @SerializedName("data")
    private MessageInfo data;

    @SerializedName("status")
    private String status;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("new_content")
    private String new_content;

    private String message_key;

    private int type_user;

    private boolean isShowYear;
    private boolean isShowDate;

    private String timeSendMsg;
    private String uploadID;

    private boolean isUpload;

    @SerializedName("type")
    private String type;

    @SerializedName("content")
    private String content;

    @SerializedName("call")
    private Call call;

    @SerializedName("receiver")
    private Receiver receiver;

    @SerializedName("thumbnail")
    private String thumbnail;

    //khi trả vê socket seen mesage
    @SerializedName("message_id")
    private String message_id;

    //khi trả vê khi typing
    @SerializedName("room_id")
    private String room_id;

    //khi trả vê vẽ draw
    @SerializedName("old_draw_id")
    private String old_draw_id;

    //set time to show
    private String message_time;

    private boolean local;

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public String getNew_content() {
        return new_content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MessageInfo getData() {
        return data;
    }

    public void setData(MessageInfo data) {
        this.data = data;
    }

    public String getTimeSendMsg() {
        return timeSendMsg;
    }

    public void setTimeSendMsg(String timeSendMsg) {
        this.timeSendMsg = timeSendMsg;
    }

    public boolean getIsShowYear() {
        return isShowYear;
    }

    public void setIsShowYear(boolean isShowYear) {
        this.isShowYear = isShowYear;
    }

   /* public boolean getIsShowDate() {
        return isShowDate;
    }

    public void setIsShowDate(boolean isShowDate) {
        this.isShowDate = isShowDate;
    }*/

    public boolean isStatusSend() {
        return isStatusSend;
    }

    public void setStatusSend(boolean statusSend) {
        isStatusSend = statusSend;
    }

    public String getUploadID() {
        return uploadID;
    }

    public void setUploadID(String uploadID) {
        this.uploadID = uploadID;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public UserInfo getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_key() {
        return message_key;
    }

    public void setMessage_key(String message_key) {
        this.message_key = message_key;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getType_user() {
        return type_user;
    }

    public void setType_user(int type_user) {
        this.type_user = type_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Call getCall() {
        return call;
    }

    public int getNumber_seen() {
        return number_seen;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public boolean isShowDate() {
        return isShowDate;
    }

    public void setShowDate(boolean showDate) {
        isShowDate = showDate;
    }

    public void setNumber_seen(int number_seen) {
        this.number_seen = number_seen;
    }

    public String getOld_draw_id() {
        return old_draw_id;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public class Call implements Serializable {
        @SerializedName("type")
        private String type;
        @SerializedName("start_at")
        private String start_at;
        @SerializedName("length")
        private String length;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }
    }

    public class Receiver implements Serializable {
        @SerializedName("type")
        private String type;
        @SerializedName("room")
        private String room;

        public String getType() {
            return type;
        }

        public String getRoom() {
            return room;
        }
    }
}
