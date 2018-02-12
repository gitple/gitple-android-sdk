package io.gitple.android.gitpleandroidexample;

import android.app.Application;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import io.gitple.android.sdk.Gitple;

public class MyCustomApplication extends Application {
    private static final String TAG = "Gitple:Application";

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Gitple with appCode
        Gitple.initialize(this, "Put your appCode");

        // hide gitple header
        Gitple.config().setHideHeader(true);

        // gitple user login
        loginUser();


        // Initialize onesignal
        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new OnesignalNotificationReceivedHandler())
                .setNotificationOpenedHandler(new OnesignalNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // send Tag to onesignal
        JSONObject tags = new JSONObject();
        try {
            tags.put("gp", "userid0000001");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OneSignal.sendTags(tags);
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

    private class OnesignalNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String fromKey;

            if (data != null) {
                fromKey = data.optString("from", null);
                if (fromKey != null)
                    Log.i(TAG, "Notification Received: fromKey set with value: " + fromKey);
            }
        }
    }

    private class OnesignalNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            String fromKey;

            if (data != null) {
                fromKey = data.optString("from", null);

                if (fromKey != null) {
                    Log.i(TAG, "Notification Opened: fromKey set with value: " + fromKey);
                }
            }
        }
    }
}
