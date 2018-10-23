package game_player.pratham.com.gameplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.modalclass.GamesAssignedStudent;
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
    // List game5List = new ArrayList();

    GamesAssignedStudent gamesAssignedStudent1 = new GamesAssignedStudent();
    GamesAssignedStudent gamesAssignedStudent2 = new GamesAssignedStudent();
    GamesAssignedStudent gamesAssignedStudent3 = new GamesAssignedStudent();
    GamesAssignedStudent gamesAssignedStudent4 = new GamesAssignedStudent();
    GamesAssignedStudent gamesAssignedStudent5 = new GamesAssignedStudent();

    List<StudentForSpinner> selectedStudentList = new ArrayList<>();
    ArrayList<GamesAssignedStudent> selectedStudentWithGame = new ArrayList();

    Context context;

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
                    gamesAssignedStudent1.setGameName(context.getResources().getString(R.string.game1));
                    gamesAssignedStudent1.setGameID("1");
                    gamesAssignedStudent1.setStudName(((StudentForSpinner) adapterView.getSelectedItem()).getStudName());
                    gamesAssignedStudent1.setStudId(((StudentForSpinner) adapterView.getSelectedItem()).getStudId());


                    game1List.clear();
                    game1List.addAll(selectedStudentList);
                    game1List.remove(pos);
                    ArrayAdapter game2Adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game1List);
                    game2Spinner.setAdapter(game2Adapter);
                    game2Spinner.setEnabled(true);

                } else {

                    gamesAssignedStudent1.setGameName(null);
                    gamesAssignedStudent1.setGameID(null);
                    gamesAssignedStudent1.setStudName(null);
                    gamesAssignedStudent1.setStudId(null);

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
                    gamesAssignedStudent2.setGameName(context.getResources().getString(R.string.game2));
                    gamesAssignedStudent2.setGameID("2");
                    gamesAssignedStudent2.setStudName(((StudentForSpinner) adapterView.getSelectedItem()).getStudName());
                    gamesAssignedStudent2.setStudId(((StudentForSpinner) adapterView.getSelectedItem()).getStudId());


                    game2List.clear();
                    game2List.addAll(game1List);
                    game2List.remove(pos);
                    ArrayAdapter game3Adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game2List);
                    game3Spinner.setAdapter(game3Adapter);
                    game3Spinner.setEnabled(true);

                } else {
                    game3Spinner.setSelection(0);
                    game3Spinner.setEnabled(false);

                    gamesAssignedStudent2.setGameName(null);
                    gamesAssignedStudent2.setGameID(null);
                    gamesAssignedStudent2.setStudName(null);
                    gamesAssignedStudent2.setStudId(null);

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

                    gamesAssignedStudent3.setGameName(context.getResources().getString(R.string.game3));
                    gamesAssignedStudent3.setGameID("3");
                    gamesAssignedStudent3.setStudName(((StudentForSpinner) adapterView.getSelectedItem()).getStudName());
                    gamesAssignedStudent3.setStudId(((StudentForSpinner) adapterView.getSelectedItem()).getStudId());

                    game3List.clear();
                    game3List.addAll(game2List);
                    game3List.remove(pos);
                    ArrayAdapter game4Adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game3List);
                    game4Spinner.setAdapter(game4Adapter);
                    game4Spinner.setEnabled(true);

                } else {
                    game4Spinner.setSelection(0);
                    game4Spinner.setEnabled(false);

                    gamesAssignedStudent3.setGameName(null);
                    gamesAssignedStudent3.setGameID(null);
                    gamesAssignedStudent3.setStudName(null);
                    gamesAssignedStudent3.setStudId(null);

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
                    gamesAssignedStudent4.setGameName(context.getResources().getString(R.string.game4));
                    gamesAssignedStudent4.setGameID("4");
                    gamesAssignedStudent4.setStudName(((StudentForSpinner) adapterView.getSelectedItem()).getStudName());
                    gamesAssignedStudent4.setStudId(((StudentForSpinner) adapterView.getSelectedItem()).getStudId());

                    game4List.clear();
                    game4List.addAll(game3List);
                    game4List.remove(pos);
                    ArrayAdapter game5Adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, game4List);
                    game5Spinner.setAdapter(game5Adapter);
                    game5Spinner.setEnabled(true);

                } else {
                    game5Spinner.setSelection(0);
                    game5Spinner.setEnabled(false);

                    gamesAssignedStudent4.setGameName(null);
                    gamesAssignedStudent4.setGameID(null);
                    gamesAssignedStudent4.setStudName(null);
                    gamesAssignedStudent4.setStudId(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        game5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gamesAssignedStudent5.setGameName(context.getResources().getString(R.string.game5));
                gamesAssignedStudent5.setGameID("5");
                gamesAssignedStudent5.setStudName(((StudentForSpinner) adapterView.getSelectedItem()).getStudName());
                gamesAssignedStudent5.setStudId(((StudentForSpinner) adapterView.getSelectedItem()).getStudId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gamesAssignedStudent5.setGameName(null);
                gamesAssignedStudent5.setGameID(null);
                gamesAssignedStudent5.setStudName(null);
                gamesAssignedStudent5.setStudId(null);
            }
        });
    }

    /*  private void checkList(int[] assigned) {
          tempList.clear();
          tempList.addAll(selectedStudentList);
          for (int i = 0; i < assigned.length; i++) {
              if (assigned[i] != 100) tempList.remove(assigned[i]);
          }
      }*/
    @OnClick(R.id.playGame)
    public void playGame() {

        /*StudentForSpinner stude1 = (StudentForSpinner) game1Spinner.getSelectedItem();
        GamesAssignedStudent gamesAssignedStudent1 = new GamesAssignedStudent();
        gamesAssignedStudent1.setStudId(stude1.getStudId());
        gamesAssignedStudent1.setStudName(stude1.getStudName());
        gamesAssignedStudent1.setGameName(getResources().getString(R.string.game1));
        gamesAssignedStudent1.setGameName("1");
        selectedStudentWithGame.add(gamesAssignedStudent1*/

        /*StudentForSpinner stude2 = (StudentForSpinner) game2Spinner.getSelectedItem();
        StudentForSpinner stude3 = (StudentForSpinner) game3Spinner.getSelectedItem();
        StudentForSpinner stude4 = (StudentForSpinner) game4Spinner.getSelectedItem();
        StudentForSpinner stude5 = (StudentForSpinner) game5Spinner.getSelectedItem();*/
        if (gamesAssignedStudent1.getStudId() != null || gamesAssignedStudent2.getStudId() != null || gamesAssignedStudent3.getStudId() != null || gamesAssignedStudent4.getStudId() != null || gamesAssignedStudent5.getStudId() != null) {
            selectedStudentWithGame.clear();
            selectedStudentWithGame.add(gamesAssignedStudent1);
            selectedStudentWithGame.add(gamesAssignedStudent2);
            selectedStudentWithGame.add(gamesAssignedStudent3);
            selectedStudentWithGame.add(gamesAssignedStudent4);
            selectedStudentWithGame.add(gamesAssignedStudent5);

            Intent intent =new Intent(this,WebViewActivity.class);
            intent.putExtra("listGames",selectedStudentWithGame);
            startActivity(intent);
        } else {
            Toast.makeText(context, "select student for all games", Toast.LENGTH_SHORT).show();


           /* selectedStudentWithGame.add(stude1);
            selectedStudentWithGame.add(stude2);
            selectedStudentWithGame.add(stude3);
            selectedStudentWithGame.add(stude4);
            selectedStudentWithGame.add(stude5);*/

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
