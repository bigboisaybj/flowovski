package com.bright.bright.checkout;

import android.view.animation.Interpolator;

/**
 * Created by bryanjordan on 20/10/17.
 */

public class slideIntepolator implements Interpolator{

    @Override
    public float getInterpolation(float paramFloat) {
            return Math.abs(paramFloat -1f);
    }
}
