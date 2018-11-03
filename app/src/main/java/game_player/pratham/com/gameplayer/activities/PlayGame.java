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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.interfaces.StudentListLisner;
import game_player.pratham.com.gameplayer.utils.BackupDatabase;

public class PlayGame extends AppCompatActivity implements StudentListLisner {
    Context context;
    @BindView(R.id.studentSpinner)
    Spinner studentSpinner;
    @BindView(R.id.villageSpinner)
    Spinner villageSpinner;
    @BindView(R.id.groupSpinner)
    Spinner groupSpinner;
    @BindView(R.id.btn_next)
    Button btn_next;

    ArrayList selectedStudent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = PlayGame.this;
        BackupDatabase.backup(context);
        int studCount = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantCount();
        if (studCount <= 0) {
            // pullStudents();
        } else {
            loadSpiner();
        }


    }

   /* private void pullStudents() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Utility.getProperty("studentPull", this);
        AndroidNetworking.get(url).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                String json = response.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Student>>() {
                }.getType();
                List<Student> studentList = gson.fromJson(json, listType);
                if (studentList != null) {
                    AppDatabase.getDatabaseInstance(context).getStudentDao().insertAllStudent(studentList);
                }
                loadSpiner();
                progressDialog.dismiss();
            }

            @Override
            public void onError(ANError error) {
                progressDialog.dismiss();
            }
        });
    }*/

    public void nextActivity(View view) {
        Intent intent = new Intent(context, InstructionForServey.class);

        intent.putExtra("selectedStudent", selectedStudent);
        startActivity(intent);
    }


    private void loadSpiner() {
        List<String> block = new ArrayList<>();
        block.add("Select block");
        block.addAll(AppDatabase.getDatabaseInstance(context).getVillageDao().getUniqBlockNames());
        if (!block.isEmpty()) {
            ArrayAdapter blockAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, block);
            blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            villageSpinner.setAdapter(blockAdapter);
            villageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    if(pos>0){


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        /*List studList = AppDatabase.getDatabaseInstance(context).getStudentDao().getAllStudant();
        // ArrayAdapter arrayAdapter=new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,studList);
        // studentSpinner.setAdapter(arrayAdapter);
        Dialog studentDialog = new SelectVillageDialog(context, studList);
        studentDialog.show();*/
    }

    @Override
    public void getSelectedVillage(ArrayList list) {
        if (list.size() >= 5) {
            selectedStudent = list;
            btn_next.setEnabled(true);
        } else {
            Toast.makeText(context, "Must Be select 5 student", Toast.LENGTH_SHORT).show();
        }
    }


}
