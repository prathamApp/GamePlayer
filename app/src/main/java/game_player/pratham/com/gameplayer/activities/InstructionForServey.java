package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import game_player.pratham.com.gameplayer.R;

public class InstructionForServey extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_for_servey);
        context = this;
    }

    public void openShowInstruction(View view) {
        Intent intent = new Intent(context, ShowInstructions.class);
        startActivity(intent);
    }
}
