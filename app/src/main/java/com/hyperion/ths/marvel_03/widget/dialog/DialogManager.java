package com.hyperion.ths.marvel_03.widget.dialog;

/**
 * Created by ths on 10/06/2017.
 */

public interface DialogManager {

    void showProgressDialog();

    void dismissProgressDialog();

    void showDialogError(String error);

    void setOnClickDialogListener(ClickDialogListener clickDialogListener);

    void showToastInsertSuccess(String toast);

    void showToastDeleteSuccess(String toast);

    /**
     * Click dialog listener
     */
    interface ClickDialogListener {
        void onClickDialog();
    }
}

