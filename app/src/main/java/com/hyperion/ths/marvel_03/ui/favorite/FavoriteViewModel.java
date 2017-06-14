package com.hyperion.ths.marvel_03.ui.favorite;

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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    private DialogManager mDialogManager;

    public FavoriteViewModel(FavoriteFragmentAdapter favoriteFragmentAdapter,
            HeroRepository heroRepository, Navigator navigator, DialogManager dialogManager) {
        mFavoriteFragmentAdapter = favoriteFragmentAdapter;
        mFavoriteFragmentAdapter.setFavoriteClickListener(this);
        mFavoriteFragmentAdapter.setItemClickListener(this);
        mHeroRepository = heroRepository;
        mNavigator = navigator;
        mDialogManager = dialogManager;
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
        Disposable disposable = mHeroRepository.getAllHero()
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
        startDisposable(disposable);
    }

    @Override
    public void onItemRecyclerViewClick(Hero item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_HERO, item);
        mNavigator.startActivity(HeroInfoActivity.class, bundle);
    }

    @Override
    public void onItemFavoriteClick(final Hero hero) {
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
                        getAllHero();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                        mDialogManager.dismissProgressDialog();
                    }
                });
        startDisposable(disposable);
    }
}

