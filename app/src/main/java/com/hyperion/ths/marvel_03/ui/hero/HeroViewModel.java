package com.hyperion.ths.marvel_03.ui.hero;

import android.util.Log;
import com.hyperion.ths.marvel_03.data.model.Heroes;
import com.hyperion.ths.marvel_03.data.source.remote.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ths on 31/05/2017.
 */

public class HeroViewModel extends BaseViewModel {
    private HeroRepository mHeroRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public HeroViewModel(HeroRepository heroRepository) {
        mHeroRepository = heroRepository;
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    public void getAllHeroesListener() throws UnsupportedEncodingException {
        Disposable disposable = mHeroRepository.getAllHero(Constant.TIMESTAMP, Constant.PUBLIC_KEY,
                Constant.getHashKey())
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribeWith(new DisposableObserver<List<Heroes>>() {
                    @Override
                    public void onNext(@NonNull List<Heroes> heroes) {
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
}
