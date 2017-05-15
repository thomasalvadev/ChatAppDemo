package ominext.com.echo.model.friendlist;

import android.content.Context;
import android.support.annotation.StringRes;

import com.google.gson.annotations.SerializedName;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

import java.util.List;
import java.util.UUID;

import ominext.com.echo.adapter.expandable.ExpCustomAdapter;

/**
 * Created by Vinh on 11/7/2016.
 */

public class FriendList extends ExpandableItemData {

    private int id;
    private String name;
    private String nickname;
    private String avatar;

    @SerializedName("room_id")
    private String roomId;

    @SerializedName("total_unread")
    private int totalUnread;

    @SerializedName("friend_at")
    private String friendAt;

    @SerializedName("last_message")
    private String lastMessage;

    @SerializedName("status")
    private String status;

    @SerializedName(value = "message_at", alternate = {"show_time"})
    private String messageAt;

    /* rooms */
    @SerializedName("is_creator")
    private int isCreator;

    /* utils */
    private boolean selected;

    @SerializedName("count")
    private int count;

    @SerializedName(value = "list_users", alternate = {"list_rooms", "list_members"})
    private List<FriendList> children;

    private int currentPage;
    private int dataType;

    public FriendList(int type, String text, String path,
                              int treeDepth, List<FriendList> children) {
        super(type, text, path, UUID.randomUUID().toString(), treeDepth, children);
    }

    public static FriendList parent(final String title, final List<FriendList> carrying_list) {
        return new FriendList(ExpCustomAdapter.ITEM_TYPE_PARENT, title, "open", 0, carrying_list);
    }

    public static FriendList parent(final Context ctx, final @StringRes int title, final String path, final List<FriendList> carrying_list) {
        return new FriendList(ExpCustomAdapter.ITEM_TYPE_PARENT,
                ctx.getResources().getString(title), path, 0, carrying_list);
    }

    public static FriendList child(final int type, final String title, final String path) {
        return new FriendList(type, title, path, 1, null);
    }

    public static FriendList child(final int type, final int dataType, int currentPage) {
        FriendList model = new FriendList(type, "", "", 1, null);
        model.dataType = dataType;
        model.currentPage = currentPage;
        return model;
    }

    public static FriendList child(final Context ctx, final int type, final @StringRes int title, final String path) {
        return new FriendList(type, ctx.getResources().getString(title), path, 1, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessageAt() {
        return messageAt;
    }

    public void setMessageAt(String messageAt) {
        this.messageAt = messageAt;
    }

    public int getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(int totalUnread) {
        this.totalUnread = totalUnread;
    }

    public String getFriendAt() {
        return friendAt;
    }

    public void setFriendAt(String friendAt) {
        this.friendAt = friendAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(int isCreator) {
        this.isCreator = isCreator;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCount() {
        return count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getDataType() {
        return dataType;
    }
}
