package cn.kyle.GRRemotePackage;

import android.app.ListActivity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListFruitActivity extends ListActivity {

    static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple", "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple", "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
    private String[] SSID = new String [] {};
    private int i = 0;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<Objects> list2 = new ArrayList<Objects>();
    private ArrayList<Object> list3 = new ArrayList<Object>();
    private ArrayList<Object> list4 = new ArrayList<Object>();

    private int deviceVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final WifiManager wm=(WifiManager)this.getSystemService(Context.WIFI_SERVICE);


        List<WifiConfiguration> existingConfigs = wm.getConfiguredNetworks();//获取保存的配置信息

//            list2.addAll(existingConfigs.get(i));
        list3.addAll(existingConfigs);

        Map map =(Map)list3.get(i);

//        Log.e("TEXT", map.toString());
//            list2.add(existingConfigs.get(i));
        list4.add(existingConfigs);
        for (int i = 0; i < existingConfigs.size(); i++) {
//            list.add(whetherToRemoveTheDoubleQuotationMarks(existingConfigs.get(i).SSID));
            list.add( existingConfigs.get(i).toString());
////            list2.addAll(existingConfigs.get(i));
//            list3.addAll(existingConfigs);
//
////            list2.add(existingConfigs.get(i));
//            list4.add(existingConfigs);



            Log.e("TEXT", list.get(0));
        }


        // no more this
        // setContentView(R.layout.list_fruit);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_fruit, list));





        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                Log.e("TEXT", ((TextView) view).getText().toString());

//                int ssid = Integer.parseInt(((TextView) view).getText().toString());

                wm.disconnect();
                wm.enableNetwork(12, true);
                wm.reconnect();

            }
        });

    }


    // 根据Android的版本判断获取到的SSID是否有双引号
    // http://wy521angel.blog.51cto.com/3262615/1604107
    private String whetherToRemoveTheDoubleQuotationMarks (String ssid) {
        //获取Android版本号
        deviceVersion = Build.VERSION.SDK_INT;
        if (deviceVersion >= 17) {
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
        }
        return ssid;
    }


//    private void ConnectWifi (Objects wm,String ssid) {
//        wm.disconnect();
////        wm.enableNetwork(ssid, true);
//        wm.reconnect();
//
//    }

}