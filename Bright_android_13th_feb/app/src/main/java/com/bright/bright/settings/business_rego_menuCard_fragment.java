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
import android.widget.TextView;
import com.bright.bright.R;

public class business_rego_menuCard_fragment extends Fragment {

    View businessMenuLayout;
    ImageButton uploadPDF;
    String venueTitle;
    TextView question;
    Boolean venueTitleAdded;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessMenuLayout = inflater.inflate(R.layout.business_menu_card_fragment, container, false);

        question = (TextView) businessMenuLayout.findViewById(R.id.businessMenuQuestion);

        findVenueTitle();

        if (!venueTitleAdded()) {
            question.setText("Upload your menu");
        }
        else if (venueTitleAdded()) {
            question.setText("Upload menu for " + venueTitle);
        }

        uploadPDF = (ImageButton) businessMenuLayout.findViewById(R.id.buttonLeftCard);
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("Review");
            }
        });

        return businessMenuLayout;
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "Review":
                openRegistrationReview();
                break;
        }
    }

    public void openRegistrationReview() {
        registrationReviewVenue mregistrationReview = new registrationReviewVenue();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, mregistrationReview, "REVIEW");
        fragmentTransaction.commit();
    }

    public boolean venueTitleAdded() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitleAdded = ((settings_Home_FirstRow_fragment) parentFragment).venueTitleAdded();
        }
        return venueTitleAdded;
    }

    public void findVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
    }

}

