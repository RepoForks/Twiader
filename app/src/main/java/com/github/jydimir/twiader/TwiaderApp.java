package com.github.jydimir.twiader;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.github.jydimir.twiader.injection.components.ApplicationComponent;
import com.github.jydimir.twiader.injection.components.DaggerApplicationComponent;
import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class TwiaderApp extends Application {

    public static final String TWITTER_KEY = "Abah2XLlgsgOLHLSUthteJgqx";
    public static final String TWITTER_SECRET = "oWXmX4TCcR4Fpn1BkFqwLXIVzBWqUrZPx8cFe8nQn6BXtkrXhE";

    public static TwiaderApp get(Context context) {
        return (TwiaderApp) context.getApplicationContext();
    }

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
