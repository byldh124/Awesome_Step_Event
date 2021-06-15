package com.moondroid.awesome_step_event.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moondroid.awesome_step_event.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OnlineCertificationActivity extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout blurContainer;
    RelativeLayout certificationContainer;
    TextView tvStepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_certification);

        toolbar = findViewById(R.id.certification_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        blurContainer = findViewById(R.id.certification_blur_container);
        certificationContainer = findViewById(R.id.certification_certification_container);
        tvStepCount = findViewById(R.id.certification_step_count);

        String stepCount = getIntent().getStringExtra("stepCount");
        tvStepCount.setText(stepCount);

        if (stepCount.equals("6,220걸음")) {
            blurContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickShare(View view) {
        startActivity(new Intent(this, ShareActivity.class));
    }

    public void clickDownload(View view) throws IOException {
        certificationContainer.buildDrawingCache();
        Bitmap bitmap = certificationContainer.getDrawingCache();
        Toast.makeText(this, bitmap.toString(), Toast.LENGTH_SHORT).show();
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder_name = "/" + "Pictures/StepForWater" +
                "" +
                "" +
                "" +
                "" + "/";
        String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        String string_path = ex_storage + folder_name;

        File file_path;
        try {
            file_path = new File(string_path);
            if (!file_path.isDirectory()) {
                file_path.mkdirs();
            }
            FileOutputStream outputStream = new FileOutputStream(string_path + name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}