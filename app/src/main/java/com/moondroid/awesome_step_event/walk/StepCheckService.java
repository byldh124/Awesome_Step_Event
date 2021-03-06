package com.moondroid.awesome_step_event.walk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.SensorsClient;
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
import com.google.android.material.snackbar.Snackbar;
import com.moondroid.awesome_step_event.MainActivity;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.global.StepValue;
import com.moondroid.awesome_step_event.view.IntroActivity;
import com.moondroid.awesome_step_event.view.StepDialogActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StepCheckService extends Service implements OnDataPointListener, SensorEventListener {

    GoogleSignInOptionsExtension fitnessOptions;
    GoogleSignInAccount googleSignInAccount;
    Notification notification;

    int count = StepValue.Step;
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;

    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 800;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;


    private static final SensorRequest sensorRequest = new SensorRequest.Builder()
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setSamplingRate(1, TimeUnit.SECONDS)
            .build();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();

//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

//        googleSignInAccount = GoogleSignIn.getAccountForExtension(this, fitnessOptions);



        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        Fitness.getSensorsClient(this, googleSignInAccount)
                .findDataSources(
                        new DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener(new OnSuccessListener<List<DataSource>>() {
                    @Override
                    public void onSuccess(List<DataSource> dataSources) {
                        connectListener();
                        for (DataSource dataSource : dataSources) {
                            if (dataSource.getDataType() == DataType.TYPE_STEP_COUNT_DELTA) {
                                Log.i("data type", "Data source for STEP_COUNT_DELTA found!");
                            }
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.e("error", "Find data sources request failed", e));

        Toast.makeText(this, googleSignInAccount.getIdToken(), Toast.LENGTH_SHORT).show();
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
        builder.setContentTitle("??????????????????");
        builder.setContentText("????????? ???????????????.");

        //??????(Notification)??? ???????????? ???????????? ????????? ??? ??????
        //????????? ?????? MainActivity ??? ????????? ??? ?????????.. ???????????? ????????? ??????
        Intent intent1 = new Intent(this, StepDialogActivity.class);
        intent1.putExtra("notification", "stepService");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        notification = builder.build();

        //????????????????????? ???????????????..
        //?????????????????? ????????? ?????? ????????? ?????? [Manifest.xml]
        startForeground(1, notification);

//        if (accelerometerSensor != null) {
//            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
//        } // end of if

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (sensorManager != null) sensorManager.unregisterListener(this);
        removeListener();
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void onDataPoint(@NonNull @NotNull DataPoint dataPoint) {
//        if (sensorManager != null){
//            sensorManager.unregisterListener(this);
//            sensorManager = null;
//            accelerometerSensor = null;
//            Toast.makeText(this, "Google fitness api on", Toast.LENGTH_SHORT).show();
//        }
        for (Field field : dataPoint.getDataType().getFields()) {
            Value value = dataPoint.getValue(field);
            for (int i = 0; i < value.asInt(); i++) {
                StepValue.Step++;
            }
        }
        Intent myResponse = new Intent("make.a.awesome.walk");
        sendBroadcast(myResponse);
    }

    public void connectListener() {
        subscribe();
        Task<Void> response = Fitness.getSensorsClient(this, googleSignInAccount)
                .add(sensorRequest, this);
        response.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Tag", "response");
            }
        });
//        Fitness.getSensorsClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
//                .add(sensorRequest,this)
//                .addOnSuccessListener(unused ->
//                        Log.i("TAG", "Listener registered!"))
//                .addOnFailureListener(task ->
//                        Log.e("TAG", "Listener not registered.", task.getCause()));
    }

    public void removeListener() {
        Task<Boolean> response = Fitness.getSensorsClient(this, googleSignInAccount)
                .remove( this);
        response.addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Log.i("Tag", "response");
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > 150) { //  gap of time of step count
                Log.i("onSensorChanged_IF", "FIRST_IF_IN");
                lastTime = currentTime;

                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD && speed < 2000) {
                    Log.i("onSensorChanged_IF", "SECOND_IF_IN");
                    Intent myFilteredResponse = new Intent("make.a.awesome.walk");

                    StepValue.Step = count++;

                    String msg = StepValue.Step + "";
                    myFilteredResponse.putExtra("stepService", msg);

                    sendBroadcast(myFilteredResponse);
                } // end of if

                lastX = event.values[0];
                lastY = event.values[1];
                lastZ = event.values[2];
            } // end of if
        } // end of if
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
