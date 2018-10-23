package game_player.pratham.com.gameplayer.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import game_player.pratham.com.gameplayer.interfaces.JSListner;

public class JavaScriptInterface {
    Context mContext;
    JSListner jsListner;
    /** Instantiate the interface and set the context */
    public JavaScriptInterface(Context c) {
        mContext = c;
        jsListner= (JSListner) c;
    }

    @JavascriptInterface   // must be added for API 17 or higher
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface   // must be added for API 17 or higher
    public void playNextGame() {
        jsListner.playNext();
    }
}
