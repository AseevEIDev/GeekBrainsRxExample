package com.speakapp.myapplication.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.speakapp.myapplication.users.UserDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DatabaseModule {

  @Provides
  @PerApplication
  SQLiteDatabase provideDatabase(Context context) {
    return new UserDatabase(context).getWritableDatabase();
  }
}
