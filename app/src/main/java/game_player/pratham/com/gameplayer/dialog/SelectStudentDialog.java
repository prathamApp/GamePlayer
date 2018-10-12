package game_player.pratham.com.gameplayer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.interfaces.StudentListLisner;
import game_player.pratham.com.gameplayer.modalclass.Student;

public class SelectStudentDialog extends Dialog {

    @BindView(R.id.txt_clear_changes_village)
    TextView clear_changes;
    @BindView(R.id.btn_close_village)
    ImageButton btn_close;
    /*  @BindView(R.id.txt_count_village)
      TextView txt_count_village;*/
    @BindView(R.id.txt_message_village)
    TextView txt_message_village;
    @BindView(R.id.flowLayout)
    GridLayout flowLayout;

    Context context;
    List<Student> studentList;
    List<CheckBox> checkBoxes = new ArrayList<>();
    StudentListLisner studentListLisner;
int count = 0;

    public SelectStudentDialog(@NonNull Context context, List tempList) {
        super(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        this.studentList = tempList;
        this.context = context;
        this.studentListLisner = (StudentListLisner) context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_student_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        for (int i = 0; i < studentList.size(); i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(studentList.get(i).getFullName());
            checkBox.setTag(studentList.get(i).getStudentId());
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setGravity(Gravity.FILL_HORIZONTAL);
            checkBox.setLayoutParams(param);
            flowLayout.addView(checkBox);
            checkBoxes.add(checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (count == 5 && b) {
                        compoundButton.setChecked(false);
                        Toast.makeText(context, "Limit reached!!!", Toast.LENGTH_SHORT).show();
                    } else if (b) {
                        count++;
                    } else if (!b) {
                        count--;
                    }
                }
            });
        }
    }


    @OnClick(R.id.btn_close_village)
    public void closeDialog() {
        dismiss();
    }

    @OnClick(R.id.txt_clear_changes_village)
    public void clearChanges() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(false);
        }
    }

    @OnClick(R.id.txt_ok_village)
    public void ok() {
        ArrayList studentList = new ArrayList();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked()) {
                studentList.add(checkBoxes.get(i).getTag());
            }
        }
        studentListLisner.getSelectedVillage(studentList);
        dismiss();
    }

}

