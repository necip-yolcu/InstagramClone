package com.example.necip.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Cpzw3OSqbsRoGSGBcqUq4HJQi4rVFa9CVFrHD970")
                // if defined
                .clientKey("zl7Unjc5zIFvszaWKZ49Qckt8XoovnSV3GR00UCt")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
