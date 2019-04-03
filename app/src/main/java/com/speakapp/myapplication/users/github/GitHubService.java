package com.speakapp.myapplication.users.github;

import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

  @GET("users")
  Single<List<GitHubUser>> getUsers(@Query("since") long id);

}
