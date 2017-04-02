package com.blq.zzc.practice.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.adapter.AutoLoadOnScrollListener;
import com.blq.zzc.practice.adapter.MainViewPagerAdapter;
import com.blq.zzc.practice.adapter.RecycleAdapterItem;
import com.blq.zzc.practice.base.LazyFragment;
import com.blq.zzc.practice.db.DailyDao;
import com.blq.zzc.practice.model.DailyBean;
import com.blq.zzc.practice.model.DailyDetail;
import com.blq.zzc.practice.model.DailyListBean;
import com.blq.zzc.practice.model.TopDailys;
import com.blq.zzc.practice.network.RetrofitHelper;
import com.blq.zzc.practice.utils.LogUtil;
import com.blq.zzc.practice.utils.NetWorkUtil;
import com.blq.zzc.practice.widget.refresh.HeaderViewRecyclerAdapter;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import me.relex.circleindicator.CircleIndicator;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/14.
 */
public class DailyListFragment extends LazyFragment implements Runnable{
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<DailyBean> list=new ArrayList<>();
    private RecycleAdapterItem recycleAdapterItem;
    private HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_list;
    }
    @Bind(R.id.swiperefresh_dailylist)
    SwipeRefreshLayout swipeRefreshLayout;
//    @Bind(R.id.circleprogress_dailylist)
//    CircleProgressView circleProgressView;
    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1)getLateDailyList(1);
            else if (msg.what==2)getLateDailyList(2);
            else {
                finishGetData();
            }
        }
    };
    private void cacheNewsDetail(int newsId)
    {

        RetrofitHelper.builder().getNewsDetails(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<DailyDetail>()
                {

                    @Override
                    public void call(DailyDetail dailyDetail)
                    {

                    }
                });
    }
    private void cacheAllDetail(List<DailyBean> dailys)
    {

        if (NetWorkUtil.isWifiConnected())
        {
            for (DailyBean daily : dailys)
            {
                cacheNewsDetail(daily.getId());
            }
        }
    }
    public DailyListBean changeReadState(DailyListBean dailyList)
    {

        List<String> allReadId = new DailyDao(getActivity()).getAllReadNew();
        for (DailyBean daily : dailyList.getStories())
        {
            daily.setDate(dailyList.getDate());
            for (String readId : allReadId)
            {
                if (readId.equals(daily.getId() + ""))
                {
                    daily.setRead(true);
                }
            }
        }
        return dailyList;
    }
    private void loadMoreDaily(String currentTime) {
        RetrofitHelper.builder().getBeforeNews(currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean,DailyListBean>()
                {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean)
                    {

                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>()
                {

                    @Override
                    public void call(DailyListBean dailyListBean)
                    {
                        autoLoadOnScrollListener.setLoading(false);
                        recycleAdapterItem.addData(dailyListBean.getStories());
                        DailyListFragment.this.currentTime = dailyListBean.getDate();
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {
                        autoLoadOnScrollListener.setLoading(false);
                        LogUtil.all("加载更多数据失败");
                    }
                });
    }
    private void showProgress() {
        progressWheel.setVisibility(View.VISIBLE);
        progressWheel.spin();
//        circleProgressView.spin();
    }
    private void hideProgress() {
        progressWheel.setVisibility(View.GONE);
        progressWheel.stopSpinning();
        swipeRefreshLayout.setRefreshing(false);
//        circleProgressView.stopSpinning();
    }
    private int size;
    private String currentTime = "";
    public void getLateDailyList(final int mode) {
        RetrofitHelper.builder().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0()
                {

                    @Override
                    public void call()
                    {
                        if (mode==1)
                        {
                            showProgress();
                        }
                    }
                })
                .map(new Func1<DailyListBean,DailyListBean>()
                {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean)
                    {
                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>()
                {

                    @Override
                    public void call(DailyListBean dailyListBean)
                    {
                        System.out.println("成功");
                        hideProgress();
                        if (dailyListBean.getStories() == null)
                        {
                            LogUtil.all("加载数据失败");
                        } else
                        {
                            recycleAdapterItem.updateData(dailyListBean.getStories());
                            currentTime = dailyListBean.getDate();
                            if (dailyListBean.getStories().size() < 8)
                            {
                                loadMoreDaily(DailyListFragment.this.currentTime);
                            }
                            List<TopDailys> tops = dailyListBean.getTop_stories();
                            mainViewPagerAdapter = new MainViewPagerAdapter(getActivity(), tops);
                            viewPager.setAdapter(mainViewPagerAdapter);
                            circleIndicator.setViewPager(viewPager);
                            size = tops.size();
                            handler.sendEmptyMessageDelayed(0,0);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {
                        hideProgress();
                        LogUtil.all("加载失败" + throwable.getMessage());
                    }
                });
    }
    private void finishGetData() {
        recyclerView.setAdapter(headerViewRecyclerAdapter);
        startViewPagerRun();
    }
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private MainViewPagerAdapter mainViewPagerAdapter;
    AutoLoadOnScrollListener autoLoadOnScrollListener;
    @Override
    public void initViews() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(2);
            }
        });
        recycleAdapterItem =new RecycleAdapterItem(getActivity(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//        StaggeredGridLayoutManager linearLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        autoLoadOnScrollListener=new AutoLoadOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreDaily(currentTime);
            }
        };
        recyclerView.addOnScrollListener(autoLoadOnScrollListener);
        View headView= LayoutInflater.from(getActivity()).inflate(R.layout.recycle_head_layout,recyclerView,false);
        viewPager= (ViewPager) headView.findViewById(R.id.main_view_pager);
        circleIndicator= (CircleIndicator) headView.findViewById(R.id.pager_indicator);
        headerViewRecyclerAdapter=new HeaderViewRecyclerAdapter(recycleAdapterItem);
        headerViewRecyclerAdapter.addHeaderView(headView);
        handler.sendEmptyMessage(1);
    }
    private Timer mTimer;
    private BannerTask mTimerTask;
    public void startViewPagerRun()
    {
        //执行ViewPager进行轮播
        if(mTimer!=null)mTimer.cancel();
        mTimer = new Timer();
        mTimerTask = new BannerTask();
        mTimer.schedule(mTimerTask, 2000, 2000);
    }
    private int mPagerPosition = 0;

    @Override
    public void run() {
        viewPager.setCurrentItem(mPagerPosition);
    }
    private boolean mIsUserTouched = false;

    private class BannerTask extends TimerTask
    {
        @Override
        public void run()
        {
            if (!mIsUserTouched)
            {
                mPagerPosition = (mPagerPosition + 1) % size;
                if (getActivity() != null)
                {
                    getActivity().runOnUiThread(DailyListFragment.this);
                }
            }
        }
    }
}
