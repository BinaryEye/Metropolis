package com.metropolis.sideMenu.AuctionHouse;


import com.metropolis.R;
import com.metropolis.views.ItemView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SellFragment extends Fragment {
	View rootView;
	LinearLayout layout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_sell, container, false);
		layout = (LinearLayout) rootView.findViewById(R.id.itemsContainer);

        for (int i = 0; i < 5; i++) {
            ItemView item = new ItemView(getActivity());
			item.setData("Awesome Armour", "Armor=100 \n Health=300", "Sell Item");
			layout.addView(item);
        }

		return rootView;
	}

//	public void setData(String reponse) {
//		JSONObject json;
//		try {
//			json = new JSONObject(reponse);
//			JSONArray items = json.getJSONArray("items");
//			for (int i = 0; i < items.length(); i++) {
//				JSONObject item = items.getJSONObject(i);
//				JSONArray stats = item.getJSONArray("stats");
//				String[] statsArray = new String[stats.length()];
//				for (int j = 0; j < statsArray.length; j++) {
//					statsArray[j] = stats.getString(j);
//				}
//				ItemView newItem = new ItemView(getActivity());
//				layout.addView(newItem);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

}
