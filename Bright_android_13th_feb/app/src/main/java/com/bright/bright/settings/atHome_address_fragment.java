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

public class atHome_address_fragment extends Fragment {

    View layoutAddress;
    ImageButton left;
    ImageButton centre;
    ImageButton right;
    String AddressResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutAddress = inflater.inflate(R.layout.athome_address_fragment, container, false);
        left = (ImageButton) layoutAddress.findViewById(R.id.buttonLeftCard);
        centre = (ImageButton) layoutAddress.findViewById(R.id.buttonCentreCard);
        right = (ImageButton) layoutAddress.findViewById(R.id.buttonRightCard);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressResult = "Current Location";
                replaceFragment("PHOTOS");
                updatePresentCard();
            }
        });

        centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressResult = "Autofill Later";
                replaceFragment("PHOTOS");
                updatePresentCard();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressResult = "Manual Input";
                replaceFragment("PHOTOS");
                updatePresentCard();

            }
        });
        return layoutAddress;
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "PHOTOS":
                openPhotosAtHome();
                sendToAtHomeUserData(AddressResult, "AddressAtHome");
                break;
        }
    }

    public void openPhotosAtHome() {
        atHome_photosRegister_fragment mPhotos = new atHome_photosRegister_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottom_row_fragment_container, mPhotos, "photosFragment");
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
            ((settings_Home_FirstRow_fragment) parentFragment).updatePresentCard("photosFragment");
        }
    }
}