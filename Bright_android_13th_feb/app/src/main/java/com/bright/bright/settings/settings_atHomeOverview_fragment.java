package com.bright.bright.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bright.bright.R;

public class settings_atHomeOverview_fragment extends Fragment{

    View atHomeLayout;
    TextView learnMoreAtHome;
    TextView registerAtHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        atHomeLayout = inflater.inflate(R.layout.settings_athome_overview, container, false);
        learnMoreAtHome = (TextView) atHomeLayout.findViewById(R.id.learnMoreAtHome);

        learnMoreAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideScreenUp();
            }
        });

        registerAtHome = (TextView) atHomeLayout.findViewById(R.id.registerAtHome);
        registerAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToTopRow("AtHome");
            }
        });

        return atHomeLayout;
    }

    public void slideScreenUp() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).slideScreenUp();
        }
    }

    public void connectToTopRow(String type) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).openVRego(type);
        }
    }
}
