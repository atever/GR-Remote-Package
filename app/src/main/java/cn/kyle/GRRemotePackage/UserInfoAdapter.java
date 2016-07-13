package cn.kyle.GRRemotePackage;

/**
 * Created by kyle on 16/7/13.
 */
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserInfoAdapter extends BaseAdapter {

    private final List<UserInfo> infos = new LinkedList<UserInfo>();
    private final Context context;

    public UserInfoAdapter(Context context, List<UserInfo> userInfos) {
        this.infos.clear();
        this.infos.addAll(userInfos);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.infos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * 绘制
     * (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.user_info, null);
            TextView userTextView = (TextView)view.findViewById(R.id.tv_user_info);
            userTextView.setText(this.infos.get(position).getName());
            return view;
        }
        return convertView;
    }

}