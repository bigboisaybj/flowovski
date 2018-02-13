package com.bright.bright.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import com.bright.bright.R;

public class settings_HomeAdapter extends RecyclerView.Adapter<settings_HomeAdapter.ViewHolder> {

    private ArrayList<String> titleData;
    private ArrayList<Integer> imageData;
    private SettingsListAdapterListener mListener;

    public settings_HomeAdapter(ArrayList<String> titleData, ArrayList<Integer> imageData, SettingsListAdapterListener mListener) {
        this.titleData = titleData;
        this.imageData = imageData;
        this.mListener = mListener;
    }

    public interface SettingsListAdapterListener {
        void onSettingsClickIcon(CharSequence title);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_icon, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.itemTitle.setText(titleData.get(position));
        viewHolder.itemImage.setImageResource(imageData.get(position));

        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSettingsClickIcon(viewHolder.itemTitle.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageButton itemImage;
        public TextView itemTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemImage = (ImageButton) itemView.findViewById(R.id.square_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
        }
    }

    public void updateAccount() {
        this.titleData.add(4, "General");
        this.imageData.add(4, R.drawable.square_orange);
        this.titleData.add(5, "Preferences");
        this.imageData.add(5, R.drawable.square_orange);
        this.titleData.add(6, "Privacy");
        this.imageData.add(6, R.drawable.square_orange);
        notifyDataSetChanged();
    }

    public void removeAccount() {
        this.titleData.remove(4);
        this.imageData.remove(4);
        notifyItemRangeChanged(0, getItemCount());
        this.titleData.remove(4);
        this.imageData.remove(4);
        notifyItemRangeChanged(0, getItemCount());
        this.titleData.remove(4);
        this.imageData.remove(4);
        notifyDataSetChanged();
    }
}