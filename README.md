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
