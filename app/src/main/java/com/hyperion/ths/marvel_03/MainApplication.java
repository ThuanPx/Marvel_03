package com.hyperion.ths.marvel_03;

import android.app.Application;
import com.hyperion.ths.marvel_03.data.source.remote.api.service.MarvelServiceClient;

/**
 * Created by ths on 01/06/2017.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MarvelServiceClient.initialize(this);
    }
}
