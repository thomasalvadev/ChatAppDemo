package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dieunv on 11/4/2016.
 */

public class ListMessageInfo implements Serializable {

    @SerializedName("error")
    private int error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;
    public Data getData() {
        return data;
    }
    public int getError() {
        return error;
    }
    public String getMessage() {
        return message;
    }

    public class Data
    {
        @SerializedName("list_messages")
        private List<MessageInfo> list_messages;
        public List<MessageInfo> getList_messages() {
            return list_messages;
        }
    }
}
