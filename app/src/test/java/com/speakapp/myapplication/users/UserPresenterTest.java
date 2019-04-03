package com.speakapp.myapplication.users;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import android.database.sqlite.SQLiteDatabase;
import com.speakapp.rules.RxImmediateSchedulerRule;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class UserPresenterTest {

  static final List<User> USERS = Arrays.asList(new User("dfdfdd", "dfdfdfd", 1));
  @Rule public RxImmediateSchedulerRule rxRule = new RxImmediateSchedulerRule();

  PublishSubject<List<User>> subject = PublishSubject.create();
  UserPresenter presenter;
  @Mock UserModel userModel;
  @Mock SQLiteDatabase sqLiteDatabase;
  @Mock UserPresenter.View view;

  @Before
  public void setUp() {
    initMocks(this);
    when(userModel.getUsers()).thenReturn(subject.firstOrError());
    presenter = new UserPresenter(userModel, sqLiteDatabase);
  }

  @Test
  public void getUsers() {
    presenter.attachView(view);
    subject.onNext(USERS);
    verify(view).onUserListLoaded(USERS);
  }

  @Test
  public void getUsers_noView() {
    presenter.attachView(view);
    presenter.detachView();
    subject.onNext(USERS);
    verify(view, never()).onUserListLoaded(anyList());
    verify(view, never()).onError(anyString());
  }

  @Test
  public void getUsers_returnDataIfExists() {
    getUsers_noView();
    Mockito.reset(userModel);
    presenter.attachView(view);
    verify(view).onUserListLoaded(USERS);
    verify(userModel, never()).getUsers();
    assertThat(rxRule.hasErrors()).isFalse();
  }
}
