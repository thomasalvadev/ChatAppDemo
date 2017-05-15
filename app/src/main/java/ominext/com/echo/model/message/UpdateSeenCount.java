package ominext.com.echo.model.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dieunv on 11/23/2016.
 */

public class UpdateSeenCount implements Serializable {
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("chat_type")
    private int chat_type;
    @SerializedName("range_of_message")
    private List<Integer> range_of_message;
    @SerializedName("metadata")
    private List<List<Integer>> metadata;

    public String getRoom_id() {
        return room_id;
    }

    public int getChat_type() {
        return chat_type;
    }

    public List<Integer> getRange_of_message() {
        return range_of_message;
    }

    public List<List<Integer>> getMetadata() {
        return metadata;
    }
}
