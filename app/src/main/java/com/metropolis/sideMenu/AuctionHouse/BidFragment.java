package com.metropolis.sideMenu.AuctionHouse;

import com.metropolis.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BidFragment extends Fragment {

	LinearLayout layout;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.bid_fragment, container, false);
		layout = (LinearLayout) rootView.findViewById(R.id.bidContainer);
		return rootView;
	}
	
}
