package com.moondroid.awesome_step_event.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.moondroid.awesome_step_event.R;

public class StepStopDialog extends Dialog implements View.OnClickListener {

    private Context context;
    Button btnStepStopDialogContinue;
    Button btnStepStopDialogStop;
    ImageView stepStopDialogClose;

    public StepStopDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_stop_step);

        btnStepStopDialogContinue = findViewById(R.id.btn_step_stop_dialog_continue);
        btnStepStopDialogStop = findViewById(R.id.btn_step_stop_dialog_stop);
        stepStopDialogClose = findViewById(R.id.iv_step_stop_dialog_close);

        btnStepStopDialogStop.setOnClickListener(this);
        btnStepStopDialogContinue.setOnClickListener(this);
        stepStopDialogClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_step_stop_dialog_continue:
            case R.id.iv_step_stop_dialog_close:
                dismiss();
                break;
            case R.id.btn_step_stop_dialog_stop:
                ((StepDialogActivity)context).finish();
                break;
        }
    }
}
