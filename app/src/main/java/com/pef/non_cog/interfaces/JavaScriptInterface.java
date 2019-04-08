package com.pef.non_cog.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    Context mContext;
    JSListner jsListner;

    /**
     * Instantiate the interface and set the context
     */
    public JavaScriptInterface(Context c) {
        mContext = c;
        jsListner = (JSListner) c;
    }

 /*   @JavascriptInterface   // must be added for API 17 or higher
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }*/


    @JavascriptInterface   // must be added for API 17 or higher
    public void playNextGame() {
        jsListner.playNext();
    }

    @JavascriptInterface   // must be added for API 17 or higher
    public void addScore(int totalMarks, int scoredMarks, boolean isHelpTaken, String timeTaken, String startTime, String label ,boolean isGroupGame) {
        jsListner.addScore(totalMarks, scoredMarks, isHelpTaken, timeTaken, startTime, label,isGroupGame);
    }

   /* @JavascriptInterface   // must be added for API 17 or higher
    public void needHelp() {
        jsListner.playNext();
    }*/
}
