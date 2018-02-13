package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bright.bright.R;

public class business_rego_operatingHoursQuestions_fragment extends Fragment {

    View detailsRegistration;
    String venueTitle;
    TextView sameTimeQuestion;
    ImageButton noButton;
    ImageButton yesButton;
    ImageButton alltimeButton;
    TextView yesAnswerText;
    TextView noAnswerText;
    TextView allTimeAnswerText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailsRegistration = inflater.inflate(R.layout.business_operatinghours, container, false);
        sameTimeQuestion = (TextView) detailsRegistration.findViewById(R.id.sameHoursQuestion);
        noButton = (ImageButton) detailsRegistration.findViewById(R.id.noAnswer);
        yesButton = (ImageButton) detailsRegistration.findViewById(R.id.yesAnswer);
        alltimeButton = (ImageButton) detailsRegistration.findViewById(R.id.twentyFourAnswer);
        yesAnswerText = (TextView) detailsRegistration.findViewById(R.id.noAnswerText);
        noAnswerText = (TextView) detailsRegistration.findViewById(R.id.yesAnswerText);
        allTimeAnswerText = (TextView) detailsRegistration.findViewById(R.id.alltimeAnswerText);

        //fadeInAnimationMultiViews(sameTimeQuestion, noButton, yesButton, alltimeButton, yesAnswerText, noAnswerText, allTimeAnswerText);

        findVenueTitle();
        sameTimeQuestion.setText("Is " + venueTitle +" open at the sametime everyday?");

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment("yesAnswer");
                sendOperatingHoursToUserData("Yes", "Opening Question");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("noAnswer");
                sendOperatingHoursToUserData("No", "Opening Question");
            }
        });

        alltimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("24/7");
                sendOperatingHoursToUserData("24/7", "Opening Question");
            }
        });

        return detailsRegistration;
    }

    public void findVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
    }

    public String sendOperatingHoursToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String venueSendToTopRow = ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
        return myString;
    }

    public void replaceFragment(String newCard) {
            switch(newCard) {
                case "yesAnswer":
                    createSameTimeCard();
                    break;
                case "noAnswer":
                    createDifferentCard();
                    break;
                case "24/7":
                    createHolidaySearch();
            }
    }

    public void createSameTimeCard() {
        business_rego_openingHours_fragment sameTimeOperatingHoursInput_fragment = new business_rego_openingHours_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, sameTimeOperatingHoursInput_fragment, "SAMETIME");
        fragmentTransaction.commit();
    }

    public void createDifferentCard() {
        business_rego_differentTime_fragment mDifferent = new business_rego_differentTime_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, mDifferent, "DIFFERENT");
        fragmentTransaction.commit();
    }

    public void createHolidaySearch() {
        business_rego_holidaySearch_fragment mHoliday = new business_rego_holidaySearch_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, mHoliday, "HOLIDAYSEARCH");
        fragmentTransaction.commit();
    }

    public void fadeInAnimationMultiViews(View v, View vv, View x, View xx, View y, View yy, View z) {
        ObjectAnimator fadeInAnimationV = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationVV = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationX = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationXX = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationY = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationYY = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationZ = ObjectAnimator.ofFloat(vv, "alpha", 0f, 1f);
        fadeInAnimationV.setDuration(250);
        fadeInAnimationVV.setDuration(250);
        fadeInAnimationX.setDuration(250);
        fadeInAnimationXX.setDuration(250);
        fadeInAnimationY.setDuration(250);
        fadeInAnimationYY.setDuration(250);
        fadeInAnimationZ.setDuration(250);
        fadeInAnimationV.start();
        fadeInAnimationVV.start();
        fadeInAnimationX.start();
        fadeInAnimationXX.start();
        fadeInAnimationY.start();
        fadeInAnimationYY.start();
        fadeInAnimationZ.start();
    }

}