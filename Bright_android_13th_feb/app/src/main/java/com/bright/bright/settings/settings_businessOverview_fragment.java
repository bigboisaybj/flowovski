package com.bright.bright.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bright.bright.R;

public class settings_businessOverview_fragment extends Fragment {

    View businessOverviewLayout;
    TextView learnMore;
    TextView advertise;
    TextView registerBusiness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessOverviewLayout =  inflater.inflate(R.layout.settings_business_overview, container, false);
        learnMore = (TextView) businessOverviewLayout.findViewById(R.id.learnMoreBusiness);
        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideScreenUp();
                addToSettingsTitles("Learn More");
            }
        });

        advertise = (TextView) businessOverviewLayout.findViewById(R.id.advertiseBusiness);
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdverts("Adverts");
            }
        });

        registerBusiness = (TextView) businessOverviewLayout.findViewById(R.id.registerBusiness);
        registerBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVenueInFirstRow("Venue");
            }
        });
        return businessOverviewLayout;
    }

    public void slideScreenUp() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).slideScreenUp();
        }
    }

    public void addToSettingsTitles(String titleToAdd) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).addTitle(titleToAdd);
        }
    }


    public void openAdverts(CharSequence title) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).onSettingsClickIcon(title);
        }
    }

    public void openVenueInFirstRow(String type) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).openVRego(type);
        }
    }

}
