package com.hyperion.ths.marvel_03.data.source;

import android.support.annotation.NonNull;
import com.hyperion.ths.marvel_03.data.model.Hero;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by ths on 30/05/2017.
 */

public interface HeroDataSource {
    /**
     * interface local
     */
    interface LocalDataSource {

        Observable<Hero> insertHero(@NonNull Hero hero);

        Observable<List<Hero>> getAllHero();

        Observable<Hero> getHeroByName(@NonNull String name);

        Observable<Hero> deleteHero(@NonNull Hero hero);
    }

    /**
     * interface remote.
     */
    interface RemoteDataSource {
        Observable<List<Hero>> getAllCharacters(@NonNull int offset, Long ts, String keyApi,
                String hash);

        Observable<List<Hero>> getCharactersByName(@NonNull String name, Long ts, String keyApi,
                String hash);
    }
}
