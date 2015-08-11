package com.metropolis.sideMenu.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.views.PostView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DashboardFragment extends Fragment {
	View rootView;
	LinearLayout layout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_dashboard, container,
				false);
		layout = (LinearLayout) rootView.findViewById(R.id.dashboardContainer);
		getDataFromServer();
		return rootView;
	}

	public void getDataFromServer() {
		GetRequest postDataRequest = new GetRequest(config.APIRARY_URL
				+ "/metropolis/123/dashboard") {
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
		try {
			JSONObject json = new JSONObject(reponse);
			JSONArray posts = json.getJSONArray("posts");
			for (int i = 0; i < posts.length(); i++) {
				JSONObject post = posts.getJSONObject(i);
				PostView view = new PostView(getActivity());
				view.setData(post.getString("username"),
						post.getString("timestamp"), post.getString("message"), post.getInt("id"));
				layout.addView(view);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}