package ominext.com.echo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.com.echo.R;
import ominext.com.echo.activity.MainActivity;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.model.UserInfo;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.EchoApplication;
import ominext.com.echo.utils.Utils;


/**
 * Created by LuongHH on 10/27/2016.
 */

public class MoreFragment extends BaseFragment {

    private final String TAG = MoreFragment.class.getSimpleName();

    private String mDeviceId, mLanguage;

    public static MoreFragment getInstance() {
        return new MoreFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mLanguage = Utils.getLanguage();
        ConfigManager configManager  = new ConfigManager(getContext());
        UserInfo user = configManager.getUserInfoShared(Constants.KEY_USER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this,  view);
        return view;
    }

    @OnClick(R.id.btn_log_out)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:

                final ConfigManager configManager = new ConfigManager(getContext());
                String accessToken = configManager.getUserInfoShared(Constants.KEY_USER).getAccessToken();
                showProgress();
                HttpUtil.getInstance(getContext()).logout(TAG, mLanguage, mDeviceId, accessToken, new OnResponseSuccess<LoginResponse, String>() {

                    @Override
                    public void onResponseSuccess(String tag, LoginResponse response, String extraData) {
                        dismissDialog();
                        configManager.removeAll();
                        ((EchoApplication) (getActivity().getApplication())).releaseSocket();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }

                    @Override
                    public void onResponseError(String tag, String message) {
                        dismissDialog();
                        configManager.removeAll();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                });

                break;
        }
    }
}
