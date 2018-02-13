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

public class settings_accountOverview_fragment extends Fragment{

    View accountLayout;
    TextView general;
    boolean accountGeneralOpen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountLayout = inflater.inflate(R.layout.settings_account_overview, container, false);
        general = (TextView) accountLayout.findViewById(R.id.userNameAccount);

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideScreenUp();
                replaceFragment("GENERAL");
                updateAdapt();
            }
        });

        return accountLayout;
    }

    public void slideScreenUp() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).slideScreenUp();
        }
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "GENERAL":
                createGeneralUserCard();
                break;
        }
    }

    public void createGeneralUserCard() {
        settings_general_account mGeneral = new settings_general_account();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.second_row_child, mGeneral, "GENERAL_ACCOUNT");
        fragmentTransaction.commit();
        updatePres("GENERAL_ACCOUNT");
    }

    public void updateAdapt() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).updateAdapter(true);
        }
    }

    public void updatePres(String card) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).updatePresentCard(card);
        }
    }



}
