package com.speakapp.myapplication.users.github;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.speakapp.myapplication.users.User;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GithubUsersUserModelTest {

  static final List<GitHubUser> GITHUBUSERS = Arrays.asList(new GitHubUser("dfdfdd", "dfdfdfd", 1));
  static final List<User> USERS = Arrays.asList(new User("dfdfdd", "dfdfdfd", 1));
  static final Throwable THROWABLE = new Exception("Error occurred");

  PublishSubject<List<GitHubUser>> subject = PublishSubject.create();
  GithubUsersUserModel userModel;
  @Mock GitHubService service;

  @Before
  public void setUp() {
    initMocks(this);
    when(service.getUsers(0)).thenReturn(subject.firstOrError());
    userModel = new GithubUsersUserModel(service);
  }

  @Test
  public void getUsers() {
    TestObserver<List<User>> testObserver = userModel.getUsers().test();
    subject.onNext(GITHUBUSERS);
    testObserver.assertValue(USERS);
    testObserver.assertNoErrors();
  }

  @Test
  public void getUsers_error() {
    TestObserver<List<User>> testObserver = userModel.getUsers().test();
    subject.onError(THROWABLE);
    testObserver.assertNoValues();
    testObserver.assertError(THROWABLE);
  }
}
