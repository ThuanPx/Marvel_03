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

        boolean insertHero(@NonNull Hero hero);

        List<Hero> getAllHero();

        List<Hero> getHeroByName(@NonNull String name);

        boolean deleteHero(@NonNull Hero hero);
    }

    /**
     * interface remote.
     */
    interface RemoteDataSource {
        Observable<List<Hero>> getAllCharacters(@NonNull Long ts, String keyApi, String hash);
    }
}
