package com.hyperion.ths.marvel_03.widget.dialog;

/**
 * Created by ths on 10/06/2017.
 */

public interface DialogManager {

    void showProgressDialog();

    void dismissProgressDialog();

    void showDialogError(String error);

    /**
     * Click dialog listener
     */
    interface ClickDialogListener {
        void onClickDialog();
    }
}

