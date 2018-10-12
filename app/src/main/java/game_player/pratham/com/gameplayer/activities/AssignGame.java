package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.modalclass.Student;
import game_player.pratham.com.gameplayer.modalclass.StudentForSpinner;

public class AssignGame extends AppCompatActivity {
    /*@BindView(R.id.recyclerView)
    RecyclerView recyclerView;*/

    @BindView(R.id.game1Spinner)
    Spinner game1Spinner;
    @BindView(R.id.game2Spinner)
    Spinner game2Spinner;
    @BindView(R.id.game3Spinner)
    Spinner game3Spinner;
    @BindView(R.id.game4Spinner)
    Spinner game4Spinner;
    @BindView(R.id.game5Spinner)
    Spinner game5Spinner;


    ArrayList selectedStudent;
    List game1List = new ArrayList();
    List game2List = new ArrayList();
    List game3List = new ArrayList();
    List game4List = new ArrayList();
    List game5List = new ArrayList();


    List<StudentForSpinner> selectedStudentList = new ArrayList<>();
    List<StudentForSpinner> tempList = new ArrayList<>();
    Context context;
    int[] assigned = {100, 100, 100, 100, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_game);
        ButterKnife.bind(this);
        context = AssignGame.this;
        selectedStudent = (ArrayList) getIntent().getSerializableExtra("selectedStudent");
        getStudents();
        loadSpinner();
        //getGameNamesFromAssets();
    }

    private void getStudents() {
        selectedStudentList.clear();
        selectedStudentList.add(new StudentForSpinner("Select Student"));
        Student student;
        for (int i = 0; i < selectedStudent.size(); i++) {
            if (selectedStudent.get(i) != null) {
                student = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantByID(selectedStudent.get(i).toString());
                selectedStudentList.add(new StudentForSpinner(student.getStudentId(), student.getFullName()));
            }
        }
    }

    private void loadSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, selectedStudentList);
        game1Spinner.setAdapter(adapter);
        game2Spinner.setAdapter(adapter);
        game3Spinner.setAdapter(adapter);
        game4Spinner.setAdapter(adapter);
        game5Spinner.setAdapter(adapter);

        game1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    game1List.clear();
                    game1List.addAll(selectedStudentList);
                    game1List.remove(pos);
                    ArrayAdapter tempAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game1List);
                    game2Spinner.setAdapter(tempAdapter);
                    game2Spinner.setEnabled(true);

                } else {
                    game2Spinner.setSelection(0);
                    game2Spinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        game2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    game2List.clear();
                    game2List.addAll(game1List);
                    game2List.remove(pos);
                    ArrayAdapter tempAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game2List);
                    game3Spinner.setAdapter(tempAdapter);
                    game3Spinner.setEnabled(true);

                } else {
                    game3Spinner.setSelection(0);
                    game3Spinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        game3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    game3List.clear();
                    game3List.addAll(game2List);
                    game3List.remove(pos);
                    ArrayAdapter tempAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game3List);
                    game4Spinner.setAdapter(tempAdapter);
                    game4Spinner.setEnabled(true);

                } else {
                    game4Spinner.setSelection(0);
                    game4Spinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        game4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    game4List.clear();
                    game4List.addAll(game3List);
                    game4List.remove(pos);
                    ArrayAdapter tempAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game4List);
                    game5Spinner.setAdapter(tempAdapter);
                    game5Spinner.setEnabled(true);

                } else {
                    game5Spinner.setSelection(0);
                    game5Spinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        game5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void checkList(int[] assigned) {
        tempList.clear();
        tempList.addAll(selectedStudentList);
        for (int i = 0; i < assigned.length; i++) {
            if (assigned[i] != 100) tempList.remove(assigned[i]);
        }
    }


































    /* private void getGameNamesFromAssets() {
        try {
           String[] list = getAssets().list("");
            AssetManager assetManager = getResources().getAssets();
            InputStream inputStream=assetManager.open("text");
            int size=inputStream.available();
            byte buffer[]=  new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonGames=new String(buffer,"UTF-8");
           // showRecycler(jsonGames);
        } catch (IOException e) {
            Toast.makeText(this, "Game Names Json not fount in assets ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

    /*private void showRecycler(String jsonGames) {
        Gson gson=new Gson();
        TypeToken<List<GameName>> token = new TypeToken<List<GameName>>() {};
        List<GameName> animals = gson.fromJson(jsonGames, token.getType());

    }*/
}
