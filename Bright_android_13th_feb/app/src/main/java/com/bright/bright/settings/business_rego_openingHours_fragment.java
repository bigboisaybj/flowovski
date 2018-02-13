package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import com.bright.bright.R;

public class business_rego_openingHours_fragment extends Fragment{

    private business_rego_closingHours_fragment closingHours_fragment;
    private Timer timer = new Timer();
    private final long DELAY = 700;

    String venueTitle;
    View sameTimeOperatingHoursLayout;
    View containerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sameTimeOperatingHoursLayout = inflater.inflate(R.layout.business_sametime_openinginput_fragment, container, false);

        containerView = (View) sameTimeOperatingHoursLayout.findViewById(R.id.business_fragment_container);

        findVenueTitle();
        final TextView businessTitle = (TextView) sameTimeOperatingHoursLayout.findViewById(R.id.opening_closing_question);
        final EditText inputOpeningTime = (EditText) sameTimeOperatingHoursLayout.findViewById(R.id.openingTimeInput);
        businessTitle.setText(venueTitle +" is open from...");

        inputOpeningTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable userInput) {
                final String input = userInput.toString();

                if (userInput.length() >= 2) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fadeAwayAnimationTwoViews(businessTitle, inputOpeningTime);
                                    createClosingTimeFragment();
                                    sendOpeningTimeToUserData(input, "Opening Hour");
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        return sameTimeOperatingHoursLayout;
    }

    void findVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
    }

    public String sendOpeningTimeToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String venueSendToTopRow = ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
        return myString;
    }

    public void createClosingTimeFragment() {
        closingHours_fragment = new business_rego_closingHours_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.business_fragment_container, closingHours_fragment);
        fragmentTransaction.commit();
        fadeInAnimationOneView(containerView);

    }

    public void fadeAwayAnimationTwoViews (View v, View vv) {
        ObjectAnimator fadeAwayV = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
        ObjectAnimator fadeAwayVV = ObjectAnimator.ofFloat(vv, "alpha", 1f, 0f);
        fadeAwayV.setDuration(150);
        fadeAwayVV.setDuration(150);
        fadeAwayV.start();
        fadeAwayVV.start();
        v.setVisibility(View.GONE);
        vv.setVisibility(View.GONE);
    }

    public void fadeInAnimationOneView(View v) {
        ObjectAnimator fadeInAnimation = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeInAnimation.setDuration(250);
        fadeInAnimation.start();
    }
}