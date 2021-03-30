package com.example.homecreditindonesia.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class HCIUtils {

    public static void showMessage(Context ctx, String msg) {
        showMessage(ctx, msg, "OK");
    }

    public static void showMessage(Context ctx, String msg, String btn) {
        showMessage(ctx, null, msg, btn, null, false);
    }

    public static void showMessage(Context ctx, String title, String msg, String btn, DialogInterface.OnClickListener onClickListener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(cancelable);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNeutralButton(btn, onClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
