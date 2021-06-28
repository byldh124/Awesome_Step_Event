package com.moondroid.awesome_step_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment1;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment2;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment3;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment4;
import com.moondroid.awesome_step_event.view.DialogActivity;
import com.moondroid.awesome_step_event.view.StepDialogActivity;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;
    private static final int REQUEST_CODE_FOR_PERMISSION = 1;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment[] fragments;
    FitnessOptions fitnessOptions;

    private View dialogView;

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        fragments = new Fragment[4];
        fragments[0] = new BottomFragment1();
        fragmentManager.beginTransaction().add(R.id.main_container, fragments[0]).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        AccountManager am = AccountManager.get(this);
//        Bundle options = new Bundle();
//        am.getAuthToken(new Account())

        String[] permissions = new String[]{Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_FOR_PERMISSION);
        }

        String notification = getIntent().getStringExtra("notification");
        if (notification != null) {
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
            switch (notification) {
                case "stepService":
                    startActivity(new Intent(this, StepDialogActivity.class));
                    break;
            }
        }

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        GoogleSignInAccount googleSignIn = GoogleSignIn.getLastSignedInAccount(this);
        if (!GoogleSignIn.hasPermissions(googleSignIn, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    googleSignIn,
                    fitnessOptions);
        } else {
            Fitness.getRecordingClient(this,
                    googleSignIn)
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                transaction.hide(fragments[i]);
            }
        }

        switch (id) {
            case R.id.bottom_01:
                if (fragments[0] == null) {
                    fragments[0] = new BottomFragment1();
                    transaction.add(R.id.main_container, fragments[0]);
                }
                transaction.show(fragments[0]);
                break;
            case R.id.bottom_02:
                if (fragments[1] == null) {
                    fragments[1] = new BottomFragment2();
                    transaction.add(R.id.main_container, fragments[1]);
                }
                transaction.show(fragments[1]);
                break;
            case R.id.bottom_03:
                if (fragments[2] == null) {
                    fragments[2] = new BottomFragment3();
                    transaction.add(R.id.main_container, fragments[2]);
                }
                transaction.show(fragments[2]);
                break;
            case R.id.bottom_04:
                if (fragments[3] == null) {
                    fragments[3] = new BottomFragment4();
                    transaction.add(R.id.main_container, fragments[3]);
                }
                transaction.show(fragments[3]);
                break;
        }

        transaction.commit();

        return true;
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
}