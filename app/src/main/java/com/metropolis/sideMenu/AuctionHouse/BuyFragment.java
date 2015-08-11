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
				search(arg0);

			}
		});
		return rootView;
	}

	public void search(View v) {
		layout.removeAllViewsInLayout();
		String itemName = searchBox.getText().toString();
		String[] nameParts = itemName.split(" ");
		itemName = "";
		for (int i = 0; i < nameParts.length; i++) {
			itemName += nameParts[i].toLowerCase();
		}
		getDataFromServer(itemName);
	}

	public void getDataFromServer(String itemName) {
		GetRequest postDataRequest = new GetRequest(config.LOCAL_HOST_URL
				+ "/api/items/show?name=" + itemName) {
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
		JSONArray json;
		try {
			json = new JSONObject(reponse).getJSONArray("items");
			JSONObject jsonObject = json.getJSONObject(0);
			String stats = jsonObject.getString("stats");
			ItemView item = new ItemView(getActivity());
			item.setData(jsonObject.getString("name"), stats, "Buy Item");
			layout.addView(item);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
