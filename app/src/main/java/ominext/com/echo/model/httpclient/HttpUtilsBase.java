package ominext.com.echo.model.httpclient;

import ominext.com.echo.appinterface.OnResponseSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dieunv on 11/30/2016.
 */

public abstract class HttpUtilsBase<T,TE,K> {
    public void callApi(final String tag, Call<T> call, final OnResponseSuccess<T,TE> onResponseSuccess) {
        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(tag, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        onResponseSuccess.onResponseError(tag, response.raw().message());
                        showErrorMessageAndFinish(response.raw().message());
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(tag, t.getMessage());
                    showErrorMessageAndFinish(t.getMessage());
                }
            }


        });

    }

    protected abstract void showErrorMessageAndFinish(String error);
    protected abstract void showErrorMessageAndFinish(String error, K object);
}
