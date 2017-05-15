package ominext.com.echo.adapter.expandable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ominext.com.echo.R;
import ominext.com.echo.fragment.FriendListFragment;
import ominext.com.echo.model.friendlist.FriendList;

/**
 * Created by LuongHH on 2/3/2017.
 */

public class FriendListAdapter extends ExpCustomAdapter<FriendList> {

    private FriendListFragment mFragment;
    public List<ExpandableParentViewHolder<FriendList>> mParentViewHolders = new ArrayList<>();
    public FriendListAdapter(Context context, FriendListFragment fragment) {
        super(context);
        this.mFragment = fragment;
    }

    @Override
    protected RecyclerView.ViewHolder initParentHolder(View parentView) {
        mParentViewHolders.add(new ExpandableParentViewHolder<FriendList>(parentView, this));
        return mParentViewHolders.get(mParentViewHolders.size() - 1);
    }

    @Override
    protected RecyclerView.ViewHolder initChildHolderNoSwipe(View parentView) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder initChildHolderWithSwipe(View parentView) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void addChildren(int position, int type, List<FriendList> items, boolean canLoadMore, int dataType,
                            int currentPage) {
        FriendList itemData = mDataSet.get(position);

        if (currentPage > 1 && (type == ITEM_TYPE_GROUP || (type == ITEM_TYPE_FRIEND))) {
            Iterator<FriendList> iterator = items.iterator();
            while (iterator.hasNext()) {
                FriendList friendListModel = iterator.next();
                for (FriendList item : (List<FriendList>) itemData.getChildren()) {
                    if (friendListModel.getId() == item.getId()) {
                        Log.v("TAG", "remove " + item.getId() + " | " + friendListModel.getName() + " | " +
                                friendListModel.getNickname());
                        iterator.remove();
                    }
                }
            }
        }

        for (FriendList item : items) {
            item.setType(type);
        }

        if (currentPage == 1) {
            if (itemData.isExpand()) {
                removeAll(position + 1, getChildrenCount(itemData) - 1);
            }
            itemData.getChildren().clear();

            itemData.getChildren().addAll(items);

            if (itemData.isExpand()) {
                addAll(itemData.getChildren(), position + 1);
            }
        } else {
            int oldSize = getChildrenCount(itemData) - 1;
            removeAll(position + oldSize, 1);
            itemData.getChildren().remove(itemData.getChildren().size() - 1);

            itemData.getChildren().addAll(items);
            addAll(items, position + oldSize);
        }
    }

    class ExpandableParentViewHolder<T extends ExpandableItemData> extends UltimateRecyclerviewViewHolder {

        public RelativeLayout mContainer;
        public TextView mSectionTitle;
        public ImageView mIndicationArrow;

        public FriendListAdapter mAdapter;
        public T mItem;

        ExpandableParentViewHolder(View itemView, FriendListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            mSectionTitle = (TextView) itemView.findViewById(R.id.exp_parent_section_title);
            mIndicationArrow = (ImageView) itemView.findViewById(R.id.exp_indication_arrow);
            mContainer = (RelativeLayout) itemView.findViewById(R.id.exp_parent_container);
        }
    }
}
