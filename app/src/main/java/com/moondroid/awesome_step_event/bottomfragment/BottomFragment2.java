package com.moondroid.awesome_step_event.bottomfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.view.OnlineCertificationActivity;
import com.moondroid.awesome_step_event.view.ShareActivity;

public class BottomFragment2 extends Fragment{

    Button btnCertification;
    Button btnShare;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCertification = view.findViewById(R.id.bottom2_btn_certification);
        btnCertification.setOnClickListener(certificationListener);
        btnShare = view.findViewById(R.id.bottom2_btn_share);
        btnShare.setOnClickListener(shareListener);
    }

    View.OnClickListener certificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), OnlineCertificationActivity.class));
        }
    };

    View.OnClickListener shareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), ShareActivity.class));
        }
    };

}
