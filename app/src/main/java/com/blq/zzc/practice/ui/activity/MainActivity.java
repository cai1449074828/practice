package com.blq.zzc.practice.ui.activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.base.LazyActivity;
import com.blq.zzc.practice.ui.fragment.YYFragment;
import com.blq.zzc.practice.ui.fragment.YiYuanXinWenFragment;
import com.blq.zzc.practice.ui.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
public class MainActivity extends LazyActivity implements NavigationView.OnNavigationItemSelectedListener{
	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}
	private List<Fragment> fragmentList=new ArrayList<>();
	private int priviousIndex;
	@Override
	public void initViews(Bundle savedInstanceState) {
		fragmentList.add(new MainFragment());
		fragmentList.add(new YiYuanXinWenFragment());
		fragmentList.add(new YYFragment());
		FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.main_frame_layout,fragmentList.get(0)).commit();
		InitNavigationView();
	}
	@Bind(R.id.navigationView)
	NavigationView navigationView;
	private void InitNavigationView() {
		navigationView.setNavigationItemSelectedListener(this);
		View headerView=navigationView.inflateHeaderView(R.layout.nav_header_main);
		CircleImageView circleImageView= (CircleImageView) headerView.findViewById(R.id.nav_head_avatar);
		navigationView.inflateMenu(R.menu.menu_nav);
	}

	@Bind(R.id.toolbar)
	Toolbar toolbar;
	@Bind(R.id.drawerLayout)
	DrawerLayout drawerLayout;
	@Override
	public void initToolBar() {
		setSupportActionBar(toolbar);
		ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
		drawerLayout.addDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();
	}
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav_home: {
				fragmentToTransaction(0);
				return true;
			}
			case R.id.nav_yiyuanxinwen: {
				fragmentToTransaction(1);
				return true;
			}
			case R.id.nav_yy: {
				fragmentToTransaction(2);
				return true;
			}
			default:return false;
		}
	}
	private void fragmentToTransaction(int index){
		FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
		fragmentTransaction.hide(fragmentList.get(priviousIndex));
		if (!fragmentList.get(index).isAdded()) fragmentTransaction.add(R.id.main_frame_layout,fragmentList.get(index));
		fragmentTransaction.show(fragmentList.get(index));
		fragmentTransaction.commit();
		priviousIndex=index;
	}
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */

	/**
	 * Called when the activity is first created.
	 */

}