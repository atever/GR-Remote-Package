package cn.kyle.GRRemotePackage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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


/**
 * 日期：16/3/31 17:44
 * <p>
 * 描述：
 */
public class BottomSheetBasicActivity extends AppCompatActivity
{

    public static List<String> mList;
    public Button showBottomSheet;
    public BottomSheetBehavior bottomSheetBehavior;
    public RecyclerView recyclerView;
    public MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_basic);
        initData();
        initView();
    }

    /**
     * initView Data
     */
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

    /**
     * init view
     */
    private void initView()
    {
        showBottomSheet = (Button) findViewById(R.id.showBottomSheet);
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
                Toast.makeText(BottomSheetBasicActivity.this, String.format("you click %s item",position), Toast.LENGTH_LONG).show();
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


    /**
     * handle onBackPressed
     */
    @Override
    public void onBackPressed()
    {
        if(bottomSheetBehavior != null){
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else
            {
                super.onBackPressed();
            }
        }
    }
}
