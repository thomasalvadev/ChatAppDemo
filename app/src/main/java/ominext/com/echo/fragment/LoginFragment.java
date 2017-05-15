package ominext.com.echo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.com.echo.R;
import ominext.com.echo.activity.HomeActivity;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.daofactory.UserDaoFactory;
import ominext.com.echo.model.UserInfo;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.DialogUtils;
import ominext.com.echo.utils.Utils;

/**
 * Created by LuongHH on 12/6/2016.
 */

public class LoginFragment extends BaseFragment {

    private final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.et_user_name)
    AutoCompleteTextView etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    private String mDeviceId, mLanguage;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mLanguage = Utils.getLanguage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                final String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();

                final ConfigManager configManager = new ConfigManager(getContext());
                String deviceToken = configManager.getString(Constants.KEY_DEVICE_TOKEN, "");
                showProgress();
                HttpUtil.getInstance(getContext()).login(TAG, mLanguage, mDeviceId, username, password, deviceToken, 0, Constants.ANDROID, new OnResponseSuccess<LoginResponse, String>() {

                    @Override
                    public void onResponseSuccess(String tag, LoginResponse response, String extraData) {
                        dismissDialog();
                        int errorCode = response.getError();
                        if (errorCode == 0) {
                            UserInfo userInfo = response.getUserInfo();
                            configManager.putUserInfoShared(Constants.KEY_USER, userInfo);
                            UserDaoFactory userDaoFactory = UserDaoFactory.getInstaince();
                            UserInfo user = new UserInfo();
                            user.setName(username);
                            userDaoFactory.insert(user);
                            startActivity(new Intent(getContext(), HomeActivity.class));
                            getActivity().finish();
                        } else {
                            DialogUtils.showAlertDialog(getContext(), response.getDetail().getTitle(), response.getDetail().getMessage());
                        }

                    }

                    @Override
                    public void onResponseError(String tag, String message) {
                        dismissDialog();
                        DialogUtils.showAlertDialog(getContext(), getString(R.string.error), getString(R.string.error_message));
                    }
                });
                break;
            default:
                break;
        }
    }
}
