/*
 * Pomodoro Productivity Timer
 * Copyright (C) 2016  Lisun Andrii
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
