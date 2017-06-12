package com.hyperion.ths.marvel_03.ui.hero;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import com.hyperion.ths.marvel_03.ui.heroinfo.HeroInfoActivity;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManager;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManagerImpl;
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

public class HeroViewModel extends BaseViewModel
        implements OnItemClickListener, DialogManager.ClickDialogListener {
    private static final String TAG = HeroViewModel.class.getSimpleName();
    private HeroRepository mHeroRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private HeroFragmentAdapter mHeroFragmentAdapter;
    private Navigator mNavigator;
    private List<Hero> mHeroList;
    private DialogManager mDialogManager;
    private DialogManagerImpl mDialogManagerImpl;
    //Method getAllHeroes() is only running one
    private Boolean mIsStart;

    public HeroViewModel(HeroRepository heroRepository, HeroFragmentAdapter heroFragmentAdapter,
            Navigator navigator, DialogManager dialogManager) {
        mHeroRepository = heroRepository;
        mHeroFragmentAdapter = heroFragmentAdapter;
        mHeroFragmentAdapter.setOnItemButtonClickListener(this);
        mHeroFragmentAdapter.setOnRecyclerViewItemClickListener(this);
        mNavigator = navigator;
        mHeroList = new ArrayList<>();
        mDialogManager = dialogManager;
        mDialogManagerImpl = new DialogManagerImpl(navigator.getActivity());
        mDialogManagerImpl.setOnClickDialogListener(HeroViewModel.this);
        mIsStart = false;
    }

    public GridLayoutManager getGridLayout() {
        return new GridLayoutManager(null, 2);
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    public HeroFragmentAdapter getAdapter() {
        return mHeroFragmentAdapter;
    }

    public void getAllHeroes() throws UnsupportedEncodingException {
        if (mIsStart) {
            return;
        }
        mDialogManager.showProgressDialog();
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
                        mDialogManager.dismissProgressDialog();
                        mIsStart = true;
                        mDialogManager.showDialogError(
                                mNavigator.getActivity().getString(R.string.message_error_connect));
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        mDialogManager.dismissProgressDialog();
                        mIsStart = true;
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
        mDialogManager.showProgressDialog();
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
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    private void deleteHero(Hero hero) {
        mDialogManager.showProgressDialog();
        mHeroRepository.deleteHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@NonNull Void aVoid) throws Exception {
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    private void insertHero(Hero hero) {
        mDialogManager.showProgressDialog();
        mHeroRepository.insertHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@NonNull Void aVoid) throws Exception {
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void onClickDialog() {
        try {
            getAllHeroes();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
}
