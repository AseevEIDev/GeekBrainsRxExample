package com.speakapp.myapplication.users.overflow;

import com.google.gson.annotations.SerializedName;
import com.speakapp.myapplication.users.User;

public class StackOverflowUser {

  @SerializedName("display_name") String name;
  @SerializedName("profile_image") String avatar;
  @SerializedName("account_id") long userId;

  public User mapToUser() {
    return new User(name, avatar, userId);
  }
}
