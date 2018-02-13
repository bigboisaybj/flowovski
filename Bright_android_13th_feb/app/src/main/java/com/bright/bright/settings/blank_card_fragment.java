package com.bright.bright.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bright.bright.R;

public class blank_card_fragment extends Fragment {

    View testCardLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        testCardLayout = inflater.inflate(R.layout.blank_card, container, false);

        /*
        nextText = (TextView) testCardLayout.findViewById(R.id.testCardNext);
        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFirstRow(1);
            }
        });
        */
        return  testCardLayout;
    }

}