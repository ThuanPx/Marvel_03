package com.hyperion.ths.marvel_03.ui.main;

import android.databinding.Bindable;
import com.hyperion.ths.marvel_03.BR;
import com.hyperion.ths.marvel_03.connection.MainReceiver;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;

/**
 * Created by ths on 27/05/2017.
 */

public class MainViewModel extends BaseViewModel implements NetworkChangeListener {
    private MainViewPageAdapter mMainViewPageAdapter;
    private boolean mIsConnected;

    public MainViewModel(MainViewPageAdapter mainViewPageAdapter, MainReceiver mainReceiver) {
        mMainViewPageAdapter = mainViewPageAdapter;
        mainReceiver.setNetworkChangeListener(this);
    }

    public MainViewPageAdapter getMainViewPageAdapter() {
        return mMainViewPageAdapter;
    }

    @Bindable
    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
        notifyPropertyChanged(BR.connected);
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        setConnected(isConnected);
    }
}
