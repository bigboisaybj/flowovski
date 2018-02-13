package com.bright.bright.settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.bright.bright.R;

public class atHome_XP_fragment extends Fragment {

    View templateCard;
    ImageButton left;
    ImageButton centre;
    ImageButton right;
    String XPResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        templateCard = inflater.inflate(R.layout.athome_yearsxp_fragment, container, false);
        left = (ImageButton) templateCard.findViewById(R.id.buttonLeftCard);
        centre = (ImageButton) templateCard.findViewById(R.id.buttonCentreCard);
        right = (ImageButton) templateCard.findViewById(R.id.buttonRightCard);


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPResult = "0 - 1 Year";
                replaceFragment("ADDRESS");
                updatePresentCard();
            }
        });

        centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPResult = "1 - 3 Years";
                replaceFragment("ADDRESS");
                updatePresentCard();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPResult = "3+ Years";
                replaceFragment("ADDRESS");
                updatePresentCard();
            }
        });

        return templateCard;
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "ADDRESS":
                openLocationAtHome();
                sendToAtHomeUserData(XPResult, "XP");
                break;
        }
    }

    public void openLocationAtHome() {
        atHome_address_fragment mAddress = new atHome_address_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottom_row_fragment_container, mAddress, "addressFragment");
        fragmentTransaction.commit();
    }

    public void sendToAtHomeUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataAtHome(myString, present);
        }
    }

    public void updatePresentCard() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).updatePresentCard("addressFragment");
        }
    }

}