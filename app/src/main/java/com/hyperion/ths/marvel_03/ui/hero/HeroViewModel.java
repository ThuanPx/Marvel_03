package com.hyperion.ths.marvel_03.ui.hero;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import com.hyperion.ths.marvel_03.BR;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.ui.OnItemClickListener;
import com.hyperion.ths.marvel_03.ui.heroinfo.HeroInfoActivity;
import com.hyperion.ths.marvel_03.ui.main.MainActivity;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManager;
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
        implements OnItemClickListener, DialogManager.ClickDialogListener, OnTextSearchListener,
        OnLoadMoreListener {

    private static final String TAG = HeroViewModel.class.getSimpleName();
    private final HeroRepository mHeroRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private final HeroFragmentAdapter mHeroFragmentAdapter;
    private final Navigator mNavigator;
    private final List<Hero> mHeroList;
    private final DialogManager mDialogManager;
    //Method getAllHeroes() is only running one
    private Boolean mIsStart;
    private boolean mRefreshing;
    private int mOffSet;
    private boolean mIsLoadMore;
    public static final int OFFSET_DEFAULT = 20;
    public static final int MAX_HERO = 1485;

    public HeroViewModel(HeroRepository heroRepository, HeroFragmentAdapter heroFragmentAdapter,
            Navigator navigator, DialogManager dialogManager) {
        mHeroRepository = heroRepository;
        mHeroFragmentAdapter = heroFragmentAdapter;
        mHeroFragmentAdapter.setOnItemButtonClickListener(this);
        mHeroFragmentAdapter.setOnRecyclerViewItemClickListener(this);
        mNavigator = navigator;
        mHeroList = new ArrayList<>();
        mDialogManager = dialogManager;
        mDialogManager.setOnClickDialogListener(HeroViewModel.this);
        mIsStart = false;
        MainActivity mainActivity = (MainActivity) navigator.getActivity();
        mainActivity.setOnTextSearchListener(this);
        mHeroFragmentAdapter.setOnLoadMoreListener(this);
        mOffSet = OFFSET_DEFAULT;
        mIsLoadMore = true;
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
        Disposable disposable =
                mHeroRepository.getAllHero(Constant.POINT, Constant.TIMESTAMP, Constant.PUBLIC_KEY,
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
                                mDialogManager.showDialogError(mNavigator.getActivity()
                                        .getString(R.string.message_error_connect));
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
        Disposable disposable = mHeroRepository.getHeroByName(item.getName())
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
        startDisposable(disposable);
    }

    private void deleteHero(Hero hero) {
        mDialogManager.showProgressDialog();
        Disposable disposable = mHeroRepository.deleteHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Hero>() {
                    @Override
                    public void accept(@NonNull Hero hero) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        mDialogManager.showToastDeleteSuccess(
                                mNavigator.getActivity().getString(R.string.delete_success));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
        startDisposable(disposable);
    }

    private void insertHero(Hero hero) {
        mDialogManager.showProgressDialog();
        Disposable disposable = mHeroRepository.insertHero(hero)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Hero>() {
                    @Override
                    public void accept(@NonNull Hero hero) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        mDialogManager.showToastInsertSuccess(
                                mNavigator.getActivity().getString(R.string.insert_success));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        Log.e(TAG, throwable.getLocalizedMessage());
                    }
                });
        startDisposable(disposable);
    }

    @Override
    public void onClickDialog() {
        try {
            getAllHeroRefresh();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void getAllHeroRefresh() throws UnsupportedEncodingException {
        Disposable disposable =
                mHeroRepository.getAllHero(Constant.POINT, Constant.TIMESTAMP, Constant.PUBLIC_KEY,
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
                                setRefreshing(false);
                                mDialogManager.showDialogError(mNavigator.getActivity()
                                        .getString(R.string.message_error_connect));
                                Log.e(TAG, throwable.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                setRefreshing(false);
                                mOffSet = OFFSET_DEFAULT;
                            }
                        });
        startDisposable(disposable);
    }

    public void onRefresh() {
        try {
            getAllHeroRefresh();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Bindable
    public boolean isRefreshing() {
        return mRefreshing;
    }

    private void setRefreshing(boolean refreshing) {
        this.mRefreshing = refreshing;
        notifyPropertyChanged(BR.refreshing);
    }

    @Override
    public void getTextListener(String text) {
        mIsLoadMore = TextUtils.isEmpty(text);
        mHeroFragmentAdapter.getFilter().filter(text);
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadMore) {
            return;
        }
        try {
            getAllHeroLoadMore();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void getAllHeroLoadMore() throws UnsupportedEncodingException {
        if (mOffSet == MAX_HERO) {
            return;
        }
        mDialogManager.showProgressDialog();
        Disposable disposable =
                mHeroRepository.getAllHero(mOffSet, Constant.TIMESTAMP, Constant.PUBLIC_KEY,
                        Constant.getHashKey())
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribeWith(new DisposableObserver<List<Hero>>() {
                            @Override
                            public void onNext(@NonNull List<Hero> heroesList) {
                                mHeroList.addAll(heroesList);
                                mHeroFragmentAdapter.updateData(mHeroList);
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                mDialogManager.showDialogError(mNavigator.getActivity()
                                        .getString(R.string.message_error_connect));
                                Log.e(TAG, throwable.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                mDialogManager.dismissProgressDialog();
                                mOffSet += OFFSET_DEFAULT;
                            }
                        });
        startDisposable(disposable);
    }
}
