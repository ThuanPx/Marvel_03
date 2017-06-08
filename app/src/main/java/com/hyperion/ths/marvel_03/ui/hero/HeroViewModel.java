package com.hyperion.ths.marvel_03.ui.hero;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 31/05/2017.
 */

public class HeroViewModel extends BaseViewModel implements OnItemClickListener {
    private static final String TAG = HeroViewModel.class.getSimpleName();
    private HeroRepository mHeroRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private HeroFragmentAdapter mHeroFragmentAdapter;
    private GridLayoutManager mGridLayoutManager;
    private Navigator mNavigator;
    private List<Hero> mHeroList;

    public HeroViewModel(HeroRepository heroRepository, HeroFragmentAdapter heroFragmentAdapter,
            Navigator navigator) {
        mHeroRepository = heroRepository;
        mHeroFragmentAdapter = heroFragmentAdapter;
        mGridLayoutManager = new GridLayoutManager(null, 2);
        mHeroFragmentAdapter.setOnItemButtonClickListener(this);
        mHeroFragmentAdapter.setOnRecyclerViewItemClickListener(this);
        mNavigator = navigator;
        mHeroList = new ArrayList<>();
    }

    public GridLayoutManager getGridLayout() {
        return mGridLayoutManager;
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    public HeroFragmentAdapter getAdapter() {
        return mHeroFragmentAdapter;
    }

    public void getAllHeroes() throws UnsupportedEncodingException {
        Disposable disposable = mHeroRepository.getAllHero(Constant.TIMESTAMP, Constant.PUBLIC_KEY,
                Constant.getHashKey())
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribeWith(new DisposableObserver<List<Hero>>() {
                    @Override
                    public void onNext(@NonNull List<Hero> heroesList) {
                        mHeroList.clear();
                        mHeroList.addAll(heroesList);
                        mHeroFragmentAdapter.updateData(mHeroList);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        startDisposable(disposable);
    }

    public void reLoadList() {
        mHeroFragmentAdapter.updateData(mHeroList);
    }

    @Override
    public void onItemRecyclerViewClick(Hero item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_HERO, item);
        mNavigator.startActivity(HeroInfoActivity.class, bundle);
    }

    @Override
    public void onItemFavoriteClick(final Hero item) {
        mHeroRepository.getHeroByName(item.getName())
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Hero>() {
                    @Override
                    public void accept(@NonNull Hero hero) throws Exception {
                        if (hero.getId() == Constant.POINT) {
                            insertHero(item);
                        } else {
                            deleteHero(item);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    private void deleteHero(Hero hero) {
        mHeroRepository.deleteHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@NonNull Void aVoid) throws Exception {
                        //TODO coming soon Toast
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    private void insertHero(Hero hero) {
        mHeroRepository.insertHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@NonNull Void aVoid) throws Exception {
                        //TODO coming soon Toast
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }
}
