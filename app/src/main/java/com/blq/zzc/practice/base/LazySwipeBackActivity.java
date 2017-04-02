package com.blq.zzc.practice.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blq.zzc.pulltorefreshview.swipebacklayout.SwipeBackActivity;
import com.blq.zzc.pulltorefreshview.swipebacklayout.SwipeBackLayout;

import butterknife.ButterKnife;

//import com.hotbitmapgg.rxzhihu.utils.StatusBarCompat;

/**
 * Created by 11 on 2016/3/31.
 * <p/>
 * 普通Activity基类
 */
public abstract class LazySwipeBackActivity extends SwipeBackActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //适配4.4状态栏
//        StatusBarCompat.compat(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //初始化侧滑
        initSwipeBackLayout();
    }
    private SwipeBackLayout swipeBackLayout;
    private void initSwipeBackLayout() {
        swipeBackLayout=getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeDp(120);
    }
    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();
}
