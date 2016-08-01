package com.github.jydimir.twiader.injection.modules;

import android.content.Context;

import com.github.jydimir.twiader.services.BetterTTS;
import com.github.jydimir.twiader.services.TwiaderService;

import dagger.Module;
import dagger.Provides;

@Module
public class TwiaderServiceModule {

    private TwiaderService service;

    public TwiaderServiceModule(TwiaderService service) {
        this.service = service;
    }

    @Provides
    Context provideContext() {
        return service;
    }

    @Provides
    BetterTTS.SpeakProgressListener provideProgressListener() {
        return service;
    }
}
