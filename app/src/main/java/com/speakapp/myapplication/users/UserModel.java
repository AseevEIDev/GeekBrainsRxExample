package com.speakapp.myapplication.users;

import io.reactivex.Single;
import java.util.List;

public interface UserModel {

  public Single<List<User>> getUsers();

  public interface ModelResponse<T> {

    public void onSuccess(T response);

    public void onError(Throwable error);
  }
}
