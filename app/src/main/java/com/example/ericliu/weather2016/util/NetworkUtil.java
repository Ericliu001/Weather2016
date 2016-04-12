package com.example.ericliu.weather2016.util;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ericliu.weather2016.R;

/**
 * Created by ericliu on 12/04/2016.
 */
public final class NetworkUtil {
    private NetworkUtil() {
    }


    public static void checkNetworkAndShowErrorMsg(Context context){
        if (!isOnline(context)) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.network_error);
            builder.setMessage(R.string.network_unavailable);
            builder.setPositiveButton(android.R.string.ok, null);

            builder.create().show();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
