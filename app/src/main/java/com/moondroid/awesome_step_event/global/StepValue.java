package com.moondroid.awesome_step_event.global;


import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.SensorRequest;
import java.util.concurrent.TimeUnit;


public class StepValue {
    public static int Step = 0;

    public static SensorRequest sensorRequest = new SensorRequest.Builder()
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setSamplingRate(1, TimeUnit.SECONDS)
            .build();
}