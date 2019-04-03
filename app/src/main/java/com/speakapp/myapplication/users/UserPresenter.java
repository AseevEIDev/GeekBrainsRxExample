package com.speakapp.myapplication.users;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class UserPresenter {

  public interface View {

    void onUserListLoaded(List<User> userList);

    void onError(String errorMessage);
  }

  private UserModel userModel;
  private SQLiteDatabase sqLiteDatabase;
  private View view;
  private Disposable disposable;
  private List<User> userList;

  public UserPresenter(UserModel userModel, SQLiteDatabase sqLiteDatabase) {
    this.userModel = userModel;
    this.sqLiteDatabase = sqLiteDatabase;
  }

  public void stopLoading() {
    if (disposable != null) {
      disposable.dispose();
    }
  }

  public void attachView(View view) {
    this.view = view;
    if (userList != null) {
      view.onUserListLoaded(userList);
    } else {
      loadUsers();
    }
  }

  public void detachView() {
    this.view = null;
  }

  private void loadUsers() {
    disposable = userModel.getUsers()
        //.doOnSuccess(list -> UserDatabase.deleteUsers(sqLiteDatabase))
        //.doOnSuccess(list -> UserDatabase.addUsers(list, sqLiteDatabase))
        //.doOnError(error -> Log.d("DDDD", error.getMessage()))
        /*.onErrorResumeNext(error -> UserDatabase.getAllUsers(sqLiteDatabase)
            .flatMap(users -> users.isEmpty() ? Single.error(new RuntimeException(
                "No users in database"
            )) : Single.just(users))
        )*/
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess(list -> userList = list)
        .filter(users -> view != null)
        .subscribe(users -> view.onUserListLoaded(users), error -> view.onError(error.getMessage()));
    Crashlytics.logException(new RuntimeException("Olololo"));
  }
}
