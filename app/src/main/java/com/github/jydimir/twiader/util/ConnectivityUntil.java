package com.github.jydimir.twiader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

public class ConnectivityUntil {

    private Context context;

    @Inject
    public ConnectivityUntil(Context context) {
        this.context = context;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }
}
