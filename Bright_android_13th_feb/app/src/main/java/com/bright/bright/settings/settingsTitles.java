package com.bright.bright.settings;

import java.util.ArrayList;

public class settingsTitles {

    private ArrayList<String> settingsTitles;

    public settingsTitles() {
        this.settingsTitles = new ArrayList<>();
    }

    public void addTitle(String title) {
        this.settingsTitles.add(title);
    }

    public String getTitle(int index) {
        return this.settingsTitles.get(index);
    }

    public int size() {
        return this.settingsTitles.size();
    }

}
