package com.moondroid.awesome_step_event.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.view.StepDialogActivity;

public class TabFragment01 extends Fragment {

    Button btnStepStart;
    FrameLayout water_fill;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_tab_01, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStepStart = view.findViewById(R.id.btn_step_start);

        btnStepStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StepDialogActivity.class));
            }
        });

//        water_fill = view.findViewById(R.id.water_fill);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        changeBar(water_fill, 4220, 10000.0f);
//    }
//
//    public static void changeBar(FrameLayout frameLayout, int i, float f) {
//        float f2 = ((float) i) / f;
//        if (f2 >= 1.0f) {
//            f2 = 0.99f;
//        } else if (f2 <= 0.1f && f2 > 0.0f) {
//            f2 = 0.15f;
//        } else if (f2 == 0.0f) {
//            f2 = 0.1f;
//        }
//        Log.e("테스트", f2 + "");
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
//        layoutParams.matchConstraintPercentHeight = f2;
//        frameLayout.setLayoutParams(layoutParams);
//    }

}
