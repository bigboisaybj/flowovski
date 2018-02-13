package com.bright.bright.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bright.bright.R;
import java.util.ArrayList;

public class settings_AtHomeAdapter extends RecyclerView.Adapter<settings_AtHomeAdapter.ViewHolder> {

    private ArrayList<String> titleData;
    private ArrayList<Integer> imageData;
    private ListAdapterListener mListener;

    public settings_AtHomeAdapter(ArrayList<String> titleData, ArrayList<Integer> imageData, ListAdapterListener mListener) {
        this.titleData = titleData;
        this.imageData = imageData;
        this.mListener = mListener;
    }

    public interface ListAdapterListener {
        void onClickButton(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_icon, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position ) {
        viewHolder.itemTitle.setText(titleData.get(position));
        viewHolder.itemImage.setImageResource(imageData.get(position));

        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickButton(position);
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