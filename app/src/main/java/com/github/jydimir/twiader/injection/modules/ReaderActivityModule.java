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
