package game_player.pratham.com.gameplayer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;

/**
 * Created by PEF on 02/01/2019.
 */

public class AssignGameDialog extends Dialog {

    @BindView(R.id.txtMsg)
    TextView textView;

    String textMsg;
    String tittle;

    public AssignGameDialog(@NonNull Context context, String textMsg, String tittle) {
        super(context);
        this.textMsg = textMsg;
        this.tittle = tittle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.assigngame_dialog);
        ButterKnife.bind(this);
        setCancelable(false);
       /* setTitle(tittle);*/
        textView.setText(Html.fromHtml(textMsg));
    }
}
