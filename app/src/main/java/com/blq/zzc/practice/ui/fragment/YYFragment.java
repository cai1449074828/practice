package com.blq.zzc.practice.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blq.zzc.practice.R;
import com.blq.zzc.practice.base.LazyFragment;
import com.blq.zzc.practice.ui.fragment.yy_fragment.ContactsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public class YYFragment extends LazyFragment {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.bottomNavigationBar)
    BottomNavigationBar mBottomNavigationBar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yy;
    }

    @Override
    public void initViews() {
//        initFrameLayout();
        initViewPager();
        initButtonNavigationBar();
    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }
        @Override
        public int getCount() {
            return list_fragment.size();
        }
    }
    private List<String> titles= Arrays.asList("1","2","3","4");
    private ArrayList<Fragment> list_fragment=new ArrayList<>();
    private Fragment fragment1=new ContactsFragment();
    private Fragment fragment2=new ContactsFragment();
    private Fragment fragment3=new ContactsFragment();
    private void initViewPager() {
        list_fragment.add(fragment1);
        list_fragment.add(fragment2);
        list_fragment.add(fragment3);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//        slidingTabLayout.setViewPager(viewPager);
    }
    private void initButtonNavigationBar(){
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_answer, "会话"))
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_article, "通讯录"))
                .addItem(new BottomNavigationItem(R.drawable.ic_profile_column, "设置"))
                .setActiveColor(R.color.colorAccent)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        mViewPager.setCurrentItem(position);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
