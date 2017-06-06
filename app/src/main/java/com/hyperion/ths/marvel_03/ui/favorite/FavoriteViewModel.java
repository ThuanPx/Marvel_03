package com.hyperion.ths.marvel_03.ui.favorite;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import java.util.List;

/**
 * Created by ths on 06/06/2017.
 */

public class FavoriteViewModel extends BaseViewModel implements OnItemClickListener {
    private FavoriteFragmentAdapter mFavoriteFragmentAdapter;
    private HeroRepository mHeroRepository;

    public FavoriteViewModel(FavoriteFragmentAdapter favoriteFragmentAdapter,
            HeroRepository heroRepository) {
        mFavoriteFragmentAdapter = favoriteFragmentAdapter;
        mFavoriteFragmentAdapter.setItemClickListener(this);
        mHeroRepository = heroRepository;
    }

    public FavoriteFragmentAdapter getAdapter() {
        return mFavoriteFragmentAdapter;
    }

    public GridLayoutManager getGridLayout() {
        return new GridLayoutManager(null, 2);
    }

    public void getAllHero() {
        mHeroRepository.getAllHero().subscribe(new DisposableObserver<List<Hero>>() {
            @Override
            public void onNext(@NonNull List<Hero> heroes) {
                mFavoriteFragmentAdapter.updateData(heroes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Error", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onItemRecyclerViewClick(Hero item) {
    }

    @Override
    public void onItemFavoriteClick(Hero hero) {

    }
}

