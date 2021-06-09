package com.moondroid.awesome_step_event.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.moondroid.awesome_step_event.R;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog2);
    }

    public void clickClose(View view) {
        finish();
    }
}