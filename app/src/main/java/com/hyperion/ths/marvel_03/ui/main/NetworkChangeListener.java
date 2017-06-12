package com.hyperion.ths.marvel_03.ui.main;

/**
 * Created by ths on 12/06/2017.
 */

/**
 * Listener Network change
 */
public interface NetworkChangeListener {
    /**
     * @param isConnected true when Connected and false when DisConnected
     */
    void onNetworkChange(boolean isConnected);
}
