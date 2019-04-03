package com.speakapp.myapplication.users;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.speakapp.myapplication.App;
import com.speakapp.myapplication.R;
import java.util.List;
import javax.inject.Inject;

public class UsersFragment extends Fragment implements UserPresenter.View {

  public static final String KEY = "USER_MODEL";
  public static final String GITHUB = "GitHub";
  public static final String STACK_OVERFLOW = "Stack";

  @BindView(R.id.recycler)
  RecyclerView recyclerView;

  @BindView(R.id.progress)
  ProgressBar progressBar;

  private String key;
  private Unbinder unbinder;
  private UserRecycleAdapter adapter;
  @Inject
  UserPresenter presenter;

  public static UsersFragment newInstance(String userModelName) {
    Bundle args = new Bundle();
    args.putString(KEY, userModelName);
    UsersFragment fragment = new UsersFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_git_hub_users, container, false);
    ViewModelProviders.of(this).get(FragmentModel.class).getUserComponent().injectUserFragment(this);

    unbinder = ButterKnife.bind(this, view);
    adapter = new UserRecycleAdapter();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    key = getArguments().getString(KEY);

    presenter.attachView(this);

    return view;
  }

  @Override
  public void onUserListLoaded(List<User> userList) {
    adapter.addUsers(userList);
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void onError(String errorMessage) {
    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.detachView();
    unbinder.unbind();
    if (isRemoving()) {
      presenter.stopLoading();
    }
  }

  public static class FragmentModel extends AndroidViewModel {

    UserComponent userComponent;

    public FragmentModel(@NonNull Application application) {
      super(application);
      userComponent = ((App) application).getAppComponent().createUserComponent();
    }

    public UserComponent getUserComponent() {
      return userComponent;
    }

    @Override
    protected void onCleared() {
      super.onCleared();
      userComponent = null;
    }
  }
}
