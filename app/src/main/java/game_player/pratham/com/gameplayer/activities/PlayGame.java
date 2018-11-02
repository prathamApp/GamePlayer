package game_player.pratham.com.gameplayer.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.database.AppDatabase;
import game_player.pratham.com.gameplayer.dialog.SelectVillageDialog;
import game_player.pratham.com.gameplayer.interfaces.StudentListLisner;
import game_player.pratham.com.gameplayer.modalclass.Student;
import game_player.pratham.com.gameplayer.utils.Utility;

public class PlayGame extends AppCompatActivity implements StudentListLisner {
    Context context;
    @BindView(R.id.studentSpinner)
    Spinner studentSpinner;
    @BindView(R.id.groupSpinner)
    Spinner groupSpinner;
    @BindView(R.id.btn_next)
    Button btn_next;

    ArrayList selectedStudent=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = PlayGame.this;
        int studCount = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudantCount();
        if (studCount <= 0) {
            pullStudents();
        } else {
            loadSpiner();
        }

      String[] data=  getExternalStorageDirectories();
        Log.d("data",data.toString());
    }

    private void pullStudents() {
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
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(context, InstructionForServey.class);

        intent.putExtra("selectedStudent",selectedStudent);
        startActivity(intent);
    }


    private void loadSpiner() {
        List studList = AppDatabase.getDatabaseInstance(context).getStudentDao().getAllStudant();
        // ArrayAdapter arrayAdapter=new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,studList);
        // studentSpinner.setAdapter(arrayAdapter);
        Dialog studentDialog = new SelectVillageDialog(context, studList);
        studentDialog.show();
    }

    @Override
    public void getSelectedVillage(ArrayList list) {
        if (list.size()>=5) {
            selectedStudent=list;
            btn_next.setEnabled(true);
        }else {
            Toast.makeText(context, "Must Be select 5 student", Toast.LENGTH_SHORT).show();
        }
    }

    public String[] getExternalStorageDirectories() {

        List<String> results = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //Method 1 for KitKat & above
            File[] externalDirs = getExternalFilesDirs(null);

            for (File file : externalDirs) {
                String path = file.getPath().split("/Android")[0];

                boolean addPath = false;

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addPath = Environment.isExternalStorageRemovable(file);
                }
                else{
                    addPath = Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(file));
                }

                if(addPath){
                    results.add(path);
                }
            }
        }

        if(results.isEmpty()) { //Method 2 for all versions
            // better variation of: http://stackoverflow.com/a/40123073/5002496
            String output = "";
            try {
                final Process process = new ProcessBuilder().command("mount | grep /dev/block/vold")
                        .redirectErrorStream(true).start();
                process.waitFor();
                final InputStream is = process.getInputStream();
                final byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    output = output + new String(buffer);
                }
                is.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if(!output.trim().isEmpty()) {
                String devicePoints[] = output.split("\n");
                for(String voldPoint: devicePoints) {
                    results.add(voldPoint.split(" ")[2]);
                }
            }
        }

        //Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().matches(".*[0-9a-f]{4}[-][0-9a-f]{4}")) {
                    Log.d("LOG_TAG", results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().contains("ext") && !results.get(i).toLowerCase().contains("sdcard")) {
                    Log.d("LOG_TAG", results.get(i)+" might not be extSDcard");
                    results.remove(i--);
                }
            }
        }

        String[] storageDirectories = new String[results.size()];
        for(int i=0; i<results.size(); ++i) storageDirectories[i] = results.get(i);

        return storageDirectories;
    }
}
