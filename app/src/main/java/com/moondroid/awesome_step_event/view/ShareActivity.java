
package com.moondroid.awesome_step_event.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moondroid.awesome_step_event.R;

public class ShareActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivShareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        toolbar = findViewById(R.id.share_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivShareImage = findViewById(R.id.share_image);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickShareKakao(View view) {
    }

    public void clickShareInstagram(View view) {
    }

    public void clickImageChange(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageActivityLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> imageActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Uri imgUri = result.getData().getData();
                Glide.with(ShareActivity.this).load(imgUri).into(ivShareImage);
            }
        }
    });

    private void requestCameraPermission(){
        ActivityResultLauncher permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result == false){
                    Toast.makeText(ShareActivity.this, "권한을 눌러주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION);

    }

}