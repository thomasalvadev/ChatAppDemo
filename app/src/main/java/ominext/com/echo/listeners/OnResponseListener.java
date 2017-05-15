package ominext.com.echo.listeners;

import ominext.com.echo.model.BaseResult;

/**
 * Created by Vinh on 10/31/2016.
 */

public interface OnResponseListener<T> {

    void onSuccess(BaseResult<T> result);

    void onError(BaseResult result);
}
