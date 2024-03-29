/**
 *
 */
package com.hualianzb.biut.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {


    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }
}
