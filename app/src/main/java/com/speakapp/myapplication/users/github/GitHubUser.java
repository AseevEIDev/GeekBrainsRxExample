package com.speakapp.myapplication.users.github;

import com.google.gson.annotations.SerializedName;
import com.speakapp.myapplication.users.User;

public class GitHubUser {

  @SerializedName("login") String name;
  @SerializedName("avatar_url") String avatar;
  @SerializedName("id") long userId;

  public GitHubUser(String name, String avatar, long userId) {
    this.name = name;
    this.avatar = avatar;
    this.userId = userId;
  }

  public User mapToUser() {
    return new User(name, avatar, userId);
  }
}
