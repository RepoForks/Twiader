package com.github.jydimir.twiader.injection.components;

import com.github.jydimir.twiader.data.Repository;
import com.github.jydimir.twiader.data.local.LocalDataSource;
import com.github.jydimir.twiader.data.remote.RemoteDataSource;
import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.util.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    RxBus provideRxBus();

    LocalDataSource provideLocalDataSource();

    RemoteDataSource provideRemoteDataSource();

    Repository provideReaderRepository();
}
