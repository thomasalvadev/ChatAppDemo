package ominext.com.echo.model.httpclient.controllers;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import ominext.com.echo.adapter.expandable.FriendListAdapter;
import ominext.com.echo.listeners.OnResponseListener;
import ominext.com.echo.model.BaseResult;
import ominext.com.echo.model.ResponseDetail;
import ominext.com.echo.model.friendlist.FriendList;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.DeviceUtils;
import ominext.com.echo.utils.Utils;
import retrofit2.Call;

import static ominext.com.echo.adapter.expandable.ExpCustomAdapter.ITEM_TYPE_FRIEND;
import static ominext.com.echo.adapter.expandable.ExpCustomAdapter.ITEM_TYPE_GROUP;

/**
 * Created by Vinh on 11/8/2016.
 */

public class FriendListController extends BaseController {

    private FriendListAdapter mFriendListAdapter;
    private Map<Integer, FriendList> mHeaders;

    private int mCount;

    public FriendListController(Context context, FriendListAdapter friendListAdapter, Map<Integer, FriendList> headers) {
        super(context);
        mFriendListAdapter = friendListAdapter;
        mHeaders = headers;
    }

    public void getRoomList(final int page, String order, final boolean showProgressDialog, final boolean expandNow) {
        Call<BaseResult<FriendList>> call = mApiService.getRoomList(Utils.getLanguage(), DeviceUtils.getDeviceId(mContext),
                mConfigManager.getUserInfoShared(Constants.KEY_USER).getAccessToken(), page, order);
        callApi(call, new OnResponseListener<FriendList>() {

            @Override
            public void onSuccess(BaseResult<FriendList> result) {
//                if (page == 1) {
//                    mFriendListAdapter.resetNewRoomIds();
//                }

                mFriendListAdapter.addChildren(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.ROOM_LIST).getUuid()),
                        ITEM_TYPE_GROUP, result.getData().getChildren(),
                        canLoadMore(result.getData().getCount(), page),
                        Constants.ROOM_LIST, page);
                mCount++;
                FriendList room = mFriendListAdapter.getDataSet().get(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.ROOM_LIST).getUuid()));
//                if (expandNow) { // notification
//                    if (!room.isExpand()) {
//                        mFriendListAdapter.mParentViewHolders.get(Constants.ROOM_LIST).toggleExpandable(room,
//                                mFriendListAdapter.imageSetLoadItems, 0);
//                    }
//                }
            }

            @Override
            public void onError(BaseResult result) {
                showNotification(result.getDetail(), false);
            }
        }, (page == 1 && showProgressDialog));
    }

    public void getRoomList(final int page, String order) {
        getRoomList(page, order, true, false);
    }

    public void getFriendList(final int page, String order, final boolean showProgressDialog, final boolean expandNow) {
        Call<BaseResult<FriendList>> call = mApiService.getFriendList(Utils.getLanguage(), DeviceUtils.getDeviceId(mContext),
                mConfigManager.getUserInfoShared(Constants.KEY_USER).getAccessToken(), page, order);
        callApi(call, new OnResponseListener<FriendList>() {

            @Override
            public void onSuccess(BaseResult<FriendList> result) {
//                if (page == 1) {
//                    mFriendListAdapter.resetNewFriendIds();
//                }

                mFriendListAdapter.addChildren(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.FRIEND_LIST).getUuid()),
                        ITEM_TYPE_FRIEND, result.getData().getChildren(),
                        canLoadMore(result.getData().getCount(), page),
                        Constants.FRIEND_LIST, page);
                mCount++;

                FriendList friend = mFriendListAdapter.getDataSet().get(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.FRIEND_LIST).getUuid()));
//                if (expandNow) { // notification
//                    if (!friend.isExpand()) {
//                        mFriendListAdapter.mParentViewHolders.get(Constants.FRIEND_LIST).toggleExpandable(friend,
//                                mFriendListAdapter.imageSetLoadItems, 0);
//                    }
//                }
            }

            @Override
            public void onError(BaseResult result) {
                showNotification(result.getDetail(), false);
            }
        }, (page == 1 && showProgressDialog));
    }

    public void getFriendList(final int page, String order) {
        getFriendList(page, order, true, false);
    }

//    public void getBlockedList(final int page, final boolean showProgressDialog, final boolean expandNow) {
//        Call<BaseResult<FriendList>> call = mApiService.getBlockedList(Utils.getLanguage(), DeviceUtils.getDeviceId(mContext),
//                mConfigManager.getUserInfoShared(Constants.KEY_USER).getAccessToken(), page);
//        callApi(call, new OnResponseListener<FriendList>() {
//
//            @Override
//            public void onSuccess(BaseResult<FriendList> result) {
//                Log.v("TAG-Blocked", String.valueOf(result.getData().getChildren().size()));
//                mFriendListAdapter.addChildren(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.BLOCKED_LIST).getUuid()),
//                        BaseExpandableAdapter.ExpandableViewTypes.ITEM_TYPE_BLOCKED, result.getData().getChildren(),
//                        canLoadMore(result.getData().getCount(), page),
//                        Constants.BLOCKED_LIST, page);
//                mCount++;
//
//                if (expandNow) {
//                    FriendList blocked = mFriendListAdapter.getDataSet().get(mFriendListAdapter.getCurrentPosition(mHeaders.get(Constants.BLOCKED_LIST).getUuid()));
//                    if (!blocked.isExpand()) {
//                        try {
//                            mFriendListAdapter.mParentViewHolders.get(Constants.BLOCKED_LIST).toggleExpandable(blocked,
//                                    mFriendListAdapter.imageSetLoadItems, 0);
//                        }catch (Exception e)
//                        {
//
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onError(BaseResult result) {
//                showNotification(result.getDetail(), false);
//            }
//        }, (page == 1 && showProgressDialog));
//    }

//    public void getBlockedList(final int page) {
//        getBlockedList(page, true, false);
//    }

    private boolean canLoadMore(int count, int currentPage) {
        return currentPage < Math.ceil((double) count / Constants.NUMBER_OF_ITEMS_PER_LOADING);
    }

    private void delayToShowSuccessMessage(final ResponseDetail detail, final int threshold) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int maxRetry = 500;
                for (int i = 0; i < maxRetry; i++) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mCount >= threshold) {
                        break;
                    }
                }
                if (mContext instanceof Activity) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showSuccessMessage(detail);
                        }
                    });
                }
            }
        }).start();
    }

//    public void getPostDetail(int postId) {
//        Call<BaseResult<Post>> call = mApiService.getPostDetail(Utils.getLanguage(), DeviceUtils.getDeviceId(mContext),
//                mConfigManager.getUserInfoShared(Constants.KEY_USER).getAccessToken(), postId);
//        callApi(call, new OnResponseListener<Post>() {
//
//            @Override
//            public void onSuccess(BaseResult<Post> result) {
//                mFriendListAdapter.showPostDetail(result.getData());
//            }
//
//            @Override
//            public void onError(BaseResult result) {
//                showNotification(result.getDetail(), false);
//            }
//        }, false);
//    }
}
