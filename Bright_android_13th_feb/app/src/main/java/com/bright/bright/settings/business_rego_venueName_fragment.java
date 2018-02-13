package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import com.bright.bright.R;

public class business_rego_venueName_fragment extends Fragment {

    private business_rego_operatingHoursQuestions_fragment mfragment;
    private Timer timer = new Timer();
    private final long DELAY = 700;
    View businessLayout;
    View containerView;
    View venueIcon;
    TextView initialsText;
    View atHomeText;
    View businessText;
    LinearLayout scrollView;
    View registrationTitleView;
    TextView registrationTitleText;
    String venue;
    float position;
    float alphaChange;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessLayout = inflater.inflate(R.layout.business_venuename_fragment, container, false);

        mfragment = new business_rego_operatingHoursQuestions_fragment();

        atHomeText = (View) getActivity().findViewById(R.id.Registration_At_Home);
        businessText = (View) getActivity().findViewById(R.id.Registration_Venue);
        containerView = (View) businessLayout.findViewById(R.id.business_fragment_container);

        scrollView = (LinearLayout) getActivity().findViewById(R.id.scrollLayout);

        registrationTitleText = (TextView) getActivity().findViewById(R.id.registrationTitle);
        registrationTitleView = (View) getActivity().findViewById(R.id.registrationTitle);

        final TextView venueText = (TextView) businessLayout.findViewById(R.id.continueRegisterQuestion);
        final EditText editTextStop = (EditText) businessLayout.findViewById(R.id.VenueNameInput);
        editTextStop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable venueName) {
                venue = (String) venueName.toString();

                if (venueName.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fadeAwayAnimationTwoViews(editTextStop, venueText);
                                    createSameTimeEverydayFragment();
                                    sendtoTopRowtoStepOne(venue);
                                    hideKeyboardFrom(getActivity(), editTextStop);
                                    shiftTitlesDown();
                                    createRegistrationTitle();
                                    sendVenueNameToUserData(venue, "VenueName");
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        return businessLayout;
    }

    public void createSameTimeEverydayFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.business_fragment_container, mfragment);
        fragmentTransaction.commit();
        fadeInAnimationOneView(containerView);
    }

    public void sendtoTopRowtoStepOne(String myString) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).sendToBusiness_StepOne(myString);
        }
    }

    public void sendVenueNameToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void shiftTitlesDown() {
        quickDropDownForTitles();
    }

    public void quickDropDownForTitles() {
        ObjectAnimator moveAtHomeY = ObjectAnimator.ofFloat(atHomeText, "translationY",atHomeText.getY(), atHomeText.getY() + 30f);
        ObjectAnimator moveBusinessY = ObjectAnimator.ofFloat(businessText, "translationY",45f, 50f);

        moveBusinessY.setDuration(10);
        moveAtHomeY.setDuration(10);
        moveBusinessY.start();
        moveAtHomeY.start();
    }

    public void createRegistrationTitle() {
        registrationTitleText.setText(venue);
        registrationTitleView.setVisibility(View.VISIBLE);
        registrationTitleText.setTextSize(18.2f);
        quickDropDownForRegistrationTitle();
    }

    public void quickDropDownForRegistrationTitle() {
        int scrollBarSize = scrollView.getScrollBarSize();
        changePaddingForTitles();
        ObjectAnimator dropRegistrationTitle = ObjectAnimator.ofFloat(registrationTitleView, "translationY", 40f, 85f);
        dropRegistrationTitle.setDuration(50);
        dropRegistrationTitle.start();
    }

    public void changePaddingForTitles() {
        ObjectAnimator moveScrollUp = ObjectAnimator.ofFloat(scrollView, "translationY", 0f, -35f);
        moveScrollUp.setDuration(10);
        moveScrollUp.start();
        registrationTitleView.setPadding(0,0,0,10);
        atHomeText.setPadding(0, 40,0,0);
        businessText.setPadding(0,10,0,50);
    }

}