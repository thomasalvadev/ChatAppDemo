package ominext.com.echo.adapter.expandable;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

import java.util.ArrayList;
import java.util.List;

import ominext.com.echo.R;
import ominext.com.echo.model.friendlist.FriendList;

/**
 * Created by hesk on 16/7/15.
 */
public abstract class ExpCustomAdapter<T extends ExpandableItemData> extends UltimateViewAdapter {

    public static final int ITEM_TYPE_PARENT = 1001;
    public static final int ITEM_TYPE_PENDING = 1002;
    public static final int ITEM_TYPE_REQUESTING = 1003;
    public static final int ITEM_TYPE_GROUP = 1004;
    public static final int ITEM_TYPE_FRIEND = 1005;
    public static final int ITEM_TYPE_BLOCKED = 1006;

    List<T> mDataSet;
    Context mContext;

    public ExpCustomAdapter(Context context) {
        this.mContext = context;
        this.mDataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_PARENT:
                return initParentHolder(initView(parent, R.layout.rcv_friend_list_header));
//            case ITEM_TYPE_PENDING:
//                return initChildHolderNoSwipe(initView(parent, R.layout.rcv_friend_list_item_no_swipe));
//            case ITEM_TYPE_REQUESTING:
//                return initChildHolderNoSwipe(initView(parent, R.layout.rcv_friend_list_item_no_swipe));
            case ITEM_TYPE_GROUP:
                return initChildHolderWithSwipe(initView(parent, R.layout.rcv_friend_list_item_with_swipe));
            case ITEM_TYPE_FRIEND:
                return initChildHolderWithSwipe(initView(parent, R.layout.rcv_friend_list_item_with_swipe_friend));
//            case ITEM_TYPE_BLOCKED:
//                return initChildHolderNoSwipe(initView(parent, R.layout.rcv_friend_list_item_no_swipe));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_PARENT:
                T itemData = mDataSet.get(position);
                FriendListAdapter.ExpandableParentViewHolder parentViewHolder = (FriendListAdapter.ExpandableParentViewHolder) holder;
                parentViewHolder.mSectionTitle.setText(itemData.getText());
                if (itemData.isExpand()) {
                    parentViewHolder.mIndicationArrow.setRotation(90);
                } else {
                    parentViewHolder.mIndicationArrow.setRotation(0);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getAdapterItemCount() {
        return getItemCount();
    }

    public List<T> getDataSet() {
        return mDataSet;
    }

    protected abstract RecyclerView.ViewHolder initParentHolder(View parentView);
    protected abstract RecyclerView.ViewHolder initChildHolderNoSwipe(View parentView);
    protected abstract RecyclerView.ViewHolder initChildHolderWithSwipe(View parentView);

    private View initView(ViewGroup parent, final @LayoutRes int layout) {
        return LayoutInflater.from(mContext).inflate(layout, parent, false);
    }

    public void add(T text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    public void addAll(List<T> list, int position) {
        mDataSet.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    public void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    public int getCurrentPosition(String uuid) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (uuid.equalsIgnoreCase(mDataSet.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    public int getChildrenCount(T item) {
        List<Object> list = new ArrayList<>();
        printChild(item, list);
        return list.size();
    }

    private void printChild(Object item, List<Object> list) {
        list.add(item);
        if (item instanceof ExpandableItemData) {
            ExpandableItemData it = (ExpandableItemData) item;
            if (it.getChildren() != null) {
                for (int i = 0; i < it.getChildren().size(); i++) {
                    printChild(it.getChildren().get(i), list);
                }
            }
        }

    }
}
