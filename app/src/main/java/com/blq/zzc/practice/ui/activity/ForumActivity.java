package com.blq.zzc.practice.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.adapter.MyAdapter.MyRecyclerViewAdapter;
import com.blq.zzc.practice.base.LazyActivity;
import com.blq.zzc.practice.base.LazySwipeBackActivity;
import com.blq.zzc.pulltorefreshview.PullToRefreshView;
import com.blq.zzc.pulltorefreshview.swipebacklayout.SwipeBackActivity;
import com.blq.zzc.pulltorefreshview.swipebacklayout.SwipeBackLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/25.
 */
    public class ForumActivity extends LazySwipeBackActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_forum;
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        initPullToRefreshView();
        initSimpleDraweeView();
    }


    @Bind(R.id.recyclerView_forum)
    RecyclerView recyclerView;
    List<String>list=new ArrayList<>();
    private void initRecyclerView() {
        for(int i=1;i<=1;i++){
            list.add(String.valueOf(i));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this,list));
    }
    @Bind(R.id.pullToRefreshView_forum)
    PullToRefreshView pullToRefreshView;
    private void initPullToRefreshView() {

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.setRefreshing(true);
                initRecyclerView();
                pullToRefreshView.setRefreshing(false);
            }
        });
    }
    @Bind(R.id.simpleDraweeView_forum)
    SimpleDraweeView simpleDraweeView;
    @Bind(R.id.collapsingToolbarLayout_forum)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.tvSubTitle)
    TextView textView;
    @Bind(R.id.appBarLayout_forum)
    AppBarLayout appBarLayout;
    private void initSimpleDraweeView(){
        String str_url="https://apic.douyucdn.cn/upload/avatar/face/201608/25/142c25ff26281729cd85ff5a710a943e_middle.jpg";
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(str_url))
                .setResizeOptions(new ResizeOptions(500, 500))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
//        collapsingToolbarLayout.setTitle("你知");
        textView.setText("1234再来一次!");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset==0){
                    pullToRefreshView.setEnabled(true);
                }
                else {
                    pullToRefreshView.setEnabled(false);
                }
            }
        });
    }
    @Bind(R.id.toolbar_forum)
    Toolbar toolbar;
    @Override
    public void initToolBar() {
        toolbar.setTitle("论坛");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.flower);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
