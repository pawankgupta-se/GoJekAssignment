package com.assignment.gojek.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class Utilities {
    public static boolean isOnline(@Nullable Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showError(@Nullable View view, String error) {
        if (view == null) return;
        Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
