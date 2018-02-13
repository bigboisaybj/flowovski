package com.bright.bright.settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bright.bright.R;

public class settings_advertiseOverview_fragment extends Fragment{

    View advertiseOverviewLayout;
    TextView advertiseLearnMore;
    TextView createAd;
    TextView register;
    TextView header;
    boolean screenUp = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        advertiseOverviewLayout =  inflater.inflate(R.layout.settings_advertise_overview, container, false);
        advertiseLearnMore = (TextView) advertiseOverviewLayout.findViewById(R.id.advertiseIntro);
        advertiseLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideScreenUp();
                addToSettingsTitles("Advertise");

            }
        });
        createAd = (TextView) advertiseOverviewLayout.findViewById(R.id.advertiseCreateAd);
        createAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideScreenUp();
                createKeywordSearchCard();
            }
        });

        register = (TextView) advertiseOverviewLayout.findViewById(R.id.advertiseRegister);
        header = (TextView) advertiseOverviewLayout.findViewById(R.id.advertiseOverviewHeader);
        return advertiseOverviewLayout;
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

    public void createKeywordSearchCard() {
        header.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        createAd.setVisibility(View.GONE);
        advertiseLearnMore.setVisibility(View.GONE);

        settings_advertise_createAd_fragment searchKeywordsFragment = new settings_advertise_createAd_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.second_row_child, searchKeywordsFragment);
        fragmentTransaction.commit();
    }
}
