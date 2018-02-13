package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bright.bright.R;

public class atHome_continueRegistering_fragment extends Fragment {

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
    boolean atHomeRegoStatus = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        continueRegisteringLayout = inflater.inflate(R.layout.athome_continueregistering_fragment, container, false);

        questionText = (TextView) continueRegisteringLayout.findViewById(R.id.continueRegisterQuestionAtHome);
        containerView = (View) continueRegisteringLayout.findViewById(R.id.childAtHomeContainer);
        questionText = (TextView) continueRegisteringLayout.findViewById(R.id.continueRegisterQuestionAtHome);
        continueText = (TextView) continueRegisteringLayout.findViewById(R.id.continueTextAtHome);
        scrollView = (ScrollView) getActivity().findViewById(R.id.settingsFirstRowScrollView);
        restartText = (TextView) continueRegisteringLayout.findViewById(R.id.restartTextAtHome);

        getAtHomeRegistrationStatus();

        Boolean temp = getAtHomeRegistrationStatus();

        Log.i("TAG", temp.toString());

        if (!atHomeRegoStatus) {
            questionText.setText("Continue registering for At-Home Cooking?");

            continueText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getLatestDataInputFromUserData();

                    switch (latestData) {
                        case "XP":
                            continueToNextRegistrationItem("AddressAtHome");
                            break;
                        case "AddressAtHome":
                            continueToNextRegistrationItem("photosAtHome");
                            break;
                        case "photosAtHome":
                            //completRego();
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
        else if (atHomeRegoStatus) {
            questionText.setText("Do you want to re-submit?");
            continueText.setText("Yes");
            restartText.setText("No");
        }

        return continueRegisteringLayout;
    }

    public String getLatestDataInputFromUserData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            latestData = ((settings_Home_FirstRow_fragment) parentFragment).getLatestDataAtHome();
        }
        return latestData;
    }

    public boolean getAtHomeRegistrationStatus() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            atHomeRegoStatus = ((settings_Home_FirstRow_fragment) parentFragment).atHomeRegoStatus();
        }
        return atHomeRegoStatus;
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
            case "AddressAtHome":
                atHome_address_fragment mAddressFragment = new atHome_address_fragment();
                continueToNextRegistrationCard = mAddressFragment;
                break;
            case "photosAtHome":
                atHome_photosRegister_fragment mPhotoFragment = new atHome_photosRegister_fragment();
                continueToNextRegistrationCard = mPhotoFragment;
                break;
            case "compeleteAtHomeRego":
                registrationReviewAtHome mAtHomeRego = new registrationReviewAtHome();
                continueToNextRegistrationCard = mAtHomeRego;
                updatePresentCard();
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

    public void updatePresentCard() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).updatePresentCard("CONTINUE_REGISTERING_ATHOME");
        }
    }

}
