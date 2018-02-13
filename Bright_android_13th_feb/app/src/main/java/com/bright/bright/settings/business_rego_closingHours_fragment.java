package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

public class business_rego_closingHours_fragment extends Fragment {

    View businessClosingHoursFragment;
    View containerForBusiness;
    business_rego_holidaySearch_fragment holidayFragment;

    private Timer timer = new Timer();
    private final long DELAY = 700;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessClosingHoursFragment = inflater.inflate(R.layout.business_sametime_closing_fragment, container, false);

        containerForBusiness = (View) businessClosingHoursFragment.findViewById(R.id.business_fragment_container);

        final TextView ClosingTimeQuestion = (TextView) businessClosingHoursFragment.findViewById(R.id.closingQuestion);
        final EditText inputClosingTime = (EditText) businessClosingHoursFragment.findViewById(R.id.closingTimeInput);
        inputClosingTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable closingTimeInput) {
                final String input = closingTimeInput.toString();

                if (closingTimeInput.length() >= 2) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fadeAwayAnimationTwoViews(ClosingTimeQuestion, inputClosingTime);
                                    createHolidaySearchFragment();
                                    sendClosingTimeToUserData(input, "Closing Hour");
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        return businessClosingHoursFragment;
    }

    public String sendClosingTimeToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String venueSendToTopRow = ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
        return myString;
    }

    public void createHolidaySearchFragment() {
        holidayFragment = new business_rego_holidaySearch_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.business_fragment_container, holidayFragment);
        fragmentTransaction.commit();
        fadeInAnimationOneView(containerForBusiness);

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