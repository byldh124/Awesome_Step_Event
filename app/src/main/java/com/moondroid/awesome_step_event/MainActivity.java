package com.moondroid.awesome_step_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment1;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment2;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment3;
import com.moondroid.awesome_step_event.bottomfragment.BottomFragment4;
import com.moondroid.awesome_step_event.view.DialogActivity;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment[] fragments;

    private View dialogView;

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

        if (true) {
            startActivity(new Intent(this, DialogActivity.class));
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
}