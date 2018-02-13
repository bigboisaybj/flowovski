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

public class business_rego_menuHome_fragment extends Fragment {

    View businessMenuLayout;
    ImageButton uploadPDF;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessMenuLayout = inflater.inflate(R.layout.business_menu_fragment, container, false);

        uploadPDF = (ImageButton) businessMenuLayout.findViewById(R.id.leftButton);
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
}
