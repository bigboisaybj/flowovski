package com.bright.bright.home;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bright.bright.R;
import com.bright.bright.maps.MapsActivity;
import com.bright.bright.maps.showBusinessOnMap;

import java.util.ArrayList;

/**
 * Created by bryanjordan on 20/11/17.
 */

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {
    private ArrayList<String> venueName;
    private ArrayList<String> product;
    private ArrayList<String> productDescription;
    private ArrayList<String> distanceAndWaitTime;
    private ArrayList<String> priceOfItem;
    private ArrayList<String> healthStatusOfItem;
    private ArrayList<String> reactionsStatus;
    private ArrayList<Integer> photoGallery;
    private Context context;

    public homeAdapter(ArrayList<String> venueName, ArrayList<String> distance, ArrayList<String> reactionsStatus, Context context) {
        this.venueName = venueName;
        this.product = product;
        this.productDescription = productDescription;
        this.distanceAndWaitTime = distance;
        this.priceOfItem = priceOfItem;
        this.healthStatusOfItem = healthStatusOfItem;
        this.reactionsStatus = reactionsStatus;
        this.photoGallery = photoGallery;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_new, viewGroup, false);
        homeAdapter.ViewHolder viewHolder = new homeAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.itemVenueName.setText(venueName.get(position));
        viewHolder.itemDistanceAndWaitTime.setText(distanceAndWaitTime.get(position));
        viewHolder.itemCustomer.setText(reactionsStatus.get(position));
    }

    @Override
    public int getItemCount() {
        return venueName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemVenueName;
        public TextView itemProduct;
        public TextView itemProductDescription;
        public TextView itemDistanceAndWaitTime;
        public TextView itemPriceOfItem;
        public TextView itemHealthStatusOfItem;
        public TextView itemCustomer;
        public ImageButton itemPhotoGallery;
        public showBusinessOnMap showBusinessOnMap;


        public ViewHolder(final View itemView) {
            super(itemView);
            itemVenueName = (TextView) itemView.findViewById(R.id.venueName);
            itemProduct = (TextView) itemView.findViewById(R.id.product);
            itemProductDescription = (TextView) itemView.findViewById(R.id.productDescription);
            itemDistanceAndWaitTime = (TextView) itemView.findViewById(R.id.distance);
            itemPriceOfItem = (TextView) itemView.findViewById(R.id.priceStatus);
            itemHealthStatusOfItem = (TextView) itemView.findViewById(R.id.healthStatus);
            itemCustomer = (TextView) itemView.findViewById(R.id.customerButton);
            itemPhotoGallery = (ImageButton) itemView.findViewById(R.id.gallery);

            itemCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Tag", "Clicked");
                }
            });

            //Need to initialise innerFragment_customer

            //When message clicked -> replace container with innerFragment_message,

            //When reserve clicked -> replace container with innerFragment_reserve,



            /*
            itemDistanceAndWaitTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openMaps = new Intent(context, MapsActivity.class);
                    context.startActivity(openMaps);
                    showBusinessOnMap = new showBusinessOnMap(getAdapterPosition());
                    //need to have this accessible.
                }
            });

            itemOrder = (ImageButton) itemView.findViewById(R.id.orderButton);
            itemMenu = (ImageButton) itemView.findViewById(R.id.menuButton);

            itemOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "Order");
                }
            });

            itemMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "Menu");
                }
            });
        */
        }
    }
}
