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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.bright.bright.R;

public class business_rego_differentTime_fragment extends Fragment {

    private Timer timer = new Timer();
    private final long DELAY = 700;

    ArrayList<String> differentTimeData = new ArrayList<>();
    View differentTimeLayout;
    TextView question;
    EditText inputText;
    String getUserDataHolder;
    String venueTitle;
    String presentQuestion = "SaturdayOpening";
    TextView suggestionLeft;
    TextView suggestionCentre;
    TextView suggestionRight;
    int questionTracker = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        differentTimeLayout = inflater.inflate(R.layout.business_differenttime_fragment, container, false);

        findVenueTitle();
        suggestionLeft = (TextView) differentTimeLayout.findViewById(R.id.suggestionsLeft);
        suggestionCentre = (TextView) differentTimeLayout.findViewById(R.id.suggestionCentre);
        suggestionRight = (TextView) differentTimeLayout.findViewById(R.id.suggestionRight);

        suggestionLeft.setVisibility(View.INVISIBLE);
        suggestionRight.setVisibility(View.INVISIBLE);
        suggestionCentre.setVisibility(View.INVISIBLE);

        question = (TextView) differentTimeLayout.findViewById(R.id.differentTimeOpeningQuestion);
        question.setText("On Saturday " + venueTitle + " is open at?");
        inputText = (EditText) differentTimeLayout.findViewById(R.id.differentTimeInput);
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String input = s.toString();

                if (input.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    differentTimeData.add(input);
                                    sendDifferentHoursToUserData(input, "DifferentTime " + questionCounterToString(questionTracker));
                                    nextQuestion();
                                    questionTracker++;
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        suggestionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suggestionLeft.getText().equals("As Sunday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(4),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(5),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
                if (suggestionLeft.getText().equals("As Wednesday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(10),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(11),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
            }
        });

        suggestionCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suggestionCentre.getText().equals("As Saturday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(2),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(3),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
                if (suggestionCentre.getText().equals("As Tuesday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(8),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(9),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
            }
        });

        suggestionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (suggestionRight.getText().equals("As Monday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(6),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(7),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
                if (suggestionRight.getText().equals("As Thursday")) {
                    sendDifferentHoursToUserData(getUserDataFromDate(12),"DifferentTime " + questionCounterToString(questionTracker));
                    sendDifferentHoursToUserData(getUserDataFromDate(13),"DifferentTime " + questionCounterToString(questionTracker));
                    skipDay();
                    questionTracker++;
                }
            }
        });

        return differentTimeLayout;
    }

    public void skipDay() {
        switch(presentQuestion) {
            case "SundayOpening":
                animationTransitionViews("On Monday " + venueTitle + " is open at?", "7am", true);
                presentQuestion = "MondayOpening";
                break;
            case "MondayOpening":
                animationTransitionViews("On Tuesday " + venueTitle + " is open at?", "6am", true);
                presentQuestion = "TuesdayOpening";
                break;
            case "TuesdayOpening":
                animationTransitionViews("On Wednesday " + venueTitle + " is open at?", "7am", true);
                presentQuestion = "WednesdayOpening";
                break;
            case "WednesdayOpening":
                animationTransitionViews("On Thursday " + venueTitle + " is open at?", "6am", true);
                presentQuestion = "ThursdayOpening";
                break;
            case "ThursdayOpening":
                animationTransitionViews("On Friday " + venueTitle + " is open at?", "7am", true);
                presentQuestion = "FridayOpening";
                break;
            case "FridayOpening":
                animationTransitionViews("On Saturday " + venueTitle + " is open at?", "6am", true);
                replaceFragment("Address");
                showUserData();
                break;
        }
    }

    public void nextQuestion() {
        switch(presentQuestion) {
            case "SaturdayOpening":
                animationTransitionViews("On Saturday " + venueTitle + " is closed at?", "8pm", false);
                presentQuestion = "SaturdayClosing";
                break;
            case "SaturdayClosing":
                animationTransitionViews("On Sunday " + venueTitle + " is open at?", "7am", false);
                presentQuestion = "SundayOpening";
                break;
            case "SundayOpening":
                animationTransitionViews("On Sunday " + venueTitle + " is closed at?", "9pm", false);
                presentQuestion = "SundayClosing";
                break;
            case "SundayClosing":
                animationTransitionViews("On Monday " + venueTitle + " is open at?", "6am", false);
                presentQuestion = "MondayOpening";
                break;
            case "MondayOpening":
                animationTransitionViews("On Monday " + venueTitle + " is closed at?", "10pm", false);
                presentQuestion = "MondayClosing";
                break;
            case "MondayClosing":
                animationTransitionViews("On Tuesday " + venueTitle + " is open at?", "7am", false);
                presentQuestion = "TuesdayOpening";
                break;
            case "TuesdayOpening":
                animationTransitionViews("On Tuesday " + venueTitle + " is closed at?", "7pm", false);
                presentQuestion = "TuesdayClosing";
                break;
            case "TuesdayClosing":
                animationTransitionViews("On Wednesday " + venueTitle + " is open at?", "6am", false);
                presentQuestion = "WednesdayOpening";
                break;
            case "WednesdayOpening":
                animationTransitionViews("On Wednesday " + venueTitle + " is closed at?", "8pm", false);
                presentQuestion = "WednesdayClosing";
                break;
            case "WednesdayClosing":
                animationTransitionViews("On Thursday " + venueTitle + " is open at?", "6am", false);
                presentQuestion = "ThursdayOpening";
                break;
            case "ThursdayOpening":
                animationTransitionViews("On Thursday " + venueTitle + "is closed at?", "10pm", false);
                presentQuestion = "ThursdayClosing";
                break;
            case "ThursdayClosing":
                animationTransitionViews("On Friday " + venueTitle + " is open at?", "6am", false);
                presentQuestion = "FridayOpening";
                break;
            case "FridayOpening":
                animationTransitionViews("On Friday " + venueTitle + " is closed at?", "11pm", false);
                presentQuestion = "FridayClosing";
                break;
            case "FridayClosing":
                replaceFragment("Address");
                showUserData();
                break;
        }
    }

    public void findVenueTitle() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            venueTitle = ((settings_Home_FirstRow_fragment) parentFragment).findVenueTitle();
        }
    }

    public void animationTransitionViews(String setQuestionTo, String hint, boolean skip) {
        ObjectAnimator fadeOutQuestion = ObjectAnimator.ofFloat(question, "alpha", 1f, 0f);
        ObjectAnimator fadeInQuestion = ObjectAnimator.ofFloat(question, "alpha", 0f, 1f);
        ObjectAnimator fadeOutInput = ObjectAnimator.ofFloat(inputText, "alpha", 1f, 0f);
        ObjectAnimator fadeInInput = ObjectAnimator.ofFloat(inputText, "alpha", 0f, 1f);
        fadeOutQuestion.setDuration(50);
        fadeInQuestion.setDuration(250);
        fadeOutInput.setDuration(50);
        fadeInInput.setDuration(250);
        fadeOutQuestion.start();
        fadeOutInput.start();
        inputText.getText().clear();
        question.setText(setQuestionTo);
        inputText.setHint(hint);
        if (!skip) {
            setSuggestions();
        }
        else {
            setSuggestionsSkipDays();
        }
        fadeInInput.start();
        fadeInQuestion.start();
    }

    public void setSuggestionsSkipDays() {
        Log.i("TAG", "skipSuggestions");
        switch(presentQuestion) {
            case "SundayOpening":
                Log.i("TAG", "sundayOpening");
                suggestionCentre.setText("As Saturday");
                suggestionLeft.setText("As Sunday");
                suggestionLeft.setVisibility(View.VISIBLE);
                suggestionCentre.setVisibility(View.VISIBLE);
                suggestionRight.setVisibility(View.VISIBLE);
                suggestionCentre.setText("As Saturday");
                suggestionLeft.setText("As Sunday");
                break;
            case "MondayOpening":
                suggestionRight.setText("As Monday");
                break;
            case "TuesdayOpening":
                suggestionCentre.setText("As Tuesday");
                break;
            case "WednesdayOpening":
                suggestionLeft.setText("As Wednesday");
                break;
            case "ThursdayOpening":
                suggestionLeft.setText("As Sunday");
                suggestionCentre.setText("As Saturday");
                suggestionRight.setText("As Thursday");
                break;
            case "FridayOpening":
                break;
        }
    }

    public void setSuggestions() {
        switch(presentQuestion) {
            case "SaturdayClosing":
                suggestionCentre.setText("As Saturday");
                suggestionLeft.setVisibility(View.VISIBLE);
                suggestionCentre.setVisibility(View.VISIBLE);
                suggestionRight.setVisibility(View.VISIBLE);
            case "SundayOpening":
                break;
            case "SundayClosing":
                suggestionLeft.setVisibility(View.VISIBLE);
                suggestionRight.setVisibility(View.VISIBLE);
                suggestionLeft.setText("As Sunday");
                suggestionRight.setText("Review");
                break;
            case "MondayOpening":
                suggestionLeft.setVisibility(View.VISIBLE);
                suggestionRight.setVisibility(View.VISIBLE);
                suggestionLeft.setText("As Sunday");
                suggestionRight.setText("Review");
                break;
            case "MondayClosing":
                suggestionRight.setText("As Monday");
                break;
            case "TuesdayOpening":
                suggestionRight.setText("As Monday");
                break;
            case "TuesdayClosing":
                suggestionCentre.setText("As Tuesday");
                break;
            case "WednesdayOpening":
                suggestionCentre.setText("As Tuesday");
                break;
            case "WednesdayClosing":
                suggestionLeft.setText("As Wednesday");
                break;
            case "ThursdayOpening":
                suggestionLeft.setText("As Wednesday");
                break;
            case "ThursdayClosing":
                suggestionLeft.setText("As Sunday");
                suggestionCentre.setText("As Saturday");
                suggestionRight.setText("As Thursday");
                break;
            case "FridayOpening":
                suggestionLeft.setText("As Sunday");
                suggestionCentre.setText("As Saturday");
                suggestionRight.setText("As Thursday");
                break;
            case "FridayClosing":
                suggestionLeft.setText("As Friday");
                break;
        }
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

    public String sendDifferentHoursToUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String venueSendToTopRow = ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataVenue(myString, present);
        }
        return myString;
    }

    public String questionCounterToString(int counter) {
        String temp = Integer.toString(counter);
        return temp;
    }

    public String getUserDataFromDate(int index) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            getUserDataHolder = ((settings_Home_FirstRow_fragment) parentFragment).getSpecificUserData(index);
        }
        return getUserDataHolder;
    }

    public String showUserData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            String temp = ((settings_Home_FirstRow_fragment) parentFragment).checkUserData();
        }
        return venueTitle;
    }
}
