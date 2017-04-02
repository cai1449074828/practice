package com.blq.zzc.practice.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.base.LazyFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/14.
 */
public class Another extends LazyFragment{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_another;
    }
    private List<String> titles= Arrays.asList("1","2","3","4");
    @Bind(R.id.slidingTabStrip)
    SlidingTabLayout slidingTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return new DailyListFragment();
        }
        @Override
        public int getCount() {
            return titles.size();
        }
    }
    @Override
    public void initViews() {
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        slidingTabLayout.setViewPager(viewPager);
    }
}
