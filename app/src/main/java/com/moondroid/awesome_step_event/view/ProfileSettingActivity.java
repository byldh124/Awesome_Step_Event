package com.moondroid.awesome_step_event.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.moondroid.awesome_step_event.R;

public class ProfileSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
    }

    public void clickComplete(View view) {
        ProfileSetCompleteDialog dialog = new ProfileSetCompleteDialog(this);
        dialog.setCancelable(false);
        dialog.show();
    }
}