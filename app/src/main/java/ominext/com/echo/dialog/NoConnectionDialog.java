package ominext.com.echo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ominext.com.echo.R;
import ominext.com.echo.listeners.OnDismissListener;

/**
 * Created by Vinh on 12/8/2016.
 */

public class NoConnectionDialog {

    private static NoConnectionDialog mInstance;
    private static Dialog mDialog;

    private NoConnectionDialog() {

    }

    public static NoConnectionDialog getInstance(Context context) {
        if (mInstance == null) {
            init(context, context.getString(R.string.no_connection_title), context.getString(R.string.no_connection_message), new OnDismissListener() {
                @Override
                public void onDismiss(Dialog dialog) {
                    dialog.dismiss();
                    mDialog = null;
                    mInstance = null;
                }
            });
        }
        return mInstance;
    }

    private static void init(final Context context, String title, String content, final OnDismissListener dismissListener) {
        mDialog = new Dialog(context);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDialog.setContentView(R.layout.alert_dialog_layout);
        mDialog.setCancelable(false);
        TextView tvTitle = (TextView) mDialog.findViewById(R.id.title_text_view);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        TextView tvMessage = (TextView) mDialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);

        mDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final Button btnOk = (Button) mDialog.findViewById(R.id.ok_button);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissListener.onDismiss(mDialog);
            }
        });

        mInstance = new NoConnectionDialog();
    }

    public void show() {
        if (mInstance != null && mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }
}
