package com.speakapp.myapplication.users.overflow;

import com.speakapp.myapplication.users.UserModel;
import com.speakapp.myapplication.users.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

public class OverflowUsersUserModel implements UserModel {

  private StackOverflowService stackOverflowService;

  public OverflowUsersUserModel(StackOverflowService stackOverflowService) {
    this.stackOverflowService = stackOverflowService;
  }

  @Override
  public Single<List<User>> getUsers() {
    return stackOverflowService.getUsers(1, "stackoverflow")
        .map(UsersResponse::getItems)
        .flatMapObservable(Observable::fromIterable)
        .map(StackOverflowUser::mapToUser)
        .toList();
  }

  public final class UsersResponse {

    private ArrayList<StackOverflowUser> items;

    public UsersResponse(ArrayList<StackOverflowUser> items) {
      this.items = items;
    }

    public ArrayList<StackOverflowUser> getItems() {
      return items;
    }
  }
}
