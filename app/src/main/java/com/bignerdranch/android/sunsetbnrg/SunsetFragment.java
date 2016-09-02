package com.bignerdranch.android.sunsetbnrg;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by smaikap on 2/9/16.
 */
public class SunsetFragment extends Fragment {
    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        this.mSceneView = view;
        this.mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SunsetFragment.this.startAnimation();
            }
        });
        this.mSunView = view.findViewById(R.id.sun);
        this.mSkyView = view.findViewById(R.id.sky);

        final Resources resources = this.getResources();
        this.mBlueSkyColor = resources.getColor(R.color.blue_sky);
        this.mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        this.mNightSkyColor = resources.getColor(R.color.night_sky);
        return view;
    }

    private void startAnimation() {
        final float sunYStart = this.mSunView.getTop();
        final float sunYEnd = this.mSkyView.getHeight();

        final ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(this.mSunView, "y", sunYStart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        final ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(this.mSkyView, "backgroundColor", this.mBlueSkyColor, this.mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        final ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(this.mSkyView, "backgroundColor", this.mSunsetSkyColor, mNightSkyColor).setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator);
        animatorSet.start();
    }
}
