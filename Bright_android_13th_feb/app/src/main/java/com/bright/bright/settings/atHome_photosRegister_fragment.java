package com.bright.bright.settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.bright.bright.R;

public class atHome_photosRegister_fragment extends Fragment {

    View photosLayout;
    View cardView;
    ImageButton left;
    ImageButton centre;
    ImageButton right;
    boolean kitchenUpload = false;
    boolean guestToiletUpload = false;
    boolean uploadLater = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        photosLayout = inflater.inflate(R.layout.athome_photos_fragment, container, false);
        left = (ImageButton) photosLayout.findViewById(R.id.buttonLeftCard);
        centre = (ImageButton) photosLayout.findViewById(R.id.buttonCentreCard);
        right = (ImageButton) photosLayout.findViewById(R.id.buttonRightCard);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kitchenUpload = true;
                changeIconAfterUpload(left);
                confirmRegistration();
            }
        });

        centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestToiletUpload = true;
                changeIconAfterUpload(centre);
                confirmRegistration();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLater = true;
                changeIconAfterUpload(right);
                confirmRegistration();
            }
        });

        return photosLayout;
    }

    /*
    public void confirmRegistration() {
        if ((kitchenUpload && guestToiletUpload) || uploadLater) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof settings_Home_FirstRow_fragment) {
                if (kitchenUpload && guestToiletUpload) {
                    sendToAtHomeUserData("Kitchen & GuestToilet Uploaded", "photosAtHome");
                }
                else if (uploadLater) {
                    sendToAtHomeUserData("UploadLater", "photosAtHome");
                }

                if (isRegistrationComplete()) {
                    ((settings_Home_FirstRow_fragment) parentFragment).completeAtHomeRegistration();
                }
            }
        }
    }
    */

    public void confirmRegistration() {
        if ((kitchenUpload && guestToiletUpload) || uploadLater) {
            if (kitchenUpload && guestToiletUpload) {
                sendToAtHomeUserData("Kitchen & GuestToilet Uploaded", "photosAtHome");
            }
            else if (uploadLater) {
                sendToAtHomeUserData("UploadLater", "photosAtHome");
            }

            if (isRegistrationComplete()) {
                createAtHomeRegoReviewCard();
            }
        }
    }

    public void sendToAtHomeUserData(String myString, String present) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).addToUserDataAtHome(myString, present);
        }
    }

    public void changeIconAfterUpload(ImageButton button) {
        button.setImageResource(R.drawable.square_lightblue);
    }

    public boolean isRegistrationComplete() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            return ((settings_Home_FirstRow_fragment) parentFragment).atHomeRegistrationStatus();
        }
        return false;
    }

    public void createAtHomeRegoReviewCard() {
        registrationReviewAtHome mReviewAtHome = new registrationReviewAtHome();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottom_row_fragment_container, mReviewAtHome, "ATHOME_REVIEW");
        updatePresentCard();
        fragmentTransaction.commit();
    }

    public void updatePresentCard() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof settings_Home_FirstRow_fragment) {
            ((settings_Home_FirstRow_fragment) parentFragment).updatePresentCard("ATHOME_REVIEW");
        }
    }
}


