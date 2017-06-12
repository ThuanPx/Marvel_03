package com.hyperion.ths.marvel_03.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.hyperion.ths.marvel_03.R;

/**
 * Created by ths on 10/06/2017.
 */

public class DialogManagerImpl implements DialogManager {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private ClickDialogListener mClickDialogListener;

    public DialogManagerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext, R.style.AppCompatAlertDialogStyle);
        }
        mProgressDialog.setMessage(mContext.getString(R.string.wait));
        mProgressDialog.show();
    }

    @Override
    public void showDialogError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.error));
        builder.setMessage(error);

        String positiveText = mContext.getString(R.string.retry);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClickDialogListener.onClickDialog();
            }
        });

        String negativeText = mContext.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void setOnClickDialogListener(ClickDialogListener clickDialogListener) {
        mClickDialogListener = clickDialogListener;
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }
}
