package game_player.pratham.com.gameplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import game_player.pratham.com.gameplayer.fragments.InstructionFragment;
import game_player.pratham.com.gameplayer.fragments.TimerForGameSelection;

public class ShowInstructions extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_instructions);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_showInstruction,new InstructionFragment());
        fragmentTransaction.commit();
    }

    public void showDialog(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dilogMsg));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_showInstruction,new TimerForGameSelection());
                fragmentTransaction.commit();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void assignGames(View view){
        Intent intent=new Intent(this,AssignGame.class);


    }
}
