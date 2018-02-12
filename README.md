# Gitple android sdk Examples

gitple for android supports API 15 and above.

 - [gitple android sdk docs](http://guide.gitple.io/#/android-sdk)

## Android java 

### Setting up

1. Changed appCode in MyCustomApplication.java

```
public class MyCustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Gitple with appCode
        Gitple.initialize(this, "your app code");
    }
}
```

2. Open project with Android Studio, Build and Run one the apps


### OneSignal Push Notification

OneSignal is push notification service for websites and mobile([Homepage](https://onesignal.com))

1. Create OneSignal account if you do not already have one
2. Setup OneSignal App ID in `build.gradle`
```
android {
   defaultConfig {
      manifestPlaceholders = [onesignal_app_id: "PUT YOUR ONESIGNAL APP ID HERE",
                              // Project number pulled from dashboard, local value is ignored.
                              onesignal_google_project_number: "REMOTE"]
    }
 }
```

3. Reference : [OneSignal setup for Android](https://documentation.onesignal.com/docs/android-sdk-setup)
