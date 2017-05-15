package ominext.com.echo.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;

import ominext.com.echo.R;
import ominext.com.echo.activity.ChatActivity;
import ominext.com.echo.databinding.ItemPostBinding;
import ominext.com.echo.model.timeline.Post;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;

/**
 * Created by Ngh on 11/26/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context mContext;
    private ObservableList<Post> mList;

    private int mUserId;

    ObservableList.OnListChangedCallback<ObservableList<Post>> callback = new ObservableList.OnListChangedCallback<ObservableList<Post>>() {
        @Override
        public void onChanged(ObservableList<Post> sender) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<Post> sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList<Post> sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList<Post> sender, int fromPosition, int toPosition, int itemCount) {
            notifyItemRangeRemoved(fromPosition, itemCount);
            notifyItemRangeInserted(toPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<Post> sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    };

    public RecyclerViewAdapter(Context context, ObservableList<Post> postList) {
        this.mList = postList;
        this.mContext = context;
        this.mList.addOnListChangedCallback(callback);
        ConfigManager configManager = new ConfigManager(context);
        mUserId = configManager.getUserInfoShared(Constants.KEY_USER).getId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View itemView;
        if (type == VIEW_TYPE_LOADING) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int typeView = getItemViewType(position);
        if (typeView == VIEW_TYPE_ITEM) {
            Post post = mList.get(position);
            ViewDataBinding binding = ((PostViewHolder) holder).getBinding();
            binding.setVariable(BR.post, post);
            binding.setVariable(BR.handlers, new Handlers());
            binding.executePendingBindings();
        } else if (typeView == VIEW_TYPE_LOADING) {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        PostViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public class Handlers {
        public void onAvatarClick(Post post) {
            if (mUserId == post.getUser().getId()) {
                Toast.makeText(mContext, "Oh, It's you!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}