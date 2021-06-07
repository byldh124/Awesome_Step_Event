package com.moondroid.awesome_step_event.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.moondroid.awesome_step_event.MainActivity;
import com.moondroid.awesome_step_event.R;

public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup rg;

    private LinearLayout containerPrivate;
    private LinearLayout containerGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rg = findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(this);

        containerPrivate = findViewById(R.id.edit_container_private);
        containerGroup = findViewById(R.id.edit_container_group);
    }

    public void clickLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void clickHome(View view) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_button_private:
                containerPrivate.setVisibility(View.VISIBLE);
                containerGroup.setVisibility(View.GONE);
                break;
            case R.id.radio_button_group:
                containerPrivate.setVisibility(View.GONE);
                containerGroup.setVisibility(View.VISIBLE);
                break;
        }
    }
}