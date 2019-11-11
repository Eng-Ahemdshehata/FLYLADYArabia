package com.ashehata.flyladyarabia.utility;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

import java.util.logging.Handler;

public class SplashScreen extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(800);
    }
}
