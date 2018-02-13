package com.bright.bright.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import com.bright.bright.R;

public class settings_RegistrationAdapter  extends RecyclerView.Adapter<settings_RegistrationAdapter.ViewHolder> {

    private ArrayList<String> titleData;
    private ArrayList<Integer> imageData;
    private settings_RegistrationAdapter.VenueAdapterInterface venueListener;

    public settings_RegistrationAdapter(ArrayList<String> titleData, ArrayList<Integer> imageData, settings_RegistrationAdapter.VenueAdapterInterface venueListener) {
        this.titleData = titleData;
        this.imageData = imageData;
        this.venueListener = venueListener;
    }

    public interface VenueAdapterInterface {
        void onVenueButtonClick(int position);
    }

    @Override
    public settings_RegistrationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_icon, viewGroup, false);
        settings_RegistrationAdapter.ViewHolder viewHolder = new settings_RegistrationAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(settings_RegistrationAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.itemTitle.setText(titleData.get(position));
        viewHolder.itemImage.setImageResource(imageData.get(position));

        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                venueListener.onVenueButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton itemImage;
        public TextView itemTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemImage = (ImageButton) itemView.findViewById(R.id.square_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}

