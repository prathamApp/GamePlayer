package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.dialog.AssignGameDialog;
import game_player.pratham.com.gameplayer.dialog.InstructionDialog;
import game_player.pratham.com.gameplayer.interfaces.JSListner;
import game_player.pratham.com.gameplayer.interfaces.JavaScriptInterface;
import game_player.pratham.com.gameplayer.modalclass.GamesAssignedStudent;
import game_player.pratham.com.gameplayer.modalclass.StudentScoreDetail;
import game_player.pratham.com.gameplayer.utils.BackupDatabase;
import game_player.pratham.com.gameplayer.utils.Utility;

public class WebViewActivity extends AppCompatActivity implements JSListner {
    @BindView(R.id.webView)
    WebView webView;

    List<GamesAssignedStudent> gamesList;
    Context context;
    int gameIndex = 0;
    int groupeGameIndex = 0;
    String sessionID;
    StudentScoreDetail studentScoreDetail;
    AssignGameDialog assignGameDialog;

    ArrayList<String> groupGamelist = new ArrayList();
    boolean isDilogDisplayed = false;

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
        sessionID = Utility.getUniqueID();
        groupGamelist.add(getString(R.string.groupGame1));
        groupGamelist.add(getString(R.string.groupGame2));

        loadGame();
    }

    public void callNextGame() {

        try {
            studentScoreDetail = new StudentScoreDetail();
            if (gameIndex < gamesList.size() && gamesList.get(gameIndex).getStudId() != null) {
                String name = gamesList.get(gameIndex).getStudName();
                if (name != null) {
                    String textMsg = getString(R.string.playgamedilogMsg1) + "<b><I><color='#4CAF50'>" + gamesList.get(gameIndex).getStudName() + "</b></I></color>" + getResources().getString(R.string.playgamedilogMsg2);
                    assignGameDialog = new AssignGameDialog(this, textMsg, "Instruction");
                    assignGameDialog.show();
                    Button btn_ok = assignGameDialog.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            assignGameDialog.dismiss();
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    String url = "file:///android_asset/games/" + gamesList.get(gameIndex).getGameName() + "/" + "index.html";
                                    webView.loadUrl(url);
                                }
                            });
                        }
                    });


                 /*   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getResources().getString(R.string.playgamedilogMsg1) + gamesList.get(gameIndex).getStudName() + getResources().getString(R.string.playgamedilogMsg2));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    String url = "file:///android_asset/games/" + gamesList.get(gameIndex).getGameName() + "/" + "index.html";
                                    webView.loadUrl(url);
                                }
                            });
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();*/
                }
            } else {
                if (!isDilogDisplayed) {
                    isDilogDisplayed = true;
                    final InstructionDialog instructionDialog = new InstructionDialog(this, getResources().getString(R.string.servey5));
                    instructionDialog.show();
                    ImageView imageView = instructionDialog.findViewById(R.id.nextBtn);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            instructionDialog.dismiss();
                        }
                    });
                }
                if (groupeGameIndex < groupGamelist.size()) {
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            String url = "file:///android_asset/games/" + groupGamelist.get(groupeGameIndex) + "/" + "index.html";
                            webView.loadUrl(url);
                            groupeGameIndex++;
                        }
                    });
                } else {
                 /*   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("all games are over");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intent = new Intent(context, PlayGame.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();*/
                    assignGameDialog = new AssignGameDialog(this, "Well done !!!", "Instruction");
                    assignGameDialog.show();
                    Button btn_ok = assignGameDialog.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            Intent intent = new Intent(context, PlayGame.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            assignGameDialog.dismiss();
                        }
                    });
                }

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadGame() {
       /* String removableStoragePath="lll";
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
        }
        Log.d("tab",removableStoragePath.toString());*/
        /*String url = "file:///android_asset/games/" + gamesList.get(gameIndex).getGameName() + "/" + "index.html";
        webView.loadUrl(url);*/
        callNextGame();
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



    /*@Override
    public void needHelp() {

    }*/

    @Override
    public void addScore(int totalMarks, int scoredMarks, boolean isHelpTaken, String timeTaken, String startTime, String label, boolean isGroupGame) {
        try {
            if (isGroupGame) {
                 /*if it is  Group game*/
                studentScoreDetail.setGameID("Groupgame_" + (groupeGameIndex - 1));
                studentScoreDetail.setGameName(groupGamelist.get(groupeGameIndex - 1));

            } else {
                /*if it is single player game*/
                studentScoreDetail.setStudID(gamesList.get(gameIndex).getStudId());
                studentScoreDetail.setStudName(gamesList.get(gameIndex).getStudName());
                studentScoreDetail.setGameID(gamesList.get(gameIndex).getGameID());
                studentScoreDetail.setGameName(gamesList.get(gameIndex).getGameName());

            }
            studentScoreDetail.setLebel(label);
            studentScoreDetail.setHelpTaken(isHelpTaken);
            studentScoreDetail.setStartTime(startTime);
            studentScoreDetail.setTimeTaken(timeTaken);
            studentScoreDetail.setTotalMarks(totalMarks);
            studentScoreDetail.setScoredMarks(scoredMarks);
            studentScoreDetail.setSession(sessionID);
            AppDatabase.getDatabaseInstance(context).getStudentScoreDetailsDao().insertScore(studentScoreDetail);
            BackupDatabase.backup(context);
        } catch (Exception e) {
            Toast.makeText(context, "score not added check addScore parameters", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("do you want to exit")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(context, PlayGame.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


    }
}
