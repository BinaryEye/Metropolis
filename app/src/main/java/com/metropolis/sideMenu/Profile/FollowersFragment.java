package com.metropolis.sideMenu.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.views.FollowerView;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FollowersFragment extends Fragment {
	View rootView;
	LinearLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_followers, container, false);
		layout = (LinearLayout) rootView.findViewById(R.id.followersContainer);
		getDataFromServer();
		return rootView;
	}

	public void getDataFromServer() {
		GetRequest postDataRequest = new GetRequest(config.APIRARY_URL
				+ "/metropolis/123/followers") {
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

	public void setData(String response) {
		try {
			JSONObject json = new JSONObject(response);
			JSONArray followers = json.getJSONArray("followers");
			for (int i = 0; i < followers.length(); i++) {
				JSONObject post = followers.getJSONObject(i);
				FollowerView view = new FollowerView(getActivity());
				view.setData(post.getString("username"));
				layout.addView(view);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
