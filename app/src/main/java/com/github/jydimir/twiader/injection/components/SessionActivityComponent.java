package com.github.jydimir.twiader.injection.components;

import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.injection.scopes.PerActivity;
import com.github.jydimir.twiader.login.SessionActivity;

import dagger.Component;

@PerActivity
@Component(modules = ApplicationModule.class, dependencies = ApplicationComponent.class)
public interface SessionActivityComponent {
    void inject(SessionActivity activity);
}
