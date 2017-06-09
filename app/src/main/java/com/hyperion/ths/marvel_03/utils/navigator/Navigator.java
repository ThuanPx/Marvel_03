package com.hyperion.ths.marvel_03.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.hyperion.ths.marvel_03.R;

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
        setActivityTransactionAnimation(ActivityTransition.START);
    }

    public void finishActivity() {
        mActivity.finish();
        setActivityTransactionAnimation(ActivityTransition.FINISH);
    }

    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    private void setActivityTransactionAnimation(@ActivityTransition int animation) {
        switch (animation) {
            case ActivityTransition.START:
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case ActivityTransition.FINISH:
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case ActivityTransition.NONE:
                break;
            default:
                break;
        }
    }
}
