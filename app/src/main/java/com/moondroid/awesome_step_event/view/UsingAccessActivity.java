package com.moondroid.awesome_step_event.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.moondroid.awesome_step_event.R;

public class UsingAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_access);
    }

    public void clickClose(View view) {
        super.onBackPressed();
    }
}