package com.moondroid.awesome_step_event.walk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.global.StepValue;
import com.moondroid.awesome_step_event.view.IntroActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StepCheckService extends Service implements OnDataPointListener {

    GoogleSignInOptionsExtension fitnessOptions;

    GoogleSignInAccount googleSignInAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        googleSignInAccount = GoogleSignIn.getAccountForExtension(this, fitnessOptions);

        Fitness.getSensorsClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .findDataSources(
                        new DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener(new OnSuccessListener<List<DataSource>>() {
                    @Override
                    public void onSuccess(List<DataSource> dataSources) {
                        for (DataSource dataSource : dataSources) {
                            if (dataSource.getDataType() == DataType.TYPE_STEP_COUNT_DELTA) {
                                Log.i("data type", "Data source for STEP_COUNT_DELTA found!");
                            }
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.e("error", "Find data sources request failed", e));

        connectListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ch01", "channel01", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, "ch01");
        } else {
            builder = new NotificationCompat.Builder(this, "ch01");
        }
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("희망걷기대회");
        builder.setContentText("걸음이 측정됩니다.");

        //알림(Notification)을 클릭하여 서비스를 제어할 수 있는
        //버튼이 있는 MainActivity 가 실행될 수 있도록.. 보류중인 인텐트 설정
        Intent intent1 = new Intent(this, IntroActivity.class);
        intent1.setAction(Intent.ACTION_MAIN);
        intent1.addCategory(Intent.CATEGORY_LAUNCHER);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);

        Notification notification = builder.build();

        //포어그라운드로 실행되도록..
        //포어그라운드 사용에 대한 퍼미션 작업 [Manifest.xml]
        startForeground(1, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeListener();
    }

    @Override
    public void onDataPoint(@NonNull @NotNull DataPoint dataPoint) {
        for (Field field : dataPoint.getDataType().getFields()){
            Value value = dataPoint.getValue(field);
            for (int i = 0; i< value.asInt(); i++){
                StepValue.Step++;
            }
        }

        Intent myResponse = new Intent("make.a.awesome.walk");
        String msg = StepValue.Step + "";
        myResponse.putExtra("stepService", msg);
        sendBroadcast(myResponse);
    }

    public void connectListener() {
        subscribe();
        Task<Void> response = Fitness.getSensorsClient(this, googleSignInAccount)
                .add(new SensorRequest.Builder()
                        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
//                        .setDataSource(new DataSource.Builder().setType(DataSource.TYPE_RAW).build())
                        .setSamplingRate(1, TimeUnit.SECONDS)
                        .setMaxDeliveryLatency(5, TimeUnit.SECONDS)
                        .build(), this);
        response.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }

    public void removeListener() {
        Task<Boolean> response = Fitness.getSensorsClient(this, googleSignInAccount)
                .remove(this);
        response.addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
            }
        });
    }

    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, googleSignInAccount)
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i("TAG", "Successfully subscribed!");
                                } else {
                                    Log.w("TAG", "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }
}
