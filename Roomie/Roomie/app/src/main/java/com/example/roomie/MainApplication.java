package com.example.roomie;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("DEV","-------MainApplication CREATED!------");
    }
}
