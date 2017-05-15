package ominext.com.echo.utils;

/**
 * Created by Vinh on 10/28/2016.
 */

public class Constants {

    public static final String EN = "en";
    public static final String JA = "ja";
    public static final String LANG = "Lang";
    public static final String DEVICE_ID = "DeviceId";
//    public static final String CURRENT_LANG = EN;
    public static final String TOKEN = "token";
    public static final String DEVICE_OS = "device_os";
    public static final String ANDROID = "android";

    // Timeline
    public static final String POST_ID = "post_id";
    public static final String USER_ID = "user_id";
    public static final String REASON_REPORT = "reason_report";
    public static final String CONTENT = "content";
    public static final String FILE_TYPE = "file_type";
    public static final String FILES = "files[]";
    public static final String KEY_VIDEO_URL = "video_url";
    public static final String FILE = "file";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String CAMERA = "camera";

    /* RegisterActivity */
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String COMMENT = "comment";
    public static final String PHONE_NUMBER = "phonenumber";
    public static final String FIRST_LOGIN = "first_login";
    /* RegisterActivity */

    /* ConfirmRegisterActivity */

    public static final String CONFIRM_TOKEN = "confirm_token";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String SANDBOX_MODE = "sandbox_mode";
    public static final int TYPE_MESSAGE_MYSELF = 0;
    public static final int TYPE_MESSAGE_FRIEND = 1;
    public static final int TYPE_MESSAGE_VIEW_IMAGE = 1;
    public static final int TYPE_MESSAGE_VIEW_TEXT = 0 ;
    public static final int TYPE_MESSAGE_VIEW_VIDEO=2;
    public static final int TYPE_MESSAGE_VIEW_FILE=3;
    public static final int TYPE_MESSAGE_VIEW_WHITE_BOARD_SUCCESS=4;
    public static final int TYPE_MESSAGE_VIEW_WHITE_BOARD_READING=5;
    public static final int TYPE_ACTION = 2;
    //Status typing
    public static final int START_TYPING = 0;
    public static final int END_TYPING = 1;
    public static final int START_EDITTING = 2;
    public static final int END_EDITTING = 3;
    /* ConfirmRegisterActivity */

    //Server production

    //- API base url: http://160.16.80.215
    // - Socket url: http://160.16.80.215:8000

    /*public static final String API_SERVER_URL = "http://ver2.loopchat.jp/";
    public static final String CHAT_HOST_NAME = "160.16.80.215";
    public static final String CHAT_SERVER_URL = "http://160.16.80.215:8181";*/

    //
    // Môi trường test
    //

    public static final String API_SERVER_URL = "http://echochat.ominext.co/";
    public static final String CHAT_HOST_NAME = "46.51.242.216";
    public static final String CHAT_SERVER_URL = "http://46.51.242.216:8000";
    public static final String SIP_SERVER_URL = "139.59.251.151";
    //
    // Môi trường dev
    //

    /*public static final String API_SERVER_URL = "http://118.70.177.23:82/dev_echochat/echochat/public/";
    public static final String CHAT_HOST_NAME = "118.70.177.23";
    public static final String CHAT_SERVER_URL = "http://118.70.177.23:8181";
    public static final String SIP_SERVER_URL = "139.59.251.151";*/

    public static final boolean DEVELOPER_MODE = true;
    public static final String KEY_DEVICE_TOKEN = "device_token";
    public static final int TYPE_NEW_MESSAGE = 1;
    public static final int TYPE_TYPING = 2;
    public static final String MS_ACTION_RECONNECT = "ms_action_reconnect";
    public static final String MS_ACTION_DISCONNECT = "ms_action_disconnect";
    public static final String KEY_GET_TOKEN_API = "042075fbf1f9e6a74aa99586f3008777";//MD5
    public static final String KEY_CURRENT_USER = "current_user";
    public final static int COARSE_LOCATION_RESULT = 100;
    public final static int FINE_LOCATION_RESULT = 101;
    public final static int CALL_PHONE_RESULT = 102;
    public final static int CAMERA_RESULT = 103;
    public final static int READ_CONTACTS_RESULT = 104;
    public final static int WRITE_EXTERNAL_RESULT = 105;
    public final static int READ_EXTERNAL_RESULT = 108;
    public final static int RECORD_AUDIO_RESULT = 106;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public static final String TYPE_MULTIPART = "multipart/form-data";
    public static final String FIELD_ROOM_ID = "room_id";
    public static final String FIELD_NAME_USER = "name";
    public static final String TYPE_MESSAGE_VIEW_CALL = "7";
    public static final String PATH_FILE_WHITE_BOARD = "path_file_white_board";
    public static final String DELETE_MESSAGE = "delete_message";
    public static final String IS_GROUP = "is_group";
    public static final String TIME_INPUT_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String HOUR_INPUT_FORMAT = "HH:mm";
    public static final String MANUFACTURE_XIAOMI = "Xiaomi";
    public static final String DRAW_SINGLE = "1";
    public static final String FIELD_DRAW_ID = "draw_id";
    public static final String DRAW_CMD_DRAW = "1";
    public static final String DRAW_CMD_UNDO = "2";
    public static final String DRAW_CMD_RESET = "3";
    public static final String DRAW_CMD_INSERT_IMAGE = "5";
    public static final String POSITION_DRAW = "position_draw";
    public static final String DRAW_LINE = "1";
    public static final String DRAW_INSERT_IMAGE = "2";
    public static final String ERASER = "eraser";
    public static final String DRAWING_PAINT = "drawing_paint";
    public static final String DRAWING_TITLE = "drawing_title";
    public static final String DRAWING_CONTENT = "drawing_content";
    public static final String MESSAGE_INFO = "message_info";
    public static final String DRAW_FINISH = "draw_finish";
    public static final String WHITE_BOARD_INFO = "white_info";
    public static final String WHITE_BOARD_IS_UPDATE = "white_board_is_update";
    public static final String IS_IMAGE_LOCAL = "is_image_local";
    public static final String URL_IMAGE = "url_image";
    public static final String SHOW_IMAGE = "show_image";
    public static final String MAX_SELECT_ITEM = "max_select_item";
    public static final String LIST_MEDIA = "list_media";
    public static final String DRAW_OFFLINE_INTIMELINE = "draw_offline_intimeline";
    public static final int TYPE_MESSAGE_WHITE_BOARD = 9;
    public static String KEY_USER = "users";
    public static String KEY_USERNAME = "username";
    public static String KEY_USER_ID = "user_id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_LANG = "Lang";
    public static final String EDIT_MESSAGE = "edit message";
    public static final String STATUS_ACTIVE="active";
    public static final String STATUS_EDITED = "editted";
    public static final String STATUS_REMOVE="removed";
    public static final String STATUS_EDITING = "editing";

    public static final String UPDATE_TOTAL_LIKE = "total_like";
    public static final String UPDATE_TOTAL_COMMENT = "total_comment";
    public static final String INSERT_DATA_POST_USER="insert_post_user";
    public static final String HIDE_POST = "hide_post";
    public static final String HIDE_POST_USER = "hide_post_user";
    public static final String UPDATE_PROFILE = "update_profile";
    public static final String HIDE_SUB_MENU = "hide_sub_menu";
    public static final String ROUTER = "router";

    //Usser_id 1
    /*public static final String mAccessToken="$2y$10$lRRXfFn7vLEWHc63dI6O1emfxrXJix3XohSl3zqkXX5929LAEHPeG";
    public static final String mDeviceId="C6A236E8-1C43-4D84-AC79-14C4E115C65D";
    public static final int mUser_id=1;*/
    //Usser_id 196
  /*  public static final String mAccessToken="$2y$10$mzgOc/SmqmdLi/W9Q5twQe9i0PYcjC3oloV8ZQcNnz0qAi7dA1p4m";
    public static final String mDeviceId="399DB223-A9C8-42C8-8764-46D3174FBBC2";
    public static final int mUser_id=196;*/
    public static String KEY_POST_ID = "post_id";

    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String PAGE = "page";
    public static final String ORDER = "order";
    public static final String HISTORY_CHAT = "history_chat";
    public static final String FRIEND_TIME = "friend_time";
    public static final String DETAILS="details";
    /* begin FriendListFragment */
    public static final int PENDING_LIST = 0;
    public static final int REQUESTING_LIST = 1;
    public static final int ROOM_LIST = 0;
    public static final int FRIEND_LIST = 1;
    public static final int BLOCKED_LIST = 4;
    public static final String BLOCK_FRIEND = "block_friend";
    public static final String ACCEPT_FRIEND = "accept_friend";
    public static final String CANCEL_REQUESTING = "cancel_requesting";
    public static final String UNBLOCK_FRIEND = "unblock_friend";
    public static final String ADD_FRIEND = "add_friend";
    public static final String ROOM_ID = "room_id";
    public static final double NUMBER_OF_ITEMS_PER_LOADING = 10.0;

    public static final String NAME_OR_ID = "name_or_id";
    /* end FriendListFragment */
    public static final int LOADDING=0;
    public static final int ERROR=1;
    public static final int SUCCESS=2;
    public static final int NOT_DATA=3;
    /* begin GroupDetailActivity */
    public static final int REQUEST_CODE_PICK_IMAGE = 1001;
    public static final int REQUEST_CODE_CREATE_GROUP = 1002;
    public static final int REQUEST_CODE_ADD_MEMBER = 1003;
    public static final int REQUEST_CODE_SEARCHING_USER = 1004;
    public static final int REQUEST_CODE_UPDATE_GROUP = 1005;
    public static final int REQUEST_CODE_CHAT_ROOM = 1006;
    public static final int REQUEST_CODE_CHAT_FRIEND = 1007;
    public static final int REQUEST_CODE_VIEW_PROFILE = 1008;
    public static final String SFA_SELECTED_FRIENDS = "sfa_selected_friends";
    public static final String GDA_NEW_ROOM_MEMBERS = "gda_new_room_members";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_FIREND_IDS = "friend_ids";
    public static final String FIELD_AVATAR = "avatar";
    public static final String FIELD_EXCEPT_USER_IDS = "except_user_ids";
    public static final String CHANGED_LIST = "changed_list";
    public static final String IS_CREATOR = "is_creator";
    /* end GroupDetailActivity */
    /*Time lines*/
    public static final int ITEM_DETAILS = 0;
    public static final int ITEM_COMMENT = 1;
    public static final int ITEM_POST = 2;

    // Profile
    public static final String OLD_PASSWORD = "old_password";
    public static final String NEW_PASSWORD = "new_password";
    public static final String JPG_EXTENSION = ".jpg";
    public static final String TEMP_FOLDER_NAME = "/.zEchoImages";

    /* status add friend*/
    public static final String REQUESTING = "requesting";
    public static final String FRIEND = "friend";
    public static final String BLOCKED = "blocked";
    public static final String UNBLOCK = "unblock";
    public static final String PENDING = "pendding";
    public static final String NO_RELATIVE = "no-relation";
    public static int RESPONSE_SUCCESS=0;
//    public static final String ACCEPT_FRIEND = "";
    public static final long FILE_SIZE_15MB = 15 * 1024 * 1024;//B
    public static final long FILE_SIZE_100MB = 100 * 1024 * 1024;//B
    public static final String WIDTH="width";
    public static final String HEIGHT="height";
    /* Notifications */
    public static final int NOTIFICATIONS_NEW_REQUEST_FRIEND = 1;
    public static final int NOTIFICATIONS_NEW_ROOM = 2;
    public static final int NOTIFICATIONS_NEW_MESSAGE = 3;
    public static final int NOTIFICATIONS_NEW_LIKE = 4;
    public static final int NOTIFICATIONS_NEW_COMMENT = 5;
    public static final String NOTIFICATIONS_CUSTOM = "custom";
    public static String NOTIFICATIONS_ROOM = "room";
    public static String NOTIFICATIONS_FRIEND = "friend";
    public static String NOTIFICATIONS_POST = "post";
    public static final String IMAGE_VIDEO_DIRECTORY_NAME = "EchoUpLoad";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int FRIEND_LIST_TAB_INDEX = 0;
    public static final int FRIEND_TIMELINE_INDEX = 2;
}
