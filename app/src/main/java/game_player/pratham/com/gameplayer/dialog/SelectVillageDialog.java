package game_player.pratham.com.gameplayer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.interfaces.VillageSelectListener;
import game_player.pratham.com.gameplayer.modalclass.VillageNameID;

public class SelectVillageDialog extends Dialog {

    @BindView(R.id.txt_clear_changes_village)
    TextView clear_changes;
    @BindView(R.id.btn_close_village)
    ImageButton btn_close;
    @BindView(R.id.txt_message_village)
    TextView txt_message_village;
    @BindView(R.id.flowLayout)
    GridLayout flowLayout;

    Context context;
    List<VillageNameID> villageList;
    List<CheckBox> checkBoxes = new ArrayList<>();
    VillageSelectListener villageSelectListener;


    public SelectVillageDialog(@NonNull Context context, List tempList) {
        super(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        this.villageList = tempList;
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
        for (int i = 0; i < villageList.size(); i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(villageList.get(i).getVillageName());
            checkBox.setTag(villageList.get(i).getVillageId());
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setGravity(Gravity.FILL_HORIZONTAL);
            checkBox.setLayoutParams(param);
            flowLayout.addView(checkBox);
            checkBoxes.add(checkBox);
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
        ArrayList<String> villageIDList = new ArrayList();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked()) {
                villageIDList.add(checkBoxes.get(i).getTag().toString());
            }
        }
        villageSelectListener.getSelectedVillage(villageIDList);
        dismiss();
    }

}

