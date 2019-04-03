package com.speakapp.myapplication;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends Application {

  AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    appComponent = DaggerAppComponent.builder()
        .setContextBuilder(this)
        .createComponent();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

}
