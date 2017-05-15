package ominext.com.echo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ominext.com.echo.R;
import ominext.com.echo.adapter.MessageAdapter;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.model.UserInfo;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.httpclient.JsonConverter;
import ominext.com.echo.model.message.Info;
import ominext.com.echo.model.message.ListMessageInfo;
import ominext.com.echo.model.message.Message;
import ominext.com.echo.model.message.MessageInfo;
import ominext.com.echo.model.message.MessageSocketInfo;
import ominext.com.echo.model.message.UpdateStatusTyping;
import ominext.com.echo.utils.ApiSocket;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.DateTimeUtils;
import ominext.com.echo.utils.EchoApplication;
import ominext.com.echo.utils.Utils;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.messages)
    RecyclerView recyclerView;
    @BindView(R.id.list_menu)
    LinearLayout listMenu;
    @BindView(R.id.message_input)
    EditText messageInput;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.lblTyping)
    LinearLayout lblTyping;
    @BindView(R.id.username)
    TextView mUsernameView;

    private String message;

    private boolean mIsLoadMore = false;
    //    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private List<MessageInfo> mList;
    private MessageAdapter mAdapter;

    private String mLanguage;
    private String mDeviceId;
    private String mAccessToken;

    private int mUserId;
    private String mRoomId;
    private int mPage = 1;

    private Socket mSocket;
    private Emitter.Listener onNewMessage;
    private Emitter.Listener onUpdateMessage;
    private Emitter.Listener onSeenMessage;
    private Emitter.Listener onUpdateStatus;

    private boolean mTyping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initializeVariable();

        initializeListener();

        initializeView();

        initializeEmitterListener();

        initializeSocket();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off(ApiSocket.NEW_MESSAGE, onNewMessage);
        mSocket.off(ApiSocket.SEEN_MESSAGE, onSeenMessage);
        mSocket.off(ApiSocket.UPDATE_MESSAGE, onUpdateMessage);
    }

    @OnClick(R.id.btnSend)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(messageInput.getText().toString().trim());
                }
                break;
        }
    }

    private void initializeVariable() {
        mDeviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mLanguage = Utils.getLanguage();
        ConfigManager configManager = new ConfigManager(this);
        mAccessToken = configManager.getUserInfoShared(Constants.KEY_USER).getAccessToken();
        mUserId = configManager.getUserInfoShared(Constants.KEY_USER).getId();
        Intent intent = getIntent();
//        mRoomId = intent.getStringExtra(Constants.FIELD_ROOM_ID);
        mRoomId = "9803929e31190d32f8b3ad455ee04af5";
    }

    private void initializeListener() {
//        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (Connectivity.isConnected(ChatActivity.this)) {
//                    if (!mIsLoadMore) {
//                        mIsLoadMore = true;
//                        loadDataListMessageByPage();
//                    } else {
//                        onItemsLoadComplete();
//                    }
//                } else {
//                    Toast.makeText(ChatActivity.this, getString(R.string.network_disconnect), Toast.LENGTH_SHORT).show();
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        };
    }

    private void initializeView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
//        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        mList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, mList);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadDataListMessageByPage();
        scrollToBottom();

        messageInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (listMenu.getVisibility() == View.VISIBLE) {
                    listMenu.setVisibility(View.GONE);
                }
                return false;
            }
        });
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (listMenu.getVisibility() == View.VISIBLE) {
                    listMenu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listMenu.getVisibility() == View.VISIBLE) {
                    listMenu.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(s.toString())) {
                    if (!TextUtils.isEmpty(message)) {
                        updateStatus(Constants.END_TYPING);
                        mTyping = false;
                    }
                } else {
                    if (!mSocket.connected()) return;
                    if (TextUtils.isEmpty(message)) {
                        updateStatus(Constants.START_TYPING);
                        recyclerView.scrollToPosition(mList.size() - 1);
                        mTyping = true;
                    }
                }
                message = s.toString();
            }
        });

        message = messageInput.getText().toString();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.this.onBackPressed();
            }
        });
    }

    private void initializeEmitterListener() {
        onNewMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseDataOnNewMessage(args[0].toString());
                    }
                });
            }
        };

        onUpdateMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseDataOnNewMessage(args[0].toString());
                    }
                });
            }
        };

        onSeenMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        parseDataOnNewMessage(args[0].toString());
                    }
                });
            }
        };
        onUpdateStatus = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseDataOnTyping(args[0].toString());
                    }
                });
            }
        };
    }

    private void initializeSocket() {

        mSocket = ((EchoApplication) getApplication()).getSocket();
        mSocket.emit(ApiSocket.JOIN, "{'access_token':'" + mAccessToken + "'}");
        mSocket.on(ApiSocket.NEW_MESSAGE, onNewMessage);
//        mSocket.on(ApiSocket.SEEN_MESSAGE, onSeenMessage);
//        mSocket.on(ApiSocket.UPDATE_MESSAGE, onUpdateMessage);
        mSocket.on(ApiSocket.UPDATE_STATUS, onUpdateStatus);
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "================>Socket Connected");
                mSocket.emit(ApiSocket.JOIN, "{'access_token':'" + mAccessToken + "'}");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "================>Socket error");
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "================>Socket Disconnect");
            }

        });
    }

    private void parseDataOnNewMessage(String data) {
        Log.i(TAG, "=======>newMessage:" + data);
        Gson gson = new Gson();
        final Info info = gson.fromJson(data, Info.class);
        if (info.sender.id != mUserId && info.receiver.getRoom().equals(mRoomId)) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setId(String.valueOf(info.id));
            messageInfo.setMessage_key(info.messageKey);
            messageInfo.setCreated_at(info.createdAt);
            messageInfo.setType_user(info.sender.id == mUserId ? Constants.TYPE_MESSAGE_MYSELF : Constants.TYPE_MESSAGE_FRIEND);
            messageInfo.setReceiver(info.receiver);

            messageInfo.setType(String.valueOf(info.data.type));
            messageInfo.setContent(info.data.content);
            messageInfo.setThumbnail(info.data.thumbnail);
            messageInfo.setData(messageInfo);

            messageInfo.setStatus(Constants.STATUS_ACTIVE);

            UserInfo sender = new UserInfo();
            sender.setId(info.sender.id);
            sender.setName(info.sender.name);
            sender.setNickname(info.sender.nickname);
            sender.setAvatar(info.sender.avatar);

            messageInfo.setSender(sender);

            mList.add(messageInfo);
            mAdapter.notifyDataSetChanged();

            HttpUtil.getInstance(this).seenMessenger(TAG, mLanguage, mDeviceId, mAccessToken, String.valueOf(info.id), new OnResponseSuccess<Message, String>() {
                @Override
                public void onResponseSuccess(String tag, Message response, String extraData) {

                }

                @Override
                public void onResponseError(String tag, String message) {

                }
            });
            scrollToBottom();
        }
    }

    private void parseDataOnTyping(String data) {
        // android.util.Log.i(TAG, "=========>onTyping:" + data.toString());
        try {
            Gson gson = new Gson();
            final Info info = gson.fromJson(data, Info.class);
            if (info.roomId.equals(mRoomId)) {
                switch (info.status) {
                    case Constants.START_TYPING:
                        addTyping(info, true);
                        scrollToBottom();
                        break;
                    case Constants.END_TYPING:
                        parseDataOnStopTyping(data);
                        break;
                    case Constants.START_EDITTING:
                        addTyping(info, false);
                        break;
                    case Constants.END_EDITTING:
                        parseDataOnStopTyping(data);
                        break;
                }
            }
        } catch (Exception e) {

        }
    }

    private void addTyping(final Info info, final boolean isTyping) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (info.sender.id != mUserId) {
                    if (isTyping) {
                        setUsername(getString(R.string.typing));
                    } else {
                        setUsername(getString(R.string.typing_editing));
                    }
                } else {
                    lblTyping.setVisibility(View.GONE);
                }
            }
        });
    }

    private void parseDataOnStopTyping(String data) {
        Log.i(TAG, "=========>onTyping:" + data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblTyping.setVisibility(View.GONE);
            }
        });

    }

    private void updateStatus(int status) {
        UpdateStatusTyping updateStatusTyping = new UpdateStatusTyping();
        updateStatusTyping.setStatus(status);
        updateStatusTyping.setRoom_id(mRoomId);
        mSocket.emit(ApiSocket.UPDATE_STATUS_TYPING, JsonConverter.toJson(updateStatusTyping));
    }

    public void sendMessage(String msg) {
        mTyping = false;
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmssSSS");
        String format = s.format(new Date());
        String timeDateCurrent = Utils.getTimeDateCurrent();
        String date_end = DateTimeUtils.formateDateFromstring(this, "dd-MM-yyyy HH:mm", "dd-MM-yyyy HH:mm", getTimeDateEndItem());
        MessageInfo info = new MessageInfo();
        info.setCreated_at(timeDateCurrent);//date_now
        info.setTimeSendMsg(timeDateCurrent);
        info.setType_user(Constants.TYPE_MESSAGE_MYSELF);
        info.setMessage_key(format);

        info.setStatusSend(false);
        info.setStatus("active");
        info.setUpload(false);
        info.setData(info);
        info.setLocal(true);
        info.getData().setType("0");
        info.getData().setContent(msg);
        info.setMessage_time(DateTimeUtils.formateDateFromstring(this, Constants.TIME_INPUT_FORMAT, Constants.HOUR_INPUT_FORMAT, info.getCreated_at()));
        if (!timeDateCurrent.equals(date_end) || mList.isEmpty()) {
            info.setShowDate(true);
        } else {
            info.setShowDate(false);
        }
        insertMessage(info);
        scrollToBottom();
        MessageSocketInfo messageSocketInfo = new MessageSocketInfo();
        messageSocketInfo.setMessage_key(format);
        messageSocketInfo.setRoom_id(mRoomId);
        messageSocketInfo.setText_message(msg);
        String data = JsonConverter.toJson(messageSocketInfo);
        // Log.i(TAG, "=================>data send:" + data);
        mSocket.emit(ApiSocket.SEND_TEXT_MESSAGE, data);
        messageInput.setText("");
    }

    public void insertMessage(MessageInfo info) {
        if (info.getType_user() == Constants.TYPE_MESSAGE_MYSELF) {
            if (info.getNumber_seen() > 0)
                info.setMessage_time(getString(R.string.seen_message) + " " +
                        DateTimeUtils.formateDateFromstring(this, Constants.TIME_INPUT_FORMAT, Constants.HOUR_INPUT_FORMAT, info.getCreated_at()));
            else
                info.setMessage_time(
                        DateTimeUtils.formateDateFromstring(this, Constants.TIME_INPUT_FORMAT, Constants.HOUR_INPUT_FORMAT, info.getCreated_at()));
        } else {
            info.setMessage_time(
                    DateTimeUtils.formateDateFromstring(this, Constants.TIME_INPUT_FORMAT, Constants.HOUR_INPUT_FORMAT, info.getCreated_at()));
        }
        mList.add(info);
        if (mList.size() > 1) {
            for (int i = mList.size() - 1; i >= 0; i--) {
                if (i > 0) {
                    if (mList.get(i).getType_user() == mList.get(i - 1).getType_user()) {
                        long time1 = DateTimeUtils.timestamp(mList.get(i).getCreated_at());
                        long time2 = DateTimeUtils.timestamp(mList.get(i - 1).getCreated_at());
                        if ((time1 / 60000) == (time2 / 60000)) {
                            if (mList.get(i - 1).getType_user() == Constants.TYPE_MESSAGE_MYSELF) {
                                if (!mList.get(i - 1).getStatus().equals(Constants.STATUS_REMOVE) || !mList.get(i - 1).getStatus().equals(Constants.STATUS_EDITED))
                                    if (mList.get(i - 1).getNumber_seen() <= 0)
                                        mList.get(i - 1).setMessage_time("");
                            } else {
                                if (!mList.get(i - 1).getStatus().equals(Constants.STATUS_REMOVE) || !mList.get(i - 1).getStatus().equals(Constants.STATUS_EDITED))
                                    mList.get(i - 1).setMessage_time("");
                            }
                        }
                    }
                    String date1 = DateTimeUtils.formateDateFromstring(this, "dd-MM-yyyy HH:mm", "dd-MM-yyyy", mList.get(i).getCreated_at());
                    String date2 = DateTimeUtils.formateDateFromstring(this, "dd-MM-yyyy HH:mm", "dd-MM-yyyy", mList.get(i - 1).getCreated_at());
                    if (!date1.equals(date2)) {
                        mList.get(i).setShowDate(true);
                    } else {
                        mList.get(i).setShowDate(false);
                    }
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    private String getTimeDateEndItem() {
        String date;
        int index = mList.size() - 1;
        if (index >= 0) {
            date = mList.get(index).getCreated_at();
            if (date == null) {
                return Utils.getTimeDateCurrent();
            } else {
                return date;
            }
        }
        return null;
    }

    public void setUsername(String username) {

        if (null == mUsernameView) return;
        mUsernameView.setText(username);
        mUsernameView.setVisibility(View.VISIBLE);
        lblTyping.setVisibility(View.VISIBLE);
        mUsernameView.setTextColor(getResources().getColor(R.color.black));
    }

    private void scrollToBottom() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
    }

    private void loadDataListMessageByPage() {
        HttpUtil.getInstance(this).requestListMessage(TAG, mLanguage, mDeviceId, mAccessToken, mRoomId, mPage + "", "", new OnResponseSuccess<ListMessageInfo, String>() {

            @Override
            public void onResponseSuccess(String tag, ListMessageInfo response, String extraData) {

                mIsLoadMore = false;
                onItemsLoadComplete();
                parseLoadDataListMessageByPage(response.getData().getList_messages());
                mPage++;
            }

            @Override
            public void onResponseError(String tag, String message) {
                mIsLoadMore = false;
                onItemsLoadComplete();
                // showToast(message);
            }
        });
    }

    private void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
        if (loadingProgress.isShown())
            loadingProgress.setVisibility(View.GONE);
    }

    private void parseLoadDataListMessageByPage(List<MessageInfo> list) {
        if (list != null) {
            if (list.size() > 0) {
//                orderMessageTime();
                Collections.reverse(list);
                mList.clear();
                for (int i = 0; i < list.size(); i++) {
                    MessageInfo messageInfo = list.get(i);
                    if (mUserId == messageInfo.getSender().getId()) {
                        messageInfo.setType_user(Constants.TYPE_MESSAGE_MYSELF);
                    } else {
                        messageInfo.setType_user(Constants.TYPE_MESSAGE_FRIEND);
                    }
                    mList.add(messageInfo);
                }
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(mList.size() - 1);
            }
        }
        loadingProgress.setVisibility(View.GONE);
    }
}
