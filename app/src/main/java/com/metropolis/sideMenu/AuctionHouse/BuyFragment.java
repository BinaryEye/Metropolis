package com.metropolis.sideMenu.AuctionHouse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.views.ItemView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BuyFragment extends Fragment {
	View rootView;
	EditText searchBox;
	LinearLayout layout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.buy_fragment, container, false);
		layout = (LinearLayout) rootView
				.findViewById(R.id.buyFragmentContainer);
		searchBox = (EditText) rootView.findViewById(R.id.searchBox);
		Button search = (Button) rootView.findViewById(R.id.searchButton);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				layout.removeAllViewsInLayout();
				String itemName = searchBox.getText().toString();
				for(int i = 0;i<5;i++) {
					ItemView item = new ItemView(getActivity());
					item.setData(itemName, "Armor:100%, Health:100%", "Buy Item");
					layout.addView(item);
				}
			}
		});
		return rootView;
	}
}
