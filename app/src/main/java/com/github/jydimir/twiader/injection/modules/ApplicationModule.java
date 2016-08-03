package com.github.jydimir.twiader.injection.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.util.ConnectivityUntil;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    RxSharedPreferences provideRxPreferences() {
        SharedPreferences sharedPreferences =
                application.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return RxSharedPreferences.create(sharedPreferences);
    }

    @Provides
    ConnectivityUntil provideConnectivityUtil() {
        return new ConnectivityUntil(application);
    }
}
