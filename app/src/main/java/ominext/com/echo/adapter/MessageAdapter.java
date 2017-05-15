package ominext.com.echo.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import ominext.com.echo.R;
import ominext.com.echo.model.message.MessageInfo;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.Utils;

/**
 * Created by LuongHH on 12/13/2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context mContext;
    private List<MessageInfo> mList;

    public MessageAdapter(Context context, List<MessageInfo> messages) {
        this.mContext = context;
        this.mList = messages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Constants.TYPE_MESSAGE_MYSELF:
                layout = R.layout.item_message_myself;
                break;
            case Constants.TYPE_MESSAGE_FRIEND:
                layout = R.layout.item_message_friend;
                break;
        }
        View v = LayoutInflater.from(mContext).inflate(layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageInfo message = mList.get(position);
        if (getItemViewType(position) == Constants.TYPE_MESSAGE_MYSELF) {
            bindMessageMySelf(message, holder, position);
        } else if (getItemViewType(position) == Constants.TYPE_MESSAGE_FRIEND) {
            bindMessageFriend(message, holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType_user();
    }

    private void bindMessageFriend(MessageInfo message, MyViewHolder holder, int position) {
        if (message.getStatus().equals(Constants.STATUS_ACTIVE) || message.getStatus().equals(Constants.STATUS_EDITED)) {
            holder.lblMessLayout.setVisibility(View.VISIBLE);
            bindViewFriendActive(message, holder, position);

        } else if (message.getStatus().equals(Constants.STATUS_REMOVE)) {
            holder.lblMessLayout.setVisibility(View.GONE);
            holder.mTxtTime.setVisibility(View.VISIBLE);
            // timeCreate(message, holder, position, "", false);
            setTime(message, holder);
        }
    }

    private void bindMessageMySelf(MessageInfo message, MyViewHolder holder, int position) {
        if (message.getStatus().equals(Constants.STATUS_ACTIVE) || message.getStatus().equals(Constants.STATUS_EDITED) || message.getStatus().equals(Constants.STATUS_EDITING)) {
            holder.lblMessLayout.setVisibility(View.VISIBLE);
            bindViewMySelfActive(message, holder, position);

        } else if (message.getStatus().equals(Constants.STATUS_REMOVE)) {
            holder.lblMessLayout.setVisibility(View.GONE);
            holder.mTxtTime.setVisibility(View.VISIBLE);
            //timeCreate(message, holder, position, "", true);
            setTime(message, holder);
        }
    }

    private void bindViewMySelfActive(final MessageInfo message, final MyViewHolder holder, final int position) {
       if (Integer.parseInt(message.getData().getType()) == Constants.TYPE_MESSAGE_VIEW_TEXT) {
           holder.mLlMedia.setVisibility(View.GONE);
           holder.mLblMess.setVisibility(View.VISIBLE);
           holder.mProgressbar.setVisibility(View.GONE);
           holder.mMessageContent.setText(message.getData().getContent().trim());
           holder.mMessageContent.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                           initSimpleTooltipMySelf(v, message, position);
                   return true;
               }
           });
        }
//        timeCreate(message, holder, position, checkTime(message, position), true);
        setTime(message, holder);
    }

    private void bindViewFriendActive(final MessageInfo message, final MyViewHolder holder, final int position) {
        if (Integer.parseInt(message.getData().getType()) == Constants.TYPE_MESSAGE_VIEW_TEXT) {
            holder.mLlMedia.setVisibility(View.GONE);
            holder.mImgPlay.setVisibility(View.GONE);
            holder.mProgressbar.setVisibility(View.GONE);
            holder.mLblMess.setVisibility(View.VISIBLE);
            holder.mMessageContent.setText(message.getData().getContent().trim());
            holder.mMessageContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    initSimpleTooltipMySelf(v, message, position);
                    return true;
                }
            });
            Glide.with(mContext)
                    .load(message.getSender().getAvatar())
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .centerCrop()
                    .into(holder.mImageAvatar);
        }

        // timeCreate(message, holder, position, checkTime(message, position), false);
        setTime(message, holder);
    }

    private void initSimpleTooltipMySelf(View view, final MessageInfo messageInfo, final int pos) {
        final SimpleTooltip simpleTooltip = new SimpleTooltip.Builder(mContext)
                .anchorView(view)
                .contentView(R.layout.dialog_action_message_myself)
                .arrowColor(Color.parseColor("#24283d"))
                .maxWidth(R.dimen.dimen250)
                .padding(R.dimen.flf_tooltip_padding)
                .gravity(Gravity.TOP)
                .onDismissListener(new SimpleTooltip.OnDismissListener() {
                    @Override
                    public void onDismiss(SimpleTooltip tooltip) {
                    }
                })
                .onShowListener(new SimpleTooltip.OnShowListener() {
                    @Override
                    public void onShow(SimpleTooltip tooltip) {
                        android.util.Log.v("TAG", "onShow");
                    }
                })
                .build();
        simpleTooltip.findViewById(R.id.txt_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", mList.get(pos).getData().getContent());
                clipboard.setPrimaryClip(clip);
                simpleTooltip.dismiss();
            }
        });
//        simpleTooltip.findViewById(R.id.txt_delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mEventBus.post(new UpdateMsgEvent(Constants.ROUTER, Constants.DELETE_MESSAGE, messageInfo.getId() + "", messageInfo.getData().getContent(), pos));
//                simpleTooltip.dismiss();
//            }
//        });
//        simpleTooltip.findViewById(R.id.txt_edit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mEventBus.post(new UpdateMsgEvent(Constants.ROUTER, Constants.EDIT_MESSAGE, messageInfo.getId() + "", messageInfo.getData().getContent(), pos));
//                simpleTooltip.dismiss();
//            }
//        });
        simpleTooltip.show();
    }

    private void setTime(MessageInfo message, MyViewHolder holder) {
        if (TextUtils.isEmpty(message.getMessage_time())) {
            holder.mTxtTime.setVisibility(View.GONE);
        } else {
            holder.setTime(message.getMessage_time());
            holder.mTxtTime.setVisibility(View.VISIBLE);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mMessageContent;
        RoundedImageView mImageContent;
        TextView mTxtTime;
        RoundedImageView mImageAvatar;
        LinearLayout mLblMess;
        ImageView mImgPlay;
        DonutProgress mProgressbar;
        RelativeLayout mLlMedia;
        TextView mTxtName;

        LinearLayout lblMessLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMessageContent = (TextView) itemView.findViewById(R.id.message);
            mImageContent = (RoundedImageView) itemView.findViewById(R.id.img);
            mImageAvatar = (RoundedImageView) itemView.findViewById(R.id.img_avatar);
            mTxtTime = (TextView) itemView.findViewById(R.id.txtTime);
            mTxtName = (TextView) itemView.findViewById(R.id.txtName);
            mLblMess = (LinearLayout) itemView.findViewById(R.id.lblMess);
            mImgPlay = (ImageView) itemView.findViewById(R.id.imgPlay);
            mProgressbar = (DonutProgress) itemView.findViewById(R.id.donut_progress);
            mLlMedia = (RelativeLayout) itemView.findViewById(R.id.rltImage);
            lblMessLayout = (LinearLayout) itemView.findViewById(R.id.lblMessLayout);
        }


        public void setTime(String time) {

            if (!time.equals("")) {
                mTxtTime.setText(time);
                mTxtTime.setVisibility(View.VISIBLE);
            } else {
                mTxtTime.setText("");
                mTxtTime.setVisibility(View.GONE);
            }

        }
    }
}
