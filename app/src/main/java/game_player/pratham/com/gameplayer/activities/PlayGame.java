package game_player.pratham.com.gameplayer.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.customView.CustomSpinner;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.dialog.InstructionDialog;
import game_player.pratham.com.gameplayer.dialog.SelectStudentDialog;
import game_player.pratham.com.gameplayer.interfaces.VillageSelectListener;
import game_player.pratham.com.gameplayer.modalclass.GroupNameID;
import game_player.pratham.com.gameplayer.modalclass.Groups;
import game_player.pratham.com.gameplayer.modalclass.Student;
import game_player.pratham.com.gameplayer.modalclass.StudentForSpinner;
import game_player.pratham.com.gameplayer.modalclass.Village;
import game_player.pratham.com.gameplayer.modalclass.VillageNameID;
import game_player.pratham.com.gameplayer.utils.BackupDatabase;

public class PlayGame extends AppCompatActivity implements VillageSelectListener {
    Context context;
    /*@BindView(R.id.studentSpinner)
    Spinner studentSpinner;*/
    @BindView(R.id.villageSpinner)
    CustomSpinner villageSpinner;
    @BindView(R.id.groupSpinner)
    CustomSpinner groupSpinner;
    /*@BindView(R.id.btn_next)
    Button btn_next;*/ ArrayList selectedStudent = new ArrayList<>();
    ArrayList<StudentForSpinner> studentForSpinnersList;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = PlayGame.this;
        BackupDatabase.backup(context);
        int studCount = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantCount();
        if (studCount <= 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("No Student Found Please Download A Data");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            Dialog dialog = builder.create();
            dialog.show();
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

   /* public void nextActivity(View view) {
        List<Student> studentList = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantsByIDs(selectedStudent);
        ArrayList<StudentForSpinner> studentForSpinnersList = new ArrayList<>();
        for (Student student : studentList) {
            studentForSpinnersList.add(new StudentForSpinner(student.getStudentId(), student.getFullName()));
        }
        Intent intent = new Intent(context, InstructionForServey.class);
        intent.putExtra("selectedStudent", studentForSpinnersList);
        startActivity(intent);
    }*/


    private void loadSpiner() {
        List<VillageNameID> villages = new ArrayList<>();
        villages.add(new VillageNameID("Select village"));
        List<String> villageIds = AppDatabase.getDatabaseInstance(context).getGroupDao().getUniqVillageIdByBlockName();
        if (villageIds != null && !villageIds.isEmpty()) {
            List<Village> villageNames = AppDatabase.getDatabaseInstance(context).getVillageDao().getUniqVillageNames(villageIds);
            // villages.addAll(AppDatabase.getDatabaseInstance(context).getVillageDao().getUniqBlockNames());
            for (Village village : villageNames) {
                villages.add(new VillageNameID(village.getVillageId(), village.getVillageName()));
            }

            if (!villages.isEmpty()) {
                ArrayAdapter blockAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, villages);
                blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                villageSpinner.setAdapter(blockAdapter);
                villageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            List<GroupNameID> groupList = new ArrayList<>();
                            groupList.add(new GroupNameID("Select Group"));
                            String groupId = ((VillageNameID) adapterView.getSelectedItem()).getVillageId();
                            List<Groups> groups = AppDatabase.getDatabaseInstance(context).getGroupDao().getUniqGroupsByVillageId(groupId);
                            for (Groups groups1 : groups) {
                                groupList.add(new GroupNameID(groups1.getGroupId(), groups1.getGroupName()));
                            }
                            ArrayAdapter groupAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, groupList);
                            groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            groupSpinner.setAdapter(groupAdapter);
                            groupSpinner.setEnabled(true);
                        } else {
                            groupSpinner.setSelection(0);
                            groupSpinner.setEnabled(false);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            GroupNameID groupNameID = (GroupNameID) adapterView.getSelectedItem();
                            List<Student> studentList = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantByGroupID(groupNameID.getGroupId());
                            if (studentList != null && !studentList.isEmpty()) {
                                SelectStudentDialog selectStudentDialog = new SelectStudentDialog(context, studentList);
                                selectStudentDialog.show();
                            } else {
                                Toast.makeText(context, "No Students are present in this group", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

        } else {
            Toast.makeText(context, "Please Download a data", Toast.LENGTH_SHORT).show();
        }




        /*List studList = AppDatabase.getDatabaseInstance(context).getStudentDao().getAllStudant();
        // ArrayAdapter arrayAdapter=new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,studList);
        // studentSpinner.setAdapter(arrayAdapter);
        Dialog studentDialog = new SelectVillageDialog(context, studList);
        studentDialog.show();*/
    }

    @Override
    public void getSelectedItems(ArrayList selectedStudent) {
        if (selectedStudent.size() >= 2) {
            //  selectedStudent = list;
            //btn_next.setEnabled(true);
            List<Student> studentList = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantsByIDs(selectedStudent);
            studentForSpinnersList = new ArrayList<>();
            for (Student student : studentList) {
                studentForSpinnersList.add(new StudentForSpinner(student.getStudentId(), student.getFullName()));
            }

            InstructionDialog instructionDialog = new InstructionDialog(this, getResources().getString(R.string.servey2));
            instructionDialog.show();
            instructionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    villageSpinner.setSelection(0);
                    groupSpinner.setSelection(0);
                }
            });
            mediaPlayer = MediaPlayer.create(context, R.raw.first);
            mediaPlayer.start();
            ImageView imageView = instructionDialog.findViewById(R.id.nextBtn);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(context, R.raw.first);
                    Intent intent = new Intent(context, InstructionForServey.class);
                    intent.putExtra("selectedStudent", studentForSpinnersList);
                    startActivity(intent);
                }
            });

        } else {
            // btn_next.setEnabled(false);
            Toast.makeText(context, "Must Be select 2 students", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }
    }
}

