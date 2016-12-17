package com.example.byoungjune.term;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by ByoungJune on 2016-12-16.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 3000);// 3 초
    }
}