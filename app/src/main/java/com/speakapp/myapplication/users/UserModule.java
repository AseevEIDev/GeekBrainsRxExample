package com.speakapp.myapplication.users;

import android.database.sqlite.SQLiteDatabase;
import com.speakapp.myapplication.di.PerFragment;
import com.speakapp.myapplication.users.github.GitHubService;
import com.speakapp.myapplication.users.github.GithubUsersUserModel;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

  @Provides
  @PerFragment
  UserPresenter provideUserPresenter(SQLiteDatabase database, GitHubService getGitHubService) {
    return new UserPresenter(new GithubUsersUserModel(getGitHubService), database);
  }
}
