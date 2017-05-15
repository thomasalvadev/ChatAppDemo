package ominext.com.echo.fragment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;
import ominext.com.echo.R;
import ominext.com.echo.adapter.RecyclerViewAdapter;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.timeline.Post;
import ominext.com.echo.model.timeline.TimelineResponse;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.DialogUtils;

/**
 * Created by LuongHH on 11/1/2016.
 */

public class TimeLineFragment extends BaseFragment {
    private final String TAG = TimeLineFragment.class.getSimpleName();
    private final int visibleThreshold = 5;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ObservableList<Post> mPostList;
    private RecyclerViewAdapter mPostAdapter;

    private boolean isLoadingMore;
    private String mDeviceId, mLanguage, mAccessToken;
    private int mPageIndex;
    private int lastVisibleItem, totalItemCount;

    public static TimeLineFragment getInstance() {
        return new TimeLineFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mLanguage = Locale.getDefault().getLanguage();
        ConfigManager configManager = new ConfigManager(getContext());
        mAccessToken = configManager.getUserInfoShared(Constants.KEY_USER).getAccessToken();
        mPageIndex = 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);

        mPostList = new ObservableArrayList();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_post);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPostAdapter = new RecyclerViewAdapter(getContext(), mPostList);
        mRecyclerView.setAdapter(mPostAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoadingMore && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        // fetch new data
                        loadMorePosts();
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPosts(true);
            }
        });

        getAllPosts(false);

        return view;
    }

    private void getAllPosts(final boolean isRefresh) {
        if (isLoadingMore)
            return;
        if (!isRefresh)
            showProgress();

        mPageIndex = 1;
        HttpUtil.getInstance(getContext()).getAllTimeLine(TAG, mLanguage, mDeviceId, mAccessToken, mPageIndex, new OnResponseSuccess<TimelineResponse, String>() {

            @Override
            public void onResponseSuccess(String tag, TimelineResponse response, String extraData) {
                if (isRefresh)
                    mSwipeRefreshLayout.setRefreshing(false);
                else
                    dismissDialog();
                int errorCode = response.getError();
                if (errorCode == 0) {
                    mPageIndex++;
                    mPostList.clear();
                    mPostList.addAll(response.getData().getListPost());
                    Toast.makeText(getContext(), "Load all - " + response.getData().getListPost().size(), Toast.LENGTH_LONG).show();
//                    mPostAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, response.getMessage());
                    DialogUtils.showAlertDialog(getContext(), response.getDetail().getTitle(), response.getDetail().getMessage());
                }
            }

            @Override
            public void onResponseError(String tag, String message) {
                Log.i(TAG, message);
                if (isRefresh)
                    mSwipeRefreshLayout.setRefreshing(false);
                else
                    dismissDialog();

                DialogUtils.showAlertDialog(getContext(), getString(R.string.error), message);
            }
        });
    }

    private void loadMorePosts() {
        isLoadingMore = true;
        // add progressbar (item loading)
        mPostList.add(null);
        mPostAdapter.notifyItemInserted(mPostList.size() - 1);

        HttpUtil.getInstance(getContext()).getAllTimeLine(TAG, mLanguage, mDeviceId, mAccessToken, mPageIndex, new OnResponseSuccess<TimelineResponse, String>() {

            @Override
            public void onResponseSuccess(String tag, TimelineResponse response, String extraData) {
                // remove progressbar (item loading)
                mPostList.remove(mPostList.size() - 1);
//                mPostAdapter.notifyItemRemoved(mPostList.size());
                isLoadingMore = false;
                int errorCode = response.getError();
                if (errorCode == 0) {
                    mPageIndex++;
                    mPostList.addAll(response.getData().getListPost());
                    Toast.makeText(getContext(), "Load more - " + response.getData().getListPost().size(), Toast.LENGTH_LONG).show();
//                    mPostAdapter.notifyItemRangeInserted(mPageSize * mPageIndex - 1, mPageSize);
                    ;
                } else {
                    Log.i(TAG, response.getMessage());
                    DialogUtils.showAlertDialog(getContext(), response.getDetail().getTitle(), response.getDetail().getMessage());
                }
            }

            @Override
            public void onResponseError(String tag, String message) {
                // remove progressbar (item loading)
                mPostList.remove(mPostList.size() - 1);
//                mPostAdapter.notifyItemRemoved(mPostList.size());
                isLoadingMore = false;
                DialogUtils.showAlertDialog(getContext(), getString(R.string.error), message);
                Log.i(TAG, message);
            }
        });
    }
}
