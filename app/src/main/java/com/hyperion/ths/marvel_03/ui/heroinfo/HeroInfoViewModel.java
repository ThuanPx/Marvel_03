package com.hyperion.ths.marvel_03.ui.heroinfo;

import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.hyperion.ths.marvel_03.BR;
import com.hyperion.ths.marvel_03.R;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.source.HeroRepository;
import com.hyperion.ths.marvel_03.ui.BaseViewModel;
import com.hyperion.ths.marvel_03.utils.Constant;
import com.hyperion.ths.marvel_03.utils.navigator.Navigator;
import com.hyperion.ths.marvel_03.utils.rx.BaseSchedulerProvider;
import com.hyperion.ths.marvel_03.widget.dialog.DialogManager;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ths on 01/06/2017.
 */

public class HeroInfoViewModel extends BaseViewModel {

    private static final String TAG = HeroInfoViewModel.class.getName();
    private Hero mHero;
    private Navigator mNavigator;
    private HeroInfoViewPageAdapter mHeroInfoViewPageAdapter;
    private boolean mIsFavorite;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private HeroRepository mHeroRepository;
    private DialogManager mDialogManager;

    public HeroInfoViewModel(Hero hero, Navigator navigator,
            HeroInfoViewPageAdapter heroInfoViewPageAdapter, HeroRepository heroRepository,
            DialogManager dialogManager) {
        mHero = hero;
        mNavigator = navigator;
        mHeroInfoViewPageAdapter = heroInfoViewPageAdapter;
        mHeroRepository = heroRepository;
        mDialogManager = dialogManager;
    }

    public void onStart() {
        Disposable disposable = mHeroRepository.getHeroByName(mHero.getName())
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Hero>() {
                    @Override
                    public void accept(@NonNull Hero hero) throws Exception {
                        if (hero.getId() == Constant.POINT) {
                            setFavorite(false);
                        } else {
                            setFavorite(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        startDisposable(disposable);
    }

    public HeroInfoViewPageAdapter getHeroInfoViewPageAdapter() {
        return mHeroInfoViewPageAdapter;
    }

    public String getHeroName() {
        return mNavigator.getActivity().getString(R.string.name) + mHero.getName();
    }

    public String getHeroId() {
        return mNavigator.getActivity().getString(R.string.id) + mHero.getId();
    }

    public String getHeroDes() {
        return mNavigator.getActivity().getString(R.string.description) + mHero.getDescription();
    }

    public String getHeroImageUrl() {
        return mHero.getImageHero().getImageUrl() + Constant.IMAGEFORMAT;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
        notifyPropertyChanged(BR.favorite);
    }

    @Bindable
    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }

    public void clickButtonFavoriteHeroInfo(View view) {
        mDialogManager.showProgressDialog();
        Disposable disposable = mHeroRepository.getHeroByName(mHero.getName())
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Hero>() {
                    @Override
                    public void accept(@NonNull Hero hero) throws Exception {
                        mDialogManager.dismissProgressDialog();
                        if (hero.getId() == Constant.POINT) {
                            insertHero(mHero);
                        } else {
                            deleteHero(mHero);
                        }
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
}
