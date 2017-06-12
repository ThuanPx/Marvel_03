package com.hyperion.ths.marvel_03.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.hyperion.ths.marvel_03.ui.main.NetworkChangeListener;

/**
 * Created by ths on 12/06/2017.
 */

public class MainReceiver extends BroadcastReceiver {
    private NetworkChangeListener mNetworkChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            mNetworkChangeListener.onNetworkChange(false);
        } else {
            mNetworkChangeListener.onNetworkChange(true);
        }
    }

    public void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        mNetworkChangeListener = networkChangeListener;
    }
}
