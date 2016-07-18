package cn.kyle.GRRemotePackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WifiBindManage extends AppCompatActivity {
	private WifiManager wifiManager;
	List<WifiConfiguration> list;
	static ListAdapter mListAdapter;
	private int deviceVersion;

	CoordinatorLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_bind_manage);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);




		init();
	}

	public void init() {
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		final ImageView imageView = (ImageView) findViewById(R.id.imageView);

		list= wifiManager.getConfiguredNetworks();

		final ListView listView = (ListView) findViewById(R.id.listView);

		final SharedPreferences sharedPref = this.getSharedPreferences("test", MODE_PRIVATE);

		container = (CoordinatorLayout) findViewById(R.id.container1);


		assert listView != null;
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Log.i("selected_device", list.get(position).networkId + "position" + list.get(position).SSID);

				SharedPreferences sharedPref = getSharedPreferences("test", MODE_PRIVATE);
				int wifiId = sharedPref.getInt(getString(R.string.wifi_id), -1);


//				Log.e("wifiinfo", l)

				if ((list.get(position).networkId != wifiId) && (list.get(position).networkId != -1)) {
					wifiManager.enableNetwork(list.get(position).networkId, true);
					wifiManager.reconnect();

					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putInt(getString(R.string.wifi_id), list.get(position).networkId);
					editor.putString(getString(R.string.bindSSID), list.get(position).SSID);
					editor.commit();

				}

				Toast.makeText(WifiBindManage.this, "已绑定"+ removeTheDoubleQuotationMarks(list.get(position).SSID), Toast.LENGTH_LONG).show();
				finish();
			}
		});


		if (list == null) {
			Toast.makeText(this, "wifi wei kai", Toast.LENGTH_LONG).show();
		}else {
			mListAdapter = new MyAdapter(this,list);
//			listView.setAdapter(new MyAdapter(this,list));

			listView.setAdapter(mListAdapter);

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
			view = inflater.inflate(R.layout.wifi_list_item, null);
//			ScanResult scanResult = list.get(position);
			WifiConfiguration wifiConfiguration = list.get(position);
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(removeTheDoubleQuotationMarks(wifiConfiguration.SSID));
//			TextView signalStrenth = (TextView) view.findViewById(R.id.signal_strenth);
//			signalStrenth.setText(String.valueOf(Math.abs(wifiConfiguration.networkId)));
			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

			SharedPreferences sharedPref = getSharedPreferences("test", MODE_PRIVATE);
			int wifiId = sharedPref.getInt(getString(R.string.wifi_id), 2);
			Log.i("wifistore", wifiId + "");

//			ImageView image = (ImageView) view.findViewById(R.id.imageView);

			if (wifiConfiguration.networkId == wifiId) {
				imageView.setVisibility(View.VISIBLE);
			}
			return view;
		}

	}


	// 去除双引号
	// http://wy521angel.blog.51cto.com/3262615/1604107
	private String removeTheDoubleQuotationMarks (String ssid) {
		if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
			ssid = ssid.substring(1, ssid.length() - 1);
		}
		return ssid;
	}

}
