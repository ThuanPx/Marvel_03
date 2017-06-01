package com.hyperion.ths.marvel_03.ui;

import android.databinding.BaseObservable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ths on 27/05/2017.
 */

public abstract class BaseViewModel extends BaseObservable {
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void startDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }
}
