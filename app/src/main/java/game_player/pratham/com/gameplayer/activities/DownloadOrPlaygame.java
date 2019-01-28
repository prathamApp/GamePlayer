package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;

public class DownloadOrPlaygame extends AppCompatActivity {

    Context context;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_or_playgame);
        ButterKnife.bind(this);
        context = DownloadOrPlaygame.this;
        //textToSpeechCustom=new TextToSpeechCustom(context,0.7f);
    }

    @OnClick(R.id.download)
    public void downloaData() {
        Intent intent = new Intent(context, DownloadData.class);
        startActivity(intent);
    }

    @OnClick(R.id.play)
    public void playGame() {
        Intent intent = new Intent(context, PlayGame.class);
        startActivity(intent);
    }
}
