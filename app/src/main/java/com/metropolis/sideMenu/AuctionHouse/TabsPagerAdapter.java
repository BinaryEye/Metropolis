package com.metropolis.sideMenu.AuctionHouse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new BuyFragment();
		case 1:
			return new SellFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}
}
