package com.hyperion.ths.marvel_03.ui.favorite;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import com.hyperion.ths.marvel_03.ui.heroinfo.HeroInfoActivity;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import java.util.List;

/**
 * Created by ths on 06/06/2017.
 */

public class FavoriteViewModel extends BaseViewModel implements OnItemClickListener {
    private static final String TAG = FavoriteViewModel.class.getSimpleName();
    private FavoriteFragmentAdapter mFavoriteFragmentAdapter;
    private HeroRepository mHeroRepository;
    private Navigator mNavigator;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public FavoriteViewModel(FavoriteFragmentAdapter favoriteFragmentAdapter,
            HeroRepository heroRepository, Navigator navigator) {
        mFavoriteFragmentAdapter = favoriteFragmentAdapter;
        mFavoriteFragmentAdapter.setFavoriteClickListener(this);
        mFavoriteFragmentAdapter.setItemClickListener(this);
        mHeroRepository = heroRepository;
        mNavigator = navigator;
    }

    public FavoriteFragmentAdapter getAdapter() {
        return mFavoriteFragmentAdapter;
    }

    public GridLayoutManager getGridLayout() {
        return new GridLayoutManager(null, 2);
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    public void getAllHero() {
        mHeroRepository.getAllHero()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<List<Hero>>() {
                    @Override
                    public void accept(@NonNull List<Hero> heroes) throws Exception {
                        mFavoriteFragmentAdapter.updateData(heroes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void onItemRecyclerViewClick(Hero item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_HERO, item);
        mNavigator.startActivity(HeroInfoActivity.class, bundle);
    }

    @Override
    public void onItemFavoriteClick(final Hero hero) {
        mHeroRepository.deleteHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new DisposableObserver<Void>() {
                    @Override
                    public void onNext(@NonNull Void aVoid) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getAllHero();
                    }
                });
    }
}

