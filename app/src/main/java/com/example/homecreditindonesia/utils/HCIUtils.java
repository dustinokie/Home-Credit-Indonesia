package com.example.homecreditindonesia.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class HCIUtils {

    public static void showMessage(Context ctx, String msg, String btn, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton(btn, onClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static int dpToPx(Context ctx, float dp){
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }
}
