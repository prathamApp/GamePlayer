package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.interfaces.JSListner;
import game_player.pratham.com.gameplayer.modalclass.GamesAssignedStudent;
import game_player.pratham.com.gameplayer.utils.JavaScriptInterface;

public class WebViewActivity extends AppCompatActivity implements JSListner {
    @BindView(R.id.webView)
    WebView webView;

    List<GamesAssignedStudent> gamesList;
    Context context;
    int gameIndex = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);
        context = WebViewActivity.this;
        ButterKnife.bind(this);
        gamesList = (ArrayList) getIntent().getSerializableExtra("listGames");
        loadGame();
    }

    public void callNextGame() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                String url = "file:///android_asset/games/" + gamesList.get(gameIndex).getGameName() + "/" + "index.html";
                webView.loadUrl(url);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadGame() {


        String removableStoragePath="lll";
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
        }
        Log.d("tab",removableStoragePath.toString());
        String url = "file:///android_asset/games/" + gamesList.get(gameIndex).getGameName() + "/" + "index.html";
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebContentsDebuggingEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.clearCache(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new JavaScriptInterface(context), "Android");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void playNext() {
        gameIndex++;
        callNextGame();
    }
}
