package com.moondroid.awesome_step_event.bottomfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.global.StepValue;
import com.moondroid.awesome_step_event.view.BottomTab3DialogActivity;
import com.moondroid.awesome_step_event.view.ProfileSettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomFragment3 extends Fragment {

    CircleImageView btnProfileSet, btnSearch;
    CardView stepBar, todayStepContainer;
    TextView tvStepToday;
    ImageView ivBackgroundImage;
    ImageView bottom3FillWater;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivBackgroundImage = view.findViewById(R.id.bottom3_backgroung_image);
        bottom3FillWater = view.findViewById(R.id.bottom3_fill_water);

        if (true) {
            showDialog();
        }
//        StepValue.Step = 1500;

        btnProfileSet = view.findViewById(R.id.bottom3_btn_profile_set);
        btnSearch = view.findViewById(R.id.bottom3_btn_search);
        btnProfileSet.setOnClickListener(profileSetListener);
        stepBar = view.findViewById(R.id.step_bar);
        todayStepContainer = view.findViewById(R.id.today_step_container);
        tvStepToday = view.findViewById(R.id.tv_step_today);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepValue.Step += 200;
                changeBar();
                changeImage();
                fillWater();
            }
        });

        changeBar();
        fillWater();


    }

    @Override
    public void onResume() {
        super.onResume();
//        changeImage();
    }

    public void changeBar() {

        float widthPercent = (float) (StepValue.Step / 6000.0);
        if (widthPercent >= (float) 1.0){
            widthPercent = (float) 0.9999999;
        } else if( widthPercent <= (float) 0.1){
            widthPercent = (float) 0.1;
        }
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.matchConstraintPercentWidth = widthPercent;
        stepBar.setLayoutParams(layoutParams);
        tvStepToday.setText(String.valueOf(StepValue.Step));
    }

    public void fillWater(){
        float heightPercent = (float) (StepValue.Step/6000.0);








        if (heightPercent >= (float) 1.0){
            heightPercent = (float) 0.999999;
        } else if( heightPercent <= (float) 0.44){
            heightPercent = (float) 0.44;
        }
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.matchConstraintPercentHeight = heightPercent;
        bottom3FillWater.setLayoutParams(layoutParams);
    }

    public void changeImage(){
        if (StepValue.Step >= 6000){
            ivBackgroundImage.setImageResource(R.drawable.bottom3_02);
        } else {
            ivBackgroundImage.setImageResource(R.drawable.bottom3_01);
        }
    }

    View.OnClickListener profileSetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), ProfileSettingActivity.class));
        }
    };

    public void showDialog() {
        startActivity(new Intent(getActivity(), BottomTab3DialogActivity.class));
    }
}
