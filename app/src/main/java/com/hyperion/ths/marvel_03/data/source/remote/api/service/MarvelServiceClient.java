package com.hyperion.ths.marvel_03.data.source.remote.api.service;

import android.app.Application;
import android.support.annotation.NonNull;
import com.hyperion.ths.marvel_03.utils.Constant;

/**
 * Created by ths on 30/05/2017.
 */

public class MarvelServiceClient extends ServiceClient {
    private static MarvelApi mMarvelApiInstance;

    public static void initialize(@NonNull Application application) {
        mMarvelApiInstance = createService(application, Constant.END_POINT_URL, MarvelApi.class);
    }

    public static MarvelApi getInstance() {
        if (mMarvelApiInstance == null) {
            throw new RuntimeException("Need call method NameServiceClient#initialize() first");
        }
        return mMarvelApiInstance;
    }
}
