package ominext.com.echo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

import ominext.com.echo.R;

/**
 * Created by LuongHH on 12/6/2016.
 */

public class BaseFragment extends Fragment {

    protected Context mContext;
    ProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void showProgress() {
        dialog = new ProgressDialog(getContext(), R.style.MyTheme);
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }
}
