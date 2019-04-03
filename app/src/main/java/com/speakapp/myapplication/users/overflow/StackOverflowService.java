package com.speakapp.myapplication.users.overflow;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackOverflowService {

  @GET("users")
  Single<OverflowUsersUserModel.UsersResponse> getUsers(@Query("page") int page, @Query("site") String site);

}
