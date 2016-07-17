package cn.kyle.GRRemotePackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
//import android.view.View;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.ldoublem.loadingviewlib.LVEatBeans;
import com.ldoublem.loadingviewlib.LVLineWithText;


public class MainActivity extends AppCompatActivity{


    private WebView webView;
    private boolean mIsExit = false;
    final String TAG = "MainActivity";
    BroadcastReceiver receiver;
    LVEatBeans pacMan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        initData();
//        initView();





        wifiManage();
        registerWifiStateReceiver();

        StartAnimal();

        WEBVIEWOPT();

    }







/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("Event", String.valueOf(event));

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mIsExit) {
                mIsExit = true;
                new Handler().postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, SystemClock.uptimeMillis() + 1000);
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void wifiManage () {
        WifiManager wm=(WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        Log.e("wifistate", String.valueOf(wm.getWifiState()));

        if (wm.isWifiEnabled()) {

            // 连接

            final SharedPreferences sharedPref = getSharedPreferences("test", MODE_PRIVATE);
            int wifiId = sharedPref.getInt(getString(R.string.wifi_id), -1);
            Log.e("wifistore", wifiId + "");

//                wifiManager.disconnect();

            if (wifiId != -1) {
                wm.enableNetwork(wifiId, true);
                wm.reconnect();
            }
        } else {
//            enable

            Toast.makeText(MainActivity.this, "正打开wifi", Toast.LENGTH_SHORT).show();
            wm.setWifiEnabled(true);
        }
    }




    public void WEBVIEWOPT () {
        webView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();



        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
//        webseting.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        settings.setDatabaseEnabled(true);
        String appCaceDir =this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        Log.e("Cachepath",appCaceDir);
        settings.setAppCachePath(appCaceDir);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
//                webView.setVisibility(View.GONE);
//                error.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.length() > 49) {
                    // -->标题  如果服务器端口长度改变,此处需同时改变
//                    String[] parts = url.split("newsId=");
//                    parts = parts[1].split("&token");

                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                view.scrollTo(0, view.getContentHeight());
                Log.e(TAG, "page finished");
//                webView.loadUrl("javascript:document.write('hello')");
            }



        });
//        webView.loadUrl("http://www.dgtle.com");
//        webView.loadUrl("http://112.124.127.57:4000/");
//        webView.loadUrl("http://192.168.0.163:3000/");
//        webView.loadUrl("https://vux.li/");
//        webView.loadUrl("http://192.168.0.163:3000/pages/wst.html");
//        webView.loadUrl("http://www.baidu.com");
//        webView.loadUrl("http://www.dgtle.com");
        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest-appcache/index.html");
//        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest/index.html");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("progressChange", String.valueOf(newProgress));

                // 网页载入进度达到100时显示
                if (newProgress == 100) {
                    pacMan.stopAnim();
                    pacMan.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);

                }


            }


            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, true);
//                Log.e("IconUrl", url);

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e("TAG", "onJsAlert url=" + url + ";message=" + message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                result.confirm();
                return true;
            }


            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
                Log.e("title", title);

            }

        });


        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //滚动条0





        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


//                Log.i(TAG+ "_scrollY", String.valueOf(scrollY));
                int height = (int) Math.floor(webView.getContentHeight() * webView.getScale());
                int webViewHeight = webView.getMeasuredHeight();
                if(scrollY + webViewHeight >= height){
                    Log.i("THE END", "reached");

                    // 等待2秒，两秒后判断当前值/  或者上面的if语句 ，成立时再弹出
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


                    new BottomSheet.Builder(MainActivity.this, R.style.BottomSheet_StyleDialog).title("设置").sheet(R.menu.menu).listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case R.id.setting:
//                                    q.toast("Help me!");
                                    Intent intent = new Intent(MainActivity.this, WifiBindManage.class);
                                    startActivity(intent);
                                    break;
                                case R.id.about:
                                    Intent intent_about = new Intent(MainActivity.this, About.class);
                                    startActivity(intent_about);
                                    break;
                            }
                        }
                    }).build().show();


                }


            }


        });
    }


    // 开始动画

    public void StartAnimal () {
        pacMan = (LVEatBeans) findViewById(R.id.lv_eatBeans);
        pacMan.startAnim();
    }




    /**
     * 注册广播接收器
     */
    public void registerWifiStateReceiver() {

        receiver = new WifiStateReceiver();

        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister
        try {
            unregisterReceiver(receiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
    }



}
