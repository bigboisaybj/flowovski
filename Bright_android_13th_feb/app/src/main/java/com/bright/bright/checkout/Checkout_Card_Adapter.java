package com.bright.bright.checkout;

import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bright.bright.R;

import java.util.ArrayList;

public class Checkout_Card_Adapter extends RecyclerView.Adapter<Checkout_Card_Adapter.ViewHolder> {
    private ArrayList<String> titleOfPurchase;
    private ArrayList<String> priceOfPurchase;
    private ArrayList<String> merchantOfPurchase;

    public Checkout_Card_Adapter(ArrayList<String> titleOfPurchase, ArrayList<String> priceOfPurchase, ArrayList<String> merchantOfPurchase) {
        this.titleOfPurchase = titleOfPurchase;
        this.priceOfPurchase = priceOfPurchase;
        this.merchantOfPurchase = merchantOfPurchase;
    }

    @Override
    public Checkout_Card_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkout_scrollable_card, viewGroup, false);
        Checkout_Card_Adapter.ViewHolder viewHolder = new Checkout_Card_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Checkout_Card_Adapter.ViewHolder viewHolder, final int position) {
        viewHolder.itemTitle.setText(titleOfPurchase.get(position));
        viewHolder.itemPrice.setText(priceOfPurchase.get(position));
        viewHolder.merchant.setText(merchantOfPurchase.get(position));
    }

    @Override
    public int getItemCount() {
        return titleOfPurchase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitle;
        public TextView itemPrice;
        public TextView merchant;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public ImageButton confirmButton;
        public CardView cardView;


        public ViewHolder(final View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemPrice = (TextView) itemView.findViewById(R.id.priceSub);
            merchant = (TextView) itemView.findViewById(R.id.merchantPurchase);
            editButton = (ImageButton) itemView.findViewById(R.id.editButton);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            confirmButton = (ImageButton) itemView.findViewById(R.id.confirmButton);
            cardView = (CardView) itemView.findViewById(R.id.checkoutCard);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Return to item in ORDER to edit.
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandCard();
                    //Return to ORDER, showing ORDER_PROCESSING card.
                }
            });
        }

        public void removeAt(int position) {
            titleOfPurchase.remove(position);
            priceOfPurchase.remove(position);
            merchantOfPurchase.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, titleOfPurchase.size());
        }

        public void expandCard() {
            ObjectAnimator cardX_EXPAND = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 1.1f);
            ObjectAnimator cardX_CONTRACT = ObjectAnimator.ofFloat(cardView, "scaleX", 1.1f, 1f);
            ObjectAnimator cardY_EXPAND = ObjectAnimator.ofFloat(cardView, "scaleY", 1f, 1.1f);
            ObjectAnimator cardY_CONTRACT = ObjectAnimator.ofFloat(cardView, "scaleY", 1.1f, 1f);

            cardX_EXPAND.setDuration(70);
            cardY_EXPAND.setDuration(70);
            cardX_CONTRACT.setDuration(70);
            cardY_CONTRACT.setDuration(70);
            cardX_EXPAND.start();
            cardY_EXPAND.start();
            cardX_CONTRACT.start();
            cardY_CONTRACT.start();
        }
    }
}
