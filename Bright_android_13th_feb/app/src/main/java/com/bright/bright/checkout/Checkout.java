package com.bright.bright.checkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bright.bright.settings_new.settings_new;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import java.util.ArrayList;
import com.bright.bright.R;
import com.bright.bright.home.HomeScreen;
import com.bright.bright.settings.settings_Home;


public class Checkout extends AppCompatActivity {

    private RecyclerView card_recyclerView;
    private RecyclerView.LayoutManager card_layoutManger;
    private RecyclerView.Adapter card_adapter;
    private ArrayList<String> cardPurchasesDataSet;
    private ArrayList<String> card_totalPrice;
    private ArrayList<String> merchants;

    public String [] productsForPurchase = {"Flat White with 1 sugar and soy milk.", "Crossaint heated to hot.", "B&E Roll with BBQ sauce."};
    public String [] totalPriceData = {"$5.40", "$19.50", "$16.30"};
    public String [] merchantsData = {"Reuben Hills", "Cafe Sydney", "Lansdowne Hotel"};

    ImageButton searchButton;
    ImageButton settingsButton;
    ImageButton orderAllButton;
    CardInputWidget mCardInputWidget;
    TextView confirmText;
    TextView cancelText;
    TextView cashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cardPurchasesDataSet = new ArrayList<>();
        for (int i = 0; i < productsForPurchase.length; i++) {
            cardPurchasesDataSet.add(productsForPurchase[i]);
        }
        card_totalPrice = new ArrayList<>();
        for (int i = 0; i < totalPriceData.length; i++) {
            card_totalPrice.add(totalPriceData[i]);
        }
        merchants = new ArrayList<>();
        for (int i = 0; i < merchantsData.length; i++) {
            merchants.add(merchantsData[i]);
        }

        card_recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        card_layoutManger = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(card_layoutManger);
        card_adapter = new Checkout_Card_Adapter(cardPurchasesDataSet, card_totalPrice, merchants);
        card_recyclerView.setAdapter(card_adapter);

        searchButton = (ImageButton) findViewById(R.id.searchToolbar);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Intent openHome = new Intent(Checkout.this, HomeScreen.class);
                    startActivity(openHome);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        settingsButton = (ImageButton) findViewById(R.id.settingsToolbar);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSettings = new Intent(Checkout.this, settings_new.class);
                startActivity(openSettings);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        findTotalAmount(totalPriceData);

        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        confirmText = (TextView) findViewById(R.id.confirmText);
        cashText = (TextView) findViewById(R.id.editText);
        cancelText = (TextView) findViewById(R.id.deleteText);

        orderAllButton = (ImageButton) findViewById(R.id.confirmButton);
        orderAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmText.getText() == "Confirm"){
                    Card card = mCardInputWidget.getCard();
                    if (card == null) {
                        // Do not continue token creation.
                        return;
                    }
                    else{
                        StripePayment(card);
                    }
                }
                else{
                    card_recyclerView.setVisibility(View.GONE);
                    mCardInputWidget.setVisibility(View.VISIBLE);
                    confirmText.setText("Confirm");
                    cashText.setText("Pay with cash");
                    cancelText.setText("Cancel");
                }
            }
        });

    }

    public int findTotalAmount(String [] values){
        int total = 0;

        for(int i = 0; i < values.length; i++){
            total += Math.round((Float.valueOf(values[i].substring(1)) * 100));
        }
        Log.d("TAG", "RESULT: " + total);
        return total;
    }

    public void StripePayment(Card card){
        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_Tx3Oto71997X7nuR5VpgXNu6");
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        Log.d("TAG", "success bby");
                        //Next step is sending data to server, execute from server.
                    }
                    public void onError(Exception error) {
                        // Show localized error message
                      Log.d("TAG", "lol");
                    }
                }
        );
    }
}
