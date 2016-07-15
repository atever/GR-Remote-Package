package cn.kyle.GRRemotePackage;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WifiListActivity extends AppCompatActivity {
	private WifiManager wifiManager;
	List<WifiConfiguration> list;
	private int deviceVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_manage);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		init();
	}

	public void init() {
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		list= wifiManager.getConfiguredNetworks();

		ListView listView = (ListView) findViewById(R.id.listView);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.e("selected_device", " position" + list.get(position).SSID);
			}
		});


		if (list == null) {
			Toast.makeText(this, "wifi wei kai", Toast.LENGTH_LONG).show();
		}else {
			listView.setAdapter(new MyAdapter(this,list));
		}
		
	}

	public class MyAdapter extends BaseAdapter {

		LayoutInflater inflater;
		List<WifiConfiguration> list;
		public MyAdapter(Context context, List<WifiConfiguration> list) {
			// TODO Auto-generated constructor stub
			this.inflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}



		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			view = inflater.inflate(R.layout.item_wifi_list, null);
//			ScanResult scanResult = list.get(position);
			WifiConfiguration wifiConfiguration = list.get(position);
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(whetherToRemoveTheDoubleQuotationMarks(wifiConfiguration.SSID));
//			TextView signalStrenth = (TextView) view.findViewById(R.id.signal_strenth);
//			signalStrenth.setText(String.valueOf(Math.abs(wifiConfiguration.networkId)));
//			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//			//
//			if (Math.abs(scanResult.level) > 100) {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_0));
//			} else if (Math.abs(scanResult.level) > 80) {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_1));
//			} else if (Math.abs(scanResult.level) > 70) {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_1));
//			} else if (Math.abs(scanResult.level) > 60) {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_2));
//			} else if (Math.abs(scanResult.level) > 50) {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_3));
//			} else {
//				imageView.setImageDrawable(getResources().getDrawable(R.drawable.stat_sys_wifi_signal_4));
//			}
			return view;
		}

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

}
