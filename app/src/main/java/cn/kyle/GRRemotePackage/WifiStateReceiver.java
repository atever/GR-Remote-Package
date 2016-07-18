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

    final String TAG = "WifiStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        final SharedPreferences sharedPref = context.getSharedPreferences("test", context.MODE_PRIVATE);
        int wifiId = sharedPref.getInt(context.getString(R.string.wifi_id), -1);
        String bindSSID = sharedPref.getString(context.getString(R.string.bindSSID),
                context.getString(R.string.defSSID));

        Log.e(TAG, "sharedPred_" + wifiId + bindSSID);
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);


        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {


            if (wifiManager.isWifiEnabled()){



//                wifiManager.disconnect();

                if (wifiId != -1) {
                    wifiManager.enableNetwork(wifiId, true);
                    wifiManager.reconnect();
                    Log.e(TAG, "enabled --> 连接相机");
                }

            } else  {
//                Toast.makeText(MainActivity.this, "请打开wifi", Toast.LENGTH_SHORT).show();
                wifiManager.setWifiEnabled(true);
                Log.e(TAG, "disabled --> 打开wifi");

                // 此后启用广播监听wifi状态，监听到打开后
            }


        }

        // 确保 连接 到 的 wifi 是否是 绑定的 wifi
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = manager.getActiveNetworkInfo();

            if (info != null) {
//                Log.e(TAG, "type---------------!null");
                String SSID = info.getExtraInfo();
                int Type = info.getType();

                if ( (Type != ConnectivityManager.TYPE_WIFI) ||
                    (!SSID.equals(bindSSID) && !SSID.equals(context.getString(R.string.defSSID))) ) {

                    wifiManager.enableNetwork(wifiId, true);
                    wifiManager.reconnect();
                }

            }

//            Log.i(TAG, "type" + String.valueOf(manager.getActiveNetworkInfo()));

        }

    }
}