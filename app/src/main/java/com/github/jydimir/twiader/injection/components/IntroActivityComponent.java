package com.github.jydimir.twiader.injection.components;

import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.injection.scopes.PerActivity;
import com.github.jydimir.twiader.intro.IntroActivity;

import dagger.Component;

@PerActivity
@Component(modules = ApplicationModule.class, dependencies = ApplicationComponent.class)
public interface IntroActivityComponent {
    void inject(IntroActivity introActivity);
}
