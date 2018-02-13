package com.bright.bright.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bright.bright.R;
import com.bright.bright.checkout.Checkout;
import com.bright.bright.settings.settings_Home;
import com.bright.bright.settings_new.settings_new;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private RecyclerView home_screen_itemFeed_recyclerView;
    private RecyclerView.LayoutManager home_screen_itemFeed_layoutManager;
    private RecyclerView.Adapter home_screen_itemFeed_apapter;

    private ArrayList<String> venueNameData;
    private ArrayList<String> productData;
    private ArrayList<String> productDescriptionData;
    private ArrayList<String> distanceAndWaitData;
    private ArrayList<String> priceData;
    private ArrayList<String> healthStatusData;
    private ArrayList<String> reactionStatusData;
    private ArrayList<Integer> photoGalleryData;

    public String [] venuesValues = {"Reuben Hills", "Belrose Hotel", "Ora"};
    public String [] productValues = {"Espresso", "Chicken Schnitzel", "Chicken Udon"};
    public String [] productDescriptionValues = {"Vittoria beans, single shot.", "Turkish-bread crumbs, free-range chicken...", "Chicken-breast, miso soup, fresh home-veggies..."};
    public String [] distanceValues = {"40m away with 2 minute wait", "158m away with 3 minute wait", "15kms away with 1 minute wait"};
    public String [] priceValues = {"$3.50", "$5.00", "$10000"};
    public String [] healthValues = {"85kJ", "3010kJ", "1285kJ"};
    public String [] reactionsValues = {"9,122,017 customers", "23k customers", "12,503 customers"};
    public Integer [] photoGalleryValues = {R.drawable.reuben_hills, R.drawable.belrose_hotel, R.drawable.ora_manly};

    ImageButton checkoutButton;
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        venueNameData = new ArrayList<>();
        for (int i = 0; i < venuesValues.length; i++) {
            venueNameData.add(venuesValues[i]);
        }
        distanceAndWaitData = new ArrayList<>();
        for (int i = 0; i < distanceValues.length; i++) {
            distanceAndWaitData.add(distanceValues[i]);
        }
        reactionStatusData = new ArrayList<>();
        for (int i = 0; i < reactionsValues.length; i++) {
            reactionStatusData.add(reactionsValues[i]);
        }


        home_screen_itemFeed_recyclerView = (RecyclerView) findViewById(R.id.home_screen_recyclerView);
        home_screen_itemFeed_layoutManager = new LinearLayoutManager(this);
        home_screen_itemFeed_recyclerView.setLayoutManager(home_screen_itemFeed_layoutManager);
        home_screen_itemFeed_apapter = new homeAdapter(venueNameData, distanceAndWaitData, reactionStatusData, getApplicationContext());
        home_screen_itemFeed_recyclerView.setAdapter(home_screen_itemFeed_apapter);

        checkoutButton = (ImageButton) findViewById(R.id.checkoutToolbar);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCheckout = new Intent(HomeScreen.this, Checkout.class);
                startActivity(openCheckout);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        settingsButton = (ImageButton) findViewById(R.id.settingsToolbar);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSettings = new Intent(HomeScreen.this, settings_new.class);
                startActivity(openSettings);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        if(getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            settingsButton.setBackgroundResource(0);
            settingsButton.setImageBitmap(Bitmap.createScaledBitmap(b, 150, 150, false));

        }


        final Button cancel_search = (Button) findViewById(R.id.cancel_search);

        final EditText home_search = (EditText) findViewById(R.id.home_search);
        home_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    home_screen_itemFeed_recyclerView.setVisibility(View.GONE);
                    cancel_search.setVisibility(View.VISIBLE);
                }
            }
        });

        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_search.clearFocus();
                home_search.getText().clear();
                cancel_search.setVisibility(View.GONE);
                hideKeyboardFrom(getApplicationContext(), home_search);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_screen_itemFeed_recyclerView.setVisibility(View.VISIBLE);
                    }
                }, 30);

            }
        });

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
