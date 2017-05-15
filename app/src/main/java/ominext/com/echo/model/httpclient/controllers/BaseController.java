package ominext.com.echo.model.httpclient.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import ominext.com.echo.R;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.dialog.NoConnectionDialog;
import ominext.com.echo.listeners.OnResponseListener;
import ominext.com.echo.listeners.OnSuccessListener;
import ominext.com.echo.model.BaseResult;
import ominext.com.echo.model.ResponseDetail;
import ominext.com.echo.model.httpclient.APIService;
import ominext.com.echo.model.httpclient.HttpUtil;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Connectivity;
import ominext.com.echo.utils.Constants;
import ominext.com.echo.utils.DialogUtils;
import ominext.com.echo.utils.EchoApplication;
import ominext.com.echo.utils.OnDismissListener;
import ominext.com.echo.utils.ProgressDialogUtils;
import ominext.com.echo.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinh on 10/28/2016.
 */

public abstract class BaseController<T> {

    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected APIService mApiService;
    protected ConfigManager mConfigManager;

    public BaseController(Context context) {
        mContext = context;
        mApiService = HttpUtil.getInstance(mContext).getService();
        mProgressDialog = ProgressDialogUtils.create(mContext);
        mConfigManager = new ConfigManager(mContext);
    }

    public void callApi(Call<BaseResult<T>> call, final OnResponseListener responseListener, boolean showProgressDialog, final boolean finishOnFailure) {
        if (Connectivity.isConnected(mContext)) {
            if (showProgressDialog) {
                mProgressDialog.show();
            }
            call.enqueue(new Callback<BaseResult<T>>() {
                @Override
                public void onResponse(Call<BaseResult<T>> call, Response<BaseResult<T>> response) {
                    if (response.isSuccessful()) {
                        try {

                            responseListener.onSuccess(response.body());
                        }catch (Exception e)
                        {}
                    } else {
                        try {
                            Gson gson = new Gson();
                            String jsonString = response.errorBody().string();
                            BaseResult result = gson.fromJson(jsonString, BaseResult.class);
                            responseListener.onError(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            DialogUtils.showAlertDialog(mContext, response.raw().message(), new OnDismissListener() {
                                @Override
                                public void onDismiss(Dialog dialog) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                        }
                    }
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<BaseResult<T>> call, Throwable t) {
                    if (finishOnFailure) {
                        showErrorMessageAndFinish();
                    }
                }
            });
        } else {
            NoConnectionDialog.getInstance(mContext).show();
        }
    }

    public void callApi(Call<BaseResult<T>> call, final OnResponseListener responseListener, boolean showProgressDialog) {
        callApi(call, responseListener, showProgressDialog, true);
    }

    public void callApi(Call<BaseResult<T>> call, final OnResponseListener responseListener) {
        callApi(call, responseListener, true, true);
    }

    public void callApi(Call<T> call, final OnSuccessListener<T> successListener) {
        if (Connectivity.isConnected(mContext)) {
            mProgressDialog.show();
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful()) {
                        successListener.onSuccess(response.body());
                        mProgressDialog.dismiss();
                    } else {
                        showErrorMessageAndFinish();
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    showErrorMessageAndFinish();
                }
            });
        } else {
            NoConnectionDialog.getInstance(mContext).show();
        }
    }

    public void finish() {
        if (mContext != null && mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }

    private void showErrorMessageAndFinish() {
        mProgressDialog.dismiss();
        DialogUtils.showAlertDialog(mContext, mContext.getString(R.string.error_message), new OnDismissListener() {
            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
                finish();
            }
        });
    }

    public void showNotification(ResponseDetail detail) {
        showNotification(detail, true, null);
    }

    public void showNotification(ResponseDetail detail, OnDismissListener dismissListener) {
        showNotification(detail, true, dismissListener);
    }

    public void showNotification(ResponseDetail detail, boolean finish) {
        showNotification(detail, finish, null);
    }

    public void showNotification(ResponseDetail detail, final boolean finish, final OnDismissListener dismissListener) {
        DialogUtils.showAlertDialog(mContext, detail.getTitle(), detail.getMessage(), new OnDismissListener() {
            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
                if (finish) {
                    finish();
                }
                if (dismissListener != null) {
                    dismissListener.onDismiss(dialog);
                }
            }
        });
    }

    public void showSuccessMessage(ResponseDetail detail) {
        DialogUtils.showAlertDialog(mContext, detail.getTitle(), detail.getMessage(), new OnDismissListener() {
            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
            }
        });
    }

    public void showSuccessMessage(ResponseDetail detail, final OnSuccessListener successListener) {
        DialogUtils.showAlertDialog(mContext, detail.getTitle(), detail.getMessage(), new OnDismissListener() {
            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
                successListener.onSuccess(null);
            }
        });
    }
}
