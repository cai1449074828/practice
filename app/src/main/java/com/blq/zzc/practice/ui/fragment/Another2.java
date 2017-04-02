package com.blq.zzc.practice.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.adapter.MyAdapter.MyRecyclerViewAdapter;
import com.blq.zzc.practice.base.LazyFragment;
import com.blq.zzc.practice.ui.activity.ForumActivity;
import com.blq.zzc.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/24.
 */
public class Another2 extends LazyFragment{
    Activity activity=getActivity();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_another2;
    }
    @Bind(R.id.pullToRefreshView)
    PullToRefreshView pullToRefreshView;
    @Bind(R.id.recyclerView_Another2)
    RecyclerView recyclerView;
    @Override
    public void initViews() {
        initRecyclerView();
        initPullToRefreshView();
    }
    private List<String> list=new ArrayList<>();
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(),list));
    }
    private void initPullToRefreshView() {
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0,1000);
            }
        });
    }
    private int i=0;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
//                for (int ii=0;ii<=9;ii++) {
                    list.add(String.valueOf(i++));
//                }
                this.sendEmptyMessage(1);
            }
            else {
                pullToRefreshView.setRefreshing(false);
            }
        }
    };

}
