package com.blq.zzc.practice.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.base.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/13.
 */
public class MainFragment extends LazyFragment{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }
    @Bind(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigationBar;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> list_Fragment=new ArrayList<>();
    private int proviousIndex=0;
    @Override
    public void initViews() {
        list_Fragment.add(new DailyListFragment());
        list_Fragment.add(new Another());
        list_Fragment.add(new Another2());
        list_Fragment.add(new Another());
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout,list_Fragment.get(0)).commit();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_answer, "日报"))
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_article, "测试"))
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_column, "论坛"))
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_favorite, "文章"))
                .setActiveColor(R.color.colorAccent)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(list_Fragment.get(proviousIndex));
                        if (!list_Fragment.get(position).isAdded())fragmentTransaction.add(R.id.frame_layout,list_Fragment.get(position));
                        else {
                            fragmentTransaction.show(list_Fragment.get(position));
                        }
                        fragmentTransaction.commit();
                        proviousIndex=position;
                    }
                    @Override
                    public void onTabUnselected(int position) {
                    }
                    @Override
                    public void onTabReselected(int position) {
                    }
                })
                .initialise();
    }
}
