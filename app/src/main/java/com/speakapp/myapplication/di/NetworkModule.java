package com.speakapp.myapplication.di;

import com.speakapp.myapplication.users.github.GitHubService;
import com.speakapp.myapplication.users.overflow.StackOverflowService;
import dagger.Module;
import dagger.Provides;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

  @Provides
  @PerApplication
  GitHubService provideGitHubService(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GitHubService.class);
  }

  @Provides
  @PerApplication
  StackOverflowService provideStackOverflowService(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(StackOverflowService.class);
  }

  @Provides
  @PerApplication
  OkHttpClient provideOkHttpClient(@Private HttpLoggingInterceptor httpLoggingInterceptor) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build();
    return okHttpClient;
  }

  @Provides
  @PerApplication
  @Private
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    return new HttpLoggingInterceptor();
  }

  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  private @interface Private {
  }
}
