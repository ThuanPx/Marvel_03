package com.hyperion.ths.marvel_03.data.source.remote;

import com.hyperion.ths.marvel_03.data.model.Heroes;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by ths on 30/05/2017.
 */

public class HeroRepository {
    private HeroDataSource.RemoteDataSource mRemoteDataSource;

    public HeroRepository(HeroDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<List<Heroes>> getAllHero(long ts, String keyApi, String hash) {
        return mRemoteDataSource.getAllCharacters(ts, keyApi, hash);
    }
}
