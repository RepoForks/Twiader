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

package com.github.jydimir.twiader.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.TwiaderApp;
import com.github.jydimir.twiader.injection.components.DaggerIntroActivityComponent;
import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.login.SessionActivity;
import com.github.jydimir.twiader.util.PreferencesConstants;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import javax.inject.Inject;

public class IntroActivity extends AppIntro {

    @Inject
    RxSharedPreferences rxPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerIntroActivityComponent.builder()
                .applicationModule(new ApplicationModule(TwiaderApp.get(this)))
                .applicationComponent(TwiaderApp.get(this).getApplicationComponent())
                .build()
                .inject(this);

        Preference<Boolean> firstRunPreference = rxPreferences.getBoolean(PreferencesConstants.FIRST_RUN, true);
        //noinspection ConstantConditions
        if (firstRunPreference.get()) {
            addSlide(AppIntroFragment.newInstance(getString(R.string.intro_title),
                    getString(R.string.intro_description), R.drawable.image_languages_128dp,
                    ContextCompat.getColor(this, R.color.colorPrimary)));
            firstRunPreference.set(false);
        } else {
            finishAndStartSessionActivity();
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finishAndStartSessionActivity();
    }

    private void finishAndStartSessionActivity() {
        Intent intent = new Intent(this, SessionActivity.class);
        startActivity(intent);
        finish();
    }
}
