/*
 * Copyright 2016  Andrii Lisun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
