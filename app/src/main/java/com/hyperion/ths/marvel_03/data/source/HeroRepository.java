package com.hyperion.ths.marvel_03.data.source;

import com.hyperion.ths.marvel_03.data.model.Hero;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by ths on 30/05/2017.
 */

public class HeroRepository {
    private HeroDataSource.RemoteDataSource mRemoteDataSource;
    private HeroDataSource.LocalDataSource mLocalDataSource;

    public HeroRepository(HeroDataSource.RemoteDataSource remoteDataSource,
            HeroDataSource.LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public Observable<List<Hero>> getAllHero(int offset, long ts, String keyApi, String hash) {
        return mRemoteDataSource.getAllCharacters(offset, ts, keyApi, hash);
    }

    public Observable<Hero> insertHero(Hero hero) {
        return mLocalDataSource.insertHero(hero);
    }

    public Observable<List<Hero>> getAllHero() {
        return mLocalDataSource.getAllHero();
    }

    public Observable<Hero> getHeroByName(String name) {
        return mLocalDataSource.getHeroByName(name);
    }

    public Observable<Void> deleteHero(Hero hero) {
        return mLocalDataSource.deleteHero(hero);
    }
}
