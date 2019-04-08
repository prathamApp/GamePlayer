package com.pef.non_cog.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.pef.non_cog.R;
import com.pef.non_cog.customView.CustomSpinner;
import com.pef.non_cog.database.AppDatabase;
import com.pef.non_cog.dialog.SelectVillageDialog;
import com.pef.non_cog.interfaces.VillageSelectListener;
import com.pef.non_cog.modalclass.Groups;
import com.pef.non_cog.modalclass.Student;
import com.pef.non_cog.modalclass.Village;
import com.pef.non_cog.modalclass.VillageNameID;
import com.pef.non_cog.utils.Utility;

public class DownloadData extends AppCompatActivity implements VillageSelectListener {
    @BindView(R.id.state)
    CustomSpinner spinner_state;

    @BindView(R.id.block)
    CustomSpinner spinner_block;

    Context context;
    String[] statesCodes;
    List<Village> villageList = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();
    int count = 0;
    int groupCount = 0;
    ProgressDialog progressDialog;
    List<String> VillageIDList = new ArrayList();
    List<Groups> groupList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);
        ButterKnife.bind(this);
        context = DownloadData.this;

        loadSpinner();
    }

    private void loadSpinner() {
        String[] states = getResources().getStringArray(R.array.india_states);
        statesCodes = getResources().getStringArray(R.array.india_states_shortcode);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, states);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(arrayAdapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos > 0) {
                    String url = Utility.getProperty("HLpullVillagesURL", context);
                    url = url + statesCodes[pos];
                    loadBlocks(url);
                } else {
                    spinner_block.setSelection(0);
                    spinner_block.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void loadBlocks(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading  blocks..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AndroidNetworking.get(url).setPriority(Priority.MEDIUM).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                villageList.clear();
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Village>>() {
                }.getType();
                ArrayList<Village> modalClassesList = gson.fromJson(response.toString(), listType);
                villageList.addAll(modalClassesList);
                ArrayList blockList = new ArrayList();
                if (villageList.isEmpty()) {
                    blockList.add("No blocks");

                } else {
                    blockList.add("Select block");
                    for (Village village : villageList) {
                        blockList.add(village.getBlock());
                    }
                }
                LinkedHashSet hs = new LinkedHashSet(blockList);
                blockList.clear();
                blockList.addAll(hs);

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, blockList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_block.setAdapter(arrayAdapter);
                spinner_block.setEnabled(true);

                spinner_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            ArrayList<VillageNameID> villageName = new ArrayList();
                            String block = adapterView.getSelectedItem().toString();
                            for (Village village : villageList) {
                                if (block.equalsIgnoreCase(village.getBlock().trim()))
                                    villageName.add(new VillageNameID(village.getVillageId(), village.getVillageName()));
                            }
                            if (!villageName.isEmpty()) {
                                Dialog villageDialog = new SelectVillageDialog(context, villageName);
                                villageDialog.show();
                            } else {
                                Toast.makeText(context, "No Villages are found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onError(ANError error) {
                // handle error
                progressDialog.dismiss();
                Toast.makeText(context, "No Internet available", Toast.LENGTH_SHORT).show();
                spinner_block.setSelection(0);
                spinner_state.setSelection(0);
                spinner_block.setEnabled(false);
            }
        });
    }

    @Override
    public void getSelectedItems(ArrayList<String> villageIDList) {
        studentList.clear();
        count = 0;
        if (villageIDList.size() > 0) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("loadingStudent..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            VillageIDList.clear();
            VillageIDList.addAll(villageIDList);
            //totalSelectedVillages = villageIDList.size();

            for (String id : villageIDList) {
                String url = Utility.getProperty("HLpullStudentURL", context);
                url = url + id;
                loadStudent(url);
            }
        } else {
            Toast.makeText(context, "Villages doent selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadStudent(String url) {
        AndroidNetworking.get(url).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                count++;
                String json = response.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Student>>() {
                }.getType();
                List<Student> studentListTemp = gson.fromJson(json, listType);
                if (studentListTemp != null) {
                    studentList.addAll(studentListTemp);
                }

                loadGroups();
                //dismissDialog();
            }

            @Override
            public void onError(ANError error) {
                studentList.clear();
                progressDialog.dismiss();
                Toast.makeText(context, "no Internet available", Toast.LENGTH_SHORT).show();
                // dismissDialog();
            }
        });
    }

    private void loadGroups() {
        if (count >= VillageIDList.size()) {
            groupCount = 0;
            groupList.clear();
            for (String id : VillageIDList) {
                String url = Utility.getProperty("HLpullGroupsURL", context);
                url = url + id;
                downloadGroups(url);
            }
        }
    }

    private void downloadGroups(String url) {

        AndroidNetworking.get(url).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                groupCount++;
                String json = response.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Groups>>() {
                }.getType();
                List<Groups> groupListTemp = gson.fromJson(json, listType);
                if (groupListTemp != null) {
                    groupList.addAll(groupListTemp);
                }
                dismissDialog();
            }

            @Override
            public void onError(ANError error) {
                studentList.clear();
                progressDialog.dismiss();
                Toast.makeText(context, "no Internet available", Toast.LENGTH_SHORT).show();
                // dismissDialog();
            }
        });
    }

    public void dismissDialog() {
        if (groupCount >= VillageIDList.size()) {

            //deleting previous data
            AppDatabase.getDatabaseInstance(context).getStudentDao().deleteAllStudent();
            AppDatabase.getDatabaseInstance(context).getVillageDao().deleteAllVillages();
            AppDatabase.getDatabaseInstance(context).getGroupDao().deleteAllGroups();

            //inserting new Downloaded data
            AppDatabase.getDatabaseInstance(context).getStudentDao().insertAllStudent(studentList);
            AppDatabase.getDatabaseInstance(context).getVillageDao().insertAllVillages(villageList);
            AppDatabase.getDatabaseInstance(context).getGroupDao().insertAllGroups(groupList);

            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Downloaded Successfully");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }
    }
}
