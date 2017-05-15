package ominext.com.echo.model.httpclient;

import ominext.com.echo.model.BaseResult;
import ominext.com.echo.model.friendlist.FriendList;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.model.message.ListMessageInfo;
import ominext.com.echo.model.message.Message;
import ominext.com.echo.model.timeline.TimelineResponse;
import ominext.com.echo.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by dieunv on 4/25/2016.
 */
public interface APIService {

    String API_LOGIN = "api/auth";
    String API_GET_ALL_TIMELINE = "api/alltimeline";
    String API_LOGOUT = "api/logout";
    String API_GET_LIST_MESSAGE = "api/message/load";
    String API_MESSAGE_SEEN = "api/seenACK";
    String API_GET_PENDING_LIST = "api/friend/listPendding";
    String API_GET_REQUESTING_LIST = "api/friend/listRequesting";
    String API_GET_ROOM_LIST = "api/room/list";
    String API_GET_FRIEND_LIST = "api/friend/list";
    String API_GET_BLOCKED_LIST = "api/friend/listBlocked";

    @FormUrlEncoded
    @POST(API_LOGIN)
    Call<LoginResponse> login(@Header(Constants.LANG) String language,
                              @Header(Constants.DEVICE_ID) String deviceId,
                              @Field(Constants.USERNAME) String username,
                              @Field(Constants.PASSWORD) String password,
                              @Field(Constants.DEVICE_TOKEN) String deviceToken,
                              @Field(Constants.SANDBOX_MODE) int sandboxMode,
                              @Field(Constants.DEVICE_OS) String deviceOS);

    @FormUrlEncoded
    @POST(API_GET_ALL_TIMELINE)
    Call<TimelineResponse> getAllTimeLine(@Header(Constants.LANG) String language,
                                          @Header(Constants.DEVICE_ID) String deviceId,
                                          @Header(Constants.ACCESS_TOKEN) String accessToken,
                                          @Field(Constants.PAGE) int page);

    @POST(API_LOGOUT)
    Call<LoginResponse> logout(@Header(Constants.LANG) String language,
                               @Header(Constants.DEVICE_ID) String deviceId,
                               @Header(Constants.ACCESS_TOKEN) String accessToken);

    @FormUrlEncoded
    @POST(API_GET_LIST_MESSAGE)
    Call<ListMessageInfo> requestListMessage(
            @Header("Lang") String language,
            @Header("DeviceId") String deviceId,
            @Header("AccessToken") String accessToken,
            @Field("room_id") String room_id,
            @Field("page") String page,
            @Field("message_id") String message_id);

    @FormUrlEncoded
    @POST(API_MESSAGE_SEEN)
    Call<Message> seenMessenger(
            @Header("Lang") String language,
            @Header("DeviceId") String deviceId,
            @Header("AccessToken") String accessToken,
            @Field("id") String id);

    @FormUrlEncoded
    @POST(API_GET_PENDING_LIST)
    Call<BaseResult<FriendList>> getPendingList(@Header(Constants.LANG) String language,
                                                @Header(Constants.DEVICE_ID) String deviceId,
                                                @Header(Constants.ACCESS_TOKEN) String accessToken,
                                                @Field(Constants.PAGE) int page);

    @FormUrlEncoded
    @POST(API_GET_REQUESTING_LIST)
    Call<BaseResult<FriendList>> getRequestingList(@Header(Constants.LANG) String language,
                                                        @Header(Constants.DEVICE_ID) String deviceId,
                                                        @Header(Constants.ACCESS_TOKEN) String accessToken,
                                                        @Field(Constants.PAGE) int page);

    @FormUrlEncoded
    @POST(API_GET_ROOM_LIST)
    Call<BaseResult<FriendList>> getRoomList(@Header(Constants.LANG) String language,
                                                  @Header(Constants.DEVICE_ID) String deviceId,
                                                  @Header(Constants.ACCESS_TOKEN) String accessToken,
                                                  @Field(Constants.PAGE) int page,
                                                  @Field(Constants.ORDER) String order);

    @FormUrlEncoded
    @POST(API_GET_FRIEND_LIST)
    Call<BaseResult<FriendList>> getFriendList(@Header(Constants.LANG) String language,
                                                    @Header(Constants.DEVICE_ID) String deviceId,
                                                    @Header(Constants.ACCESS_TOKEN) String accessToken,
                                                    @Field(Constants.PAGE) int page,
                                                    @Field(Constants.ORDER) String order);

    @FormUrlEncoded
    @POST(API_GET_BLOCKED_LIST)
    Call<BaseResult<FriendList>> getBlockedList(@Header(Constants.LANG) String language,
                                                     @Header(Constants.DEVICE_ID) String deviceId,
                                                     @Header(Constants.ACCESS_TOKEN) String accessToken,
                                                     @Field(Constants.PAGE) int page);
}
