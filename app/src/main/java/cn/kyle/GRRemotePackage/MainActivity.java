package cn.kyle.GRRemotePackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
//import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetBehavior;

import com.cocosw.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

import cn.kyle.GRRemotePackage.R;

public class MainActivity extends AppCompatActivity{


    private WebView webView;
    private boolean mIsExit = false;
    final String TAG = "MainActivity";


    public static List<String> mList;
    public Button showBottomSheet;
    public BottomSheetBehavior bottomSheetBehavior;
    public RecyclerView recyclerView;
    public MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        initData();
//        initView();
//        openWifi();


/*        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

/*        Button test = (Button) findViewById(R.id.btn);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
//                Intent intent = new Intent(MainActivity.this, ListFruitActivity.class);
//                Intent intent = new Intent(MainActivity.this, BottomSheetBasicActivity.class);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                startActivity(intent);
            }
        });*/


        WEBVIEWOPT();


    }


    public void initData()
    {
        mList = new ArrayList<>();
        for(int i = 0; i < 5; i++)
        {
            mList.add(String.format("index %s position", i));
        }
    }



//    @Override
//    public void onClick(View v)
//    {
//        switch(v.getId())
//        {
//            case R.id.showBottomSheet:
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                break;
//        }
//    }



    private void openWifi () {
        WifiManager wm=(WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        Log.e("wifistate", String.valueOf(wm.getWifiState()));

        if (WifiManager.WIFI_STATE_DISABLED == wm.getWifiState()) {
            Toast.makeText(MainActivity.this, "请打开wifi", Toast.LENGTH_SHORT).show();
            wm.setWifiEnabled(true);
        }
    }



    /**
     * init view
     */
    private void initView()
    {
//        showBottomSheet = (Button) findViewById(R.id.showBottomSheet);
//        showBottomSheet.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new MyAdapter(this);
        adapter.setItemClickListener(new MyAdapter.ItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Toast.makeText(MainActivity.this, String.format("you click %s item",position), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
        bottomSheetBehavior = BottomSheetBehavior.from(recyclerView);
        recyclerView.setVisibility(View.GONE);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN)
                {
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {
                recyclerView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(recyclerView, slideOffset);
            }
        });


    }

    /**
     * custom adapter
     */
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {

        public ItemClickListener mItemClickListener;

        public void setItemClickListener(ItemClickListener listener)
        {
            mItemClickListener = listener;
        }

        public interface ItemClickListener
        {
            void onItemClick(int position);
        }

        private Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder
        {

            public final TextView mTextView;

            public ViewHolder(View view)
            {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.textview);
            }
        }

        public MyAdapter(Context context)
        {
            super();
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position)
        {
            holder.mTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mItemClickListener.onItemClick(position);
                }
            });

            holder.mTextView.setText(mList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mList.size();
        }
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

        else if (bottomSheetBehavior != null) {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
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
    }



    public void WEBVIEWOPT () {
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
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
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
//
//
//                    //覆写返回、分享按钮
//                    setTitle("资讯");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                view.scrollTo(0, view.getContentHeight());
//                Log.e(TAG, String.valueOf(view.getContentHeight()));
            }



        });
//        webView.loadUrl("http://www.dgtle.com");
//        webView.loadUrl("http://112.124.127.57:4000/");
//        webView.loadUrl("http://192.168.0.163:3000/");
//        webView.loadUrl("https://vux.li/");
//        webView.loadUrl("http://192.168.0.163:3000/pages/wst.html");
//        webView.loadUrl("http://192.168.0.103:8000/public/ada/index.html");

//        webView.loadUrl("http://www.dgtle.com");
        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest-appcache/index.html");
//        webView.loadUrl("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest/index.html");


//        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//        webView.loadData(summary, "text/html", null);
//        webView.loadDataWithBaseURL("http://www.ricoh-imaging.co.jp/english/products/gr_remote/app/latest-appcache/index.html",summary, "text/html", null, null);
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
                Log.e("title", title);

            }

        });


        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //滚动条0






        Log.e("TEst", String.valueOf(webView.getProgress()));



        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Log.i(TAG+ "_scrollY", scrollY+"");
                int height = (int) Math.floor(webView.getContentHeight() * webView.getScale());
                int webViewHeight = webView.getMeasuredHeight();
                if(scrollY + webViewHeight >= height){
                    Log.i("THE END", "reached");

                    // 等待2秒，两秒后判断当前值/  或者上面的if语句 ，成立时再弹出
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


                    new BottomSheet.Builder(MainActivity.this).title("设置").sheet(R.menu.menu).listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case R.id.setting:
//                                    q.toast("Help me!");
                                    Intent intent = new Intent(MainActivity.this, ListFruitActivity.class);
                                    // Intent intent = new Intent(MainActivity.this, BottomSheetBasicActivity.class);
//                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                    startActivity(intent);
                                    break;
                                case R.id.about:
                                    break;
                            }
                        }
                    }).build().show();


                }
/*

                if (scrollY < 144 && (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN)) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

*/

//                else if ((bottomSheetBehavior != null) && (scrollY == 0)) {
//                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    }
//                }

            }


        });
    }

}
