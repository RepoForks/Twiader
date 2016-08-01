package com.github.jydimir.twiader.reader;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.TwiaderApp;
import com.github.jydimir.twiader.injection.components.DaggerReaderActivityComponent;
import com.github.jydimir.twiader.injection.modules.ApplicationModule;
import com.github.jydimir.twiader.injection.modules.ReaderActivityModule;
import com.github.jydimir.twiader.login.SessionActivity;
import com.github.jydimir.twiader.services.TwiaderService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderActivity extends AppCompatActivity implements ServiceConnection {

    @BindView(R.id.main_content)
    ViewGroup content;
    @BindView(R.id.reader_toolbar)
    Toolbar toolbar;
    @Inject
    ReaderContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ReaderContract.View mvpView = (ReaderContract.View) getLayoutInflater()
                .inflate(R.layout.reader_view, content)
                .findViewById(R.id.reader_view);
        DaggerReaderActivityComponent.builder()
                .applicationComponent(TwiaderApp.get(this).getApplicationComponent())
                .applicationModule(new ApplicationModule(TwiaderApp.get(this)))
                .readerActivityModule(new ReaderActivityModule(mvpView))
                .build()
                .inject(this);
        bindService(new Intent(this, TwiaderService.class), this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_refresh:
                presenter.refresh();
                return true;
            case R.id.mi_mark_as_read:
                presenter.markAllAsRead();
                return true;
            case R.id.mi_log_out:
                startSessionActivityWithLogoutAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        TwiaderService service = ((TwiaderService.Binder) binder).getService();
        presenter.setService(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    private void startSessionActivityWithLogoutAction() {
        Intent intent = new Intent(this, SessionActivity.class);
        intent.setAction(SessionActivity.ACTION_LOG_OUT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
