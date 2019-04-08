package com.pef.non_cog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.pef.non_cog.R;

/**
 * Created by PEF on 28/12/2018.
 */

public class InstructionDialog extends Dialog {
    @BindView(R.id.message)
    TextView message;

    Context context;
    String text;

    public InstructionDialog(@NonNull Context context, String message) {
        super(context, R.style.Theme_AppCompat_DayNight_NoActionBar);
        this.context = context;
        this.text = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_dialog);
        ButterKnife.bind(this);
        message.setText(Html.fromHtml(text));
    }

    /**
     * Called when the dialog is starting.
     */

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Called to tell you that you're stopping.
     */

    @Override
    protected void onStop() {

        super.onStop();
    }

}
