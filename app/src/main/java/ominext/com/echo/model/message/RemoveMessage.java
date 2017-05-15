package ominext.com.echo.model.message;

/**
 * Created by P.Tuan on 11/14/2016.
 */

public class RemoveMessage {
    public int id;
    public String room_id;
    public String deleted_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
