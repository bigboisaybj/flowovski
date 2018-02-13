package com.bright.bright.settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bright.bright.R;

/**
 * Created by bryanjordan on 13/10/17.
 */

public class busines_rego_addressCard_fragment extends Fragment {

    private int test = 1333;

    View businessAddressLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        businessAddressLayout = inflater.inflate(R.layout.business_address_card_fragment, container, false);

        TextView currentLocation = (TextView) businessAddressLayout.findViewById(R.id.zero_to_one_YearCard);
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replaceFragment("Menu");
            }
        });
        return businessAddressLayout;
    }

    public void replaceFragment(String newCard) {
        switch(newCard) {
            case "Menu":
                createBusinessMenuCard();
                break;
        }
    }

    public void createBusinessMenuCard() {
        business_rego_menuHome_fragment menuHome_fragment = new business_rego_menuHome_fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.business_fragment_container, menuHome_fragment, "MENU");
        fragmentTransaction.commit();
    }
}
