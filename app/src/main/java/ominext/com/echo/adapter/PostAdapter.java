package ominext.com.echo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import ominext.com.echo.R;
import ominext.com.echo.model.UserInfo;
import ominext.com.echo.model.timeline.Post;
import ominext.com.echo.utils.ConfigManager;

/**
 * Created by LuongHH on 11/1/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = PostAdapter.class.getSimpleName();

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private int mPostId;
    private int mUserId;
    private int mPostPosition;
    private int mPostUserId;

    private String mDeviceId, mLanguage, mAccessToken;

    private List<Post> mPostList;
    private Context mContext;

    public PostAdapter(Context context, List<Post> postList, String deviceId, String language, String accessToken) {
        this.mPostList = postList;
        this.mDeviceId = deviceId;
        this.mLanguage = language;
        this.mAccessToken = accessToken;
        this.mContext = context;
        ConfigManager configManager = new ConfigManager(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_LOADING) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
            final PostViewHolder holder = new PostViewHolder(itemView);

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int typeView = getItemViewType(position);
        if (typeView == VIEW_TYPE_ITEM) {
            final PostViewHolder postViewHolder = (PostViewHolder) holder;
            Context context = postViewHolder.ivUserAvatar.getContext();

            Post post = mPostList.get(position);
            UserInfo user = post.getUser();

            Glide.with(context)
                    .load(user.getAvatar())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .into(postViewHolder.ivUserAvatar);

            postViewHolder.tvCreatedTime.setText(post.getCreatedAt());
            // nickname is prefer
            if (user.getNickname().equalsIgnoreCase(""))
                postViewHolder.tvUsername.setText(user.getName());
            else
                postViewHolder.tvUsername.setText(user.getNickname());

            if (post.getUser().getId() == mUserId)
                postViewHolder.btnMenuPost.setVisibility(View.INVISIBLE);
            else
                postViewHolder.btnMenuPost.setVisibility(View.VISIBLE);

            postViewHolder.tvContent.setText(post.getContent());
            postViewHolder.tvContent.setAnimationDuration(750L);
            postViewHolder.tvContent.setInterpolator(new OvershootInterpolator());
            postViewHolder.tvContent.setExpandInterpolator(new OvershootInterpolator());
            postViewHolder.tvContent.setCollapseInterpolator(new OvershootInterpolator());
            postViewHolder.tvContent.post(new Runnable() {
                @Override
                public void run() {
                    int lineCnt = postViewHolder.tvContent.getLineCount();
                    if (lineCnt>5)
                        postViewHolder.tvReadMore.setVisibility(View.VISIBLE);
                    else postViewHolder.tvReadMore.setVisibility(View.GONE);
                }
            });
            postViewHolder.tvReadMore.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v)
                {
                    if ( postViewHolder.tvContent.isExpanded())
                    {
                        postViewHolder.tvContent.collapse();
                        postViewHolder.tvReadMore.setText(mContext.getString(R.string.read_more));
                    }
                    else
                    {
                        postViewHolder.tvContent.expand();
                        postViewHolder.tvReadMore.setText(mContext.getString(R.string.less));
                    }
                }
            });

            postViewHolder.btnLikedUsers.setText(mContext.getString(R.string.liked_users) + "(" + post.getTotalLike() + ")");
            postViewHolder.btnComment.setText(mContext.getString(R.string.comment) + "(" + post.getTotalComment() + ")");


        } else if (typeView == VIEW_TYPE_LOADING) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mPostList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivUserAvatar;
        TextView tvCreatedTime, tvUsername, tvReadMore;
        ExpandableTextView tvContent;
        TextView btnLikedUsers, btnComment;
        ImageView btnMenuPost;

        public PostViewHolder(View itemView) {
            super(itemView);
            ivUserAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar_user);
            tvCreatedTime = (TextView) itemView.findViewById(R.id.txt_time_create);
            tvUsername = (TextView) itemView.findViewById(R.id.txt_name_user);
            tvContent = (ExpandableTextView) itemView.findViewById(R.id.expandableTextView);
            tvReadMore = (TextView) itemView.findViewById(R.id.txtReadViews);
            btnLikedUsers = (TextView) itemView.findViewById(R.id.btn_liked_users);
            btnComment = (TextView) itemView.findViewById(R.id.btn_comment);
            btnMenuPost = (ImageView) itemView.findViewById(R.id.img_down);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    interface OnReportPost {
        void reportPost(String reportReason, ProgressDialog progressDialog);
    }
}
