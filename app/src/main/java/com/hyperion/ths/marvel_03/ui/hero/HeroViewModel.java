package com.hyperion.ths.marvel_03.ui.hero;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseRecyclerView;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.ui.heroinfo.HeroInfoActivity;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ths on 31/05/2017.
 */

public class HeroViewModel extends BaseViewModel
        implements BaseRecyclerView.OnRecyclerViewItemClickListener<Hero>,
        BaseRecyclerView.OnItemButtonClickListener<Hero> {
    private HeroRepository mHeroRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private HeroFragmentAdapter mHeroFragmentAdapter;
    private GridLayoutManager mGridLayoutManager;
    private Navigator mNavigator;

    public HeroViewModel(HeroRepository heroRepository, HeroFragmentAdapter heroFragmentAdapter,
            Navigator navigator) {
        mHeroRepository = heroRepository;
        mHeroFragmentAdapter = heroFragmentAdapter;
        mGridLayoutManager = new GridLayoutManager(null, 2);
        mHeroFragmentAdapter.setOnItemButtonClickListener(this);
        mHeroFragmentAdapter.setOnRecyclerViewItemClickListener(this);
        mNavigator = navigator;
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
                        mHeroFragmentAdapter.updateData(heroesList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        startDisposable(disposable);
    }

    @Override
    public void onItemRecyclerViewClick(Hero item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_HERO, item);
        mNavigator.startActivity(HeroInfoActivity.class, bundle);
    }

    @Override
    public void onItemButtonClick(Hero item) {
        if (mHeroRepository.getHeroByName(item.getName()).isEmpty()) {
            if (!mHeroRepository.insertHero(item)) {
                Log.e("Error", " Insert hero error!");
            }
        } else {
            if (!mHeroRepository.deleteHero(item)) {
                Log.e("Error", " Delete hero error!");
            }
        }
    }
}
