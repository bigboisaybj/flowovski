package com.bright.bright.settings;

import android.util.Log;

import java.util.ArrayList;

public class userData {

    private ArrayList<String> userData;
    private String latestData;

    String userReview;
    boolean experienceComplete;
    boolean addressComplete;
    boolean photosComplete;

    public userData() {
        this.userData = new ArrayList<>();
    }

    public void addData(String userItem, String presentData) {
        this.userData.add(userItem);
        latestData = presentData;
    }

    public void showData() {
        for (int i = 0; i < this.userData.size(); i++) {
            Log.i("TAG", "userData collected: " + this.userData.get(i));
        }
    }

    public int size() {
        return this.userData.size();
    }

    public String getData(int index) {
        return this.userData.get(index);
    }

    public String getLatestData() {
        return this.latestData;
    }

    public String userReview() {
        if (!this.userData.isEmpty()) {

            if (this.userData.get(1).equals("Yes")) {
                userReview = this.userData.get(0) + " is open everyday from " + this.userData.get(2) + " to " + this.userData.get(3) + " but closed on " + this.userData.get(4) + ".";
            }

            else if (this.userData.get(1).equals("No")) {
                userReview = "Saturday: " + this.userData.get(2) + "to " + this.userData.get(3);

            }
            else if (this.userData.get(1).equals("24/7")) {
                userReview = this.userData.get(0) + " is open 24/7 but closed on " + this.userData.get(2) + " .";
            }
        }
        return userReview;
    }

    public boolean activateScrollView() {
        boolean result = false;

        if (this.userData.get(0).equals("24/7")) {
            return true;
        }
        return  false;
    }

    public boolean atHomeRegistrationComplete() {
        if (!this.userData.get(0).isEmpty()) {
            experienceComplete = true;
        }
        if (!this.userData.get(1).isEmpty()) {
            addressComplete = true;
        }
        if (!this.userData.get(2).isEmpty()) {
            photosComplete = true;
        }
        if (experienceComplete && addressComplete && photosComplete) {
            Log.i("TAG", "all completed");
            return true;
        }

        return false;
    }
}
