package com.yan.rxbustest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by yan on 2016/9/18.
 */
public class App extends Application {
    RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
          refWatcher= LeakCanary.install(this) ;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
