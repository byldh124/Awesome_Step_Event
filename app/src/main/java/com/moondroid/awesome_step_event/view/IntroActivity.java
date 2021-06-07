package com.moondroid.awesome_step_event.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.moondroid.awesome_step_event.R;

public class IntroActivity extends AppCompatActivity {

    private ImageView ivSplash01;
    private ImageView ivSplash02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ivSplash01 = findViewById(R.id.intro_iv_splash01);
        ivSplash02 = findViewById(R.id.intro_iv_splash02);

        Animation animation01 = AnimationUtils.loadAnimation(this, R.anim.splash_fade_out);
        Animation animation02 = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in);

        ivSplash01.startAnimation(animation01);
        ivSplash02.startAnimation(animation02);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(IntroActivity.this).load(R.drawable.splash02).into(ivSplash01);
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}