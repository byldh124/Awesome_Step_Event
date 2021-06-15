package com.moondroid.awesome_step_event.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moondroid.awesome_step_event.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingActivity extends AppCompatActivity {

    CircleImageView ivMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        ivMyProfile = findViewById(R.id.profile_set_profile_image);
    }

    public void clickComplete(View view) {
        ProfileSetCompleteDialog dialog = new ProfileSetCompleteDialog(this);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void clickDeleteImage(View view) {
        Glide.with(this).load(R.drawable.app_logo).into(ivMyProfile);
    }

    ActivityResultLauncher<Intent> imageActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Uri imgUri = result.getData().getData();
                Glide.with(ProfileSettingActivity.this).load(imgUri).into(ivMyProfile);
            }
        }



    });

    private void requestCameraPermission(){
        ActivityResultLauncher permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result == false){
                    Toast.makeText(ProfileSettingActivity.this, "권한을 눌러주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION);

    }

    public void clickChangeProfile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageActivityLauncher.launch(intent);
    }

    public void clickCancel(View view) {
        super.onBackPressed();
    }
}