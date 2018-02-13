package com.bright.bright.settings;

import android.view.animation.Interpolator;

public class AnimationInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float paramFloat) {
        return Math.abs(paramFloat -1f);
    }
}