package com.moondroid.awesome_step_event.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.walk.StepCheckService;

public class StepDialogActivity extends AppCompatActivity {

    TextView tvStatus;
    TextView tvStatusIcon;
    TextView countText;

    ImageView ivStepStatusImage;
    ImageView ivStepDialogClose;

    LottieAnimationView lottie;

    BroadcastReceiver receiver;
    Intent manboService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_dialog);

        tvStatus = findViewById(R.id.tv_step_status);
        tvStatusIcon = findViewById(R.id.tv_step_status_icon);
        ivStepStatusImage = findViewById(R.id.iv_step_status_image);
        ivStepStatusImage.setOnClickListener(statusImageListener);
        lottie = findViewById(R.id.lottie_step_dialog);
        countText = findViewById(R.id.countText);

        ivStepDialogClose = findViewById(R.id.iv_step_dialog_close);
        ivStepDialogClose.setOnClickListener(closeListener);
        receiver = new StepReceiver();
        manboService = new Intent(this, StepCheckService.class);
        register();
    }

    View.OnClickListener statusImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (v.getTag().equals("play")){
//                Toast.makeText(StepDialogActivity.this, "this is playing now", Toast.LENGTH_SHORT).show();
//            }
            String tag = (String) v.getTag();
            switch (tag) {
                case "play":
                    pauseStep(v);
                    break;
                case "pause":
                    playStep(v);
                    break;
            }
        }
    };

    View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StepStopDialog dialog = new StepStopDialog(StepDialogActivity.this);
            dialog.setCancelable(false);
            dialog.show();
        }
    };

    public void pauseStep(View v) {
        tvStatusIcon.setText("OFF");
        tvStatusIcon.setBackground(getResources().getDrawable(R.color.none_active));
        tvStatus.setText("걸음 쉬는 중");
        ((ImageView) v).setImageResource(R.drawable.ic_button_play);
        lottie.pauseAnimation();
        v.setTag("pause");
        unRegister();

    }

    public void playStep(View v) {
        tvStatusIcon.setText("ON");
        tvStatusIcon.setBackgroundColor(0xffff0000);
        tvStatus.setText("걸음 수 세는 중");
        ((ImageView) v).setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        lottie.resumeAnimation();
        v.setTag("play");
        register();
    }

    public void register(){
        try {
            IntentFilter mainFilter = new IntentFilter("make.a.awesome.walk");
            registerReceiver(receiver, mainFilter);
            startService(manboService);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void unRegister(){
        try {
            unregisterReceiver(receiver);
            stopService(manboService);
            // txtMsg.setText("After stoping Service:\n"+service.getClassName());
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    String serviceData;
    class StepReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            serviceData = intent.getStringExtra("stepService");
            countText.setText(serviceData);
        }
    }
}