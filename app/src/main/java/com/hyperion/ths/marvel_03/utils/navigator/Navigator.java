package com.hyperion.ths.marvel_03.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ths on 01/06/2017.
 */

public class Navigator {
    private Activity mActivity;

    public Navigator(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    private void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }
}
