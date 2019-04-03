package com.speakapp.myapplication.network;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Network request interceptor, that put headers with authentication token and device id to the
 * request.
 */
public class AddTokenInterceptor implements Interceptor {

  private final SharedPreferences preferences;

  public AddTokenInterceptor(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    String token = preferences.getString("token", "");
    Request.Builder requestBuilder = chain.request().newBuilder();
    if (!token.isEmpty()) {
      requestBuilder.addHeader("X-Token", token);
    }
    return chain.proceed(requestBuilder.build());
  }
}
