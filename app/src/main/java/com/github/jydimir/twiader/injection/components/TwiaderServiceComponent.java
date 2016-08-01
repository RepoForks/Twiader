package com.github.jydimir.twiader.injection.components;

import com.github.jydimir.twiader.injection.modules.TwiaderServiceModule;
import com.github.jydimir.twiader.injection.scopes.PerService;
import com.github.jydimir.twiader.services.TwiaderService;

import dagger.Component;

@PerService
@Component(modules = TwiaderServiceModule.class, dependencies = ApplicationComponent.class)
public interface TwiaderServiceComponent {
    void inject(TwiaderService service);
}
