package com.hyperion.ths.marvel_03.data.source.remote.api;

import com.hyperion.ths.marvel_03.data.model.CharactersList;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroDataSource;
import com.hyperion.ths.marvel_03.data.source.remote.api.service.MarvelApi;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;

/**
 * Created by ths on 31/05/2017.
 */

public class HeroRemoteDataSource extends BaseRemoteDataSource
        implements HeroDataSource.RemoteDataSource {
    public HeroRemoteDataSource(MarvelApi marvelApi) {
        super(marvelApi);
    }

    @Override
    public Observable<List<Hero>> getAllCharacters(int offset, Long ts, String keyApi,
            String hash) {
        return mMarvelApi.getCharacters(offset, ts, keyApi, hash)
                .flatMap(new Function<CharactersList, Observable<List<Hero>>>() {
                    @Override
                    public Observable<List<Hero>> apply(@NonNull CharactersList charactersList)
                            throws Exception {
                        if (charactersList != null) {
                            return Observable.just(charactersList.getHeroesLists().getHeroes());
                        }
                        return Observable.error(new NullPointerException());
                    }
                });
    }
}
