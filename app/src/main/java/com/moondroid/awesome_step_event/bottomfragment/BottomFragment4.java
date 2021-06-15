package com.moondroid.awesome_step_event.bottomfragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moondroid.awesome_step_event.MainActivity;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.view.LoginActivity;
import com.moondroid.awesome_step_event.view.NotificationSettingActivity;
import com.moondroid.awesome_step_event.view.PrivateAccessActivity;
import com.moondroid.awesome_step_event.view.UsingAccessActivity;

import org.jetbrains.annotations.NotNull;

public class BottomFragment4 extends Fragment {

    Button btnLogout;

    RelativeLayout conNotification, conUsingAccess, conPrivateAccess;
    Intent intent = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout = view.findViewById(R.id.btn_logout);
        conNotification = view.findViewById(R.id.bottom4_tv_notification_setting);
        conUsingAccess = view.findViewById(R.id.bottom4_tv_using_access);
        conPrivateAccess = view.findViewById(R.id.bottom4_tv_private_access);
        conNotification.setOnClickListener(clickListener);
        conUsingAccess.setOnClickListener(clickListener);
        conPrivateAccess.setOnClickListener(clickListener);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ((MainActivity)getActivity()).finish();
            }
        });
    }

   View.OnClickListener clickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
            String tag = (String) v.getTag();
            switch (tag){
                case "notification":
                    intent = new Intent(getActivity(), NotificationSettingActivity.class);
                    break;
                case "usingAccess":
                    intent = new Intent(getActivity(), UsingAccessActivity.class);
                    break;
                case "privateAccess":
                    intent = new Intent(getActivity(), PrivateAccessActivity.class);
                    break;
            }
            if (intent != null) startActivity(intent);
       }
   };
}
