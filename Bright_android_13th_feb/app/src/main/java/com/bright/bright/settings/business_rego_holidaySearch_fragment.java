package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import com.bright.bright.R;

public class business_rego_holidaySearch_fragment extends Fragment {

    autoCompleteSearch holidaySearch;

    private Timer timer;
    private final long DELAY = 30;
    boolean encodedHolidays = false;
    boolean suggestionsActivated = false;
    View holidayLayout;
    String venueTitle;

    String [] defaultList = {"Christmas Eve", "New Years Day", "Australia Day"};
    String [] christmasList = {"Christmas Eve", "Christmas Day", "Boxing Day"};
    String [] easterList = {"Easter Sunday", "Easter Monday", "Shrove Tuesday", "Ash Wednesday"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        holidayLayout = inflater.inflate(R.layout.business_holidays_fragment, container, false);

        findVenueTitle();
        updateSuggestions("DEFAULT");
        holidaySearch = new autoCompleteSearch("HOLIDAYS");
        encodedHolidays = true;

        final TextView leftOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxLeft);
        final TextView centreOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxCentre);
        final TextView rightOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxRight);

        final EditText holidayInput = (EditText) holidayLayout.findViewById(R.id.holidayInput);
        final TextView holidayQuestion = (TextView) holidayLayout.findViewById(R.id.holidayQuestion);
        holidayQuestion.setText("Which holidays is " + venueTitle + " closed for?");

        holidayInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                String presentText = s.toString();
                Log.i("TAG", "present text length is: " + presentText.length());

                if ((s.length() == 0) && encodedHolidays) {
                    holidaySearch.clearCaches();
                    Log.i("TAG", "clearCache");
                }

                if (s.length() > 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!encodedHolidays) {
                                        holidaySearch = new autoCompleteSearch("HOLIDAYS");
                                        holidaySearch.search(s);
                                        suggestionsActivated = true;

                                        if ("christmas".equals(holidaySearch.SuggestionsFromSearch())) {
                                            updateSuggestions("christmas");
                                        }
                                        else if ("easter".equals(holidaySearch.SuggestionsFromSearch())) {
                                            updateSuggestions("easter");
                                        }

                                        encodedHolidays = true;
                                    }
                                    else {
                                        holidaySearch.search(s);

                                        CharSequence temp = centreOption.getText();

                                        if ("christmas".equals(holidaySearch.SuggestionsFromSearch())) {
                                            Log.i("TAG", "update textView to: Christmas");

                                            if (!temp.equals("Christmas Day")) {
                                                updateSuggestions("christmas");
                                            }
                                        }
                                        else if ("easter".equals(holidaySearch.SuggestionsFromSearch())) {
                                            Log.i("TAG", "update textView to: Easter");

                                            if (!temp.equals("Easter Monday")) {
                                                updateSuggestions("easter");
                                            }
                                        }

                                    }
                                }
                            });
                        }
                    }, DELAY);
                }
            }

            @Override
            public void afterTextChanged(final Editable userInput) {}
        });

        leftOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tempLeft = leftOption.getText();
                Log.i("TAG", "Clicked: " + leftOption.getText());
                replaceFragment("Address");
                sendHolidayToUserData("Christmas Eve", "Holiday Search");
            }
        });



        return holidayLayout;
    }

    public String sendHolidayToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String venueSendToTopRow = ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
        return myString;
    }

    public void findVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
    }

    public void updateSuggestions(String root) {
        TextView leftOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxLeft);
        TextView centreOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxCentre);
        TextView rightOption = (TextView) holidayLayout.findViewById(R.id.autoTextBoxRight);

        if (root.equals("DEFAULT")) {
            leftOption.setText(defaultList[0]);
            centreOption.setText(defaultList[1]);
            rightOption.setText(defaultList[2]);
        }
        else if (root.equals("christmas")) {
            leftOption.setText(christmasList[0]);
            centreOption.setText(christmasList[1]);
            rightOption.setText(christmasList[2]);
            fadeInAnimationThreeViews(leftOption, centreOption, rightOption);
        }
        else if (root.equals("easter")) {
            leftOption.setText(easterList[0]);
            centreOption.setText(easterList[1]);
            rightOption.setText(easterList[2]);
            fadeInAnimationThreeViews(leftOption, centreOption, rightOption);
        }
    }

    public void fadeInAnimationThreeViews(View v, View x, View y) {
        ObjectAnimator fadeInAnimationV = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationX = ObjectAnimator.ofFloat(x, "alpha", 0f, 1f);
        ObjectAnimator fadeInAnimationY = ObjectAnimator.ofFloat(y, "alpha", 0f, 1f);
        fadeInAnimationV.setDuration(350);
        fadeInAnimationX.setDuration(350);
        fadeInAnimationY.setDuration(350);
        fadeInAnimationV.start();
        fadeInAnimationX.start();
        fadeInAnimationY.start();
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "Address":
                createBusinessAddressCard();
                break;
        }
    }

    public void createBusinessAddressCard() {
        business_rego_address_fragment address_fragment = new business_rego_address_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, address_fragment, "ADDRESS");
        fragmentTransaction.commit();
    }

}