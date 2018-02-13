package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bright.bright.R;

public class business_rego_continueRegistering_fragment extends Fragment{

    View continueRegisteringLayout;
    String latestData;
    String venueTitle;
    View containerView;
    ScrollView scrollView;
    Fragment continueToNextRegistrationCard;
    TextView questionText;
    TextView continueText;
    TextView restartText;
    TextView spotlightText;
    boolean venueRegistrationStatus = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        continueRegisteringLayout = inflater.inflate(R.layout.business_continueregistering_fragment, container, false);

        questionText = (TextView) continueRegisteringLayout.findViewById(R.id.continueRegisterQuestion);
        containerView = (View) continueRegisteringLayout.findViewById(R.id.business_fragment_container);
        questionText = (TextView) continueRegisteringLayout.findViewById(R.id.continueRegisterQuestion);
        continueText = (TextView) continueRegisteringLayout.findViewById(R.id.continueText);
        scrollView = (ScrollView) getActivity().findViewById(R.id.settingsFirstRowScrollView);
        restartText = (TextView) continueRegisteringLayout.findViewById(R.id.restartText);

        getVenueRegistrationStatus();

        if (!venueRegistrationStatus) {
            questionText.setText("Continue registeringVenue " + getVenueTitle() + "?");

            continueText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getLatestDataInputFromUserData();

                    switch (latestData) {
                        case "VenueName":
                            continueToNextRegistrationItem("VenueName");
                            break;
                        case "Opening Question":
                            continueToNextRegistrationItem("Opening Question");
                            break;
                        case "Opening Hour":
                            continueToNextRegistrationItem("Opening Hour");
                            break;
                        case "Closing Hour":
                            continueToNextRegistrationItem("Closing Hour");
                            break;
                        case "Holiday Search":
                            continueToNextRegistrationItem("Holiday Search");
                            break;
                    }

                    restartText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: DISABLE CLICKING ON THIS TWICE WHEN ALREADY CHANGED.
                            expandCard();
                            questionText.setText("Are you sure you want to restart?");
                            continueText.setText("Yes");
                            restartText.setText("No");
                            //TODO: IF YES, CLEAR USER DATA.
                        }
                    });
                }
            });
        }
        else if (venueRegistrationStatus) {
            questionText.setText("Do you want to register a different venue?");
            continueText.setText("Yes");
            restartText.setText("No");
        }

        return continueRegisteringLayout;
    }

    public String getLatestDataInputFromUserData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            latestData = ((settings_Home_FirstRow_fragment) parentFragment).getLatestDataVenue();
        }
        return latestData;
    }

    public String getVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
        return venueTitle;
    }

    public boolean getVenueRegistrationStatus() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueRegistrationStatus = ((settings_Home_FirstRow_fragment) parentFragment).registrationStatus();
        }
        return venueRegistrationStatus;
    }

    public void expandCard() {
        CardView card = (CardView) getActivity().findViewById(R.id.continueCard);
        ObjectAnimator cardX_EXPAND = ObjectAnimator.ofFloat(card, "scaleX", 1f, 1.1f);
        ObjectAnimator cardX_CONTRACT = ObjectAnimator.ofFloat(card, "scaleX", 1.1f, 1f);
        ObjectAnimator cardY_EXPAND = ObjectAnimator.ofFloat(card, "scaleY", 1f, 1.1f);
        ObjectAnimator cardY_CONTRACT = ObjectAnimator.ofFloat(card, "scaleY", 1.1f, 1f);

        cardX_EXPAND.setDuration(70);
        cardY_EXPAND.setDuration(70);
        cardX_CONTRACT.setDuration(70);
        cardY_CONTRACT.setDuration(70);
        cardX_EXPAND.start();
        cardY_EXPAND.start();
        cardX_CONTRACT.start();
        cardY_CONTRACT.start();
    }

    public void continueToNextRegistrationItem(String nextCard) {

        switch (nextCard) {
            case "VenueName":
                business_rego_operatingHoursQuestions_fragment mOperatingQuestionFragment = new business_rego_operatingHoursQuestions_fragment();
                continueToNextRegistrationCard = mOperatingQuestionFragment;
                break;
            case "Opening Question":
                business_rego_openingHours_fragment mOpeningHoursFragment = new business_rego_openingHours_fragment();
                continueToNextRegistrationCard = mOpeningHoursFragment;
                break;
            case "Opening Hour":
                business_rego_closingHours_fragment mClosingHoursFragment = new business_rego_closingHours_fragment();
                continueToNextRegistrationCard = mClosingHoursFragment;
                break;
            case "Closing Hour":
                business_rego_holidaySearch_fragment mHolidaySearchFragment = new business_rego_holidaySearch_fragment();
                continueToNextRegistrationCard = mHolidaySearchFragment;
                break;
            case "Holiday Search":
                business_rego_address_fragment mBusinessAddressFragment = new business_rego_address_fragment();
                continueToNextRegistrationCard = mBusinessAddressFragment;
                break;
        }
        clearContainer();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.business_fragment_container, continueToNextRegistrationCard);
        fragmentTransaction.commit();
        fadeInAnimationOneView(containerView);
    }

    public void fadeInAnimationOneView(View v) {
        ObjectAnimator fadeInAnimation = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeInAnimation.setDuration(250);
        fadeInAnimation.start();
    }

    public void clearContainer() {
        restartText = (TextView)  continueRegisteringLayout.findViewById(R.id.restartText);
        spotlightText = (TextView) continueRegisteringLayout.findViewById(R.id.spotLightText);

        questionText.setVisibility(View.GONE);
        continueText.setVisibility(View.GONE);
        restartText.setVisibility(View.GONE);
        spotlightText.setVisibility(View.GONE);
    }

}
