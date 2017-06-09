package com.hyperion.ths.marvel_03.utils.navigator;

import android.support.annotation.IntDef;

/**
 * Created by ths on 09/06/2017.
 */

@IntDef({ ActivityTransition.NONE, ActivityTransition.START, ActivityTransition.FINISH })
@interface ActivityTransition {
    int NONE = 0x00;
    int START = 0x01;
    int FINISH = 0x02;
}
