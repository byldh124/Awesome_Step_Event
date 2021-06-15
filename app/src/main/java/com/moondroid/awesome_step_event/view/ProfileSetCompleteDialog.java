package com.moondroid.awesome_step_event.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.moondroid.awesome_step_event.R;

public class ProfileSetCompleteDialog extends Dialog implements View.OnClickListener {

    private Context context;
    Button btnProfileSetCompleteDone;
    ImageView ivProfileSetCompleteClose;

    public ProfileSetCompleteDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_profile_set_complete);

        btnProfileSetCompleteDone = findViewById(R.id.btn_profile_set_complete_done);
        ivProfileSetCompleteClose = findViewById(R.id.iv_profile_set_complete_dialog_close);

        btnProfileSetCompleteDone.setOnClickListener(this);
        ivProfileSetCompleteClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        ((ProfileSettingActivity)context).finish();
    }
}
