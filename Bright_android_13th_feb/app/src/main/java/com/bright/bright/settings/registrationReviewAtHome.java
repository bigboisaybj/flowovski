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

public class registrationReviewAtHome extends Fragment {

    View registerLayout;
    EditText userSummary;
    TextView confirmText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerLayout = inflater.inflate(R.layout.registration_review, container, false);

        userSummary = (EditText) registerLayout.findViewById(R.id.reviewSummary);
        confirmText = (TextView) registerLayout.findViewById(R.id.confrimText);
        confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAtHomeRegistration();
                updatePresentCard();
            }
        });
        return registerLayout;
    }

    public void confirmAtHomeRegistration() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
           ((settings_Home_FirstRow_fragment) parentFragment).completeAtHomeRegistration();
        }
    }

    public void updatePresentCard() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).updatePresentCard("ATHOME_REVIEW");
        }
    }
}
