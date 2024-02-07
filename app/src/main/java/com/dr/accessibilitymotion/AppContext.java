package com.dr.accessibilitymotion;

import android.app.Application;

import com.fxn.stash.Stash;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
    }
}
