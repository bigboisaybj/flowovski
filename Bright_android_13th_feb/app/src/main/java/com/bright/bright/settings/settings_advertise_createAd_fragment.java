package com.bright.bright.settings;

import android.app.Fragment;
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

/**
 * Created by bryanjordan on 21/10/17.
 */

public class settings_advertise_createAd_fragment extends Fragment {

    private Timer timer;
    private final long DELAY = 750;
    View advertiseLayout;
    TextView suggestionLeft;
    TextView suggestionRight;
    TextView suggestionCentre;
    TextView question;
    EditText input;
    String nextQuestion = "LOCATION";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        advertiseLayout = inflater.inflate(R.layout.settings_advertise_searchkeywords, container, false);

        suggestionLeft = (TextView) advertiseLayout.findViewById(R.id.keywordSuggestionLeft);
        suggestionRight = (TextView) advertiseLayout.findViewById(R.id.keywordSuggestionRight);
        suggestionCentre = (TextView) advertiseLayout.findViewById(R.id.keywordSuggestionCentre);
        question = (TextView) advertiseLayout.findViewById(R.id.keywordSearchQuestion);
        input = (EditText) advertiseLayout.findViewById(R.id.keywordSearch);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() > 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch(nextQuestion) {
                                        case "LOCATION":
                                            updateAdapter("ADVERTISE");
                                            advertiseLocationQuestion();
                                            nextQuestion = "TARGET";
                                            input.setHint("Target audience");
                                            s.clear();
                                            break;
                                        case "TARGET":
                                            advertiseAgeQuestion();
                                            nextQuestion = "LANGUAGES";
                                            input.setHint("Age");
                                            s.clear();
                                            break;
                                        case "LANGUAGES":
                                            advertiseLanguageQuestion();
                                            nextQuestion = "GENDER";
                                            input.setHint("Language");
                                            s.clear();
                                            break;
                                        case "GENDER":
                                            advertiseGenderQuestion();
                                            nextQuestion = "DEMOGRAPHICS";
                                            input.setHint("Gender");
                                            s.clear();
                                            break;
                                        case "DEMOGRAPHICS":
                                            advertiseDemographicsQuestion();
                                            nextQuestion = "DAILY_BUDGET";
                                            input.setHint("Demographics");
                                            s.clear();
                                            break;
                                        case "DAILY_BUDGET":
                                            advertiseDailyBudgetQuestion();
                                            nextQuestion = "CONFIRM";
                                            input.setHint("Daily Budget");
                                            s.clear();
                                            break;
                                        case "CONFIRM":
                                            advertiseConfirmQuestion();
                                            input.setHint("Confirm");
                                            s.clear();
                                            break;

                                    }
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        return advertiseLayout;
    }

    public void advertiseLocationQuestion() {
        suggestionLeft.setText("People living here");
        suggestionCentre.setText("Everyone here");
        suggestionRight.setText("People travelling here");
        question.setText("Where would you like your ad to be seen?");
    }

    public void advertiseAgeQuestion() {
        suggestionLeft.setText("Anyone can see it");
        suggestionCentre.setText("18");
        suggestionRight.setText("26");
        question.setText("What's the minimum age to see your ad?");
    }

    public void advertiseLanguageQuestion() {
        suggestionLeft.setText("English");
        suggestionCentre.setText("Mandarin");
        suggestionRight.setText("Japanese");
        question.setText("What language should your target audience speak?");
    }

    public void advertiseGenderQuestion() {
        suggestionLeft.setText("Any");
        suggestionCentre.setText("Male");
        suggestionRight.setText("Female");
        question.setText("What gender should your target audience be?");
    }

    public void advertiseDemographicsQuestion() {
        suggestionLeft.setText("Rich");
        suggestionCentre.setText("Done");
        suggestionRight.setText("Poor");
        question.setText("Describe any personality traits you want to target.");
    }

    public void advertiseDailyBudgetQuestion() {
        suggestionLeft.setText("$5");
        suggestionCentre.setText("$10");
        suggestionRight.setText("$25");
        question.setText("How much do you want to allocate per day?");
    }

    public void advertiseConfirmQuestion() {
        suggestionLeft.setText("Pause");
        suggestionCentre.setText("Confirm");
        suggestionRight.setText("Edit");
        question.setText("Confirm");
    }

    public void updateAdapter(String newAdapter) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_SecondRow_fragment) {
            ((settings_Home_SecondRow_fragment) parentFragment).changeAdapterForTitle(newAdapter);
        }
    }

    public void addDataToAdapter(String data) {

    }



}


