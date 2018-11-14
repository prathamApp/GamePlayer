package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.modalclass.StudentForSpinner;

public class InstructionForServey extends AppCompatActivity {
    Context context;
    ArrayList<StudentForSpinner> selectedStudent;

    @BindView(R.id.spiner_leaderName)
    Spinner spiner_leaderName;

    @BindView(R.id.next_button)
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_for_servey);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        selectedStudent = (ArrayList) intent.getSerializableExtra("selectedStudent");
        nextButton.setEnabled(false);
        loadLeaderSpinner();
    }

    private void loadLeaderSpinner() {
        selectedStudent.add(0, new StudentForSpinner("select student"));
        ArrayAdapter leaderSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, selectedStudent);
        leaderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_leaderName.setAdapter(leaderSpinnerAdapter);
        spiner_leaderName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    nextButton.setEnabled(true);

                } else {
                    nextButton.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void openShowInstruction(View view) {
        Intent intent = new Intent(context, ShowInstructions.class);
        intent.putExtra("selectedStudent", selectedStudent);
        startActivity(intent);
    }
}
