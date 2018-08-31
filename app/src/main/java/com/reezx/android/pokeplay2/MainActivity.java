package com.reezx.android.pokeplay2;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import layout.GuessPokemonFragment;
import layout.MainFragment;


public class MainActivity extends AppCompatActivity
    implements MainFragment.OnMainFragmentInteractionListener,
                GuessPokemonFragment.OnGuessFragmentInteractionListener{
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;

    public static Typeface font_1;
    public static Typeface font_2;
    private final Handler mHideHandler = new Handler();
    public static AssetManager assetManager;

    private View mContentView;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentView = findViewById(R.id.relativeLayoutMainActivity);
        font_1 = Typeface.createFromAsset(this.getAssets(),"font/font_1.ttf");
        font_2 = Typeface.createFromAsset(this.getAssets(),"font/font_2.otf");
        //Load Main Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();

        fragmentTransaction.replace(R.id.frameLayoutMainActivity,fragment);
        fragmentTransaction.commit();

        assetManager = this.getAssets();


        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onMainFragmentInteraction(int buttonID) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (buttonID) {
            case 1:
                //Who's that Pokemon --> GuessPokemonFragment
                fragment = new GuessPokemonFragment();
                break;
            case 2:
                Toast.makeText(getBaseContext(),"In Development",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getBaseContext(),"Something is Wrong... Contact Developer",Toast.LENGTH_SHORT).show();
        }
        if(fragment != null) {
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frameLayoutMainActivity, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onGuessFragmentInteraction(int score) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FinalScoreFragment.newInstance(score);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frameLayoutMainActivity,fragment);
        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }
}
