package cn.kyle.GRRemotePackage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 16/7/13.
 */
public class ListViewItemActivity extends Activity {

    /*
     * 声明一个List对象
     */
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.list_view);

        // 设置list_view的项
        List<UserInfo> infos = this.getUserInfos();
        this.listView.setAdapter(new UserInfoAdapter(this, infos));

        // 设置单击事件
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                UserInfo info = (UserInfo)arg0.getItemAtPosition(pos);
                Log.i("listView.ItemClick", "用户信息：" + info.toString());
                Toast.makeText(ListViewItemActivity.this,info.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<UserInfo> getUserInfos() {
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        int i = 1;
        while (i < 11) {
            UserInfo user = new UserInfo();
            user.setId(i+"");
            user.setName("名字_"+i);
            user.setAddress("地址_"+i);
            user.setAge(i+40);
            userInfos.add(user);
            i++;
        }
        return userInfos;
    }

}