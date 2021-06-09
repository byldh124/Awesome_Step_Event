package com.moondroid.awesome_step_event.bottomfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.view.BottomTab3DialogActivity;
import com.moondroid.awesome_step_event.view.ProfileSettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomFragment3 extends Fragment {

    CircleImageView btnProfileSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (true){
            showDialog();
        }

        btnProfileSet = view.findViewById(R.id.bottom3_btn_profile_set);
        btnProfileSet.setOnClickListener(profileSetListener);
    }

    View.OnClickListener profileSetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), ProfileSettingActivity.class));
        }
    };

    public void showDialog(){
        startActivity(new Intent(getActivity(), BottomTab3DialogActivity.class));
    }
}
