package io.gitple.android.gitpleandroidexample;

import android.app.Application;
import android.util.Log;

import io.gitple.android.sdk.Gitple;

public class MyCustomApplication extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Gitple with appCode
        Gitple.initialize(this, "xxxxxxxxxxxxxx");

        // hide gitple header
        Gitple.config().setHideHeader(true);

        // gitple user login
        loginUser();
    }

    public void loginUser() {
        // Register gitple user
        Gitple.user().setId("userid0000001")
                .setEmail("android@gitple.com")
                .setName("android user")
                .setPhone("010-0000-0000")
                .setMeta("company", "gitple");
    }

    public void logoutUser() {
        // reset gitple user
        Gitple.user().reset();
    }
}
