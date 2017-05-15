package ominext.com.echo.utils;

import android.app.Application;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class EchoApplication extends Application {

    private final String LOG_TAG = EchoApplication.class.getSimpleName();

    private Socket mSocket;

    public Socket getSocket() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket(Constants.CHAT_SERVER_URL);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        // Connect if disconnected
        if (!mSocket.connected()) {
            Log.d(LOG_TAG, "================>Connecting with Socket...");
            mSocket.connect();
        } else {
            Log.d(LOG_TAG, "================>Socket Connected");
        }
        return mSocket;
    }

    public void releaseSocket() {
        if (mSocket != null) {
            try {
                if (mSocket.connected()) {
                    mSocket.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                mSocket = null;
            }
        }
    }
}

