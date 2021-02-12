package com.lannguyen.ma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


/** https://github.com/ittianyu/BottomNavigationViewEx **/
public class BottomNavigationViewUtils {
    private static final String TAG = "BottomNavigationView";

    public static void configBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "Config Bottom Navigation View!");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
    }

    //Navigate between activities
    public static void navigating(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch(item.getItemId()){
                    case R.id.draw:  // Activity 0
                        Intent intent1 = new Intent(context, MainActivity.class);
                        // prevent create new instance of the home activity every time press the home button more than once
                        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent1);
                        break;

                    case R.id.image:    // Activity 1
                        Intent intent2 = new Intent(context, ImageActivity.class);
                        // prevent create new instance of the search activity every time press the search button more than once
                        intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent2);
                        break;

                }
                return false;
            }
        });
    }
}
