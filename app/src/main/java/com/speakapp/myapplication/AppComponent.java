package com.speakapp.myapplication;

import android.content.Context;
import com.speakapp.myapplication.di.DatabaseModule;
import com.speakapp.myapplication.di.NetworkModule;
import com.speakapp.myapplication.di.PerApplication;
import com.speakapp.myapplication.users.UserComponent;
import dagger.BindsInstance;
import dagger.Component;

@PerApplication
@Component(modules = {NetworkModule.class, DatabaseModule.class})
public interface AppComponent {

  Context getContext();

  UserComponent createUserComponent();

  @Component.Builder
  interface MyBuilder {

    AppComponent createComponent();

    @BindsInstance
    MyBuilder setContextBuilder(Context contextBuilder);
  }
}
