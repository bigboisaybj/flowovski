package com.bright.bright.settings_new;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bright.bright.R;
import com.bright.bright.checkout.Checkout;
import com.bright.bright.home.HomeScreen;
import com.bright.bright.settings.blank_card_fragment;
import com.bright.bright.settings.settings_Home;
import com.bright.bright.settings.settings_facebookLogin_fragment;

public class settings_new extends AppCompatActivity {

    ImageButton homeButton;
    ImageButton checkoutButton;
    TextView account_user;
    TextView account_contact_details;
    TextView account_password;
    TextView business_registration;
    TextView business_data_analytics;
    TextView business_features;
    TextView diet_filters_ingredients;
    TextView diet_filters_daily;
    TextView diet_allergies;
    TextView payment_card_details;
    TextView payment_purchase_history;
    TextView payment_report;
    TextView help_faq;
    TextView help_chat;
    TextView help_privacy;
    TextView about_team;
    TextView about_goal;
    TextView about_chapter;
    TextView career_openings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_new);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        homeButton = (ImageButton) findViewById(R.id.searchToolbar);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openHome = new Intent(settings_new.this, HomeScreen.class);
                startActivity(openHome);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        checkoutButton = (ImageButton) findViewById(R.id.checkoutToolbar);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCheckout = new Intent(settings_new.this, Checkout.class);
                startActivity(openCheckout);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //By default open User-create
        FragmentManager childFrag = getFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_facebookLogin_fragment facebookLoginCard = new settings_facebookLogin_fragment();
        childFragTrans.add(R.id.account_feature, facebookLoginCard, "FACEBOOK_REGISTRATION");
        childFragTrans.commit();

        //Default opening
        account_user = (TextView) findViewById(R.id.account_user);
        account_user.setTextColor(Color.parseColor("#6895EE"));

        account_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        account_contact_details = (TextView) findViewById(R.id.account_contact_details);
        account_contact_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        account_password = (TextView) findViewById(R.id.account_password);
        account_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        business_registration = (TextView) findViewById(R.id.business_registration);
        business_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        business_data_analytics = (TextView) findViewById(R.id.business_data_analytics);
        business_data_analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        business_features = (TextView) findViewById(R.id.business_features);
        business_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        diet_filters_ingredients = (TextView) findViewById(R.id.diet_filters_ingredients);
        diet_filters_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        diet_filters_daily = (TextView) findViewById(R.id.diet_filters_daily_kJ_cap);
        diet_filters_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        diet_allergies = (TextView) findViewById(R.id.diet_filters_allergies);
        diet_allergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        payment_card_details = (TextView) findViewById(R.id.payment_card_details);
        payment_card_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        payment_purchase_history = (TextView) findViewById(R.id.payment_purchase_history);
        payment_purchase_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        payment_report = (TextView) findViewById(R.id.payment_report);
        payment_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        help_faq = (TextView) findViewById(R.id.help_faq);
        help_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        help_chat = (TextView) findViewById(R.id.help_chat);
        help_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        help_privacy = (TextView) findViewById(R.id.help_privacy);
        help_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about_team = (TextView) findViewById(R.id.about_team);
        about_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about_goal = (TextView) findViewById(R.id.about_goal);
        about_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about_chapter = (TextView) findViewById(R.id.about_chapter);
        about_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        career_openings = (TextView) findViewById(R.id.career_openings);
        career_openings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void clickedSetting(String item){

        //Change colour,
        //Initialise fragment
        //Change all other colours
    }


}
