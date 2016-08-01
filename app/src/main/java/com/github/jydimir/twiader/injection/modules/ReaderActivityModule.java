package com.github.jydimir.twiader.injection.modules;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.data.Repository;
import com.github.jydimir.twiader.reader.ReaderContract;
import com.github.jydimir.twiader.reader.ReaderPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ReaderActivityModule {

    private ReaderContract.View view;

    public ReaderActivityModule(ReaderContract.View view) {
        this.view = view;
    }

    @Provides
    ReaderContract.Presenter providePresenter(Repository repository, RxSharedPreferences rxPreferences) {
        return new ReaderPresenter(repository, view, rxPreferences);
    }
}
