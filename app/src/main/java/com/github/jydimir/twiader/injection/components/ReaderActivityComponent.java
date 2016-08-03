package com.github.jydimir.twiader.injection.components;

import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.injection.modules.ReaderActivityModule;
import com.github.jydimir.twiader.injection.scopes.PerActivity;
import com.github.jydimir.twiader.reader.ReaderActivity;

import dagger.Component;

@PerActivity
@Component(modules = {ApplicationModule.class, ReaderActivityModule.class}, dependencies = ApplicationComponent.class)
public interface ReaderActivityComponent {
    void inject(ReaderActivity activity);
}
