package com.bright.bright.settings;

import android.util.Log;

import java.util.ArrayList;
import com.bright.bright.R;

public class advertiseData {

    private ArrayList<String> advertData;
    private String latestAdvertData;

    public advertiseData() {
        this.advertData = new ArrayList<>();
    }

    public void addAdvertData(String newData) {
        this.advertData.add(newData);
    }

    public void showAdvertData() {
        for (int i = 0; i < this.advertData.size(); i++) {
            Log.i("TAG", "userData collected: " + this.advertData.get(i));
        }
    }

    public int size() {
        return this.advertData.size();
    }

    public String getAdvertData(int index) {
        return this.advertData.get(index);
    }

    public String getLatestAdvertData() {
        return this.latestAdvertData;
    }
}
