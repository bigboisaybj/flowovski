package com.bright.bright.settings;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import com.bright.bright.R;

public class settings_titlesAdapter extends RecyclerView.Adapter<settings_titlesAdapter.ViewHolder> {

    private ArrayList<String> titleData;
    private ArrayList<Float> alphaValues;
    //private settings_HomeAdapter.SettingsListAdapterListener mListener;

    public settings_titlesAdapter(ArrayList<String> titleData, ArrayList<Float> alpha) {
        this.titleData = titleData;
        this.alphaValues = alpha;
        //this.mListener = mListener;
    }

    public interface SettingsListAdapterListener {
        void onSettingsClickIcon(int position);
    }

    @Override
    public settings_titlesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_title, viewGroup, false);
        settings_titlesAdapter.ViewHolder viewHolder = new settings_titlesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(settings_titlesAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.itemTitle.setText(titleData.get(position));
        viewHolder.itemTitle.setAlpha(alphaValues.get(position));
        viewHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "clicked position:" + position);
                //mListener.onSettingsClickIcon(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.settingsTitle);
        }
    }

}
