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
import game_player.pratham.com.gameplayer.interfaces.VillageSelectListener;
import game_player.pratham.com.gameplayer.modalclass.Student;

import static android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectStudentDialog extends Dialog {

    @BindView(R.id.txt_clear_changes)
    TextView clear_changes;
    @BindView(R.id.btn_close)
    ImageButton btn_close;
    @BindView(R.id.txt_message)
    TextView txt_message;
    @BindView(R.id.flowLayout)
    GridLayout flowLayout;

    Context context;
    List<Student> studentList;
    List<CheckBox> checkBoxes = new ArrayList<>();
    VillageSelectListener villageSelectListener;
    int count = 0;

    public SelectStudentDialog(@NonNull Context context, List tempList) {
        super(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        this.studentList = tempList;
        this.context = context;
        this.villageSelectListener = (VillageSelectListener) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_village_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        txt_message.setText("Select Student");
        for (int i = 0; i < studentList.size(); i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(studentList.get(i).getFullName());
            checkBox.setTag(studentList.get(i).getStudentId());
            checkBox.setTextSize(1, 20);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setGravity(Gravity.FILL_HORIZONTAL);
            checkBox.setLayoutParams(param);
            flowLayout.addView(checkBox);
            checkBoxes.add(checkBox);
            checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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


    @OnClick(R.id.btn_close)
    public void closeDialog() {
        dismiss();
    }

    @OnClick(R.id.txt_clear_changes)
    public void clearChanges() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(false);
        }
    }

    @OnClick(R.id.txt_ok)
    public void ok() {
        ArrayList<String> villageIDList = new ArrayList();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked()) {
                villageIDList.add(checkBoxes.get(i).getTag().toString());
            }
        }
        villageSelectListener.getSelectedItems(villageIDList);
        dismiss();
    }

}

