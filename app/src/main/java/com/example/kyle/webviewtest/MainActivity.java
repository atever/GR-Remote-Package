package com.example.kyle.webviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private WebView webView;
    private boolean mIsExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wm=(WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        Log.e("wifistate", String.valueOf(wm.getWifiState()));

        if (WifiManager.WIFI_STATE_DISABLED == wm.getWifiState()) {
            Toast.makeText(MainActivity.this, "请打开wifi", Toast.LENGTH_SHORT).show();
            wm.setWifiEnabled(true);
        }

        webView = (WebView) findViewById(R.id.web_view);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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

//                Log.e("getURL.length", " " + String.valueOf(webView.getUrl().length()));
//                Log.e("getURL", webView.getUrl());
//                Log.e("getOriginalUrl", " " + webView.getOriginalUrl());


                if (url.length() > 49) {
                    // -->标题  如果服务器端口长度改变,此处需同时改变
//                    String[] parts = url.split("newsId=");
//                    parts = parts[1].split("&token");
//
//
//                    //覆写返回、分享按钮
//                    setTitle("资讯");

                }


            }

        });
//        webView.loadUrl("http://www.dgtle.com");
//        webView.loadUrl("http://112.124.127.57:4000/");
//        webView.loadUrl("http://192.168.0.163:3000/");
//        webView.loadUrl("https://vux.li/");
//        webView.loadUrl("http://192.168.0.163:3000/pages/wst.html");
//        webView.loadUrl("http://42.121.65.52:8000/public/ada/index.html");
//        webView.loadUrl("http://192.168.0.103:8000/public/ada/index.html");

//        webView.loadUrl("http://www.dgtle.com");
        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest-appcache/index.html");
//        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest/index.html");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("progressChange", String.valueOf(newProgress));

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
                //写h5的那个二逼把所有标题设成ada了,此处作废.
                Log.e("title", title);

                if (title.length() > 4) {

                    setTitle(title);
/*                    setCustomBack(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            drawer.openDrawer(Gravity.LEFT);

                            webView.goBack();
//                            toolbar.setVisibility(View.GONE);
//                            bottomBar.setVisibility(View.VISIBLE);
//                            mReward.setVisibility(View.GONE);

                        }
                    });*/


                }
//
//                articletitle = title;
//                shareTitle = title;


//                Log.e("URL", webView.getUrl() +"----"+ webView.getOriginalUrl());
/*                if (!title.equals("数字尾巴-分享美好数字生活")){
                    //settitle = title
                    //share
                    //dashang
                    setTitle("资讯");
//                    bottomBar.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
//                    mReward.setVisibility(View.VISIBLE);
                    shareTitle = title;
                    setRightAction(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showShare();
                        }
                    });




                    setCustomBack(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            showShare();
//                            webView.goBack();// 返回前一个页面
//                            toolbar.setVisibility(View.GONE);
//                            mReward.setVisibility(View.GONE);
//                            bottomBar.setVisibility(View.VISIBLE);
//
*//*
                            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                                return true;
                            }
*//*


                            if (webView.canGoBack()){
                                webView.goBack();// 返回前一个页面
//                                toolbar.setVisibility(View.GONE);
////                                mReward.setVisibility(View.GONE);
//                                bottomBar.setVisibility(View.VISIBLE);
//                                mReward.setVisibility(View.GONE);
                            }



                        }
                    });
                }*/
            }


//            webView.getOriginalUrl()


        });


        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //滚动条0

//        // 如果已经处于底端
        if (webView.getContentHeight() - (webView.getHeight() + webView.getScrollY()) > 0) {
            //XXX
            Log.e("TAG", "TEST");
        }

        boolean isBottom = webView.getContentHeight() - (webView.getHeight() + webView.getScrollY()) <= 0;
        if (isBottom) {
            // TODO
            Log.e("TAG", "daodile");
        }


        Log.e("TEst", String.valueOf(webView.getProgress()));



    }





    @Override
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
    }


    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("Event", String.valueOf(event));


        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
//        else if (keyCode == KeyEvent.KEYCODE_BACK && drawer.isDrawerOpen(GravityCompat.START)) {
////                drawer.closeDrawer(GravityCompat.START);
//            ada.makeText("gaga");}
        else if (keyCode == KeyEvent.KEYCODE_BACK) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //蓝牙开启状态判断后 回调结果
        // -->  0 开启了蓝牙, -1 开启失败
//        if (requestCode == REQUEST_ENABLE_BT) {
//            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
//                ada.makeText(String.valueOf(resultCode));
//                Log.e(TAG, String.valueOf(resultCode));
//            } else if (0 != resultCode) {
//                readLocalopenId();
//            } else {
//                finish();
//            }
//        }

//        Log.e("wifistate", String.valueOf(wm.getWifiState()));

    }


}
