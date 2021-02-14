package com.lannguyen.ma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    //For debugging
    private static final String TAG = "SplashScreenActivity";

    //time setting
    private static final int SPLASHSCREEN = 6000;

    //Variables
    Animation topAnim, botAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash_screen);
        image = findViewById(R.id.splashImage);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        slogan.setAnimation(botAnim);


        image.animate().translationY(-1600).setDuration(2000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(2000).setStartDelay(4000);
        slogan.animate().translationY(1400).setDuration(2000).setStartDelay(4000);

        // Handle delay process for splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run!");
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();   // remove from activity list so users can't return to splash screen activity anymore
            }
        }, SPLASHSCREEN);

    }

}

