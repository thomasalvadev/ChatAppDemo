package ominext.com.echo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by LuongHH on 11/11/2016.
 */

public class KeyboardEditText extends EditText {

    private KeyboardListener keyboardListener;

    public KeyboardEditText(Context context) {
        super(context);
    }

    public KeyboardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            if (keyboardListener != null)
                keyboardListener.setKeyboardListener();
        }
        return super.dispatchKeyEvent(event);
    }

    public void setKeyboardListener(KeyboardListener listener) {
        keyboardListener = listener;
    }

    public interface KeyboardListener {
        void setKeyboardListener();
    }

}
