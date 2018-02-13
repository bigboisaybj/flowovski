package com.bright.bright.settings_new;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bright.bright.R;

/**
 * Created by bryanjordan on 13/2/18.
 */

public class account_user_fragment extends Fragment {

    View genealLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        genealLayout = inflater.inflate(R.layout.settings_general_account, container, false);

        return genealLayout;

    }
}
