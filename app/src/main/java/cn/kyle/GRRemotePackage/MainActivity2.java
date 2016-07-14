package cn.kyle.GRRemotePackage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    public static List<String> mDatas;
    public Button byRecycleView;
    public BottomSheetBehavior behavior;
    public RecyclerView recyclerView;
    public View fillView;
    public WjjAdapter wjjAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.byRecycleView:

                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
        }

    }

    private void init() {

        initData();

        byRecycleView = (Button) findViewById(R.id.byRecycleView);
        byRecycleView.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//       recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        wjjAdapter = new WjjAdapter(this);

        wjjAdapter.setItemClickListener(new WjjAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Toast.makeText(MainActivity2.this, "--->" + pos, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(wjjAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        behavior = BottomSheetBehavior.from(recyclerView);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    blackView.setBackgroundColor(Color.TRANSPARENT);
                    fillView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                fillView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(fillView, slideOffset);
            }
        });

        fillView = findViewById(R.id.fillView);
        fillView.setBackgroundColor(getResources().getColor(R.color.white));
        fillView.setVisibility(View.GONE);
        fillView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

    }

    public void initData() {
        mDatas = new ArrayList<>();
        for (int k = 0; k < 6; k++) {
            mDatas.add("第" + k + "个");
        }
    }

    public static class WjjAdapter
            extends RecyclerView.Adapter<WjjAdapter.ViewHolder> {

        public ItemClickListener mItemClickListener;

        public void setItemClickListener(ItemClickListener listener) {
            mItemClickListener = listener;
        }

        public interface ItemClickListener {
            void onItemClick(int pos);
        }

        private Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mTextView;

            public ViewHolder(View view) {

                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv);

            }


        }

        public WjjAdapter(Context context) {

            super();
            mContext = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ViewHolder holder = new ViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.list_item2, parent,
                    false));
            return holder;

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mItemClickListener.onItemClick(position);
                }
            });

            holder.mTextView.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

}
