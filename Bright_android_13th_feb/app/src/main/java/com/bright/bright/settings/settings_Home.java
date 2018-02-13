package com.bright.bright.settings;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.bright.bright.R;
import com.bright.bright.checkout.Checkout;
import com.bright.bright.home.HomeScreen;

public class settings_Home extends AppCompatActivity implements settings_Home_FirstRow_fragment.onTopLeftClicked, settings_Home_SecondRow_fragment.slideTopRow, settings_Home_SecondRow_fragment.registerTopRow, settings_Home_SecondRow_fragment.openVenueRegistration {

    private settings_Home_FirstRow_fragment fragmentTopRow;
    private settings_Home_SecondRow_fragment fragmentSecondRow;

    ImageButton homeButton;
    ImageButton checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            fragmentTopRow = new settings_Home_FirstRow_fragment();
            fragmentSecondRow = new settings_Home_SecondRow_fragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.Above_top_row_fragment_container, fragmentTopRow, "TopRow");
        fragmentTransaction.add(R.id.second_row_fragment_container, fragmentSecondRow);
        fragmentTransaction.commit();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        homeButton = (ImageButton) findViewById(R.id.searchToolbar);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openHome = new Intent(settings_Home.this, HomeScreen.class);
                startActivity(openHome);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        checkoutButton = (ImageButton) findViewById(R.id.checkoutToolbar);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCheckout = new Intent(settings_Home.this, Checkout.class);
                startActivity(openCheckout);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void moveSecondRow(int slidingAnimationStatus) {
        settings_Home_SecondRow_fragment secondRow = (settings_Home_SecondRow_fragment) getFragmentManager().findFragmentById(R.id.second_row_fragment_container);
        secondRow.slideDownAnimation(slidingAnimationStatus);
    }

    @Override
    public void moveUpTopRow(int slidingAnimationStatus) {
        settings_Home_FirstRow_fragment mFirstRow = (settings_Home_FirstRow_fragment) getFragmentManager().findFragmentById(R.id.Above_top_row_fragment_container);
        mFirstRow.moveUpFirstRow(slidingAnimationStatus);
    }

    @Override
    public void expandTopRow(String type) {
        settings_Home_FirstRow_fragment mFirstRow = (settings_Home_FirstRow_fragment) getFragmentManager().findFragmentById(R.id.Above_top_row_fragment_container);
        mFirstRow.expandTopRow(type);
        //createBlankCard();
    }

    @Override
    public void register(String reg) {
        settings_Home_SecondRow_fragment mSecondRow = (settings_Home_SecondRow_fragment) getFragmentManager().findFragmentById(R.id.Above_top_row_fragment_container);
        mSecondRow.registration(reg);

        /*
        UPDATE IF NECESSARY --> ADD TO REGISTRATION
         */
    }
    @Override
    public void openVenueRego(String type) {
        settings_Home_FirstRow_fragment mSecondRow = (settings_Home_FirstRow_fragment) getFragmentManager().findFragmentById(R.id.Above_top_row_fragment_container);
        mSecondRow.openCardsForSecondRow(type);
    }

    public void createBlankCard() {
        FragmentManager childFrag = getFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        blank_card_fragment blank_card_fragment = new blank_card_fragment();
        childFragTrans.add(R.id.Above_top_row_fragment_container, blank_card_fragment, "TESTCARD");
        childFragTrans.commit();
    }

}