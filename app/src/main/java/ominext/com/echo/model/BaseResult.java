package ominext.com.echo.model;

/**
 * Created by Vinh on 10/28/2016.
 */

public class BaseResult<T> extends BaseModel {

    private int error;
    private String message;
    private T data;
    private ResponseDetail detail;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseDetail getDetail() {
        return detail;
    }

    public void setDetail(ResponseDetail detail) {
        this.detail = detail;
    }
}
