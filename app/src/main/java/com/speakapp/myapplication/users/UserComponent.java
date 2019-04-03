package com.speakapp.myapplication.users;

import com.speakapp.myapplication.di.PerFragment;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

  void injectUserFragment(UsersFragment usersFragment);
}
