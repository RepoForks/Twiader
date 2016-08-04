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

package com.github.jydimir.twiader.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.TwiaderApp;
import com.github.jydimir.twiader.injection.components.DaggerSessionActivityComponent;
import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.reader.ReaderActivity;
import com.github.jydimir.twiader.util.PreferencesConstants;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SessionActivity extends AppCompatActivity {

    public static final String ACTION_LOG_OUT = "log_out";

    @Inject
    RxSharedPreferences rxPreferences;
    @BindView(R.id.login_button)
    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DaggerSessionActivityComponent.builder()
                .applicationModule(new ApplicationModule(TwiaderApp.get(this)))
                .applicationComponent(TwiaderApp.get(this).getApplicationComponent())
                .build()
                .inject(this);

        Intent intent = getIntent();
        if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_LOG_OUT)
                && Twitter.getSessionManager().getActiveSession() != null) {
            Twitter.logOut();
        }

        if (Twitter.getSessionManager().getActiveSession() != null) {
            startNextActivity();
        } else {
            loginButton.setCallback(loginCallback);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void startNextActivity() {
        Intent intent = new Intent(SessionActivity.this, ReaderActivity.class);
        startActivity(intent);
        finish();
    }

    private Callback loginCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            String username = Twitter.getSessionManager().getActiveSession().getUserName();
            Preference<String> usernamePref = rxPreferences.getString(PreferencesConstants.USERNAME);
            usernamePref.set(username); //asynchronously
            startNextActivity();
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(SessionActivity.this, R.string.login_error,
                    Toast.LENGTH_LONG).show();
        }
    };
}
