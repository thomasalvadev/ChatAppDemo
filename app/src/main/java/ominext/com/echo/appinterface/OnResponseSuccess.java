package ominext.com.echo.appinterface;

/**
 * Created by Administrator on 10/27/2016.
 */

public interface OnResponseSuccess<T, TE> {
    void onResponseSuccess(String tag, T response, TE extraData);

    void onResponseError(String tag, String message);

}