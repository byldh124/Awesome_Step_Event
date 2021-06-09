package com.moondroid.awesome_step_event.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.moondroid.awesome_step_event.R;

public class BottomTab3DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab3_dialog);
    }

    public void closeDialog(View view) {
        finish();
    }
}