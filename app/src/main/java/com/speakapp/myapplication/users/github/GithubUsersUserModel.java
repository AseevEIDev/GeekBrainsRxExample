package com.speakapp.myapplication.users.github;

import com.speakapp.myapplication.users.UserModel;
import com.speakapp.myapplication.users.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public class GithubUsersUserModel implements UserModel {

  private GitHubService gitHubService;

  public GithubUsersUserModel(GitHubService gitHubService) {
    this.gitHubService = gitHubService;
  }

  @Override
  public Single<List<User>> getUsers() {
    return gitHubService.getUsers(0)
        .flatMapObservable(Observable::fromIterable)
        .map(GitHubUser::mapToUser)
        .toList();
  }


}
