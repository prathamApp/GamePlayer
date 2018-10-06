package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import game_player.pratham.com.gameplayer.R;

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        pullStudents();
    }

    private void pullStudents() {

    }

    public void nextActivity(View view) {
        Intent intent=new Intent(context,InstructionForServey.class);
        startActivity(intent);
    }
}
