package ominext.com.echo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ominext.com.echo.R;
import ominext.com.echo.adapter.expandable.FriendListAdapter;
import ominext.com.echo.model.friendlist.FriendList;
import ominext.com.echo.model.httpclient.APIService;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.httpclient.controllers.FriendListController;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;


/**
 * Created by LuongHH on 10/27/2016.
 */

public class FriendListFragment extends BaseFragment {

    private final String TAG = FriendListFragment.class.getSimpleName();

    @BindView(R.id.rcv_friend_list)
    UltimateRecyclerView rcvFriendList;

    private FriendListAdapter mFriendListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ConfigManager mConfigManager;
    private HashMap<Integer, FriendList> mHeaders;
    private APIService mApiService;
    private FriendListController mFriendListController;

    public static FriendListFragment getInstance() {
        return new FriendListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.bind(this,  view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mConfigManager = new ConfigManager(mContext);
        mApiService = HttpUtil.getInstance(mContext).getService();

        mFriendListAdapter = new FriendListAdapter(mContext, this);
        mHeaders = new HashMap<>();
        mHeaders.put(Constants.ROOM_LIST, FriendList.parent(mContext.getString(R.string.flf_group), new ArrayList<FriendList>()));
        mHeaders.put(Constants.FRIEND_LIST, FriendList.parent(mContext.getString(R.string.flf_friend), new ArrayList<FriendList>()));

        mFriendListAdapter.add(mHeaders.get(Constants.ROOM_LIST), Constants.ROOM_LIST);
        mFriendListAdapter.add(mHeaders.get(Constants.FRIEND_LIST), Constants.FRIEND_LIST);
        mFriendListController = new FriendListController(mContext, mFriendListAdapter, mHeaders);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rcvFriendList.setLayoutManager(mLinearLayoutManager);
        rcvFriendList.setHasFixedSize(false);
        rcvFriendList.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        addExpandableFeatures();
        initData(true);
    }

    private void addExpandableFeatures() {
        rcvFriendList.getItemAnimator().setAddDuration(100);
        rcvFriendList.getItemAnimator().setRemoveDuration(100);
        rcvFriendList.getItemAnimator().setMoveDuration(200);
        rcvFriendList.getItemAnimator().setChangeDuration(100);
    }

    private void initData(boolean showProgressDialog) {
        initRoomList(1, showProgressDialog, false);
        initFriendList(1, showProgressDialog, false);
    }

    private void initRoomList(int page, boolean showProgressDialog, boolean expandNow) {
        mFriendListController.getRoomList(page, "", showProgressDialog, expandNow);
    }

    private void initFriendList(int page, boolean showProgressDialog, boolean expandNow) {
        mFriendListController.getFriendList(page, "", showProgressDialog, expandNow);
    }
}
