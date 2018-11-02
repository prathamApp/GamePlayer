package game_player.pratham.com.gameplayer.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import game_player.pratham.com.gameplayer.R;

public class TimerForGameSelection extends Fragment {
    ProgressBar barTimer;
    TextView textTimer;
    Button ok_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barTimer=view.findViewById(R.id.barTimer);
        textTimer=view.findViewById(R.id.textTimer);
        ok_btn=view.findViewById(R.id.ok);
        startTimer(1);
        //ok_btn.setEnabled(true);
    }

    private void startTimer(final int minuti) {
        CountDownTimer countDownTimer = new CountDownTimer(60 * minuti * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                int progress=(int)(100*seconds)/60;

                barTimer.setProgress((int) progress);
                  textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                if (textTimer.getText().equals("00:00")) {
                    textTimer.setText("STOP");
                    ok_btn.setEnabled(true);
                } else {
                    textTimer.setText("2:00");
                    barTimer.setProgress(60 * minuti);
                }
            }
        }.start();
    }


}