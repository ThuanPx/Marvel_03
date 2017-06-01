package com.hyperion.ths.marvel_03.utils.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ths on 31/05/2017.
 */

public final class SchedulerProvider implements BaseSchedulerProvider {
    @Nullable
    private static SchedulerProvider mSchedulerProvider;

    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (mSchedulerProvider == null) {
            mSchedulerProvider = new SchedulerProvider();
        }

        return mSchedulerProvider;
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
