package game_player.pratham.com.gameplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;

public class GameInfo extends AppCompatActivity {

    ArrayList selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        selectedStudent = (ArrayList) intent.getSerializableExtra("selectedStudent");
    }

    @OnClick(R.id.nextBtn)
    public void nextBtn() {
        Intent intent = new Intent(GameInfo.this, AssignGame.class);
        intent.putExtra("selectedStudent", selectedStudent);
        startActivity(intent);
        finish();
    }
}
