package cn.kyle.GRRemotePackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kyle on 16/7/16.
 */

public class WifiStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关


            WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()){
                Log.e("wifistate", "尝试连接相机");

                final SharedPreferences sharedPref = context.getSharedPreferences("test", context.MODE_PRIVATE);
                int wifiId = sharedPref.getInt(context.getString(R.string.wifi_id), -1);
                Log.e("wifistore", wifiId + "");

//                wifiManager.disconnect();

                if (wifiId != -1) {
                    wifiManager.enableNetwork(wifiId, true);
                    wifiManager.reconnect();
                }

            } else  {
//                Toast.makeText(MainActivity.this, "请打开wifi", Toast.LENGTH_SHORT).show();
                wifiManager.setWifiEnabled(true);
                Log.e("wifistate", "正打开wifi");

                // 此后启用广播监听wifi状态，监听到打开后
            }




/*            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.e("wifistate", "disabled");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.e("wifistate", "Enabled");

                    break;
                //
            }*/
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
/*        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
                Log.e("H3c", "isConnected" + isConnected);
                if (isConnected) {
                } else {

                }
            }
        }*/
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
/*        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo gprs = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            Log.i("网络状态改变:",  wifi.isConnected() + " 3g:" + gprs.isConnected());
            NetworkInfo info = intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                Log.e("H3c", "info.getTypeName()" + info.getTypeName());
                Log.e("H3c", "getSubtypeName()" + info.getSubtypeName());
                Log.e("H3c", "getState()" + info.getState());
                Log.e("H3c", "getDetailedState()"
                        + info.getDetailedState().name());
                Log.e("H3c", "getDetailedState()" + info.getExtraInfo());
                Log.e("H3c", "getType()" + info.getType());

                if (NetworkInfo.State.CONNECTED == info.getState()) {
                } else if (info.getType() == 1) {
                    if (NetworkInfo.State.DISCONNECTING == info.getState()) {

                    }
                }
            }
        }*/
    }
}