package com.bright.bright.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.bright.bright.R;

public class registrationReviewVenue extends Fragment {

    View registerLayout;
    String userReview;
    EditText userSummary;
    TextView confirmText;
    Boolean showScrollView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerLayout = inflater.inflate(R.layout.registration_review, container, false);

        userSummary = (EditText) registerLayout.findViewById(R.id.reviewSummary);

        if (!makeScrollViewVisible()) {
            userSummary.setText(updateReviewBlock());
        }
        else {

        }

        confirmText = (TextView) registerLayout.findViewById(R.id.confrimText);
        confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRegistration();
            }
        });
        return registerLayout;
    }


    public String updateReviewBlock() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            userReview = ((settings_Home_FirstRow_fragment) parentFragment).getUserReviewVenue();
        }
        return userReview;
    }

    public boolean makeScrollViewVisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            showScrollView = ((settings_Home_FirstRow_fragment) parentFragment).activateScrolling();
        }
        return showScrollView;
    }

    public void confirmRegistration() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).registrationConfirmed();
        }
    }
}
