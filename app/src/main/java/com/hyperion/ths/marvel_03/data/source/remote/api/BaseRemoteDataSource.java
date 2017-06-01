package com.hyperion.ths.marvel_03.data.source.remote.api;

import com.hyperion.ths.marvel_03.data.source.remote.api.service.MarvelApi;

/**
 * Created by ths on 31/05/2017.
 */

public abstract class BaseRemoteDataSource {
    MarvelApi mMarvelApi;

    public BaseRemoteDataSource(MarvelApi marvelApi) {
        mMarvelApi = marvelApi;
    }
}
