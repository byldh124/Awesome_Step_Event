package com.moondroid.awesome_step_event.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.global.StepValue;
import com.moondroid.awesome_step_event.walk.StepCheckService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StepDialogActivity extends AppCompatActivity {


    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    TextView tvStatus;
    TextView tvStatusIcon;
    TextView countText;

    ImageView ivStepStatusImage;
    ImageView ivStepDialogClose;

    ImageView back01;

//    LottieAnimationView lottie;

    BroadcastReceiver receiver;
    Intent manboService;

    String TAG = "StepCounter:";
    FitnessOptions fitnessOptions;

    FrameLayout waterFillBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_dialog);

        tvStatus = findViewById(R.id.tv_step_status);
        tvStatusIcon = findViewById(R.id.tv_step_status_icon);
        ivStepStatusImage = findViewById(R.id.iv_step_status_image);
        ivStepStatusImage.setOnClickListener(statusImageListener);
//        lottie = findViewById(R.id.lottie_step_dialog);
        countText = findViewById(R.id.countText);

        countText.setText("걸음수 : " + StepValue.Step);
        waterFillBar = findViewById(R.id.water_fill);

        ivStepDialogClose = findViewById(R.id.iv_step_dialog_close);
        ivStepDialogClose.setOnClickListener(closeListener);
        receiver = new StepReceiver();
        manboService = new Intent(this, StepCheckService.class);
        registerStepService();


//        fitnessOptions = FitnessOptions.builder()
//                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
//                .build();
//        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
//            GoogleSignIn.requestPermissions(
//                    this,
//                    REQUEST_OAUTH_REQUEST_CODE,
//                    GoogleSignIn.getLastSignedInAccount(this),
//                    fitnessOptions);
//        } else {
//            subscribe();
//            readData();
//        }

        Snackbar.make(countText, "행동감지를 위한 시간이 필요합니다.", Snackbar.LENGTH_LONG).show();
    }

    View.OnClickListener statusImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            readData();
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
           onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        StepStopDialog dialog = new StepStopDialog(StepDialogActivity.this);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void pauseStep(View v) {
        tvStatusIcon.setText("OFF");
        tvStatusIcon.setBackground(getResources().getDrawable(R.color.none_active));
        tvStatus.setText("걸음 쉬는 중");
        ((ImageView) v).setImageResource(R.drawable.ic_button_play);
//        lottie.pauseAnimation();
        v.setTag("pause");
        unRegisterStepService();

    }

    public void playStep(View v) {
        tvStatusIcon.setText("ON");
        tvStatusIcon.setBackgroundColor(0xffff0000);
        tvStatus.setText("걸음 수 세는 중");
        ((ImageView) v).setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
//        lottie.resumeAnimation();
        v.setTag("play");
        registerStepService();
    }

    public void registerStepService(){
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

    public void unRegisterStepService(){
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

    @Override
    protected void onResume() {
        super.onResume();
        changeBar();
    }

    public void clickImage(View view) {
        StepValue.Step += 50

        ;
        countText.setText("걸음수 : " + StepValue.Step);
        changeBar();
    }

    class StepReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            countText.setText("걸음수 : " + StepValue.Step);
            changeBar();
        }
    }

    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(StepDialogActivity.this, "Successfully subscribed!", Toast.LENGTH_SHORT).show();
                                    Log.i("TAG", "Successfully subscribed!");
                                } else {
                                    Toast.makeText(StepDialogActivity.this, "There was a problem subscribing.", Toast.LENGTH_SHORT).show();
                                    Log.w("TAG", "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    private void readData() {
        final Calendar cal = Calendar.getInstance();
//        final Calendar cal2 = Calendar.getInstance();
        Date now = Calendar.getInstance().getTime();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
//        cal2.setTime(now);
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();

//        // 시작 시간
//        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH),06,00,00);
//
//        // 종료 시간
//        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH),22,00,00);


        Fitness.getHistoryClient(this,
                GoogleSignIn.getLastSignedInAccount(this))
                .readData(new DataReadRequest.Builder()
//                        .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                        .read(DataType.TYPE_STEP_COUNT_DELTA) // Raw 걸음 수
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
//                        .bucketByTime(1, TimeUnit.DAYS)
                        .build())
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse response) {
                        DataSet dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
//                        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
//                        Log.i(TAG, dataSet.getDataPoints().size() + "");
                        int step = 0;
                        for (DataPoint dp : dataSet.getDataPoints()) {
//                            Log.i(TAG, "Data point:");
//                            Log.i(TAG, "\tType: " + dp.getDataType().getName());
//                            Log.i(TAG, "\tStart: " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(dp.getStartTime(TimeUnit.MILLISECONDS))));
//                            Log.i(TAG, "\tEnd: " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(dp.getEndTime(TimeUnit.MILLISECONDS))));
                            for (Field field : dp.getDataType().getFields()) {
                                Value value = dp.getValue(field);
//                                Log.i(TAG, "\tField: " + field.getName() + " Value: " + value);
                                step += value.asInt();
                            }
                        }
                        countText.setText("걸음수:" + String.valueOf(step));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        stopService(manboService);
        super.onDestroy();
    }

    public void changeBar() {
        float heightPercent = (float) (StepValue.Step/1000.0);
        if (heightPercent >= (float) 1.0){
            heightPercent = (float) 0.999999;
        } else if( heightPercent <= (float) 0.2){
            heightPercent = (float) 0.2;
        }
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.matchConstraintPercentHeight = heightPercent;
        waterFillBar.setLayoutParams(layoutParams);
    }
}