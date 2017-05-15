package ominext.com.echo.utils;

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

/**
 * Created by Vinh on 10/28/2016.
 */

public class DialogUtils {

    public static void showAlertDialog(final Context context, String title, String content, final OnDismissListener dismissListener) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);

        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissListener.onDismiss(dialog);
            }
        });

        dialog.show();
    }

    public static void showAlertDialog(final Context context, String content, final OnDismissListener dismissListener) {
        showAlertDialog(context, null, content, dismissListener);
    }

    public static void showAlertDialog(final Context context, String title, String content) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);

        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showAlertDialog(final Context context, String content) {
        showAlertDialog(context, null, content);
    }

    public static void showConfirmationDialog(final Context context, String title, String content,
                                              String leftButtonText, String rightButtonText,
                                              final OnPressListener pressListener) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.confirmation_dialog_layout);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final Button btnLeft = (Button) dialog.findViewById(R.id.btn_left);
        btnLeft.setText(leftButtonText);
        final Button btnRight = (Button) dialog.findViewById(R.id.btn_right);
        btnRight.setText(rightButtonText);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressListener.onPressLeftButton(dialog);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressListener.onPressRightButton(dialog);
            }
        });

        dialog.show();
    }

    public static void showConfirmationDialog(final Context context, String content,
                                              String leftButtonText, String rightButtonText,
                                              final OnPressListener pressListener) {
        showConfirmationDialog(context, null, content, leftButtonText, rightButtonText, pressListener);
    }
}
