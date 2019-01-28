package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.dialog.AssignGameDialog;


public class ShowInstructions extends AppCompatActivity {

    ArrayList selectedStudent;

    @BindView(R.id.message)
    TextView message;
    AssignGameDialog assignGameDialog;

    MediaPlayer mediaPlayer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_instructions);
        ButterKnife.bind(this);
        context = this;
        message.setText(Html.fromHtml(getString(R.string.servey4)));
        Intent intent = getIntent();
        selectedStudent = (ArrayList) intent.getSerializableExtra("selectedStudent");

    }

    @OnClick(R.id.nextBtn)
    public void showDialog(View view) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.servey5dilogheading));
        builder.setMessage(getString(R.string.servey5dilogMsg));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                openNextActivity();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

        assignGameDialog = new AssignGameDialog(this, getString(R.string.servey5dilogMsg), getString(R.string.servey5dilogheading));
        assignGameDialog.show();
        mediaPlayer = MediaPlayer.create(this, R.raw.second);
        mediaPlayer.start();
        Button btn_ok = assignGameDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                openNextActivity();
                assignGameDialog.dismiss();
            }
        });
    }

    public void openNextActivity() {
        Intent intent = new Intent(this, GameInfo.class);
        intent.putExtra("selectedStudent", selectedStudent);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
