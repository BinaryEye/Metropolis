package com.metropolis.sideMenu.AuctionHouse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.views.ItemView;
import com.metropolis.views.PostView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SellFragment extends Fragment {
	View rootView;
	LinearLayout layout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_sell, container, false);
		layout = (LinearLayout) rootView.findViewById(R.id.itemsContainer);
		getDataFromServer();
		return rootView;
	}

	public void getDataFromServer() {
		GetRequest postDataRequest = new GetRequest(config.APIRARY_URL
				+ "/metropolis/123/items") {
			protected void onPostExecute(String response) {
				if (this.getStatusCode() == 200) {
					setData(response);
				} else {
					Toast.makeText(getActivity(), "Check Your Connection",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		postDataRequest.execute();
	}

	public void setData(String reponse) {
		JSONObject json;
		try {
			json = new JSONObject(reponse);
			JSONArray items = json.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);
				JSONArray stats = item.getJSONArray("stats");
				String[] statsArray = new String[stats.length()];
				for (int j = 0; j < statsArray.length; j++) {
					statsArray[j] = stats.getString(j);
				}
				ItemView newItem = new ItemView(getActivity());
				//newItem.setData(item.getString("itemname"), statsArray,
				//		item.getString("price"), "Sell Item");
				layout.addView(newItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
